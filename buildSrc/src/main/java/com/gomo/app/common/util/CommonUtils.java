package com.gomo.app.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommonUtils {

    private static final String BEARER_AUTH = "bearerAuth";

    public static String addAuthOption(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);

        JsonObject securitySchemes = new JsonObject();
        JsonObject bearerAuthObject = new JsonObject();

        bearerAuthObject.addProperty("type", "http");
        bearerAuthObject.addProperty("scheme", "bearer");
        bearerAuthObject.addProperty("bearerFormat", "JWT");
        securitySchemes.add(BEARER_AUTH, bearerAuthObject);

        jsonObject.getAsJsonObject("components")
            .add("securitySchemes", securitySchemes);

        jsonObject.getAsJsonObject("paths").entrySet().forEach(path -> {
            path.getValue().getAsJsonObject().entrySet().forEach(operation -> {
                JsonArray securityRequirement = new JsonArray();
                JsonObject securityObject = new JsonObject();
                securityObject.add(BEARER_AUTH, new JsonArray());
                securityRequirement.add(securityObject);
                operation.getValue().getAsJsonObject().add("security", securityRequirement);
            });
        });

        JsonArray globalSecurity = new JsonArray();
        JsonObject globalSecurityObject = new JsonObject();
        globalSecurityObject.add(BEARER_AUTH, new JsonArray());
        globalSecurity.add(globalSecurityObject);

        jsonObject.add("security", globalSecurity);

        return gson.toJson(jsonObject);
    }
}
