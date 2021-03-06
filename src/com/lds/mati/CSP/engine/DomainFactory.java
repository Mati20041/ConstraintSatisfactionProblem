package com.lds.mati.CSP.engine;

import java.util.List;

public interface DomainFactory<T> {
	public List<List<T>> getDomain(int arg);
	public List<Coinstraint<T>> getCoinstraints(int arg);
}
