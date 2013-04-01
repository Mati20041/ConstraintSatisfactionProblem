package com.lds.mati.CSP.engine;


public interface Coinstraint<T> {
	public boolean isSatisfied(T[] vertices);
}
