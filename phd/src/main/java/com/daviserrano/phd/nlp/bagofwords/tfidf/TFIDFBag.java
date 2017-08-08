package com.daviserrano.phd.nlp.bagofwords.tfidf;

import java.util.List;

import com.daviserrano.phd.nlp.bagofwords.Bag;
import com.daviserrano.phd.nlp.tfidf.IDF;
import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;


public class TFIDFBag extends Bag<TFIDFDocument> {
	
	public IDF idf;

	public TFIDFBag(Tokenizer t) {
		super(t);
		this.idf = new IDF();
		
	}

	public double[] getVector(TFIDFDocument d) {
		double[] vector = new double[this.maxIndex+1];
		
		List<Token> tokens = d.getTokens();
		for (Token token : tokens) {
			String key = token.getValue();
			Integer i = this.tokenIndexes.getOrDefault(key, -1);
			
			if(i>=0){
				vector[i] = d.getTokenTF(key)*this.idf.getTermIDF(key);
			}
		}
		
		return vector;
	}

	protected void processDocument(TFIDFDocument d) {
		this.idf.addNewDocument(d.getTokens());
	}

	public TFIDFDocument string2Document(String str) {
		TFIDFDocument tid = new TFIDFDocument(str, this.tokenizer);
		return tid;
	}
}
