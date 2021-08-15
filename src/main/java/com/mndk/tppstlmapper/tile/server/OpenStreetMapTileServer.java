package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.projection.WebMercatorTileProjection;

import java.net.URL;

public class OpenStreetMapTileServer extends TileServer {

    private static final TilePosToUrlFunction function = tilePos ->
            new URL("https://tile.openstreetmap.org/" + tilePos.zoom + "/" + tilePos.x + "/" + tilePos.y + ".png");

    public OpenStreetMapTileServer() {
        super(new WebMercatorTileProjection(), function, 2);
    }
}
