package com.daviserrano.phd.nlp.token;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.token.context.Context;

public class TokenContext implements Cloneable{
	
	private List<Context> allContexts;
	
	public TokenContext(){
		this.allContexts = new ArrayList<Context>();
	}
	
	public TokenContext clone(){
		TokenContext clone = new TokenContext();
		for (Context context : allContexts) {
			clone.getAllContexts().add(context.clone());
		}
		return clone;
	}

	public List<Context> getAllContexts() {
		return allContexts;
	}

	public void setAllContexts(List<Context> allContexts) {
		this.allContexts = allContexts;
	}
	
	public void addContext(Context c){
		this.allContexts.add(c);
	}
	
	public void removeContext(Context c){
		this.allContexts.remove(c);
	}

}
