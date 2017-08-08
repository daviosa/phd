package com.daviserrano.phd.nlp.token;

public class Token implements Comparable<Token>, Cloneable{
	
	protected String value;
	
	protected TokenContext context;
	
	protected int cluster_id;
	
	public Token(){
		this.setContext(new TokenContext());
	}
	
	public Token clone(){
		Token clone = null;
		try {
			clone = (Token) super.clone();
			clone.context = this.context.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Object 'Token.java' could not be cloned.");
			// DO NOTHING
		}
		return clone;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public TokenContext getContext() {
		return context;
	}

	public void setContext(TokenContext context) {
		this.context = context;
	}

	@Override
	public int compareTo(Token o) {
		return this.value.compareToIgnoreCase(o.value);
	}

	public int getCluster() {
		return cluster_id;
	}

	public void setCluster(int cluster_id) {
		this.cluster_id = cluster_id;
	}

}
