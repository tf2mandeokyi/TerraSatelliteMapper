package com.mndk.tppstlmapper.tile;

import java.net.MalformedURLException;
import java.net.URL;

@FunctionalInterface
public interface TilePosToUrlFunction {
    URL get(TileImagePos pos) throws MalformedURLException;
}
