package com.daviserrano.phd.nlp.preprocessing;

public class Capitalization implements IPreprocess {
	
	public static final boolean LOWER_CASE = true;
	public static final boolean UPPPER_CASE = false;
	
	private boolean toLower;
	
	public Capitalization(boolean lower){
		this.toLower = lower;
	}
	
	public String process(String txt){
		String processed;
		if(toLower)
			processed = txt.toLowerCase();
		else
			processed = txt.toUpperCase();
		return processed;
	}
}
