package com.daviserrano.phd.nlp.preprocessing;

import java.text.Normalizer;

public class Normalization implements IPreprocess {
	public String process(String txt){
		String normalized = Normalizer.normalize(txt,  Normalizer.Form.NFD);
		normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
		return normalized;
	}
	
//	public static void main(String[] args) {
//		Normalization n = new Normalization();
//		java.util.Scanner s = new java.util.Scanner(System.in);
//		while(s.hasNextLine()){
//			String line = s.nextLine();
//			System.out.println(n.process(line));
//		}
//		s.close();
//	}
}
