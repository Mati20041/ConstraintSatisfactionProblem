package com.lds.mati.CSP.HetmanProblem;

import java.util.ArrayList;
import java.util.List;

import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.DomainCoinstrantFactory;

public class HetmansProblem implements DomainCoinstrantFactory<Integer>{
	private static HetmansProblem singleton;
	private HetmansProblem(){
	}
	
	public static HetmansProblem getFactory(){
		if(singleton==null)
			singleton = new HetmansProblem();
		return singleton;
	}
	
	@Override
	public List<List<Integer>> getDomain(int arg) {
		List<List<Integer>> domains = new ArrayList<>(arg);
		for(int i = 0 ; i < arg ; ++i){
			List<Integer> tempDom = new ArrayList<>(8);
			for(int j = 0 ; j < arg ; ++j){
				tempDom.add(j);
			}
			domains.add(tempDom);
		}
		return domains;
	}

	@Override
	public List<Coinstraint<Integer>> getCoinstraints(int arg) {
		List<Coinstraint<Integer>> coinstraints = new ArrayList<>(arg);
		for(int i = 0 ; i < arg ; ++i){
			coinstraints.add(new HetmansCoinstraint(i));
		}
		return coinstraints;
	}
}
