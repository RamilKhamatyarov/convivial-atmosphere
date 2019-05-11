package ru.rkhamatyarov.convivialatmosphere.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrettyJsonBuilder {
    private PrettyJsonBuilder() {}

    public static String prettyJson(Object prettifyJsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(prettifyJsonObject);
    }
}
