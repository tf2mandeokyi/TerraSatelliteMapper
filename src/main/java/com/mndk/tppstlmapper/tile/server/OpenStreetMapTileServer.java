package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.projection.WebMercatorTileProjection;

import java.net.URL;
import java.util.Random;

public class OpenStreetMapTileServer extends TileServer {

    private static final String[] abc = new String[] { "a", "b", "c" };
    private static final Random RANDOM = new Random();

    public OpenStreetMapTileServer() {
        super(new WebMercatorTileProjection(), tilePos -> {
            synchronized (RANDOM) {
                return new URL("https://" + abc[RANDOM.nextInt(3)] + ".tile.openstreetmap.org/" +
                        tilePos.zoom + "/" + tilePos.x + "/" + tilePos.y + ".png");
            }
        }, 1);
    }
}
