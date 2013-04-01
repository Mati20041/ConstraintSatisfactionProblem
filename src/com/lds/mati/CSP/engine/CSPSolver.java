package com.lds.mati.CSP.engine;

import java.lang.reflect.Array;
import java.util.List;

public class CSPSolver<T> {

    T[] vertices;

    public T[] backTracking(ConstraintsProblem<T> problem) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                problem.domains[0].get(0).getClass(), problem.domains.length);
        int[] domainIndex = new int[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
            if (domainIndex[i] >= problem.domains[i].size()) {
                if (i == 0) {
                    return null;
                } else {
                	vertices[i] = null;
                    domainIndex[i] = 0;
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                }
            } else {
                vertices[i] = problem.domains[i].get(domainIndex[i]);
                if (!problem.coinstraints[i].isSatisfied(vertices)) {
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                }
            }
        }
        return vertices;
    }

    public T[] forwardChecking(ConstraintsProblem<T> problem) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                problem.domains[0].get(0).getClass(), problem.domains.length);
        int[] domainIndex = new int[vertices.length];
        boolean isEmptyDomain = false;

        for (int i = 0; i < vertices.length; ++i) {
            List<T> currentDomain = problem.domains[i];
            while (domainIndex[i] < currentDomain.size()
                    && problem.restrictedDomain[i][domainIndex[i]]) {
                domainIndex[i] = domainIndex[i] + 1;
            }
            if (domainIndex[i] >= currentDomain.size()) {
                if (i == 0) {
                    return null;
                } else {
                    clearRestrictedDomain(problem, domainIndex, i);
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                }
            } else {
                vertices[i] = currentDomain.get(domainIndex[i]);
                int j = i + 1;
                for (; j < vertices.length && !isEmptyDomain; ++j) {
                    List<T> temporaryDomain = problem.domains[j];
                    Coinstraint<T> temporaryCoinstraint = problem.coinstraints[j];
                    isEmptyDomain = true;
                    for (int k = 0; k < temporaryDomain.size(); ++k) {
                        if (!problem.restrictedDomain[j][k]) {
                            vertices[j] = temporaryDomain.get(k);
                            if (!temporaryCoinstraint.isSatisfied(vertices)) {
                                problem.restrictedDomain[j][k] = true;
                            } else {
                                isEmptyDomain = false;
                            }
                        }
                    }
                    vertices[j] = null;
                }
                if (isEmptyDomain) {
                    clearRestrictedDomain(problem, domainIndex, i + 1);
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                    isEmptyDomain = false;
                }
            }
        }
        return vertices;
    }

    private void clearRestrictedDomain(ConstraintsProblem<T> problem,
            int[] domainIndex, int i) {
        for (int j = i; j < domainIndex.length; ++j) {
            problem.restrictedDomain[j] = new boolean[problem.restrictedDomain[j].length];
            domainIndex[j] = 0;
        }
    }

    public T[] backTracking(ConstraintsProblem<T> problem, Hook<T> hook) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                problem.domains[0].get(0).getClass(), problem.domains.length);
        int[] domainIndex = new int[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
            if (domainIndex[i] >= problem.domains[i].size()) {
                if (i == 0) {
                    return null;
                } else {
                	vertices[i] = null;
                    domainIndex[i] = 0;
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                }
            } else {
                vertices[i] = problem.domains[i].get(domainIndex[i]);
                if (!problem.coinstraints[i].isSatisfied(vertices)) {
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                }
            }
            hook.partialResult(vertices);
        }
        return vertices;
    }

    public T[] forwardChecking(ConstraintsProblem<T> problem, Hook<T> hook) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                problem.domains[0].get(0).getClass(), problem.domains.length);
        int[] domainIndex = new int[vertices.length];
        boolean isEmptyDomain = false;

        for (int i = 0; i < vertices.length; ++i) {
            List<T> currentDomain = problem.domains[i];
            while (domainIndex[i] < currentDomain.size()
                    && problem.restrictedDomain[i][domainIndex[i]]) {
                domainIndex[i] = domainIndex[i] + 1;
            }
            if (domainIndex[i] >= currentDomain.size()) {
                if (i == 0) {
                    return null;
                } else {
                    clearRestrictedDomain(problem, domainIndex, i);
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                }
            } else {
                vertices[i] = currentDomain.get(domainIndex[i]);
                int j = i + 1;
                for (; j < vertices.length && !isEmptyDomain; ++j) {
                    List<T> temporaryDomain = problem.domains[j];
                    Coinstraint<T> temporaryCoinstraint = problem.coinstraints[j];
                    isEmptyDomain = true;
                    for (int k = 0; k < temporaryDomain.size(); ++k) {
                        if (!problem.restrictedDomain[j][k]) {
                            vertices[j] = temporaryDomain.get(k);
                            if (!temporaryCoinstraint.isSatisfied(vertices)) {
                                problem.restrictedDomain[j][k] = true;
                            } else {
                                isEmptyDomain = false;
                            }
                        }
                    }
                    vertices[j] = null;
                }
                if (isEmptyDomain) {
                    clearRestrictedDomain(problem, domainIndex, i + 1);
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                    isEmptyDomain = false;
                }
            }
            hook.partialResult(vertices);
        }
        return vertices;
    }
}