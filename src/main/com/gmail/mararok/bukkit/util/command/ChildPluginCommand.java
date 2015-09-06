package com.gmail.mararok.bukkit.util.command;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class ChildPluginCommand<P extends JavaPlugin> extends PluginCommand<P> {
  private ParentPluginCommand<P> parent;
  private int index = -1;

  public ChildPluginCommand(String name, CommandMetadata metadata, P plugin) {
    super(name, metadata, plugin);
  }

  public ParentPluginCommand<P> getParent() {
    return parent;
  }

  public boolean hasParent() {
    return parent != null;
  }

  public int getIndex() {
    return index;
  }
  
  public int getDepthLevel() {
    return hasParent() ? parent.getDepthLevel() + 1 : 0;
  }

  public void setParent(ParentPluginCommand<P> newParent, int newIndex) {
    parent = newParent;
    index = newIndex;
  }
}
