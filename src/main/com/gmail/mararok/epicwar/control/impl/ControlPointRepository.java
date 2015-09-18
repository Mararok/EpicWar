package com.gmail.mararok.epicwar.control.impl;

import java.util.List;

import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.control.ControlPoint;
import com.gmail.mararok.epicwar.control.ControlPointData;

public class ControlPointRepository {
  private List<ControlPoint> controlPoints;
  private ControlPointMapper controlPointMapper;
  private War war;

  public ControlPointRepository(ControlPointMapper controlPointMapper, War war) {
    this.controlPointMapper = controlPointMapper;
    this.war = war;
  }
  
  public void loadAll() {
    for(ControlPointData data : controlPointMapper.findAll()) {
      controlPoints.set(data.id,)
    }
  }
  
  public ControlPoint getById(int id) {
    if (controlPoints.size() < id) {
      return controlPoints.get(id);
    }
    
    return null;
  }
  
  /**
  public void load() throws Exception {

    PreparedStatement controlPointsStatement = DatabaseConnection
        .get()
        .prepareQuery(
            "SELECT id,name,sectorID,ownerID,centerX,centerY,centerZ,radius,power,maxPower FROM ew_controlPoints WHERE warID = ?");
    controlPointsStatement.setInt(1, getWar().getID());
    ResultSet results = controlPointsStatement.executeQuery();

    while (results.next()) {
      put(new ControlPointImpl(ControlPointData.fromDBResults(results), this));
    }

    controlPointsStatement.close();
    getPlugin().logInfo("Loaded " + size() + " control points");
  }

  private void createNoControlPoint() {
    ControlPointData info = new ControlPointData();
    info.name = "No point";
    put(new EmptyControlPoint(info, this));
  }

  public void init() {
    List<ControlPointImpl> points = getList();
    for (ControlPointImpl point : points) {
      point.init();
    }
    updater = new ControlPointsUpdater(this);
    updater.runTaskTimer(getPlugin(), 5 * 20, 5 * 20);
  }

  public void setUpdate(boolean update) {
    updater.setEnabled(update);
  }

  public void create(ControlPointData info) throws ControlPointExistsException {
    if (isExists(info.name)) {
      throw new ControlPointExistsException();
    }

    try {
      PreparedStatement st = DatabaseConnection.get().getCachedQuery(
          SQLID_AddControlPoint);

      st.setString(1, info.name);
      st.setInt(2, getWar().getID());
      st.setInt(3, info.sectorID);

      st.setInt(4, info.centerX);
      st.setInt(5, info.centerY);
      st.setInt(6, info.centerZ);
      st.setInt(7, info.radius);
      st.setInt(8, info.power);
      st.setInt(9, info.maxPower);
      st.executeUpdate();
      DatabaseConnection.get().commit();

      ResultSet rs = st.getGeneratedKeys();
      rs.next();
      info.id = rs.getInt(1);

      rs.close();
      ControlPointImpl point = new ControlPointImpl(info, this);
      point.init();
      getSectorManager().getByID(info.sectorID).getControlPoints().add(point);
      put(point);
    } catch (SQLException e) {
      DatabaseConnection.get().rollback();
      e.printStackTrace();
    }
  }

  public List<ControlPointImpl> getControlPointsFor(SectorImpl sector) {
    List<ControlPointImpl> list = new LinkedList<ControlPointImpl>();
    for (ControlPointImpl cp : getList()) {
      if (cp.getInfo().sectorID == sector.getInfo().id)
        list.add(cp);
    }
    return list;
  }

  public void onCapture(ControlPointImpl point) {
    getSectorManager().getByID(point.getInfo().sectorID).tryCapture(
        point.getInfo().ownerFactionID);
  }

  public void update() {
    List<ControlPointImpl> points = getList();
    for (ControlPointImpl point : points) {
      point.update();
    }
  }
  */
}
