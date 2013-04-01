package com.lds.mati.CSP.HetmanProblem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.DomainCoinstrantFactory;

public class HetmansProblemFactory implements DomainCoinstrantFactory<Integer>{
	private static HetmansProblemFactory singleton;
	private HetmansProblemFactory(){
	}
	
	public static HetmansProblemFactory getFactory(){
		if(singleton==null)
			singleton = new HetmansProblemFactory();
		return singleton;
	}
	
	@Override
	public List<Integer>[] getDomain(int arg) {
		List<Integer>[] domains = (List<Integer>[]) Array.newInstance(List.class, arg);
		for(int i = 0 ; i < arg ; ++i){
			List<Integer> tempDom = new ArrayList<>(arg);
			for(int j = 0 ; j < arg ; ++j){
				tempDom.add(j);
			}
			domains[i] = tempDom;
		}
		return domains;
	}

	@Override
	public Coinstraint<Integer>[] getCoinstraints(int arg) {
		Coinstraint<Integer>[] coinstraints = (Coinstraint<Integer>[]) Array.newInstance(Coinstraint.class, arg);
		for(int i = 0 ; i < arg ; ++i){
			coinstraints[i] = new HetmansCoinstraint(i);
		}
		return coinstraints;
	}
}
