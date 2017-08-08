package com.daviserrano.phd.nlp.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daviserrano.phd.nlp.preprocessing.IPreprocess;

public class WordUnigram extends Tokenizer{

	public WordUnigram() {
		super();
	}
	
	public WordUnigram(List<IPreprocess> preprocessing) {
		super(preprocessing);
	}

	@Override
	public List<String> ngrams(String normalized) {
		String[] splitedStr = normalized.split("[^\\p{IsAlphabetic}'-]+");
		List<String> grams = Arrays.asList(splitedStr);
		ArrayList<String> ngrams = new ArrayList<String>(grams);
		return ngrams;
	}
	
//	public static void main(String[] args) {
//		ArrayList<Preprocess> pre = new ArrayList<Preprocess>();
//		pre.add(new com.daviserrano.phd.nlp.preprocessing.Normalization());
//		pre.add(new com.daviserrano.phd.nlp.preprocessing.Capitalization(com.daviserrano.phd.nlp.preprocessing.Capitalization.UPPPER_CASE));
//		WordUnigram u = new WordUnigram(pre);
//		List<com.daviserrano.phd.nlp.token.Token> list = u.tokenizeText("árvíztűrő 98 tü99körfúrógép aáeéií--oóöő,uúüű AÁEÉ.IÍOÓÖ\"ŐUÚÜŰ");
//		for (com.daviserrano.phd.nlp.token.Token token : list) {
//			System.out.println(token.getValue());
//		}
//	}
	
}
