/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gmail.mararok.bukkit.util.database.CachedQuery;
import com.gmail.mararok.bukkit.util.database.DatabaseConnection;
import com.gmail.mararok.bukkit.util.entity.ObservedEntity.PropertyEntry;

public abstract class EntityDatabaseMapper<E extends Entity, ED> extends EntityMapper<E, ED> {
  protected final static int INSERT_QUERY_SQLID = 0;
  protected final static int SELECT_QUERY_SQLID = 1;
  protected final static int UPDATE_QUERY_SQLID = 2;
  protected final static int DELETE_QUERY_SQLID = 3;
  
  private DatabaseConnection connection;
  private CachedQuery[] cachedQueries;
  private String tableName;

  public EntityDatabaseMapper(EntityFactory<E, ED> entityFactory, DatabaseConnection connection, CachedQuery[] sqlQueries, String tableName) {
    super(entityFactory);
    this.connection = connection;
    this.cachedQueries = sqlQueries;
    this.tableName = tableName;
  }

  public E findById(int id) throws Exception {
    PreparedStatement query = getCachedQuery(SELECT_QUERY_SQLID);
     query.setString(1, "*");
     query.setString(2, tableName);
     query.setString(3, "id=" + id);
    ResultSet resultSet = query.executeQuery();
    if (resultSet.first()) {
      createEntity(resultSet);
    }
    throw new Exception("No entity in table("+tableName+") with id("+id+")");
  }

  public List<E> findAll() throws Exception {
    PreparedStatement query = getCachedQuery(SELECT_QUERY_SQLID);
     query.setString(1, "*");
     query.setString(2, tableName);
    ResultSet resultSet = query.executeQuery();
    List<E> list = new LinkedList<E>();
    while (resultSet.next()) {
      list.add(createEntity(resultSet));
    }
   
    return list;
  }

  public void saveChanges(ObservedEntity entity) throws Exception {
    if (entity.hasAnyChangedProperties()) {
      PropertyEntry[] properties = entity.getChangedProperties();
      String setString = "";
      for (PropertyEntry property : properties) {
        setString += property.name+"="+property.value;
      }
      
      PreparedStatement query = getCachedQuery(UPDATE_QUERY_SQLID);
        query.setString(1, tableName);
        query.setString(2, setString);
        query.setString(3, "id=" + entity.getId());
      query.executeUpdate();
    }
  }

  protected void delete(Entity entity) throws SQLException {
    PreparedStatement query = getCachedQuery(DELETE_QUERY_SQLID);
      query.setString(1, tableName);
      query.setString(2, "id=" + entity.getId());
    query.executeUpdate();
  }

  protected int insert(String columns, String values) throws SQLException {
    PreparedStatement query = getCachedQuery(DELETE_QUERY_SQLID);
      query.setString(1, tableName);
      query.setString(2, columns);
      query.setString(3, values);
    query.executeUpdate();
    ResultSet result = query.getGeneratedKeys();
    if (result.next()) {
      return result.getInt(1);
    }
    
    return 0;
  }
  
  protected E createEntity(ResultSet resultSet) throws Exception {
    ED entityData = createEntityData(resultSet);
    return entityFactory.create(entityData);
  }
  
  protected abstract ED createEntityData(ResultSet resultSet) throws SQLException;

  protected PreparedStatement getCachedQuery(int index) throws SQLException {
    return cachedQueries[index].getCompiledQuery();
  }

  protected DatabaseConnection getConnection() {
    return connection;
  }
}
