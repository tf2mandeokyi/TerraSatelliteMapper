package com.mndk.tppstlmapper;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TerraSatelliteMapperMod.MODID)
@Mod.EventBusSubscriber
public class TppStlMapperModConfig {

    @Config.Name("Enabled")
    @Config.Comment("Whether to enable the satellite imagery generation or not.")
    public static boolean enabled = true;

    @Config.Name("Map Service URL")
    @Config.Comment({
            "The map service URL. The map service should be using WebMercator projection.",
            "List of all parameters:",
            "   {x}: Tile X position",
            "   {y}: Tile Y position",
            "   {z}: Tile zoom value",
            "   {u}: Tile quadkey (Used for Bing maps)",
            "URL examples:",
            "   OpenStreetMap -> https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            "   Bing maps -> https://t.ssl.ak.dynamic.tiles.virtualearth.net/comp/ch/{u}?it=A"
    })
    public static String url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png";

    @Config.Name("Default Zoom Value")
    @Config.Comment("The default map zoom value. For example if the value's 16, the {z} parameter in \"Map Service URL\" will be changed into 16.")
    public static int zoom = 17;

    @Config.Name("Max Concurrent Request")
    @Config.Comment("The maximum concurrent request count.")
    public static int maxConcurrentRequest = 2;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (TerraSatelliteMapperMod.MODID.equals(event.getModID())) {
            ConfigManager.sync(TerraSatelliteMapperMod.MODID, Config.Type.INSTANCE);
        }
    }
}
