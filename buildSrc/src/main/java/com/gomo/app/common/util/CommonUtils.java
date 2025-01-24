package com.gomo.app.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommonUtils {
    private static final String COOKIE_AUTH = "cookieAuth";

    public static String addAuthOption(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);

        JsonObject securitySchemes = new JsonObject();
        JsonObject cookieAuthObject = new JsonObject();
        cookieAuthObject.addProperty("type", "apiKey");
        cookieAuthObject.addProperty("in", "cookie");
        cookieAuthObject.addProperty("name", "Access-Token");
        securitySchemes.add(COOKIE_AUTH, cookieAuthObject);

        jsonObject.getAsJsonObject("components")
                .add("securitySchemes", securitySchemes);

        jsonObject.getAsJsonObject("paths").entrySet().forEach(path -> {
            System.out.println("path key: "+path.getKey());
            System.out.println("path value: "+path.getValue());
            path.getValue().getAsJsonObject().entrySet().forEach(operation -> {
                JsonArray securityRequirement = new JsonArray();
                JsonObject securityObject = new JsonObject();
                securityObject.add(COOKIE_AUTH, new JsonArray());
                securityRequirement.add(securityObject);
                operation.getValue().getAsJsonObject().add("security", securityRequirement);
            });
        });

        JsonArray globalSecurity = new JsonArray();
        JsonObject globalSecurityObject = new JsonObject();
        globalSecurityObject.add(COOKIE_AUTH, new JsonArray());
        globalSecurity.add(globalSecurityObject);

        jsonObject.add("security", globalSecurity);

        return gson.toJson(jsonObject);
    }
}
