package com.lds.mati.CSP.engine;

import java.util.List;

public interface DomainCoinstrantFactory<T> {
	public List<T>[] getDomain(int arg);
	public Coinstraint<T>[] getCoinstraints(int arg);
}
