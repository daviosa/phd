package com.daviserrano.phd.nlp;

import java.util.ArrayList;
import java.util.HashMap;

import com.daviserrano.phd.autotests.TestResult;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;
import weka.core.Instance;
import weka.core.Instances;

public class ClassificationModels {
	
	private ArrayList<Classifier> classifiers;
	private HashMap<String, TestResult> results;
	
	public ClassificationModels() {
		classifiers = new ArrayList<Classifier>();
		classifiers.add(new SMO());
		classifiers.add(new NaiveBayes());
		classifiers.add(new RandomTree());
		classifiers.add(new J48());
		classifiers.add(new LibSVM());
		
		HashMap<String, TestResult> r = new HashMap<String, TestResult>();
		for (Classifier classifier : classifiers) {
			r.put(classifier.getClass().getName(), new TestResult());
		}
		this.setResults(r);
	}
	
	public ArrayList<Classifier> trainModels(Instances trainData) {
		ArrayList<Classifier> error = new ArrayList<Classifier>();
		for (Classifier classifier : this.classifiers) {
			try {
				classifier.buildClassifier(trainData);
			} catch (Exception e) {
				error.add(classifier);
			}
		}
		classifiers.removeAll(error);
		
		return error;
	}

	public void testModels(Instances testInsts) {
		for (Classifier classifier : this.classifiers) {
			TestResult tr = results.get(classifier.getClass().getName());
			for (Instance instance : testInsts) {
				try {
					double value = instance.classValue();
					double prediction = classifier.classifyInstance(instance);
					tr.analysePrediciton(prediction,value);
				} catch (Exception e) {
					tr.noPredictionMade();
				}
			}
		}
	}

	public HashMap<String, TestResult> getResults() {
		return results;
	}

	private void setResults(HashMap<String, TestResult> results) {
		this.results = results;
	}

}
