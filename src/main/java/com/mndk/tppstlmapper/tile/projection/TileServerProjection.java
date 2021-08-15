package com.mndk.tppstlmapper.tile.projection;

import com.mndk.tppstlmapper.tile.TilePosition;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;

public interface TileServerProjection {

    double[] toGeoCoordinates(final TilePosition pos) throws OutOfProjectionBoundsException;

    TilePosition toTileCoordinates(double lon, double lat, int zoom) throws OutOfProjectionBoundsException;
    double[] toDoubleTileCoordinates(double lon, double lat, int zoom) throws OutOfProjectionBoundsException;

    TilePosition[] getAllIntersecting(Bounds2d bounds2d, int zoom) throws OutOfProjectionBoundsException;

}
