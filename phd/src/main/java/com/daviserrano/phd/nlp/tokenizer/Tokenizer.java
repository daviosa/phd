package com.daviserrano.phd.nlp.tokenizer;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.preprocessing.IPreprocess;
import com.daviserrano.phd.nlp.token.Token;

public abstract class Tokenizer {
	
	protected List<IPreprocess> preprocessing;
	
	public Tokenizer(){
		this.preprocessing = new ArrayList<IPreprocess>();
	}
	
	public Tokenizer(List<IPreprocess> preprocessing){
		this.preprocessing = preprocessing;
	}
	
	public List<Token> tokenizeText(String str){
		ArrayList<Token> tokens = new ArrayList<Token>();
		String preprocessed = str;
		for (IPreprocess pp : preprocessing) {
			preprocessed = pp.process(preprocessed);
		}
		List<String> ngrams = this.ngrams(preprocessed);
		for (String string : ngrams) {
			if(string.length()>0){
				Token t = new Token();
				t.setValue(string);
				tokens.add(t);
			}
		}
		
		return tokens;
	}
	
	public void addPreProcessing(IPreprocess p){
		this.preprocessing.add(p);
	}
	
	protected abstract List<String> ngrams(String str);
}
