package com.lds.mati.CSP.engine;

import java.util.ArrayList;
import java.util.List;


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
			while (domainIndex[i] < problem.domains.get(i).size()
					&& problem.restrictedDomain.get(i).contains(
							problem.domains.get(i).get(domainIndex[i]))) {
				domainIndex[i] = domainIndex[i] + 1;
			}
			if (domainIndex[i] >= problem.domains.get(i).size()) {
				if (i == 0) {
					return null;
				} else {
					clearRestrictedDomains(problem, domainIndex, i);
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}
			} else {
				vertices.set(i, problem.domains.get(i).get(domainIndex[i]));
				isEmptyDomain = setRestrictedDomains(problem, vertices, i);
				if (isEmptyDomain) {
					clearRestrictedDomains(problem, domainIndex, i);
					domainIndex[i - 1] = domainIndex[i - 1] + 1;
					i -= 2;
				}

			}
		}
		return vertices;
	}

	private boolean setRestrictedDomains(
			ConstraintsProblem<T> problem, List<T> vertices,
			int i) {
		boolean isEmptyDomain=false;
		for (int j = i + 1; j < vertices.size() && !isEmptyDomain; ++j) {
			for (int k = 0; k < problem.domains.get(j).size() && !isEmptyDomain; ++k) {
				vertices.set(j, problem.domains.get(j).get(k));
				if (!problem.coinstraints.get(j).isSatisfied(vertices)) {
					problem.restrictedDomain.get(j).add(
							problem.domains.get(j).get(k));
					if (problem.restrictedDomain.get(j).size() == problem.domains
							.get(j).size()) {
						isEmptyDomain = true;
					}
				}
			}
			vertices.set(j, null);
		}
		return isEmptyDomain;
	}

	private static <T> void clearRestrictedDomains(
			ConstraintsProblem<T> problem, int[] domainIndex, int i) {
		for (int j = i; j < problem.restrictedDomain.size(); ++j) {
			problem.restrictedDomain.get(j).clear();
			domainIndex[j] = 0;
		}
	}

}