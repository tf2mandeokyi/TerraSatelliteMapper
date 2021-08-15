package com.mndk.tppstlmapper.util;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MemoryCache<Key, Type> {

    public static final int DEFAULT_SIZE = 10000;

    private final List<Map.Entry<Key, Type>> cacheList;
    private final int cacheSize;

    public MemoryCache(int cacheSize) {
        this.cacheList = new ArrayList<>();
        this.cacheSize = cacheSize;
    }

    public MemoryCache() {
        this(DEFAULT_SIZE);
    }

    public Type get(@Nonnull Key key, Type fallback) {
        return this.get(key, () -> fallback);
    }

    public Type get(@Nonnull Key key, Supplier<Type> fallback) {
        synchronized (this) {
            for (int i = 0; i < cacheList.size(); ++i) {
                Map.Entry<Key, Type> entry = cacheList.get(i);
                if(key.equals(entry.getKey())) {
                    // Cache hit
                    cacheList.remove(i);
                    cacheList.add(0, entry);
                    return entry.getValue();
                }
            }
            // Cache miss
            if (fallback == null) return null;
            return this.cache(key, fallback.get());
        }
    }

    /*
    * I'm using Supplier<CompletableFuture<Type>> here instead of CompletableFuture<Type>, because
    * if you use CompletableFuture<T>, and call CompletableFuture.supplyAsync(), the async function will be
    * called regardless of cache hit or cache miss.
    * That's not good, but if you use Supplier<CompletableFuture<T>> instead, the issue won't happen because
    * the async function will be sealed from being called.
    * */
    public CompletableFuture<Type> getAsync(@Nonnull Key key, Supplier<CompletableFuture<Type>> fallback) {
        synchronized (this) {
            for (int i = 0; i < cacheList.size(); ++i) {
                Map.Entry<Key, Type> entry = cacheList.get(i);
                if(key.equals(entry.getKey())) {
                    // Cache hit
                    cacheList.remove(i);
                    cacheList.add(0, entry);
                    return CompletableFuture.supplyAsync(entry::getValue);
                }
            }
            // Cache miss
            if (fallback == null) return null;
            return fallback.get().thenApply(value -> this.cache(key, value));
        }
    }

    private Type cache(@Nonnull Key key, Type value) {
        synchronized (this) {
            if(value == null) return null;
            cacheList.add(0, new AbstractMap.SimpleEntry<>(key, value));
            if (cacheList.size() > cacheSize) {
                cacheList.remove(cacheSize - 1);
            }
            return value;
        }
    }

}
