/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2015 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.bukkit.util.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends Entity type with tracking properties changes. lazy create changed properties list for memory saves.
 */
public class ObservedEntity extends Entity {
  private List<PropertyEntry> changedProperties;

  public ObservedEntity(int id) {
    super(id);
  }

  /**
   * Execute when some property changes value. Helper method for integer properties.
   */
  protected void onChangeProperty(String name, int newValue) {
    onChangeProperty(name, Integer.toString(newValue));
  }

  /**
   * Execute when some property changes value.
   */
  protected void onChangeProperty(String name, String newValue) {
    if (!hasAnyChangedProperties()) {
      changedProperties = new ArrayList<PropertyEntry>();
    }

    PropertyEntry propertyEntry = getChangedProperty(name);
    if (propertyEntry != null) {
      propertyEntry.value = newValue;
    } else {
      changedProperties.add(new PropertyEntry(name, newValue));
    }

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
      list = new PropertyEntry[changedProperties.size()];
      int index = 0;
      for (PropertyEntry property : changedProperties) {
        list[index++] = new PropertyEntry(property.name, property.value);
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

  public class PropertyEntry {
    public String name;
    public String value;

    public PropertyEntry(String name, String value) {
      this.name = name;
      this.value = value;
    }

  }
}
