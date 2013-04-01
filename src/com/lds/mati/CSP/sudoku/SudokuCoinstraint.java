package com.lds.mati.CSP.sudoku;

import com.lds.mati.CSP.engine.Coinstraint;

public class SudokuCoinstraint implements Coinstraint<Integer>{
	private int position;
	public SudokuCoinstraint(int position) {
		this.position = position;
	}


	@Override
	public boolean isSatisfied(Integer[] vertices) {
		return false;
	}

}
