package com.lds.mati.CSP.HetmanProblem;


import com.lds.mati.CSP.engine.Coinstraint;


public class HetmansCoinstraint implements Coinstraint<Integer> {

	private int hetman;

	public HetmansCoinstraint(int hetman) {
		this.hetman = hetman;
	}

	@Override
	public boolean isSatisfied(Integer[] vertices) {
		if (hetman == 0) {
			return true;
		} else {
			int position = vertices[hetman];
			for (int i = 0; i < hetman; ++i) {
				Integer position2 = vertices[i];
				if(position2 == null)
					continue;
				if (hetman - i == Math.abs(position2 - position) || position2 == position) {
					return false;
				}
			}
			return true;
		}
	}
}
