package com.mndk.tppstlmapper.tile;

import com.mndk.tppstlmapper.util.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

@AllArgsConstructor
@Getter
public class TileImageData {

    private final TileImagePos pos;
    private final BufferedImage image;

    /**
     * Returns the corresponding RGB color from an image
     * @param x The x coordinate of the image (0 ~ 1)
     * @param y The y coordinate of the image (0 ~ 1)
     * @return The RGB value
     */
    public int getRGB(double x, double y) {
        x = MathUtils.bound(x, 0, 1);
        y = MathUtils.bound(y, 0, 1);
        int pixelX = (int) Math.min(x * image.getWidth(), image.getWidth() - 1);
        int pixelY = (int) Math.min(y * image.getHeight(), image.getHeight() - 1);
        return image.getRGB(pixelX, pixelY);
    }

}
