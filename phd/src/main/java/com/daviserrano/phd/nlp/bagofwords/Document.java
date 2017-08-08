package com.daviserrano.phd.nlp.bagofwords;

import java.util.List;

import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.token.context.Context;
import com.daviserrano.phd.nlp.token.context.factory.ContextFactory;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;

public class Document {
	
	protected String text;
	protected List<Token> tokens;
	
	public Document(){
		
	}
	
	public Document(String d, Tokenizer tokenizer){
		this();
		this.setText(d, tokenizer);
	}

	public String getText() {
		return text;
	}

	public void setText(String text, Tokenizer tokenizer) {
		this.text = text;
		this.tokens = tokenizer.tokenizeText(text);
	}
	
	public void addContextToTokens(ContextFactory cf){
		for (Token token : tokens) {
			Context c = cf.makeContext(token);
			token.getContext().addContext(c);
		}
	}
	
	public List<Token> getTokens(){
		return this.tokens;
	}
}
