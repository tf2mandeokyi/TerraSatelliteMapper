package com.mndk.tppstlmapper;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;

@Mod(
        modid = TerraSatelliteMapperMod.MODID,
        dependencies = "required-after:terraplusplus",
        acceptableRemoteVersions = "*"
)
public class TerraSatelliteMapperMod {

    public static final String MODID = "tppstlmapper";
    public static String VERSION;

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        ModContainer container = Loader.instance().getIndexedModList().get(TerraSatelliteMapperMod.MODID);
        VERSION = container.getVersion();
    }
}
