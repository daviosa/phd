package com.daviserrano.phd.nlp.tfidf;

import java.util.HashMap;
import java.util.List;

import com.daviserrano.phd.nlp.token.Token;

public class IDF implements Cloneable{
	
	private Double num_docs;
	
	private HashMap<String, Integer> terms_values;
	
	public IDF(){
		this.num_docs = 0.0;
		this.terms_values = new HashMap<String, Integer>();
	}
	
	@SuppressWarnings("unchecked")
	public IDF clone(){
		IDF i = new IDF();
		i.num_docs = this.num_docs;
		i.terms_values = (HashMap<String, Integer>) this.terms_values.clone();
		return i;
	}
	
	public void addNewDocument(List<Token> tokens){
		if(tokens.size()>0){
			this.num_docs++;
			for (Token term : tokens) {
				Integer newValue = this.terms_values.getOrDefault(term.getValue(), 0)+1;
				this.terms_values.put(term.getValue(), newValue);
			}
		}
	}
	
	public Double getTermIDF(String term){
		if(this.terms_values.containsKey(term))
			return 1+Math.log(this.num_docs / this.terms_values.get(term));
		else
			return 0.0;
	}

	public Double getNum_docs() {
		return num_docs;
	}

	public void setNum_docs(Double num_docs) {
		this.num_docs = num_docs;
	}

	public HashMap<String, Integer> getTerms_values() {
		return terms_values;
	}

	public void setTerms_values(HashMap<String, Integer> terms_values) {
		this.terms_values = terms_values;
	}

}
