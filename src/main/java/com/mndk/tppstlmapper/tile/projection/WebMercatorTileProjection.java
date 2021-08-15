package com.mndk.tppstlmapper.tile.projection;

import com.mndk.tppstlmapper.tile.TileImagePos;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebMercatorTileProjection implements TileServerProjection {

    public final boolean invertLatitude;

    public WebMercatorTileProjection() {
        this(false);
    }

    @Override
    public double[] toGeoCoordinates(TileImagePos pos) {
        double divisor = Math.pow(2, pos.zoom);
        double lat_rad = Math.atan(Math.sinh(Math.PI - (2.0 * Math.PI * pos.y) / divisor));
        if(invertLatitude) lat_rad = -lat_rad;
        return new double[] {
                (pos.x * 360 / divisor) - 180,
                Math.toDegrees(lat_rad)
        };
    }

    @Override
    public TileImagePos toTileCoordinates(double lon, double lat, int zoom) {
        double factor = Math.pow(2, zoom);
        double a = Math.log(Math.tan(Math.toRadians(lat)) + (1 / Math.cos(Math.toRadians(lat)))) / Math.PI;
        if(invertLatitude) a = -a;
        return new TileImagePos(
                (int) Math.floor(factor * (lon + 180) / 360), (int) Math.floor(factor * (1 - a) / 2), zoom);
    }
}
