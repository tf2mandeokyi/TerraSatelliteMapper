package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.projection.WebMercatorTileProjection;

import java.net.URL;

public class OpenStreetMapTileServer extends TileServer {

    public OpenStreetMapTileServer() {
        super(new WebMercatorTileProjection(), tilePos ->
                new URL("https://tile.openstreetmap.org/"
                        + tilePos.zoom + "/" + tilePos.x + "/" + tilePos.y + ".png"),
                1);
    }
}
