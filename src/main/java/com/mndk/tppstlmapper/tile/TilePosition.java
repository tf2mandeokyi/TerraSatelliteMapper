package com.mndk.tppstlmapper.tile;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@RequiredArgsConstructor
@ToString

public class TilePosition {
    public final int x, y, zoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TilePosition that = (TilePosition) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return zoom == that.zoom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, zoom);
    }
}
