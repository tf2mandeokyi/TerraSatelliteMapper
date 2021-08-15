package com.mndk.tppstlmapper.util;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MemoryCache<Key, Type> {

    public static final int DEFAULT_SIZE = 10000;

    private final Map<Key, Map.Entry<Long, Type>> cacheMap;
    private final int cacheSize;

    public MemoryCache(int cacheSize) {
        this.cacheMap = new HashMap<>();
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
            if(cacheMap.containsKey(key)) {
                // Cache hit
                Type result = cacheMap.get(key).getValue();
                cacheMap.put(key, new AbstractMap.SimpleEntry<>(System.currentTimeMillis(), result));
                return result;
            }
            else {
                // Cache miss
                if (fallback == null) return null;
                return this.cache(key, fallback.get());
            }
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
            if(cacheMap.containsKey(key)) {
                // Cache hit
                Type result = cacheMap.get(key).getValue();
                cacheMap.put(key, new AbstractMap.SimpleEntry<>(System.currentTimeMillis(), result));
                return CompletableFuture.supplyAsync(() -> result);
            }
            else {
                // Cache miss
                if (fallback == null) return null;
                return fallback.get().thenApply(value -> this.cache(key, value));
            }
        }
    }

    private Type cache(@Nonnull Key key, Type value) {
        synchronized (this) {
            if(value == null) return null;
            cacheMap.put(key, new AbstractMap.SimpleEntry<>(System.currentTimeMillis(), value));
            if (cacheMap.size() > cacheSize) {
                this.removeTheOldestOne();
            }
            return value;
        }
    }

    private void removeTheOldestOne() {
        synchronized (this) {
            long oldest = Long.MAX_VALUE;
            Key oldestKey = null;
            for (Map.Entry<Key, Map.Entry<Long, Type>> entry : cacheMap.entrySet()) {
                long time = entry.getValue().getKey();
                if (time < oldest) {
                    oldest = time;
                    oldestKey = entry.getKey();
                }
            }
            if (oldestKey != null) {
                cacheMap.remove(oldestKey);
            }
        }
    }

}
