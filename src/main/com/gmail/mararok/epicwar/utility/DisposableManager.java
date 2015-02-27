/**
 * EpicWar
 * The MIT License
 * Copyright (C) 2014 Mararok <mararok@gmail.com>
 */
package com.gmail.mararok.epicwar.utility;

import java.util.LinkedList;
import java.util.List;

public class DisposableManager implements Disposable {
	private List<Disposable> disposables;
	
	public DisposableManager() {
		disposables = new LinkedList<Disposable>();
	}
	
	public void add(Disposable disposable) {
		disposables.add(disposable);
	}
	@Override
	public void dispose() {
		for (Disposable disposable : disposables) {
			disposable.dispose();
		}
	}
}
