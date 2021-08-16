package com.mndk.tppstlmapper.baker;

import com.mndk.tppstlmapper.block.RGBColorToBlock;
import com.mndk.tppstlmapper.dataset.SatelliteImageryDataset;
import com.mndk.tppstlmapper.event.EarthRegistryEventHandler;
import com.mndk.tppstlmapper.tile.TileImageData;
import com.mndk.tppstlmapper.util.RGBColorDouble;
import lombok.RequiredArgsConstructor;
import net.buildtheearth.terraplusplus.generator.CachedChunkData;
import net.buildtheearth.terraplusplus.generator.GeneratorDatasets;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.buildtheearth.terraplusplus.projection.OutOfProjectionBoundsException;
import net.buildtheearth.terraplusplus.util.CornerBoundingBox2d;
import net.buildtheearth.terraplusplus.util.bvh.Bounds2d;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SatelliteImageryBaker implements IEarthDataBaker<int[]> {

    private final boolean leaveOsmElements;

    @Override
    public CompletableFuture<int[]> requestData(
            ChunkPos pos, GeneratorDatasets datasets, Bounds2d bounds, CornerBoundingBox2d boundsGeo) throws OutOfProjectionBoundsException {

        SatelliteImageryDataset dataset =
                datasets.getCustom(EarthRegistryEventHandler.KEY_DATASET_SATELLITE_IMAGERY);
        return dataset.getAsync(boundsGeo).thenApply(tiles -> {
            try {
                int[] result = new int[256];
                for(TileImageData tile : tiles) {
                    for (int x = 0; x < 16; ++x) {
                        for (int z = 0; z < 16; ++z) {
                            double[] geoCoordinates = datasets.projection().toGeo((pos.x << 4) + x, (pos.z << 4) + z);
                            RGBColorDouble color = tile.getRGBByGeoCoordinate(geoCoordinates[0], geoCoordinates[1]);
                            if(color != null) {
                                result[x * 16 + z] = color.toRGBColor();
                            }
                        }
                    }
                }
                return result;
            } catch (OutOfProjectionBoundsException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public void bake(ChunkPos pos, CachedChunkData.Builder builder, int[] data) {
        if(data == null || data.length == 0) {
            return;
        }

        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                int index = x * 16 + z;
                IBlockState blockState = builder.surfaceBlocks()[index];
                Material original = blockState == null ? null : blockState.getMaterial();

                if (!leaveOsmElements || (original == null || original.getMaterialMapColor() == MapColor.GRASS)) {
                    builder.surfaceBlocks()[index] = RGBColorToBlock.getNearestColor(data[index]).getKey();
                }
            }
        }
    }
}
