/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

public interface EntityFactory<E extends Entity, ED> {
  public E create(ED entityData) throws Exception;
}
