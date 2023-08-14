package org.secure.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final Gson gson;

    private final Path dataPath;

    private final ConcurrentHashMap<String, Object> data;
    private final ConcurrentHashMap<String, Object> defaultConfig;

    public Data() {

        gson = new GsonBuilder().setPrettyPrinting().create();

        dataPath = Path.of("src/main/resources/files/json/data.json");

        data = loadJsonAsConcurrentHashMap(dataPath);
        defaultConfig = loadJsonAsConcurrentHashMap(Path.of("src/main/resources/files/json/defaultConfig.json"));

    }

    public ConcurrentHashMap<String, Object> loadJsonAsConcurrentHashMap(Path path) {

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            return gson.fromJson(reader, new TypeToken<ConcurrentHashMap<String, Object>>(){}.getType());

        } catch (IOException e) {

            return new ConcurrentHashMap<>();

        }
    }

    public Object getGuildData(String guildID) {

        return data.computeIfAbsent(guildID, key -> {
            updateGuildData(guildID, defaultConfig); // Optional: Update the map in one place
            return defaultConfig;
        });
    }

    public void updateGuildData(String guildID, ConcurrentHashMap<String, Object> newData) {

        data.put(guildID, newData);

        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8)) {

            writer.write(gson.toJson(data));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
