package com.mndk.tppstlmapper.block;

import com.mndk.tppstlmapper.TerraSatelliteMapperMod;
import com.mndk.tppstlmapper.util.MathUtils;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class RGBColorToBlock {

    private static final Map<RGBColorDouble, Map.Entry<IBlockState, BufferedImage>> blockColorMappings = new HashMap<>();

    public static void printAllStates() {
        for (Block block : Block.REGISTRY) {
            TerraSatelliteMapperMod.logger.info(block.getRegistryName());
            IBlockState defaultState = block.getDefaultState();
            if(defaultState.isBlockNormalCube()) {
                Collection<IProperty<?>> properties = defaultState.getPropertyKeys();
                for (IProperty<?> property : properties) {
                    TerraSatelliteMapperMod.logger.info(
                            " ㅏ " + property.getName() + " (" + property.getClass().getName() + "=" + property.getValueClass().getName() + "): [");
                    for(Object value : property.getAllowedValues()) {
                        TerraSatelliteMapperMod.logger.info(" ㅣ       " + value);
                    }
                    TerraSatelliteMapperMod.logger.info(" ㅣ ]");
                }
            }
        }
    }

    public static void init(FMLInitializationEvent event) {
        // printAllStates();
        registerBlockProperties(Blocks.STONE, BlockStone.VARIANT);
        registerBlock(Blocks.GRASS, BlockGrass.SNOWY, false);
        registerBlockProperties(Blocks.DIRT, BlockDirt.VARIANT);
        registerBlock(Blocks.COBBLESTONE);
        registerBlockProperties(Blocks.PLANKS, BlockPlanks.VARIANT);
        registerBlock(Blocks.BEDROCK);
        registerBlockProperties(Blocks.SAND, BlockSand.VARIANT);
        registerBlock(Blocks.GRAVEL);
        registerBlock(Blocks.GOLD_ORE);
        registerBlock(Blocks.IRON_ORE);
        registerBlock(Blocks.COAL_ORE);
        registerBlockProperties(Blocks.LOG, BlockLog.AXIS, BlockPlanks.VARIANT);
        // registerBlockProperties(Blocks.LEAVES, BlockLeaves.CHECK_DECAY, false, BlockLeaves.DECAYABLE,
        //         false, BlockPlanks.VARIANT);
        registerBlock(Blocks.LAPIS_ORE);
        registerBlock(Blocks.LAPIS_BLOCK);
        registerBlockProperties(Blocks.DISPENSER, BlockDispenser.TRIGGERED, false, BlockDispenser.FACING);
        registerBlockProperties(Blocks.SANDSTONE, BlockSandStone.TYPE);
        registerBlock(Blocks.NOTEBLOCK);
        registerBlockProperties(Blocks.WOOL, BlockColored.COLOR);
        registerBlockProperties(Blocks.DOUBLE_STONE_SLAB, BlockStoneSlab.VARIANT);
        registerBlock(Blocks.BRICK_BLOCK);
        registerBlock(Blocks.OBSIDIAN);
        registerBlock(Blocks.DIAMOND_ORE);
        registerBlock(Blocks.DIAMOND_BLOCK);
        registerBlock(Blocks.CRAFTING_TABLE);
        registerBlock(Blocks.FURNACE, BlockFurnace.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.REDSTONE_ORE);
        registerBlock(Blocks.ICE);
        registerBlock(Blocks.SNOW);
        registerBlock(Blocks.CLAY);
        registerBlock(Blocks.JUKEBOX, BlockJukebox.HAS_RECORD, false);
        registerBlock(Blocks.PUMPKIN, BlockPumpkin.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.NETHERRACK);
        registerBlock(Blocks.SOUL_SAND);
        registerBlock(Blocks.GLOWSTONE);
        registerBlockProperties(Blocks.STONEBRICK, BlockStoneBrick.VARIANT);
        registerBlockProperties(Blocks.BROWN_MUSHROOM_BLOCK, BlockHugeMushroom.VARIANT);
        registerBlockProperties(Blocks.RED_MUSHROOM_BLOCK, BlockHugeMushroom.VARIANT);
        registerBlock(Blocks.MELON_BLOCK);
        registerBlock(Blocks.MYCELIUM, BlockMycelium.SNOWY, false);
        registerBlock(Blocks.NETHER_BRICK);
        // registerBlock(Blocks.NETHER_WART);
        // registerBlock(Blocks.ENCHANTING_TABLE);
        registerBlock(Blocks.EMERALD_ORE);
        registerBlock(Blocks.EMERALD_BLOCK);
        // registerBlock(Blocks.COMMAND_BLOCK, BlockCommandBlock.CONDITIONAL, false, BlockCommandBlock.FACING,
        //         EnumFacing.UP);
        // registerBlock(Blocks.BEACON);
        registerBlock(Blocks.REDSTONE_BLOCK);
        registerBlock(Blocks.QUARTZ_ORE);
        registerBlockProperties(Blocks.QUARTZ_BLOCK, BlockQuartz.VARIANT);
        registerBlockProperties(Blocks.STAINED_HARDENED_CLAY, BlockStainedHardenedClay.COLOR);
        // registerBlock(Blocks.SLIME_BLOCK);
        registerBlockProperties(Blocks.PRISMARINE, BlockPrismarine.VARIANT);
        registerBlock(Blocks.SEA_LANTERN);
        registerBlockProperties(Blocks.HAY_BLOCK, BlockHay.AXIS);
        registerBlock(Blocks.HARDENED_CLAY);
        registerBlock(Blocks.COAL_BLOCK);
        registerBlock(Blocks.PACKED_ICE);
        registerBlockProperties(Blocks.RED_SANDSTONE, BlockRedSandstone.TYPE);
        registerBlockProperties(Blocks.PURPUR_PILLAR, BlockRotatedPillar.AXIS);
        registerBlock(Blocks.END_BRICKS);
        // registerBlock(Blocks.GRASS_PATH);
        // registerBlock(Blocks.REPEATING_COMMAND_BLOCK, BlockCommandBlock.CONDITIONAL, false, BlockCommandBlock.FACING,
        //         EnumFacing.UP);
        // registerBlock(Blocks.CHAIN_COMMAND_BLOCK, BlockCommandBlock.CONDITIONAL, false, BlockCommandBlock.FACING,
        //         EnumFacing.UP);
        registerBlock(Blocks.MAGMA);
        registerBlock(Blocks.NETHER_WART_BLOCK);
        registerBlock(Blocks.RED_NETHER_BRICK);
        registerBlockProperties(Blocks.BONE_BLOCK, BlockBone.AXIS);
        registerBlock(Blocks.WHITE_SHULKER_BOX);
        registerBlock(Blocks.ORANGE_SHULKER_BOX);
        registerBlock(Blocks.MAGENTA_SHULKER_BOX);
        registerBlock(Blocks.LIGHT_BLUE_SHULKER_BOX);
        registerBlock(Blocks.YELLOW_SHULKER_BOX);
        registerBlock(Blocks.LIME_SHULKER_BOX);
        registerBlock(Blocks.PINK_SHULKER_BOX);
        registerBlock(Blocks.GRAY_SHULKER_BOX);
        registerBlock(Blocks.SILVER_SHULKER_BOX);
        registerBlock(Blocks.CYAN_SHULKER_BOX);
        registerBlock(Blocks.PURPLE_SHULKER_BOX);
        registerBlock(Blocks.BLUE_SHULKER_BOX);
        registerBlock(Blocks.BROWN_SHULKER_BOX);
        registerBlock(Blocks.GREEN_SHULKER_BOX);
        registerBlock(Blocks.RED_SHULKER_BOX);
        registerBlock(Blocks.BLACK_SHULKER_BOX);
        registerBlock(Blocks.WHITE_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.ORANGE_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.MAGENTA_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.YELLOW_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.LIME_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.PINK_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.GRAY_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.SILVER_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.CYAN_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.PURPLE_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.BLUE_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.BROWN_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.GREEN_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.RED_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlock(Blocks.BLACK_GLAZED_TERRACOTTA, BlockGlazedTerracotta.FACING, EnumFacing.NORTH);
        registerBlockProperties(Blocks.CONCRETE, BlockColored.COLOR);
        registerBlockProperties(Blocks.CONCRETE_POWDER, BlockColored.COLOR);

        try {
            rgbImageTest();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static <T extends Comparable<T>> void registerBlockProperties(Block block, IProperty<T> property) {
        IBlockState defaultState = block.getDefaultState();
        for(T value : property.getAllowedValues()) {
            try {
                add(defaultState.withProperty(property, value));
            } catch(IllegalArgumentException ignored) {}
        }
    }

    private static <T extends Comparable<T>, U extends Comparable<U>> void registerBlockProperties(
            Block block, IProperty<T> property1, IProperty<U> property2) {

        IBlockState defaultState = block.getDefaultState();
        for(U value2 : property2.getAllowedValues()) {
            for (T value1 : property1.getAllowedValues()) {
                try {
                    add(defaultState.withProperty(property1, value1).withProperty(property2, value2));
                } catch(IllegalArgumentException ignored) {}
            }
        }
    }

    private static <T extends Comparable<T>, U extends Comparable<U>>
    void registerBlockProperties(Block block, IProperty<T> property1, T value1, IProperty<U> property2) {

        IBlockState state = block.getDefaultState().withProperty(property1, value1);
        for (U value2 : property2.getAllowedValues()) {
            try {
                add(state.withProperty(property2, value2));
            } catch(IllegalArgumentException ignored) {}
        }
    }

    private static <T extends Comparable<T>, U extends Comparable<U>, V extends Comparable<V>>
    void registerBlockProperties(Block block, IProperty<T> property1, T value1, IProperty<U> property2,
                                 U value2, IProperty<V> property3) {

        IBlockState state = block.getDefaultState().withProperty(property1, value1).withProperty(property2, value2);
        for (V value3 : property3.getAllowedValues()) {
            try {
                add(state.withProperty(property3, value3));
            } catch(IllegalArgumentException ignored) {}
        }
    }

    private static void registerBlock(Block block) {
        add(block.getDefaultState());
    }

    private static <T extends Comparable<T>> void registerBlock(Block block, IProperty<T> property, T value) {
        IBlockState defaultState = block.getDefaultState();
        add(defaultState.withProperty(property, value));
    }

    private static <T extends Comparable<T>, U extends Comparable<U>> void registerBlock(
            Block block, IProperty<T> property1, T value1, IProperty<U> property2, U value2) {

        IBlockState defaultState = block.getDefaultState();
        add(defaultState.withProperty(property1, value1).withProperty(property2, value2));
    }

    private static void add(IBlockState blockState) {
        IBakedModel bakedModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(blockState);
        List<BakedQuad> quads = bakedModel.getQuads(blockState, EnumFacing.UP, 0);
        if(quads.size() != 0) {
            TextureAtlasSprite sprite = quads.get(0).getSprite();
            BufferedImage image = extractImagesFromTextureSprite(sprite)[0];
            add(getAverageColorOfAnImage(image), blockState, image);
        }
        else {
            TerraSatelliteMapperMod.logger.warn("No BakedQuads found for block " + blockState.getBlock().getRegistryName());
        }
    }

    private static void add(RGBColorDouble color, IBlockState block, BufferedImage image) {
        blockColorMappings.put(color, new AbstractMap.SimpleEntry<>(block, image));
    }

    private static BufferedImage[] extractImagesFromTextureSprite(TextureAtlasSprite sprite) {
        int[][] rawTextureData = sprite.getFrameTextureData(0);
        BufferedImage[] result = new BufferedImage[rawTextureData.length];
        for(int i = 0; i < rawTextureData.length; ++i) {
            int[][] parsedTextureData = new int[sprite.getIconHeight()][sprite.getIconWidth()];
            int iconWidth = sprite.getIconWidth();
            BufferedImage image = new BufferedImage(iconWidth, sprite.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < sprite.getIconHeight(); ++y) {
                for (int x = 0; x < iconWidth; ++x) {
                    parsedTextureData[y][x] = rawTextureData[i][y * iconWidth + x] & 0x00ffffff;
                    image.setRGB(x, y, parsedTextureData[y][x]);
                }
            }
            result[i] = image;
        }
        return result;
    }

    private static RGBColorDouble getAverageColorOfAnImage(BufferedImage image) {
        double red = 0, green = 0, blue = 0;
        int width = image.getWidth(), height = image.getHeight();
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                int rgb = image.getRGB(x, y);
                red += (rgb & 0xff0000) >> 16;
                green += (rgb & 0x00ff00) >> 8;
                blue += (rgb & 0x0000ff);
            }
        }
        double wh = width * height;
        red = red / wh; green = green / wh; blue = blue / wh;
        return new RGBColorDouble(red, green, blue);
    }

    public static Map.Entry<IBlockState, BufferedImage> getNearestColor(int rgb) {
        CIELabColor color = new CIELabColor(new RGBColorDouble(rgb));
        double d, min = Double.POSITIVE_INFINITY;
        Map.Entry<IBlockState, BufferedImage> result = null;
        for(Map.Entry<RGBColorDouble, Map.Entry<IBlockState, BufferedImage>> entry : blockColorMappings.entrySet()) {
            CIELabColor blockColor = new CIELabColor(entry.getKey());
            d = color.getCIE2000DiffSq(blockColor);
            if(d < min) { min = d; result = entry.getValue(); }
        }
        return result;
    }

    public static void rgbImageTest() throws IOException {
        BufferedImage image = new BufferedImage(1536 * 16, 256 * 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        for(int y = 0; y < 256; ++y) {
            for(int x = 0; x < 1536; ++x) {
                int rgb = getRGB(x, y);
                Map.Entry<IBlockState, BufferedImage> entry = getNearestColor(rgb);
                g2d.drawImage(entry.getValue(), x * 16, y * 16, null);
            }
        }
        ImageIO.write(image, "png", new File("D:\\test.png"));
    }

    private static int getRGB(int i, int black) {

        i = MathUtils.bound(i, 0, 1536);
        black = MathUtils.bound(black, 0, 255);
        int j = 255 - black;

        if(i < 256) return (j << 16) + ((int) Math.round(i * j / 256.) << 8);
        i -= 256;
        if(i < 256) return (j << 8) + ((j - (int) Math.round(i * j / 256.)) << 16);
        i -= 256;
        if(i < 256) return (j << 8) + (int) Math.round(i * j / 256.);
        i -= 256;
        if(i < 256) return j + ((j - (int) Math.round(i * j / 256.)) << 8);
        i -= 256;
        if(i < 256) return j + ((int) Math.round(i * j / 256.) << 16);
        i -= 256;
        if(i < 256) return (j << 16) + (j - (int) Math.round(i * j / 256.));
        return 0;
    }

    private static class RGBColorDouble {

        public final double red, green, blue;

        public RGBColorDouble(double red, double green, double blue) {
            this.red = MathUtils.bound(red, 0, 255);
            this.green = MathUtils.bound(green, 0, 255);
            this.blue = MathUtils.bound(blue, 0, 255);
        }
        public RGBColorDouble(int rgb) {
            this.red = (rgb & 0xff0000) >> 16;
            this.green = (rgb & 0x00ff00) >> 8;
            this.blue = rgb & 0x0000ff;
        }

        public int toRGBColor() {
            return (redInt() << 16) + (greenInt() << 8) + blueInt();
        }

        public int redInt() { return (int) Math.round(red) & 0xff; }
        public int greenInt() { return (int) Math.round(green) & 0xff; }
        public int blueInt() { return (int) Math.round(blue) & 0xff; }

        public double getSRGBDiffSq(RGBColorDouble other) {
            double r = (red - other.red) * 0.30, g = (green - other.green) * 0.59, b = (blue - other.blue) * 0.11;
            return r * r + g * g + b * b;
        }
    }

    private static class CIELabColor {

        public final double l, a, b;

        public CIELabColor(double l, double a, double b) {
            this.l = MathUtils.bound(l, 0, 1);
            this.a = MathUtils.bound(a, 0, 1);
            this.b = MathUtils.bound(b, 0, 1);
        }

        /**
         * Reference to <a href="https://gist.github.com/manojpandey/f5ece715132c572c80421febebaf66ae">here</a>
         * @param color The RGB color
         */
        public CIELabColor(RGBColorDouble color) {
            double red100 = color.red * 100 / 255.,
                    green100 = color.green * 100 / 255.,
                    blue100 = color.blue * 100 / 255.;
            double x = red100 * 0.4124 + green100 * 0.3576 + blue100 * 0.1805,
                    y = red100 * 0.2126 + green100 * 0.7152 + blue100 * 0.0722,
                    z = red100 * 0.0193 + green100 * 0.1192 + blue100 * 0.9505;

            x /= 95.047; y /= 100; z /= 108.883;

            x = x > 0.008856 ? Math.cbrt(x) : 7.787 * x + (16 / 116.);
            y = y > 0.008856 ? Math.cbrt(y) : 7.787 * y + (16 / 116.);
            z = z > 0.008856 ? Math.cbrt(z) : 7.787 * z + (16 / 116.);

            this.l = (116 * y) - 16;
            this.a = 500 * (x - y);
            this.b = 200 * (y - z);
        }

        public double getCIE76DiffSq(CIELabColor other) {
            double dl = l - other.l, da = a - other.a, db = b - other.b;
            return dl * dl + da * da + db * db;
        }

        public double getCIE94DiffSq(CIELabColor other) {
            double dL = l - other.l, da = a - other.a, db = b - other.b;
            double C1 = Math.sqrt(a * a + b * b), C2 = Math.sqrt(other.a * other.a + other.b * other.b);
            double dC = C1 - C2;
            double dH = Math.sqrt(da * da + db * db - dC * dC);
            double SL = 1, SC = 1 + 0.045 * C1, SH = 1 + 0.015 * C1;
            double dL1 = dL / SL, dC1 = dC / SC, dH1 = dH / SH;
            return dL1 * dL1 + dC1 * dC1 + dH1 * dH1;
        }

        public double getCIE2000DiffSq(CIELabColor other) {
            double l1 = l, l2 = other.l, a1 = a, a2 = other.a, b1 = b, b2 = other.b;

            double deltaLPrime = l1 - l2;
            double c1 = Math.sqrt(a1 * a1 + b1 * b1), c2 = Math.sqrt(a2 * a2 + b2 * b2);
            double dashL = (l1 + l2) / 2, dashC = (c1 + c2) / 2, dashC7 = Math.pow(dashC, 7);
            double v1 = Math.sqrt(dashC7 / (dashC7 + Math.pow(25, 7)));
            double v = Math.sqrt(1 - v1);
            double a1Prime = a1 + (a1 / 2) * v;
            double a2Prime = a2 + (a2 / 2) * v;
            double c1Prime = Math.sqrt(a1Prime * a1Prime + b1 * b1);
            double c2Prime = Math.sqrt(a2Prime * a2Prime + b2 * b2);
            double deltaCPrime = c2Prime - c1Prime;
            double dashCPrime = (c1Prime + c2Prime) / 2;
            double h1Prime = Math.toDegrees(Math.atan2(b1, a1Prime)) % 360;
            double h2Prime = Math.toDegrees(Math.atan2(b2, a2Prime)) % 360;
            double absDeltaHPrime = Math.abs(h1Prime - h2Prime), deltaHPrime, dashBigHPrime;
            if(absDeltaHPrime <= 180) {
                deltaHPrime = h2Prime - h1Prime;
                dashBigHPrime = (h1Prime + h2Prime) / 2;
            }
            else {
                if(h2Prime <= h1Prime) deltaHPrime = h2Prime - h1Prime + 360;
                else deltaHPrime = h2Prime - h1Prime - 360;

                if(h1Prime + h2Prime < 360) dashBigHPrime = (h1Prime + h2Prime + 360) / 2;
                else dashBigHPrime = (h1Prime + h2Prime - 360) / 2;
            }
            double deltaBigHPrime = 2 * Math.sqrt(c1Prime * c2Prime) * Math.sin(Math.toRadians(deltaHPrime / 2));
            double bigT = 1
                    - 0.17 * Math.cos(Math.toRadians(dashBigHPrime - 30))
                    + 0.24 * Math.cos(Math.toRadians(2 * dashBigHPrime))
                    + 0.32 * Math.cos(Math.toRadians(3 * dashBigHPrime + 6))
                    - 0.20 * Math.cos(Math.toRadians(4 * dashBigHPrime - 63));
            double SL = 1 + (0.015 * (dashL - 50) * (dashL - 50)) / Math.sqrt(20 + (dashL - 50) * (dashL - 50));
            double SC = 1 + 0.045 * dashCPrime;
            double SH = 1 + 0.015 * dashCPrime * bigT;
            double v2 = Math.floor((dashBigHPrime - 275) / 25);
            double RT = -2 * v1 * Math.sin(Math.toRadians(60 * Math.exp(-v2 * v2)));

            double dL1 = deltaLPrime / SL, dC1 = deltaCPrime / SC, dH1 = deltaBigHPrime / SH;
            return dL1 * dL1 + dC1 * dC1 + dH1 * dH1 + RT * deltaCPrime * deltaBigHPrime / (SC * SH);
        }
    }

}
