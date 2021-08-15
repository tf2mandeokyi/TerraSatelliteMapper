package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.TileImageData;
import com.mndk.tppstlmapper.tile.TileImagePos;
import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.projection.TileServerProjection;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public abstract class TileServer {

    private final TileServerProjection projection;
    private final TilePosToUrlFunction urlFunction;
    private final ExecutorService executorService;

    public TileServer(TileServerProjection projection, TilePosToUrlFunction urlFunction, int maximumConcurrentRequests) {
        this.projection = projection;
        this.urlFunction = urlFunction;
        this.executorService = Executors.newFixedThreadPool(maximumConcurrentRequests);
    }

    private static final String USER_AGENT;

    public CompletableFuture<TileImageData> fetch(TileImagePos pos) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = this.urlFunction.get(pos);
                if("file".equals(url.getProtocol())) {
                    return new TileImageData(pos, ImageIO.read(url));
                }
                else {
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestProperty("user-agent", USER_AGENT);
                    return new TileImageData(pos, ImageIO.read(connection.getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }, this.executorService);
    }

    public CompletableFuture<TileImageData> fetch(double lon, double lat, int zoom) {
        return this.fetch(this.projection.toTileCoordinates(lon, lat, zoom));
    }

    static {
        USER_AGENT =
            System.getProperty("os.name") + " / " + System.getProperty("os.version") + " / " + System.getProperty("os.arch");
        System.out.println(USER_AGENT);
    }

}
