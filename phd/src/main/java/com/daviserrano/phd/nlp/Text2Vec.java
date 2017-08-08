package com.daviserrano.phd.nlp;


public abstract class Text2Vec {
	
	public abstract double[] string2Vector(String d);
	
	public abstract void addDocumentAsString(String str);
	
	public abstract int getVectorSize();

}
