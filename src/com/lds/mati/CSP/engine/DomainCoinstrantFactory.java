package com.lds.mati.CSP.engine;

import java.util.List;

public interface DomainCoinstrantFactory<T> {
	public List<T>[] getDomain(Object arg);
	public Coinstraint<T>[] getCoinstraints(Object arg);
}
