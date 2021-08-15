package com.mndk.tppstlmapper.dataset;

import com.mndk.tppstlmapper.tile.TileImageData;
import com.mndk.tppstlmapper.tile.server.TileServer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.buildtheearth.terraplusplus.dataset.IElementDataset;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;
import net.buildtheearth.terraplusplus.util.CornerBoundingBox2d;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SatelliteImageryDataset implements IElementDataset<TileImageData> {

    @Getter private final TileServer tileServer;
    @Getter private final int defaultZoom;

    @Override
    public CompletableFuture<TileImageData[]> getAsync(@NonNull CornerBoundingBox2d boundsGeo) throws OutOfProjectionBoundsException {
        return tileServer.fetchAllAsync(boundsGeo, defaultZoom);
    }
}