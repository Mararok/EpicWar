/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.ControlPointManager;

public class ControlPointManagerImpl implements ControlPointManager {
  private List<ControlPoint> controlPoints;
  private Collection<ControlPointImpl> occupiedControlPoints;
  private ControlPointMapper mapper;
  private UpdateTask updateTask;
  private War war;

  public ControlPointManagerImpl(ControlPointMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;

    controlPoints = new ArrayList<ControlPoint>();
    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<ControlPointImpl> collection = mapper.findAll();
    for (ControlPoint controlPoint : collection) {
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
    if (id > 0 && id < controlPoints.size()) {
      return controlPoints.get(id);
    }
    return null;
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
    ControlPointImpl entity = (ControlPointImpl) controlPoint;
    mapper.update(entity);
  }

  @Override
  public void delete(ControlPoint controlPoint) throws Exception {
    ControlPointImpl entity = (ControlPointImpl) controlPoint;
    mapper.delete(entity);
    controlPoints.remove(entity.getId());
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
