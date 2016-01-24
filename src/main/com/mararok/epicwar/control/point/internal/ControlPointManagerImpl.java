/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.mararok.epicwar.control.point.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.scheduler.BukkitRunnable;

import com.mararok.epicwar.War;
import com.mararok.epicwar.control.ControlPoint;
import com.mararok.epicwar.control.ControlPointData;
import com.mararok.epicwar.control.ControlPointManager;

public class ControlPointManagerImpl implements ControlPointManager {
  private ControlPoint[] controlPoints;

  private Collection<ControlPointImpl> occupiedControlPoints;
  private UpdateTask updateTask;

  private ControlPointMapper mapper;
  private War war;

  public ControlPointManagerImpl(ControlPointMapper mapper, War war) throws Exception {
    this.mapper = mapper;
    this.war = war;
    loadAll();
  }

  private void loadAll() throws Exception {
    Collection<ControlPointImpl> collection = mapper.findAll();
    controlPoints = new ControlPoint[collection.size() + 1];
    for (ControlPoint controlPoint : collection) {
      controlPoints[controlPoint.getId()] = controlPoint;
    }

    if (!war.getSettings().editMode) {
      occupiedControlPoints = new LinkedList<ControlPointImpl>();
      updateTask = this.new UpdateTask();
      updateTask.runTaskTimer(war.getPlugin(), 0, war.getSettings().controlPoint.updateInterval);
    }
  }

  @Override
  public ControlPoint findById(int id) {
    return (id >= 0 && id < controlPoints.length) ? controlPoints[id] : null;
  }

  @Override
  public Collection<ControlPoint> findAll() {
    Collection<ControlPoint> collection;
    if (controlPoints.length == 1) {
      collection = Collections.emptyList();
    } else {
      collection = new ArrayList<ControlPoint>(controlPoints.length - 1);

      for (int i = 1; i < controlPoints.length - 1; i++) {
        collection.add(controlPoints[i]);
      }
    }
    return Collections.unmodifiableCollection(collection);
  }

  @Override
  public ControlPoint create(ControlPointData data) throws Exception {
    ControlPointImpl controlPoint = mapper.insert(data);
    controlPoints = Arrays.copyOf(controlPoints, controlPoints.length + 1);
    controlPoints[controlPoint.getId()] = controlPoint;
    return controlPoint;
  }

  @Override
  public void update(ControlPoint controlPoint) throws Exception {
    ControlPointImpl entity = (ControlPointImpl) controlPoint;
    mapper.update(entity);
    entity.clearChanges();
  }

  @Override
  public War getWar() {
    return war;
  }

  private class UpdateTask extends BukkitRunnable {
    @Override
    public void run() {
      try {
        for (ControlPointImpl controlPoint : occupiedControlPoints) {
          controlPoint.getOccupation().update();
        }
      } catch (Exception e) {
        war.getPlugin().getLogger().log(Level.SEVERE, "Exception in control point occupation update", e);
      }
    }
  }

  public void addOccupied(ControlPointImpl controlPoint) {
    if (!occupiedControlPoints.contains(controlPoint)) {
      occupiedControlPoints.add(controlPoint);
    }
  }

  public void removeOccupied(ControlPointImpl controlPoint) {
    occupiedControlPoints.remove(controlPoint);
  }

}
