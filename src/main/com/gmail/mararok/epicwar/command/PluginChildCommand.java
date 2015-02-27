package com.gmail.mararok.epicwar.command;

import com.gmail.mararok.epicwar.EpicWarPlugin;

public class PluginChildCommand extends PluginCommand {
	private PluginParentCommand parent;
	private int index = -1;
	
	public PluginChildCommand(EpicWarPlugin plugin, String name, PluginParentCommand parent) {
		super(plugin, name);
		this.parent = parent;
	}
	
	public PluginParentCommand getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public int getDepthLevel() {
		if (hasParent())
			return parent.getDepthLevel()+1;
		return 0;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int newIndex) {
		index = newIndex;
	}

}
