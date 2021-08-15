package com.mndk.tppstlmapper.tile.server;

import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.projection.WebMercatorTileProjection;

import java.net.URL;

public class BingMapsTileServer extends TileServer {

    private static final TilePosToUrlFunction function = tilePos -> {
        String quadKey = "";
        for (int i = tilePos.zoom; i > 0; i--) {
            char digit = '0';
            int mask = 1 << (i - 1);
            if ((tilePos.x & mask) != 0) digit++;
            if ((tilePos.y & mask) != 0) digit+=2;
            quadKey += digit;
        }
        return new URL("https://t.ssl.ak.dynamic.tiles.virtualearth.net/comp/ch/" + quadKey + "?it=A&shading=hill");
    };

    public BingMapsTileServer() {
        super(new WebMercatorTileProjection(), function, 2);
    }
}
