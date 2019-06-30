package com.java.yaroshevich.heartRateMonitor.repository;

import java.util.List;

public interface Repository<Entity extends Object> {

    Entity find(long id);

    List<Entity> findAll();

    void delete(long id);

    void insert(Entity entity);

    void update(Entity entity);

}
