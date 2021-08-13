package com.mndk.tppstlmapper.event;

import com.mndk.tppstlmapper.baker.EarthDataBakerTest;
import net.buildtheearth.terraplusplus.event.InitEarthRegistryEvent;
import net.buildtheearth.terraplusplus.generator.data.IEarthDataBaker;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EarthRegistryEventHandler {

    @SubscribeEvent
    @SuppressWarnings("rawtypes")
    public void onEarthRegistryEvent(InitEarthRegistryEvent<IEarthDataBaker> event) {
        event.registry().addBefore("osm", "stlmapper_test", new EarthDataBakerTest());
    }

}
