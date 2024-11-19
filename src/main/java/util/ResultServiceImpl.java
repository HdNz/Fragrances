package util;

import dto.Entity;
import util.json.Merger;
import util.json.Serializer;

import java.util.List;

public class ResultServiceImpl implements ResultService {

    private static final Merger merger = new Merger();

    @Override
    public void addResult(Entity entity) {
        List<Entity> entities = Serializer.readEntitiesFromJson();
        entities = merger.add(entity, entities);
        Serializer.saveEntitiesToJsonFile(entities);
    }

    @Override
    public void deleteResult(Entity entity) {
        List<Entity> entities = Serializer.readEntitiesFromJson();
        entities = merger.delete(entity, entities);
        Serializer.saveEntitiesToJsonFile(entities);
    }
}
