package com.daviserrano.phd.autotests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weka.core.Attribute;
import au.com.bytecode.opencsv.CSVReader;

import com.daviserrano.phd.nlp.ClassificationModels;
import com.daviserrano.phd.nlp.Text2Vec;
import com.daviserrano.phd.nlp.bagofwords.binarymap.BinaryMapBag;
import com.daviserrano.phd.nlp.bagofwords.tfidf.TFIDFBag;
import com.daviserrano.phd.nlp.preprocessing.Capitalization;
import com.daviserrano.phd.nlp.preprocessing.Normalization;
import com.daviserrano.phd.nlp.preprocessing.IPreprocess;
import com.daviserrano.phd.nlp.preprocessing.RemovePonctuation;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;
import com.daviserrano.phd.nlp.tokenizer.WordUnigram;

public class AutoTest {
	public static void main(String[] args) {
		
		Tokenizer t = new WordUnigram();
		IPreprocess p1 = new Normalization();
		IPreprocess p2 = new Capitalization(Capitalization.LOWER_CASE);
		IPreprocess p3 = new RemovePonctuation();
		t.addPreProcessing(p1);
		t.addPreProcessing(p2);
		t.addPreProcessing(p3);
		
		String dataset = System.getProperty("user.dir")+"/dataset/tweetsTratados_base_dados.csv";
		try {
			CSVReader csv = new CSVReader(new FileReader(dataset), ';','"');
			List<String[]> tweets = csv.readAll();
			csv.close();
			
			ArrayList<Attribute> atts = new ArrayList<Attribute>();
			ArrayList<String> classValues = new ArrayList<String>();
			classValues.add("1");
			classValues.add("0");
			classValues.add("-1");
			Attribute classAtt = new Attribute("opinion", classValues);
			Attribute textAtt = new Attribute("text");
			atts.add(textAtt); atts.add(classAtt);
			
			K_Fold test = new K_Fold(10, tweets, atts);
			ClassificationModels cm = new ClassificationModels();

			System.out.println("============= STARTED BINARYMAP BOW - TRAINING BEFORE ===============");
			BinaryMapBag bowBinMap = new BinaryMapBag(t);
			test.setText2vec_training(K_Fold.TEXT2VEC_TRAINING_BEFORE_SPLIT);
			runTestsAndPrintResult(test, cm, bowBinMap);
			
			System.out.println();System.out.println();
			
			System.out.println("============= STARTED BINARYMAP BOW - TRAINING AFTER ===============");
			BinaryMapBag bowBinMap2 = new BinaryMapBag(t);
			test.setText2vec_training(K_Fold.TEXT2VEC_TRAINING_AFTER_SPLIT);
			runTestsAndPrintResult(test, cm, bowBinMap2);
			
			System.out.println();System.out.println();
			
			System.out.println("============= STARTED TF-IDF BOW - TRAINING BEFORE ===============");
			TFIDFBag bowTfidf = new TFIDFBag(t);
			test.setText2vec_training(K_Fold.TEXT2VEC_TRAINING_BEFORE_SPLIT);
			runTestsAndPrintResult(test, cm, bowTfidf);
			
			System.out.println();System.out.println();
			
			System.out.println("============= STARTED TF-IDF BOW - TRAINING AFTER ===============");
			TFIDFBag bowTfidf2 = new TFIDFBag(t);
			test.setText2vec_training(K_Fold.TEXT2VEC_TRAINING_AFTER_SPLIT);
			runTestsAndPrintResult(test, cm, bowTfidf2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void runTestsAndPrintResult(K_Fold test,
			ClassificationModels cm, Text2Vec t2v) {
		
		test.runTests(t2v, cm);
		HashMap<String, TestResult> results = cm.getResults();
		for (String classifier : results.keySet()) {
			String print = results.get(classifier).toString();
			System.out.println(classifier+": "+print);
		}
	}
}
