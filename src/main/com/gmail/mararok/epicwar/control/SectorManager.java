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

import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.epicwar.War;
import com.gmail.mararok.epicwar.sector.SectorExistsException;
import com.gmail.mararok.epicwar.util.DataSetManager;

public class SectorManager {
  private static int SQLID_AddSector = -1;

  public static void precompileSQL() throws SQLException {
    int[] ids = DatabaseConnection.get().prepareCachedQueriesFromScript(
        "SectorsQueries");

    SQLID_AddSector = ids[0];
  }

  public SectorManager(SectorDao sectorDao, ControlPointDao controlPointDao, War war) {
    super(war);
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
  public void dump2File() throws IOException {
    dump2File("Sectors");
  }

  @Override
  public void dispose() {
  }
}
