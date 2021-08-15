package com.mndk.tppstlmapper.event;

import com.mndk.tppstlmapper.baker.SampleImageBaker;
import net.buildtheearth.terraplusplus.event.InitDatasetsEvent;
import net.buildtheearth.terraplusplus.event.InitEarthRegistryEvent;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EarthRegistryEventHandler {

    public static final String KEY_DATASET_SATELLITE_IMAGERY = "satellite_dataset";

    @SubscribeEvent
    public void datasets(InitDatasetsEvent event) {
        // event.register(KEY_DATASET_SATELLITE_IMAGERY, new SatelliteImageryDataset());
    }

    @SubscribeEvent
    @SuppressWarnings("rawtypes")
    public void dataBakers(InitEarthRegistryEvent<IEarthDataBaker> event) {
        // event.registry().addLast("stlmapper_tileimage", new SatelliteImageryBaker());
        event.registry().addLast("stlmapper_test", new SampleImageBaker());
    }

}
