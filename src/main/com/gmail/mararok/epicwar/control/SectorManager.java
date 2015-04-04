/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.control;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Location;

import com.gmail.mararok.bukkit.util.Disposable;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.sector.SectorExistsException;
import com.gmail.mararok.epicwar.util.DataSetManager;

public class SectorManager implements Disposable {
  private List<Sector> sectors;
  private SubsectorMap subsectorMap;
  
  private SectorDao sectorDao;
  private ControlPointDao controlPointDao;
  private SubsectorDao subsectorDao;
  
  private War war;
  
  public SectorManager(SectorDao sectorDao, ControlPointDao controlPointDao, SubsectorDao subsectorDao, War war) {
    this.sectorDao = sectorDao;
    this.controlPointDao = controlPointDao;
    this.subsectorDao = subsectorDao;
    this.war = war;
  }

  public void load() throws Exception {
    createWildSector();
    PreparedStatement sectorsInfoStatement = DatabaseConnection
        .get()
        .prepareQuery(
            "SELECT id,name,desc,ownerID,centerX,centerZ,size FROM ew_sectors WHERE warID = ?");
    sectorsInfoStatement.setInt(1, getWar().getID());
    ResultSet results = sectorsInfoStatement.executeQuery();
    Sector sector;
    while (results.next()) {
      sector = new Sector(SectorInfo.fromDBResults(results), this);
      put(sector);
    }

    sectorsInfoStatement.close();
    getPlugin().logInfo("Loaded " + size() + " sectors");
  }

  private void createWildSector() {
    SectorInfo info = new SectorInfo();
    info.id = 0;
    info.name = "Wild";
    info.desc = "This is wild :)";
    put(new WildSector(info, this));
  }

  public void init() {
    List<Sector> sectors = getList();
    for (Sector sector : sectors) {
      sector.init();
    }
  }

  public void createFromList(List<SectorInfo> sectors)
      throws SectorExistsException, SQLException {
    for (SectorInfo info : sectors) {
      create(info);
    }
  }

  public void create(SectorInfo info) throws SectorExistsException,
      SQLException {
    if (isExists(info.name)) {
      throw new SectorExistsException(info.name);
    }

    PreparedStatement st = DatabaseConnection.get().getCachedQuery(
        SQLID_AddSector);
    st.setString(1, info.name);
    st.setInt(2, getWar().getID());
    st.setInt(3, info.centerX);
    st.setInt(4, info.centerZ);
    st.setInt(5, info.size);

    st.executeUpdate();
    DatabaseConnection.get().commit();
    ResultSet rs = st.getGeneratedKeys();
    info.id = rs.getInt(1);

    Sector sector = new Sector(info, this);
    sector.init();
    put(sector);
    rs.close();
  }

  public Sector getFromLocation(Location loc) {
    ApplicableRegionSet regions = getRegions().getApplicableRegions(loc);

    for (ProtectedRegion region : regions) {
      if (NameConverter.isValidRegionName(Sector.REGION_PREFIX, region.getId())) {
        return getByName(NameConverter.regionNameToConvertedName(
            Sector.REGION_PREFIX, region.getId()));
      }
    }

    return getByID(0);
  }


  @Override
  public void dispose() {
  }

  public War getWar() {
    return 
  }
}
