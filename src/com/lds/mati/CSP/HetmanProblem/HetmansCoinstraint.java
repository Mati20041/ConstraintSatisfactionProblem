package com.lds.mati.CSP.HetmanProblem;

import java.util.List;

import com.lds.mati.CSP.engine.Coinstraint;


public class HetmansCoinstraint implements Coinstraint<Integer> {

	private int hetman;

	public HetmansCoinstraint(int hetman) {
		this.hetman = hetman;
	}

	@Override
	public boolean isSatisfied(List<Integer> vertices) {
		if (hetman == 0) {
			return true;
		} else {
			int position = vertices.get(hetman);
			for (int i = 0; i < hetman; ++i) {
				Integer position2 = vertices.get(i);
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
