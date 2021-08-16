package com.mndk.tppstlmapper.tile;

import com.mndk.tppstlmapper.tile.server.TileServer;
import com.mndk.tppstlmapper.util.MathUtils;
import com.mndk.tppstlmapper.util.RGBColorDouble;
import lombok.Getter;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

@Getter
public class TileImageData {

    private final TilePosition pos;
    private final BufferedImage image;
    private final int width, height;
    private final TileServer parent;

    public TileImageData(TilePosition pos, BufferedImage image, TileServer parent) {
        this.pos = pos;
        this.image = image;
        this.width = image.getWidth(); this.height = image.getHeight();
        this.parent = parent;
    }

    /**
     * Returns the corresponding RGB color from an image
     * @param x The x coordinate of the image (0 ~ 1)
     * @param y The y coordinate of the image (0 ~ 1)
     * @return The RGB value
     */
    public RGBColorDouble getRGB_old(double x, double y) {
        if(isOutside(x, y)) return null;
        int pixelX = (int) Math.min(x * image.getWidth(), image.getWidth() - 1);
        int pixelY = (int) Math.min(y * image.getHeight(), image.getHeight() - 1);
        return new RGBColorDouble(image.getRGB(pixelX, pixelY));
    }

    /**
     * Returns the corresponding RGB color from an image
     * @param x The x coordinate of the image (0 ~ 1)
     * @param y The y coordinate of the image (0 ~ 1)
     * @return The RGB value
     */
    public RGBColorDouble getRGB_bicubic(double x, double y) {
        if(isOutside(x, y)) return null;
        /*
         *  (tx-1, ty-1)   (tx, ty-1) (tx+1, ty-1) (tx+2, ty-1)
         *       |            |            |            |
         *       |            |            |            |
         *       |            |            |            |
         *       |            |            |            |
         *  (tx-1, ty)     (tx, ty)   (tx+1, ty)   (tx+2, ty)
         *       |            |            |            |
         *       *p0----------*p1----*-----*p2----------*p3
         *       |            |    (x,y)   |            |
         *       |            |            |            |
         *  (tx-1, ty+1)   (tx, ty+1) (tx+1, ty+1) (tx+2, ty+1)
         *       |            |            |            |
         *       |            |            |            |
         *       |            |            |            |
         *       |            |            |            |
         *  (tx-1, ty+2)   (tx, ty+2) (tx+1, ty+2) (tx+2, ty+2)
         */
        x *= width; y *= height;

        int tx = (int) Math.floor(MathUtils.bound(x, 0, width - 2));
        int ty = (int) Math.floor(MathUtils.bound(y, 0, height - 2));
        double dx = x - tx, dy = y - ty;

        RGBColorDouble[] p = new RGBColorDouble[4];
        for(int jj = 0; jj < 4; ++jj) {
            int txjj = tx + jj - 1;
            if(txjj < 0 || txjj > width - 1) {
                p[jj] = null;
            }
            else {
                p[jj] = rgbInterpolation(
                        ty < 1 ? null : image.getRGB(txjj, ty - 1),
                        image.getRGB(txjj, ty),
                        image.getRGB(txjj, ty + 1),
                        ty >= height - 2 ? null : image.getRGB(txjj, ty + 2),
                        dy
                );
            }
        }

        return rgbInterpolation(p[0], p[1], p[2], p[3], dx);
    }


    private static RGBColorDouble rgbInterpolation(
            Integer rgb0, @Nonnull Integer rgb1, @Nonnull Integer rgb2, Integer rgb3, double t) {

        double r = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : (rgb0 & 0xff0000) >> 16,
                (rgb1 & 0xff0000) >> 16,
                (rgb2 & 0xff0000) >> 16,
                rgb3 == null ? Double.NaN : (rgb3 & 0xff0000) >> 16,
                t
        );
        double g = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : (rgb0 & 0x00ff00) >> 8,
                (rgb1 & 0x00ff00) >> 8,
                (rgb2 & 0x00ff00) >> 8,
                rgb3 == null ? Double.NaN : (rgb3 & 0x00ff00) >> 8,
                t
        );
        double b = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : rgb0 & 0x0000ff,
                rgb1 & 0x0000ff,
                rgb2 & 0x0000ff,
                rgb3 == null ? Double.NaN : rgb3 & 0x0000ff,
                t
        );
        return new RGBColorDouble(r, g, b);
    }


    private static RGBColorDouble rgbInterpolation(
            RGBColorDouble rgb0, @Nonnull RGBColorDouble rgb1, @Nonnull RGBColorDouble rgb2, RGBColorDouble rgb3, double t) {

        double r = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : rgb0.red,
                rgb1.red,
                rgb2.red,
                rgb3 == null ? Double.NaN : rgb3.red,
                t
        );
        double g = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : rgb0.green,
                rgb1.green,
                rgb2.green,
                rgb3 == null ? Double.NaN : rgb3.green,
                t
        );
        double b = MathUtils.cubicInterpolation(
                rgb0 == null ? Double.NaN : rgb0.blue,
                rgb1.blue,
                rgb2.blue,
                rgb3 == null ? Double.NaN : rgb3.blue,
                t
        );
        return new RGBColorDouble(r, g, b);
    }


    public RGBColorDouble getRGBByGeoCoordinate(double lon, double lat) throws OutOfProjectionBoundsException {
        double[] coordinates = parent.getProjection().toDoubleTileCoordinates(lon, lat, pos.zoom);
        double x = coordinates[0] - pos.x, y = coordinates[1] - pos.y;
        return getRGB_bicubic(x, y);
    }

    public boolean isOutside(double x, double y) {
        return x < 0 || x > 1 || y < 0 || y > 1;
    }

}
