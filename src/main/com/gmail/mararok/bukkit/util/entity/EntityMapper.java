/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.util.Collection;

public abstract class EntityMapper<E extends ObservedEntity, ED> {
  protected EntityFactory<E,ED> entityFactory;
  
  public EntityMapper(EntityFactory<E,ED> entityFactory) {
    this.entityFactory = entityFactory;
  }
  
  public abstract E findById(int id) throws Exception;
  public abstract Collection<E> findAll() throws Exception;
  
  public abstract E create(ED entityData) throws Exception;
  
  public abstract void update(E entity) throws Exception;
  public abstract void delete(E entity) throws Exception;
}
