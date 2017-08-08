package com.daviserrano.phd.dimensionality.clustering;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.token.Token;

public abstract class TokenCluster {
	
	protected List<Token> tokens;
	protected Token centroid;
	protected int id;
	
	public TokenCluster(int id){
		this.id = id;
		this.tokens = new ArrayList<Token>();
		this.centroid = null;
	}
	
	protected abstract double calculateDistance(Token p, Token centroid);
	
	public void addToken(Token t ){
		this.tokens.add(t);
	}
	
	public void clear(){
		this.tokens.clear();
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Token getCentroid() {
		return centroid;
	}

	public void setCentroid(Token centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract void calculateNewCentroid();

}