package com.lds.mati.CSP.engine;

import java.util.List;

public interface Hook<T> {
	public void partialResult(List<T> result);
}
