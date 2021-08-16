package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.TileImageData;
import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.TilePosition;
import com.mndk.tppstlmapper.tile.projection.TileServerProjection;
import com.mndk.tppstlmapper.util.MemoryCache;
import io.netty.buffer.ByteBufInputStream;
import lombok.Getter;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;
import net.buildtheearth.terraplusplus.util.http.Http;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class TileServer {


    private final TileServerProjection projection;
    private final TilePosToUrlFunction urlFunction;
    private final ExecutorService executorService;
    private final MemoryCache<TilePosition, BufferedImage> cache;


    public TileServer(TileServerProjection projection, TilePosToUrlFunction urlFunction, int maximumConcurrentRequests) {
        this.projection = projection;
        this.urlFunction = urlFunction;
        this.executorService = Executors.newFixedThreadPool(maximumConcurrentRequests);
        this.cache = new MemoryCache<>();
        try {
            Http.setMaximumConcurrentRequestsTo(
                    this.urlFunction.get(new TilePosition(0, 0, 0)).toString(), maximumConcurrentRequests);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public CompletableFuture<TileImageData> fetch(TilePosition pos) {
        synchronized (this.cache) {
            String url;
            try {
                url = this.urlFunction.get(pos).toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            return CompletableFuture.supplyAsync(() -> {
                BufferedImage image = cache.get(pos, () -> {
                    try {
                        ByteBufInputStream stream = new ByteBufInputStream(Http.get(url).get());
                        return ImageIO.read(stream);
                    } catch (IOException | ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                return new TileImageData(pos, image, this);
            }, this.executorService);
        }
    }


    public CompletableFuture<TileImageData> fetch(double lon, double lat, int zoom) throws OutOfProjectionBoundsException {
        return this.fetch(this.projection.toTileCoordinates(lon, lat, zoom));
    }


    public CompletableFuture<TileImageData[]> fetchAllAsync(Bounds2d bounds2d, int zoom) throws OutOfProjectionBoundsException {
        TilePosition[] posList = this.projection.getAllIntersecting(bounds2d, zoom);
        List<CompletableFuture<TileImageData>> futures = new ArrayList<>();
        for (TilePosition pos : posList) {
            CompletableFuture<TileImageData> future = fetch(pos);
            if(future != null) futures.add(fetch(pos));
        }
        CompletableFuture<?>[] cfs = futures.toArray(new CompletableFuture[0]);
        return CompletableFuture.allOf(cfs).thenApply(ignored -> futures.stream()
                .map(CompletableFuture::join)
                .toArray(TileImageData[]::new));
    }

}
