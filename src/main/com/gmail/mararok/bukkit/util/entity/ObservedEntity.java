/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.util.ArrayList;
import java.util.List;

public class ObservedEntity extends Entity {
  private List<PropertyEntry> changedProperties;
  
  public ObservedEntity(int id) {
    super(id);
  }
  
  protected void onChangeProperty(String name, String newValue) {
    if (!hasAnyChangedProperties()) {
      changedProperties = new ArrayList<PropertyEntry>(); // lazy create for memory save
    }

    PropertyEntry propertyEntry = getChangedProperty(name);
    if (propertyEntry != null) {
      propertyEntry.value = newValue;
    }

    changedProperties.add(new PropertyEntry(name, newValue));
  }

  public PropertyEntry getChangedProperty(String name) {
    if (hasAnyChangedProperties()) {
      for (PropertyEntry entry : changedProperties) {
        if (entry.name.equalsIgnoreCase(name)) {
          return entry;
        }
      }
    }
    return null;
  }

  public PropertyEntry[] getChangedProperties() {
    PropertyEntry[] list = null;
    if (hasAnyChangedProperties()) {
      int number = changedProperties.size();
      list = new PropertyEntry[number];
      int index = 0;
      try {
        for (PropertyEntry property : changedProperties) {
          list[index] = (PropertyEntry) property.clone();
        }
      } catch (CloneNotSupportedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return list;
  }

  public void clearChanges() {
    changedProperties = null;
  }

  public boolean hasAnyChangedProperties() {
    return changedProperties != null;
  }

  public class PropertyEntry implements Cloneable {
    public String name;
    public String value;

    public PropertyEntry(String name, String value) {
      this.name = name;
      this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }
}
