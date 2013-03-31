package com.lds.mati.CSP.sudoku;

import java.util.List;

import com.lds.mati.CSP.engine.Coinstraint;

public class SudokuCoinstraint implements Coinstraint<Integer>{
	private int position;
	public SudokuCoinstraint(int position) {
		
	}

	@Override
	public boolean isSatisfied(List<Integer> vertices) {
		
		return false;
	}

}
