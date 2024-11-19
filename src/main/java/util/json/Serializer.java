package util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dto.Entity;
import util.resource.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Serializer {

    private static final String DATA_FILE_NAME = "fragrances.json";

    public static void saveEntitiesToJsonFile(List<Entity> entity) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(entity);
        FileUtil.writeContent(DATA_FILE_NAME, json);
    }

    public static List<Entity> readEntitiesFromJson() {
        List<Entity> entities = new ArrayList<>();
        String workout = FileUtil.readContent(DATA_FILE_NAME);

        if (!workout.isEmpty()) {
            entities = new Gson().fromJson(workout, new TypeToken<List<Entity>>() {
            }.getType());
        }
        return entities;
    }
}
