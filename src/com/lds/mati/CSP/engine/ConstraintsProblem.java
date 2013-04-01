package com.lds.mati.CSP.engine;

import java.util.List;


public class ConstraintsProblem<T> {
	Coinstraint<T>[] coinstraints;
	List<T>[]domains;
	boolean[][] restrictedDomain;
	
	public ConstraintsProblem(Coinstraint<T>[] coinstraints,List<T>[] domains){
		this.coinstraints = coinstraints;
		this.domains = domains;
		restrictedDomain = new boolean[domains.length][];
		for(int i = 0 ; i < restrictedDomain.length ; ++i){
			restrictedDomain[i] = new boolean[domains[i].size()];
		}
	}
	
}
