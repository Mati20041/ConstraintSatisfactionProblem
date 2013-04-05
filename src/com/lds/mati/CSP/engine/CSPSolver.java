package com.lds.mati.CSP.engine;

import java.lang.reflect.Array;
import java.util.List;

public class CSPSolver<T> {

    private T[] vertices;
    private int[] domainIndex;
    private int iterations = 0;
    private int backs = 0;
    
    @SuppressWarnings({ "rawtypes", "unchecked"})
	public T[] backTracking(ConstraintsProblem<T> problem) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        if(vertices==null)vertices = (T[]) Array.newInstance(
                sampleClass, problem.domains.length);
        if(domainIndex==null)
        	domainIndex = new int[vertices.length];
        else
        	domainIndex[domainIndex.length-1] = domainIndex[domainIndex.length-1]+1;
        
        for (int i = 0; i < vertices.length; ++i) {
            if (domainIndex[i] >= problem.domains[i].size()) {
                if (i == 0) {
                    return null;
                } else {
                	vertices[i] = null;
                    domainIndex[i] = 0;
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                    backs++;
                }
            } else {
                vertices[i] = problem.domains[i].get(domainIndex[i]);
                if (!problem.coinstraints[i].isSatisfied(vertices)) {
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                }
            }
            ++iterations;
        }
        return vertices;
    }
    

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public T[] backTracking(ConstraintsProblem<T> problem, Hook<T> hook) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        if(vertices==null)vertices = (T[]) Array.newInstance(
                sampleClass, problem.domains.length);
        if(domainIndex==null)domainIndex = new int[vertices.length];
        else{
        	domainIndex[domainIndex.length-1] = domainIndex[domainIndex.length-1]+1;
        }
        
        for (int i = 0; i < vertices.length; ++i) {
            if (domainIndex[i] >= problem.domains[i].size()) {
                if (i == 0) {
                    return null;
                } else {
                	vertices[i] = null;
                    domainIndex[i] = 0;
                    domainIndex[i - 1] = domainIndex[i - 1] + 1;
                    i -= 2;
                    ++backs;
                }
            } else {
                vertices[i] = problem.domains[i].get(domainIndex[i]);
                if (!problem.coinstraints[i].isSatisfied(vertices)) {
                    domainIndex[i] = domainIndex[i] + 1;
                    --i;
                }
            }
            ++iterations;
            hook.partialResult(vertices);
        }
        return vertices;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public T[] forwardChecking(ConstraintsProblem<T> problem) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                sampleClass, problem.domains.length);
        if(domainIndex==null)domainIndex = new int[vertices.length];
        else{
        	domainIndex[domainIndex.length-1] = domainIndex[domainIndex.length-1]+1;
        }
        
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
                    vertices[i] = null;
                    i -= 2;
                    ++backs;
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
                    vertices[i] = null;
                    --i;
                    isEmptyDomain = false;
                }
            }
            ++iterations;
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


    @SuppressWarnings({ "rawtypes", "unchecked" })
	public T[] forwardChecking(ConstraintsProblem<T> problem, Hook<T> hook) {
        Class sampleClass = problem.domains[0].get(0).getClass();
        vertices = (T[]) Array.newInstance(
                sampleClass, problem.domains.length);
        if(domainIndex==null)domainIndex = new int[vertices.length];
        else{
        	domainIndex[domainIndex.length-1] = domainIndex[domainIndex.length-1]+1;
        }
        
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
                    vertices[i] = null;
                    i -= 2;
                    ++backs;
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
                    vertices[i] = null;
                    --i;
                    isEmptyDomain = false;
                }
            }
            ++iterations;
            hook.partialResult(vertices);
        }
        
        return vertices;
    }
    
    public void reset(){
    	this.domainIndex = null;
    	this.vertices = null;
    }
    
    public int getIterations(){
    	return iterations;
    }
    
    public int getBacks(){
    	return backs;
    }
}