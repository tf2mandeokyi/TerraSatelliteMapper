package com.mndk.tppstlmapper.tile;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString

public class TileImagePos {
    public final int x, y, zoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileImagePos that = (TileImagePos) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return zoom == that.zoom;
    }
}
