/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

import org.bukkit.scheduler.BukkitRunnable;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.ControlPointManager;

public class ControlPointManagerImpl implements ControlPointManager {
  private Vector<ControlPointImpl> controlPoints;
  private Collection<ControlPointImpl> occupiedControlPoints;
  private ControlPointMapper mapper;
  private UpdateTask updateTask;
  private War war;

  public ControlPointManagerImpl(ControlPointMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;
    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<ControlPointImpl> collection = mapper.findAll();
    for (ControlPointImpl controlPoint : collection) {
      controlPoints.set(controlPoint.getId(), controlPoint);
    }

    if (!war.getSettings().editMode) {
      occupiedControlPoints = new LinkedList<ControlPointImpl>();
      updateTask = this.new UpdateTask();
      updateTask.runTaskTimer(war.getPlugin(), 0, war.getSettings().controlPoint.updateInterval);
    }
  }

  @Override
  public ControlPoint findById(int id) {
    return controlPoints.get(id);
  }

  @Override
  public Collection<ControlPoint> findAll() {
    return Collections.unmodifiableCollection(controlPoints);
  }

  @Override
  public ControlPoint create(ControlPointData data) throws Exception {
    ControlPointImpl controlPoint = mapper.insert(data);
    controlPoints.set(controlPoint.getId(), controlPoint);
    return controlPoint;
  }

  @Override
  public void update(ControlPoint controlPoint) throws Exception {
    ControlPointImpl s = (ControlPointImpl) findById(controlPoint.getId());
    if (s != null) {
      mapper.update(s);
    }
  }

  @Override
  public void delete(ControlPoint controlPoint) throws Exception {
    ControlPointImpl s = (ControlPointImpl) findById(controlPoint.getId());
    if (s != null) {
      mapper.delete(s);
    }
  }

  @Override
  public War getWar() {
    return war;
  }

  private class UpdateTask extends BukkitRunnable {
    @Override
    public void run() {
      for (ControlPointImpl controlPoint : occupiedControlPoints) {
        controlPoint.getOccupation().update();
      }
    }
  }

}
