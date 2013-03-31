package com.lds.mati.CSP.engine;

import java.util.List;

public interface Coinstraint<T> {
	public boolean isSatisfied(List<T> vertices);
}
