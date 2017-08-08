package com.daviserrano.phd.nlp.bagofwords;

import java.util.HashMap;
import java.util.Map;

import com.daviserrano.phd.nlp.Text2Vec;
import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;

public abstract class Bag<T extends Document> extends Text2Vec {
	
	protected Integer maxIndex;
	protected Map<String, Integer> tokenIndexes;
	protected Tokenizer tokenizer;
	
	public Bag(Tokenizer t){
		this.tokenIndexes = new HashMap<String, Integer>();
		this.maxIndex = -1;
		this.tokenizer = t;
	}
	
	public void addDocumentAsString(String str){
		T d = this.string2Document(str);
		this.addDocument(d);
	}
	
	public abstract T string2Document(String str);
	
	public abstract double[] getVector(T d);
	
	public int getVectorSize(){
		return this.maxIndex+1;
	}
	
	public double[] string2Vector(String d){
		return this.getVector(this.string2Document(d));
	}

	public void addDocument(T d){
		if(d.getText().trim().length()>0 || d.getTokens().size()>0){
			d.setText(d.getText(), this.tokenizer);
			this.processDocument(d);
			for (Token token : d.getTokens()) {
				if(!this.tokenIndexes.containsKey(token.getValue())){
					this.maxIndex++;
					this.tokenIndexes.put(token.getValue(), this.maxIndex);
				}
			}
		}
	}
	
	protected abstract void processDocument(T d);
	
	
	public void clearBag(){
		this.tokenIndexes.clear();
		this.maxIndex = -1;
	}
	
	public Map<String, Integer> getTokenIndexes(){
		return this.tokenIndexes;
	}
	
	public Tokenizer getTokenizer(){
		return this.tokenizer;
	}

}
