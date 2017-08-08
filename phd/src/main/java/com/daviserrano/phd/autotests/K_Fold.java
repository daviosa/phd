package com.daviserrano.phd.autotests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import com.daviserrano.phd.nlp.ClassificationModels;
import com.daviserrano.phd.nlp.Text2Vec;

public class K_Fold {
	
	public static final int TEXT2VEC_TRAINING_BEFORE_SPLIT = 0;
	public static final int TEXT2VEC_TRAINING_AFTER_SPLIT = 1;
	
	private int k;
	private ArrayList<List<String[]>> folds;
	private List<String[]> data;
	private ArrayList<Attribute> atts;
	private int foldSize;
	private int text2vec_training;
	
	public K_Fold(int n, List<String[]> data, ArrayList<Attribute> atts) {
		if(n>1 && data.size()>1){
			this.data = new ArrayList<String[]>(data);
			this.atts = atts;
			this.setK(n);
			this.text2vec_training = K_Fold.TEXT2VEC_TRAINING_AFTER_SPLIT;
		}else{
			return;
		}
	}
	
	private void splitFolds(List<String[]> data2) {
		Collections.shuffle(data2);
		for (int i = 0; i < k; i++) {
			int startIndex = i*foldSize;
			int endIndex = startIndex + foldSize;
			if(endIndex>data2.size())
				endIndex = data2.size();
			folds.add(data2.subList(startIndex, endIndex));
		}
	}
	
	public void runTests(Text2Vec t2v, ClassificationModels cm){
		if(this.data.size()>1 && this.k>1){
			if(this.text2vec_training == TEXT2VEC_TRAINING_BEFORE_SPLIT)
				trainText2Vec(this.data, t2v);
			for (int i = 0; i < this.folds.size(); i++) {
				System.out.print("START FOLD "+(i+1));
				List<String[]> train = new ArrayList<String[]>();
				List<String[]> test = new ArrayList<String[]>();
				for (int j = 0; j < this.folds.size(); j++) {
					if(i != j){
						train.addAll(this.folds.get(j));
					}else{
						test = this.folds.get(i);
					}
				}
				if(this.text2vec_training == TEXT2VEC_TRAINING_AFTER_SPLIT)
					trainText2Vec(train, t2v);
				Instances dataTrain = runTrain(t2v, train);
				cm.trainModels(dataTrain);
				
				runTests(t2v, cm, test);
			}
			System.out.println();
		}else{
			return;
		}
	}

	private void runTests(Text2Vec t2v, ClassificationModels cm,
			List<String[]> test) {
		Instances testInsts = this.createEmptyInstances(t2v,"Test Rel");
		testInsts.setClassIndex(testInsts.numAttributes()-1);
		ArrayList<Instance> testData  = createInstaces(test, t2v, testInsts);
		testInsts.addAll(testData);
		cm.testModels(testInsts);
	}

	private Instances runTrain(Text2Vec t2v, List<String[]> train) {
		Instances dataTrain = this.createEmptyInstances(t2v,"Training Rel");
		dataTrain.setClassIndex(dataTrain.numAttributes()-1);
		ArrayList<Instance> trainInsts = createInstaces(train, t2v, dataTrain);
		dataTrain.addAll(trainInsts);
		return dataTrain;
	}
	
	private ArrayList<Instance> createInstaces(List<String[]> data, Text2Vec t2v, Instances dataset) {
		ArrayList<Instance> instances = new ArrayList<Instance>();
		for (String[] strings : data) {
			double[] str2vec = t2v.string2Vector(strings[Configuration.TEXT_INDEX]);
			Instance inst = new DenseInstance(dataset.numAttributes());
			inst.setDataset(dataset);
			for (int i = 0; i < dataset.numAttributes(); i++) {
				if(i<str2vec.length){
					inst.setValue(dataset.attribute(i), str2vec[i]);
				}else{
					if(i-str2vec.length != Configuration.TEXT_INDEX)
						inst.setValue(dataset.attribute(i), strings[i-str2vec.length]);
				}
			}
			try{
				inst.setClassValue(strings[strings.length-1]);
			}catch(Exception e){
				e.printStackTrace();
			}
			instances.add(inst);
		}
		return instances;
	}

	private void trainText2Vec(List<String[]> train, Text2Vec t2v) {
		for (String[] strings : train) {
			t2v.addDocumentAsString(strings[Configuration.TEXT_INDEX]);
		}
		
	}

	private Instances createEmptyInstances(Text2Vec t2v, String relName){
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for (int j = 0; j < t2v.getVectorSize(); j++) {
			Attribute atj = new Attribute("att_"+j);
			attributes.add(atj);
		}
		for (int i = 0; i < this.atts.size(); i++) {
			if(i!=Configuration.TEXT_INDEX)
				attributes.add(this.atts.get(i));
		}
		int trainSize = (this.foldSize*(this.k-1));
		return new Instances(relName, attributes, trainSize);
	}

	public int getK() {
		return k;
	}

	private void setK(int k) {
		this.k = k;
		updateFolds();
	}
	
	private void updateFolds(){
		this.foldSize = (int) Math.ceil(data.size()*1.0/k);
		this.folds = new ArrayList<List<String[]>>(this.k);
		this.splitFolds(this.data);
	}

	public int getText2vec_training() {
		return text2vec_training;
	}

	public void setText2vec_training(int text2vec_training2) {
		if(text2vec_training2 == TEXT2VEC_TRAINING_AFTER_SPLIT || text2vec_training2 == TEXT2VEC_TRAINING_BEFORE_SPLIT)
			this.text2vec_training = text2vec_training2;
	}
}