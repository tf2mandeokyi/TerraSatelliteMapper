package com.mndk.tppstlmapper.block;

import com.mndk.tppstlmapper.TerraSatelliteMapperMod;
import com.mndk.tppstlmapper.util.CIELabColor;
import com.mndk.tppstlmapper.util.JarResourceManager;
import com.mndk.tppstlmapper.util.RGBColorDouble;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class RGBColorToBlock {


    private static final Map<CIELabColor, Map.Entry<IBlockState, BufferedImage>> blockColorMappings = new HashMap<>();


    public static void init(FMLInitializationEvent event) {
        try {
            loadBlockModelsFromResources();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @SideOnly(Side.CLIENT)
    public static void initForClient(FMLInitializationEvent event) {
        registerBlockProperties(Blocks.STONE, BlockStone.VARIANT);
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
        registerBlock(Blocks.EMERALD_ORE);
        registerBlock(Blocks.EMERALD_BLOCK);
        registerBlock(Blocks.REDSTONE_BLOCK);
        registerBlock(Blocks.QUARTZ_ORE);
        registerBlockProperties(Blocks.QUARTZ_BLOCK, BlockQuartz.VARIANT);
        registerBlockProperties(Blocks.STAINED_HARDENED_CLAY, BlockStainedHardenedClay.COLOR);
        registerBlockProperties(Blocks.PRISMARINE, BlockPrismarine.VARIANT);
        registerBlock(Blocks.SEA_LANTERN);
        registerBlockProperties(Blocks.HAY_BLOCK, BlockHay.AXIS);
        registerBlock(Blocks.HARDENED_CLAY);
        registerBlock(Blocks.COAL_BLOCK);
        registerBlock(Blocks.PACKED_ICE);
        registerBlockProperties(Blocks.RED_SANDSTONE, BlockRedSandstone.TYPE);
        registerBlockProperties(Blocks.PURPUR_PILLAR, BlockRotatedPillar.AXIS);
        registerBlock(Blocks.END_BRICKS);
        registerBlock(Blocks.MAGMA);
        registerBlock(Blocks.NETHER_WART_BLOCK);
        registerBlock(Blocks.RED_NETHER_BRICK);
        registerBlockProperties(Blocks.BONE_BLOCK, BlockBone.AXIS);
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
            saveBlockModelsWithClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SideOnly(Side.CLIENT)
    private static void saveBlockModelsWithClient() throws IOException {
        for(Map.Entry<CIELabColor, Map.Entry<IBlockState, BufferedImage>> entry : blockColorMappings.entrySet()) {
            IBlockState blockState = entry.getValue().getKey();
            BufferedImage image = entry.getValue().getValue();
            ResourceLocation registryName = blockState.getBlock().getRegistryName();
            if(registryName != null) {
                File file = new File("D:\\minecraft_block_textures_for_tppstlmapper\\" +
                        URLEncoder.encode(blockState.getBlock().getRegistryName().toString(), "UTF-8") +
                        blockState.getPropertyKeys().stream()
                                .map(property -> "[" + property.getName() + "=" + blockState.getValue(property) + "]")
                                .reduce("", String::concat) +
                        ".png");
                ImageIO.write(image, "png", file);
            }
        }
    }


    @SideOnly(Side.CLIENT)
    private static <T extends Comparable<T>> void registerBlockProperties(Block block, IProperty<T> property) {
        IBlockState defaultState = block.getDefaultState();
        for(T value : property.getAllowedValues()) {
            try {
                add(defaultState.withProperty(property, value));
            } catch(IllegalArgumentException ignored) {}
        }
    }


    @SideOnly(Side.CLIENT)
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


    @SideOnly(Side.CLIENT)
    private static <T extends Comparable<T>, U extends Comparable<U>>
    void registerBlockProperties(Block block, IProperty<T> property1, T value1, IProperty<U> property2) {

        IBlockState state = block.getDefaultState().withProperty(property1, value1);
        for (U value2 : property2.getAllowedValues()) {
            try {
                add(state.withProperty(property2, value2));
            } catch(IllegalArgumentException ignored) {}
        }
    }


    @SideOnly(Side.CLIENT)
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


    @SideOnly(Side.CLIENT)
    private static void registerBlock(Block block) {
        add(block.getDefaultState());
    }


    @SideOnly(Side.CLIENT)
    private static <T extends Comparable<T>> void registerBlock(Block block, IProperty<T> property, T value) {
        IBlockState defaultState = block.getDefaultState();
        add(defaultState.withProperty(property, value));
    }


    @SideOnly(Side.CLIENT)
    private static <T extends Comparable<T>, U extends Comparable<U>> void registerBlock(
            Block block, IProperty<T> property1, T value1, IProperty<U> property2, U value2) {

        IBlockState defaultState = block.getDefaultState();
        add(defaultState.withProperty(property1, value1).withProperty(property2, value2));
    }


    @SideOnly(Side.CLIENT)
    private static void add(IBlockState blockState) {
        // Accessing class Minecraft in the server side throws ClassNotFoundException
        // TODO: Find another way to access block models
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


    /**
     * Adds block image entry to blockColorMappings
     */
    private static void add(RGBColorDouble color, IBlockState block, BufferedImage image) {
        blockColorMappings.put(new CIELabColor(color), new AbstractMap.SimpleEntry<>(block, image));
    }


    @SideOnly(Side.CLIENT)
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


    private static void loadBlockModelsFromResources() throws IOException, URISyntaxException {
        final String path = "assets/tppstlmapper/textures/block/";
        String[] resourcePathList = JarResourceManager.getResourceListing(RGBColorToBlock.class, path);
        for(String resourcePath : resourcePathList) {
            if("".equals(resourcePath)) continue;
            IBlockState blockState = getBlockStateFromResourcePath(path + resourcePath);
            InputStream stream = RGBColorToBlock.class.getClassLoader().getResourceAsStream(path + resourcePath);
            BufferedImage image = ImageIO.read(Objects.requireNonNull(stream));
            RGBColorDouble color = getAverageColorOfAnImage(image);
            add(color, Objects.requireNonNull(blockState), image);
        }
        TerraSatelliteMapperMod.logger.info("Found " + blockColorMappings.size() + " block textures");
    }


    /**
     * Should be only called on initialization
     */
    private static IBlockState getBlockStateFromResourcePath(String path) throws UnsupportedEncodingException {
        path = URLDecoder.decode(path, "UTF-8"); // URL decode
        if("".equals(path)) return null; // null if nothing
        // path now should be like : assets/tppstlmapper/textures/block/minecraft:<block_name>[p1=v1]...[pn=vn].png

        path = path.substring(0, path.length() - 4); // Cutting ".png" out
        String[] args = path.split("\\[");
        String[] folders = args[0].split("/");
        args[0] = folders[folders.length - 1];
        // args = ["minecraft:<block_name>", "p1=v1]", "p2=v2]", ... , "pn=vn]"]

        IBlockState blockState = Block.REGISTRY.getObject(new ResourceLocation(args[0])).getDefaultState();
        Collection<IProperty<?>> propertyKeys = blockState.getPropertyKeys();
        for(int i = 1; i < args.length; ++i) {
            args[i] = args[i].substring(0, args[i].length() - 1);
            // args[i] = "p1=v1"
            String[] keyValue = args[i].split("=");
            Optional<IProperty<?>> property = propertyKeys.stream().filter(p -> keyValue[0].equals(p.getName()))
                    .findFirst();
            if(!property.isPresent()) continue;
            blockState = a(blockState, property.get(), keyValue[1]);
        }
        return blockState;
    }


    private static <T extends Comparable<T>> IBlockState a(IBlockState blockState, IProperty<T> property, String value) {
        com.google.common.base.Optional<T> optional = property.parseValue(value);
        if(!optional.isPresent()) return blockState;
        return blockState.withProperty(property, optional.get());
    }


    private static <T extends Comparable<T>> IBlockState a(IBlockState original, IProperty<T> property, T value) {
        return original.withProperty(property, value);
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
        for(Map.Entry<CIELabColor, Map.Entry<IBlockState, BufferedImage>> entry : blockColorMappings.entrySet()) {
            d = color.getCIE2000DiffSq(entry.getKey());
            if(d < min) { min = d; result = entry.getValue(); }
        }
        return result;
    }


}
