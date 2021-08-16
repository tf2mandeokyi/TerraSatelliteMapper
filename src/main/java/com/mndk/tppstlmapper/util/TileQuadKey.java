package com.mndk.tppstlmapper.util;

import com.mndk.tppstlmapper.tile.TilePosition;

public class TileQuadKey {

    public static String toQuadKey(TilePosition tilePos) {
        StringBuilder quadKey = new StringBuilder();
        for (int i = tilePos.zoom; i > 0; i--) {
            char digit = '0';
            int mask = 1 << (i - 1);
            if ((tilePos.x & mask) != 0) digit++;
            if ((tilePos.y & mask) != 0) digit+=2;
            quadKey.append(digit);
        }
        return quadKey.toString();
    }
}
