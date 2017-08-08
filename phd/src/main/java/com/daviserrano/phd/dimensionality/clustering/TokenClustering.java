package com.daviserrano.phd.dimensionality.clustering;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.token.Token;

public abstract class TokenClustering {
	
	protected List<Token> allTokens;
	protected List<? extends TokenCluster> clusters;
	
	public TokenClustering(){
		this.allTokens = new ArrayList<Token>();
		this.clusters = null;  
	}
	
	public void addTokens(List<Token> tokens){
		this.allTokens.addAll(tokens);
	}
	
	public abstract List<? extends TokenCluster> cluster();

	public List<Token> getAllTokens() {
		return allTokens;
	}

	public void setAllTokens(List<Token> allTokens) {
		this.allTokens = allTokens;
	}

	public List<? extends TokenCluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<? extends TokenCluster> clusters) {
		this.clusters = clusters;
	}

}
