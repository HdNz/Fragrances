package util.json;

import dto.Entity;

import java.util.List;

public class Merger {

    public List<Entity> add(Entity entity, List<Entity> entities) {
        boolean forExistingUser = entities.stream().anyMatch(e -> e.getUser().getName().equals(entity.getUser().getName()));
        if (forExistingUser) {
            mergeFoundEntity(entity, entities, false);
        } else {
            entities.add(entity);
        }
        return entities;
    }

    public List<Entity> delete(Entity entity, List<Entity> entities) {
        mergeFoundEntity(entity, entities, true);
        return entities;
    }

    private void mergeFoundEntity(Entity entity, List<Entity> entities, boolean toRemove) {
        for (Entity e : entities) {
            if (e.getUser().getName().equals(entity.getUser().getName())) {
                mergeUser(entity, e, toRemove);
            }
        }
    }

    private void mergeUser(Entity entity, Entity e, boolean toRemove) {
        if (toRemove) {
            e.getFragrances().removeIf(f -> entity.getFragrances().getFirst().getName().equals(f.getName()));
        } else {
            e.getFragrances().addAll(entity.getFragrances());
        }
    }
}
