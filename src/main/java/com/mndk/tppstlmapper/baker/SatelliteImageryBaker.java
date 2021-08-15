package com.mndk.tppstlmapper.baker;

import net.buildtheearth.terraplusplus.generator.CachedChunkData;
import net.buildtheearth.terraplusplus.generator.GeneratorDatasets;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.buildtheearth.terraplusplus.util.CornerBoundingBox2d;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;
import net.minecraft.util.math.ChunkPos;

import java.util.concurrent.CompletableFuture;

public class SatelliteImageryBaker implements IEarthDataBaker<Void> {

    @Override
    public CompletableFuture<Void> requestData(
            ChunkPos pos, GeneratorDatasets datasets, Bounds2d bounds, CornerBoundingBox2d boundsGeo) {

        return null; // TODO
    }

    @Override
    public void bake(ChunkPos pos, CachedChunkData.Builder builder, Void data) {

    }
}
