package com.mndk.tppstlmapper.tile.projection;

import com.mndk.tppstlmapper.tile.TileImagePos;
import lombok.RequiredArgsConstructor;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;

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
                (int) Math.floor(factor * (lon + 180) / 360),
                (int) Math.floor(factor * (1 - a) / 2),
                zoom
        );
    }

    @Override
    public double[] toDoubleTileCoordinates(double lon, double lat, int zoom) {
        double factor = Math.pow(2, zoom);
        double a = Math.log(Math.tan(Math.toRadians(lat)) + (1 / Math.cos(Math.toRadians(lat)))) / Math.PI;
        if(invertLatitude) a = -a;
        return new double[]{
                factor * (lon + 180) / 360,
                factor * (1 - a) / 2
        };
    }

    @Override
    public TileImagePos[] getAllIntersecting(Bounds2d bounds2d, int zoom) {
        TileImagePos t1 = this.toTileCoordinates(bounds2d.minX(), bounds2d.minZ(), zoom);
        TileImagePos t2 = this.toTileCoordinates(bounds2d.maxX(), bounds2d.maxZ(), zoom);
        int minX = Math.min(t1.x, t2.x), maxX = Math.max(t1.x, t2.x);
        int minY = Math.min(t1.y, t2.y), maxY = Math.max(t1.y, t2.y);
        int width = maxX - minX + 1, height = maxY - minY + 1;
        TileImagePos[] result = new TileImagePos[width * height];
        for(int y = minY, dy = 0; y <= maxY; ++y, ++dy) {
            for(int x = minX, dx = 0; x <= maxX; ++x, ++dx) {
                result[dy * width + dx] = new TileImagePos(x, y, zoom);
            }
        }
        return result;
    }
}
