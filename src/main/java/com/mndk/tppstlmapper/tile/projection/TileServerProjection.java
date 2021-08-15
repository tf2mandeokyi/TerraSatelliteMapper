package com.mndk.tppstlmapper.tile.projection;

import com.mndk.tppstlmapper.tile.TileImagePos;

public interface TileServerProjection {

    double[] toGeoCoordinates(final TileImagePos pos);

    TileImagePos toTileCoordinates(double lon, double lat, int zoom);

}
