package com.mndk.btestlmapper;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;

@Mod(
        modid = BTESatelliteMapperMod.MODID,
        dependencies = "required-after:terraplusplus",
        acceptableRemoteVersions = "*"
)
public class BTESatelliteMapperMod {

    public static final String MODID = "btestlmapper";
    public static String VERSION;

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        ModContainer container = Loader.instance().getIndexedModList().get(BTESatelliteMapperMod.MODID);
        VERSION = container.getVersion();
    }
}
