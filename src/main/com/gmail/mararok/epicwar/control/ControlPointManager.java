package com.gmail.mararok.epicwar.control;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.util.DataSetManager;

public class ControlPointManager {
  private ControlPointsUpdater updater;
  
  private SectorManager sectors;
  
  private ControlPointDao controlPointDao;

  public ControlPointManager(SectorManager sectors, ControlPointDao controlPointDao) {
    this.sectors = sectors;
    this.controlPointDao = controlPointDao;
  }
  
  public void load() throws Exception {

    PreparedStatement controlPointsStatement = DatabaseConnection
        .get()
        .prepareQuery(
            "SELECT id,name,sectorID,ownerID,centerX,centerY,centerZ,radius,power,maxPower FROM ew_controlPoints WHERE warID = ?");
    controlPointsStatement.setInt(1, getWar().getID());
    ResultSet results = controlPointsStatement.executeQuery();

    while (results.next()) {
      put(new ControlPoint(ControlPointInfo.fromDBResults(results), this));
    }

    controlPointsStatement.close();
    getPlugin().logInfo("Loaded " + size() + " control points");
  }

  private void createNoControlPoint() {
    ControlPointInfo info = new ControlPointInfo();
    info.name = "No point";
    put(new EmptyControlPoint(info, this));
  }

  public void init() {
    List<ControlPoint> points = getList();
    for (ControlPoint point : points) {
      point.init();
    }
    updater = new ControlPointsUpdater(this);
    updater.runTaskTimer(getPlugin(), 5 * 20, 5 * 20);
  }

  public void setUpdate(boolean update) {
    updater.setEnabled(update);
  }

  public void create(ControlPointInfo info) throws ControlPointExistsException {
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
      ControlPoint point = new ControlPoint(info, this);
      point.init();
      getSectors().getByID(info.sectorID).getControlPoints().add(point);
      put(point);
    } catch (SQLException e) {
      DatabaseConnection.get().rollback();
      e.printStackTrace();
    }
  }

  public List<ControlPoint> getControlPointsFor(Sector sector) {
    List<ControlPoint> list = new LinkedList<ControlPoint>();
    for (ControlPoint cp : getList()) {
      if (cp.getInfo().sectorID == sector.getInfo().id)
        list.add(cp);
    }
    return list;
  }

  public void onCapture(ControlPoint point) {
    getSectors().getByID(point.getInfo().sectorID).tryCapture(
        point.getInfo().ownerFactionID);
  }

  public void update() {
    List<ControlPoint> points = getList();
    for (ControlPoint point : points) {
      point.update();
    }
  }

  @Override
  public void dump2File() throws IOException {
    dump2File("Points");
  }

  @Override
  public void dispose() {
    updater.cancel();
  }
}
