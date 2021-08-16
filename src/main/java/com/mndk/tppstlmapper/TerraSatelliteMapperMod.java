package com.mndk.tppstlmapper;

import com.mndk.tppstlmapper.block.RGBColorToBlock;
import com.mndk.tppstlmapper.event.EarthRegistryEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = TerraSatelliteMapperMod.MODID,
        dependencies = "required-after:terraplusplus",
        acceptableRemoteVersions = "*"
)
public class TerraSatelliteMapperMod {

    public static final String MODID = "tppstlmapper";
    public static String VERSION;

    public static Logger logger;

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        ModContainer container = Loader.instance().getIndexedModList().get(TerraSatelliteMapperMod.MODID);
        VERSION = container.getVersion();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.TERRAIN_GEN_BUS.register(new EarthRegistryEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RGBColorToBlock.init(event);
    }
}
