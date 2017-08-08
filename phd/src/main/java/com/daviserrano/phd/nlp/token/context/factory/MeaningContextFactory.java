package com.daviserrano.phd.nlp.token.context.factory;

import java.util.Map;

import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.token.context.Context;
import com.daviserrano.phd.nlp.token.context.MeaningContext;
import com.daviserrano.phd.nlp.tokenizer.WordUnigram;

public class MeaningContextFactory implements ContextFactory {
	
	private Map<String, String> dictionary;
	private WordUnigram tokenizer;
	
	public MeaningContextFactory(Map<String, String> dic, WordUnigram tokenizer) {
		this.setDictionary(dic);
	}

	@Override
	public Context makeContext(Token token) {
		String value = dictionary.get(token.getValue());
		if(value != null && value.trim().length()>0){
			MeaningContext mc = new MeaningContext();
			mc.setValue(value, this.tokenizer);
			return mc;
		}else{
			return null;
		}
	}

	public Map<String, String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

}
