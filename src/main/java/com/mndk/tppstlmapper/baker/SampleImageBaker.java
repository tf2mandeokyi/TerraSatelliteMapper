package com.mndk.tppstlmapper.baker;

import com.mndk.tppstlmapper.TerraSatelliteMapperMod;
import com.mndk.tppstlmapper.block.RGBColorToBlock;
import net.buildtheearth.terraplusplus.generator.CachedChunkData;
import net.buildtheearth.terraplusplus.generator.GeneratorDatasets;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.buildtheearth.terraplusplus.util.CornerBoundingBox2d;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

public class SampleImageBaker implements IEarthDataBaker<Void> {

    @Override
    public CompletableFuture<Void> requestData(ChunkPos pos, GeneratorDatasets datasets, Bounds2d bounds, CornerBoundingBox2d boundsGeo) {
        return null;
    }

    @Override
    public void bake(ChunkPos pos, CachedChunkData.Builder builder, Void data) {

        BufferedImage sampleImage = TerraSatelliteMapperMod.SAMPLE_IMAGE;
        int width = sampleImage.getWidth(), height = sampleImage.getHeight();

        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                int index = x * 16 + z;
                // IBlockState blockState = builder.surfaceBlocks()[index];
                // Material original = blockState == null ? null : blockState.getMaterial();

                // if (original == null || original.getMaterialMapColor() == MapColor.GRASS) {
                    if(builder.waterDepth()[index] == -128) {
                        BlockPos blockPos = pos.getBlock(x, 0, z);
                        int rgb = sampleImage.getRGB(
                                ((blockPos.getX() % width) + width) % width,
                                ((blockPos.getZ() % height) + height) % height
                        ) & 0x00ffffff;
                        IBlockState block = RGBColorToBlock.getNearestColor(rgb).getKey();
                        builder.surfaceBlocks()[index] = block;
                    }
                // }
            }
        }
    }
}
