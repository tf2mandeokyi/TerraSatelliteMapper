package com.mndk.tppstlmapper.tile;

import com.mndk.tppstlmapper.tile.server.TileServer;
import com.mndk.tppstlmapper.util.RGBColorDouble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;

import java.awt.image.BufferedImage;

@AllArgsConstructor
@Getter
public class TileImageData {

    private final TilePosition pos;
    private final BufferedImage image;
    private final TileServer parent;

    /**
     * Returns the corresponding RGB color from an image
     * @param x The x coordinate of the image (0 ~ 1)
     * @param y The y coordinate of the image (0 ~ 1)
     * @return The RGB value
     */
    public RGBColorDouble getRGB(double x, double y) {
        if(!isInside(x, y)) return null;
        int pixelX = (int) Math.min(x * image.getWidth(), image.getWidth() - 1);
        int pixelY = (int) Math.min(y * image.getHeight(), image.getHeight() - 1);
        return new RGBColorDouble(image.getRGB(pixelX, pixelY));
    }

    public RGBColorDouble getRGBByGeoCoordinate(double lon, double lat) throws OutOfProjectionBoundsException {
        double[] coordinates = parent.getProjection().toDoubleTileCoordinates(lon, lat, pos.zoom);
        double x = coordinates[0] - pos.x, y = coordinates[1] - pos.y;
        return getRGB(x, y);
    }

    public boolean isInside(double x, double y) {
        return x >= 0 && x <= 1 && y >= 0 && y <= 1;
    }

}
