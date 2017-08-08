package com.daviserrano.phd.nlp.token.context;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.tfidf.IDF;
import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;

public class MeaningContext implements Context{
	
	private String value;
	private ArrayList<Token> terms;
	
	private IDF idf;
	
	public MeaningContext(){
		
	}
	
	@SuppressWarnings("unchecked")
	public MeaningContext clone(){
		MeaningContext mc = new MeaningContext();
		mc.value = this.value;
		mc.terms = (ArrayList<Token>) this.terms.clone();
		mc.setIdf(this.idf.clone());
		return mc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value, Tokenizer t) {
		this.value = value;
		List<Token> tokens = t.tokenizeText(this.value);
		idf.addNewDocument(tokens);
	}

	public IDF getIdf() {
		return idf;
	}

	public void setIdf(IDF idf) {
		this.idf = idf;
	}

	public List<Token> getTerms() {
		return terms;
	}
}
