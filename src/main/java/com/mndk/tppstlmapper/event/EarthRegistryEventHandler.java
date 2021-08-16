package com.mndk.tppstlmapper.event;

import com.mndk.tppstlmapper.TppStlMapperModConfig;
import com.mndk.tppstlmapper.baker.SatelliteImageryBaker;
import com.mndk.tppstlmapper.dataset.SatelliteImageryDataset;
import com.mndk.tppstlmapper.tile.TilePosToUrlFunction;
import com.mndk.tppstlmapper.tile.projection.WebMercatorTileProjection;
import com.mndk.tppstlmapper.tile.server.TileServer;
import com.mndk.tppstlmapper.util.TileQuadKey;
import net.buildtheearth.terraplusplus.event.InitDatasetsEvent;
import net.buildtheearth.terraplusplus.event.InitEarthRegistryEvent;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.net.URL;

public class EarthRegistryEventHandler {

    public static final String KEY_DATASET_SATELLITE_IMAGERY = "satellite_dataset";

    public static final TilePosToUrlFunction function = tilePos -> new URL(TppStlMapperModConfig.url
            .replace("{x}", Integer.toString(tilePos.x))
            .replace("{y}", Integer.toString(tilePos.y))
            .replace("{z}", Integer.toString(tilePos.zoom))
            .replace("{u}", TileQuadKey.toQuadKey(tilePos))
    );

    @SubscribeEvent
    public void datasets(InitDatasetsEvent event) {
        if(TppStlMapperModConfig.enabled) {
            event.register(KEY_DATASET_SATELLITE_IMAGERY, new SatelliteImageryDataset(
                    new TileServer(new WebMercatorTileProjection(), function, TppStlMapperModConfig.maxConcurrentRequest),
                    TppStlMapperModConfig.zoom)
            );
        }
    }

    @SubscribeEvent
    @SuppressWarnings("rawtypes")
    public void dataBakers(InitEarthRegistryEvent<IEarthDataBaker> event) {
        if(TppStlMapperModConfig.enabled) {
            event.registry().addLast("stlmapper_tileimage", new SatelliteImageryBaker());
        }
    }

}
