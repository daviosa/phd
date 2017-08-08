package com.daviserrano.phd.nlp.bagofwords.tfidf;

import com.daviserrano.phd.nlp.bagofwords.Document;
import com.daviserrano.phd.nlp.tfidf.TF;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;

public class TFIDFDocument extends Document {
	
	private TF tfs;
	
	public TFIDFDocument() {
		super();
		this.tfs = new TF();
	}
	
	public TFIDFDocument(String d, Tokenizer tokenizer){
		this();
		this.setText(d, tokenizer);
	}
	
	public void setText(String text, Tokenizer tokenizer) {
		super.setText(text, tokenizer);
		this.tfs.setDocument(this.tokens);
	}
	
	public double getTokenTF(String token){
		return this.tfs.getTermTF(token);
	}

}
