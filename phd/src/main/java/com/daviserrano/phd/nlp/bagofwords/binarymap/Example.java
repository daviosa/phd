package com.daviserrano.phd.nlp.bagofwords.binarymap;

import java.util.Map;

import com.daviserrano.phd.nlp.bagofwords.tfidf.TFIDFDocument;
import com.daviserrano.phd.nlp.preprocessing.Capitalization;
import com.daviserrano.phd.nlp.preprocessing.Normalization;
import com.daviserrano.phd.nlp.preprocessing.IPreprocess;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;
import com.daviserrano.phd.nlp.tokenizer.WordUnigram;

public class Example {
	
	public static void main(String[] args) {
		Tokenizer t = new WordUnigram();
		IPreprocess p1 = new Normalization();
		IPreprocess p2 = new Capitalization(Capitalization.LOWER_CASE);
		t.addPreProcessing(p1);
		t.addPreProcessing(p2);
		
		BinaryMapBag binmapbag = new BinaryMapBag(t);
		
		binmapbag.addDocumentAsString("The game of life is a game of everlasting learning");
		binmapbag.addDocumentAsString("The unexamined life is not worth living");
		binmapbag.addDocumentAsString("Never stop learning");
		
		TFIDFDocument d = new TFIDFDocument("The game of life is a game of everlasting learning", t);
		
		Map<String,Integer> m = binmapbag.getTokenIndexes();
		double[] vec = binmapbag.getVector(d);
		
		for (String key : m.keySet()) {
			System.out.println(key+" -> "+vec[m.get(key)]);
		}
	}

}
