package com.lds.mati.CSP.engine;

import java.util.List;


public class ConstraintsProblem<T> {
	List<Coinstraint<T>> coinstraints;
	List<List<T>>domains;
	boolean[][] restrictedDomain;
	
	public ConstraintsProblem(int size,List<Coinstraint<T>> coinstraints,List<List<T>>domains){
		if(domains.size()!=size){
			throw new ArrayIndexOutOfBoundsException();
		}
		this.coinstraints = coinstraints;
		this.domains = domains;
		restrictedDomain = new boolean[size][];
		for(int i = 0 ; i < size ; ++i){
			restrictedDomain[i] = new boolean[domains.get(i).size()];
		}
	}
	
}
