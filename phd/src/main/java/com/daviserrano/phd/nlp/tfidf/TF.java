package com.daviserrano.phd.nlp.tfidf;

import java.util.HashMap;
import java.util.List;

import com.daviserrano.phd.nlp.token.Token;

public class TF implements Cloneable{
	
	private Double num_words;
	
	private HashMap<String, Integer> terms_values;
	
	public TF(){
		this.num_words = 0.0;
		this.terms_values = new HashMap<String, Integer>();
	}
	
	@SuppressWarnings("unchecked")
	public TF clone(){
		TF i = new TF();
		i.num_words = this.num_words;
		i.terms_values = (HashMap<String, Integer>) this.terms_values.clone();
		return i;
	}
	
	public void setDocument(List<Token> tokens){
		if(tokens.size()>0){
			for (Token term : tokens) {
				this.num_words++;
				Integer newValue = this.terms_values.getOrDefault(term.getValue(), 0)+1;
				this.terms_values.put(term.getValue(), newValue);
			}
		}
	}
	
	public Double getTermTF(String term){
		return this.terms_values.getOrDefault(term, 0) / this.num_words;
	}

	public Double getNum_docs() {
		return num_words;
	}

	public void setNum_docs(Double num_docs) {
		this.num_words = num_docs;
	}

	public HashMap<String, Integer> getTerms_values() {
		return terms_values;
	}

	public void setTerms_values(HashMap<String, Integer> terms_values) {
		this.terms_values = terms_values;
	}

}
