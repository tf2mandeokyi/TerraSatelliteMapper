package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.TileImageData;
import com.mndk.tppstlmapper.tile.TileImagePos;
import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.projection.TileServerProjection;
import com.mndk.tppstlmapper.util.MemoryCache;
import lombok.Getter;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;
import net.buildtheearth.terraplusplus.util.http.Http;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class TileServer {


    private final TileServerProjection projection;
    private final TilePosToUrlFunction urlFunction;
    private final ExecutorService executorService;
    private final MemoryCache<TileImagePos, BufferedImage> cache;


    private static final String USER_AGENT;


    public TileServer(TileServerProjection projection, TilePosToUrlFunction urlFunction, int maximumConcurrentRequests) {
        this.projection = projection;
        this.urlFunction = urlFunction;
        this.executorService = Executors.newFixedThreadPool(maximumConcurrentRequests);
        this.cache = new MemoryCache<>();
        try {
            Http.setMaximumConcurrentRequestsTo(
                    this.urlFunction.get(new TileImagePos(0, 0, 0)).toString(), maximumConcurrentRequests);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public CompletableFuture<TileImageData> fetch(TileImagePos pos) {
        synchronized (this) {
            String url;
            try {
                url = this.urlFunction.get(pos).toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            return cache.getAsync(pos, () -> Http.get(url).thenApply(byteBuf -> {
                try {
                    byte[] bytes = new byte[byteBuf.readableBytes()];
                    byteBuf.readBytes(bytes);
                    return ImageIO.read(new ByteArrayInputStream(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            })).thenApply(image -> new TileImageData(pos, image, this));
        }
    }


    public CompletableFuture<TileImageData> fetch(double lon, double lat, int zoom) throws OutOfProjectionBoundsException {
        return this.fetch(this.projection.toTileCoordinates(lon, lat, zoom));
    }


    public CompletableFuture<TileImageData[]> fetchAllAsync(Bounds2d bounds2d, int zoom) throws OutOfProjectionBoundsException {
        TileImagePos[] posList = this.projection.getAllIntersecting(bounds2d, zoom);
        List<CompletableFuture<TileImageData>> futures = new ArrayList<>();
        for (TileImagePos pos : posList) {
            CompletableFuture<TileImageData> future = fetch(pos);
            if(future != null) futures.add(fetch(pos));
        }
        CompletableFuture<?>[] cfs = futures.toArray(new CompletableFuture[0]);
        return CompletableFuture.allOf(cfs).thenApply(ignored -> futures.stream()
                .map(CompletableFuture::join)
                .toArray(TileImageData[]::new));
    }

    static {
        USER_AGENT =
            System.getProperty("os.name") + " / " + System.getProperty("os.version") + " / " + System.getProperty("os.arch");
        System.out.println(USER_AGENT);
    }

}
