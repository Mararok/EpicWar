/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.sector.internal;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.Sector;
import com.mararok.epicwar.control.SectorData;
import com.mararok.epicwar.control.internal.NamedControlAreaImpl;
import com.mararok.epicwar.faction.Faction;

public class SectorImpl extends NamedControlAreaImpl implements Sector {
  private Faction owner;
  private War war;

  public SectorImpl(SectorData data, War war) {
    super(data);
    this.owner = war.getFactionManager().findById(data.ownerId);
    this.war = war;
  }

  // @TODO implement method
  @Override
  public boolean canCapture(Faction faction) {
    return false;
  }

  @Override
  public Faction getOwner() {
    return owner;
  }

  @Override
  public void setOwner(Faction newOwner) {
    owner = newOwner;
    onChangeProperty("ownerId", owner.getId());
  }

  @Override
  public boolean isOwner(Faction faction) {
    return owner == faction;
  }

  @Override
  public War getWar() {
    return war;
  }

}
