package com.mndk.tppstlmapper.dataset;

import lombok.NonNull;
import net.buildtheearth.terraplusplus.dataset.TiledDataset;
import net.buildtheearth.terraplusplus.projection.GeographicProjection;
import net.minecraft.util.math.ChunkPos;

import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

public class SatelliteImageryDataset extends TiledDataset<BufferedImage> {

    public SatelliteImageryDataset(@NonNull GeographicProjection projection, double tileSize) {
        super(projection, tileSize);
    }

    @Override
    public CompletableFuture<BufferedImage> load(@NonNull ChunkPos key) {
        return null;
    }
}
