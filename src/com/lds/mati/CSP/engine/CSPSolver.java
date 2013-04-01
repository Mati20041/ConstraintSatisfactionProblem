package com.lds.mati.CSP.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



public class CSPSolver<T> {
	
	 List<T> vertices;
	

	public List<T> backTracking(ConstraintsProblem<T> problem) {
		vertices = new ArrayList<>(problem.domains.size());
		for (int i = 0; i < problem.domains.size(); ++i) {
			vertices.add(null);
		}
		int[] domainIndex = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); ++i) {
			if (domainIndex[i] >= problem.domains.get(i).size()) {
				if (i == 0) {
					return null;
				} else {
					domainIndex[i] = 0;
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}
			} else {
				vertices.set(i, problem.domains.get(i).get(domainIndex[i]));
				if (!problem.coinstraints.get(i).isSatisfied(vertices)) {
					domainIndex[i] = domainIndex[i] + 1;
					--i;
				}
			}
		}
		return vertices;
	}

	public List<T> forwardChecking(ConstraintsProblem<T> problem) {
		vertices = new ArrayList<>(problem.domains.size());
		for (int i = 0; i < problem.domains.size(); ++i) {
			vertices.add(null);
		}
		int[] domainIndex = new int[vertices.size()];
		boolean isEmptyDomain = false;
		
		for (int i = 0; i < vertices.size(); ++i) {
			List<T> currentDomain = problem.domains.get(i);
			while (domainIndex[i] < currentDomain.size()
					&& problem.restrictedDomain[i][domainIndex[i]]) {
				domainIndex[i] = domainIndex[i] + 1;
			}
			if (domainIndex[i] >= currentDomain.size()) {
				if (i == 0) {
					return null;
				} else {
					clearRestrictedDomains(problem, domainIndex, i);
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}
			} else {
				vertices.set(i, currentDomain.get(domainIndex[i]));
				int j = i + 1;
				for (; j < vertices.size() && !isEmptyDomain; ++j) {
					List<T> temporaryDomain = problem.domains.get(j);
					Coinstraint<T> temporaryCoinstraint = problem.coinstraints.get(j);
					isEmptyDomain = true;
					for (int k = 0; k < temporaryDomain.size() && isEmptyDomain; ++k) {
						vertices.set(j, temporaryDomain.get(k));
						if (!temporaryCoinstraint.isSatisfied(vertices)) {
							problem.restrictedDomain[j][k]=true;
						} else{
							isEmptyDomain = false;
						}
					}
					vertices.set(j, null);
				}
				if (isEmptyDomain) {
					clearRestrictedDomains(problem, domainIndex, i+1);
					domainIndex[i] = domainIndex[i] + 1;
					--i;
					isEmptyDomain = false;
				}
			}
		}
		return vertices;
	}


	private static <T> void clearRestrictedDomains(
			ConstraintsProblem<T> problem, int[] domainIndex, int i) {
		for (int j = i; j < problem.restrictedDomain.length; ++j) {
			problem.restrictedDomain[j] = new boolean[problem.restrictedDomain[i].length];
			domainIndex[j] = 0;
		}
	}

	public List<T> backTracking(ConstraintsProblem<T> problem,
			Hook<T> hook) {
		vertices = new ArrayList<>(problem.domains.size());
		for (int i = 0; i < problem.domains.size(); ++i) {
			vertices.add(null);
		}
		int[] domainIndex = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); ++i) {
			if (domainIndex[i] >= problem.domains.get(i).size()) {
				if (i == 0) {
					return null;
				} else {
					domainIndex[i] = 0;
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}
			} else {
				vertices.set(i, problem.domains.get(i).get(domainIndex[i]));
				if (!problem.coinstraints.get(i).isSatisfied(vertices)) {
					domainIndex[i] = domainIndex[i] + 1;
					--i;
				}
			}
			hook.partialResult(vertices);
		}
		return vertices;
	}

	public List<T> forwardChecking(ConstraintsProblem<T> problem,
			Hook<T> hook) {
		vertices = new ArrayList<>(problem.domains.size());
		for (int i = 0; i < problem.domains.size(); ++i) {
			vertices.add(null);
		}
		int[] domainIndex = new int[vertices.size()];
		boolean isEmptyDomain = false;
		
		for (int i = 0; i < vertices.size(); ++i) {
			List<T> currentDomain = problem.domains.get(i);
			while (domainIndex[i] < currentDomain.size()
					&& problem.restrictedDomain[i][domainIndex[i]]) {
				domainIndex[i] = domainIndex[i] + 1;
			}
			if (domainIndex[i] >= currentDomain.size()) {
				if (i == 0) {
					return null;
				} else {
					clearRestrictedDomains(problem, domainIndex, i);
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}
			} else {
				vertices.set(i, currentDomain.get(domainIndex[i]));
				int j = i + 1;
				for (; j < vertices.size() && !isEmptyDomain; ++j) {
					List<T> temporaryDomain = problem.domains.get(j);
					Coinstraint<T> temporaryCoinstraint = problem.coinstraints.get(j);
					isEmptyDomain = true;
					for (int k = 0; k < temporaryDomain.size() && isEmptyDomain; ++k) {
						vertices.set(j, temporaryDomain.get(k));
						if (!temporaryCoinstraint.isSatisfied(vertices)) {
							problem.restrictedDomain[j][k]=true;
						} else{
							isEmptyDomain = false;
						}
					}
					vertices.set(j, null);
				}
				if (isEmptyDomain) {
					clearRestrictedDomains(problem, domainIndex, i+1);
					domainIndex[i] = domainIndex[i] + 1;
					--i;
					isEmptyDomain = false;
				}
				hook.partialResult(vertices);
			}
		}
		return vertices;
	}

}