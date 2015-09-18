/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.util.Collection;

public interface EntityMapper<E extends ObservedEntity, ED> {
  public abstract E findById(int id) throws Exception;
  public abstract Collection<E> findAll() throws Exception;
  
  public abstract E create(ED entityData) throws Exception;
  
  public abstract void update(E entity) throws Exception;
  public abstract void delete(E entity) throws Exception;
}
