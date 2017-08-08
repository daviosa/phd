package com.daviserrano.phd.nlp.preprocessing;

import java.text.Normalizer;

public class RemovePonctuation implements IPreprocess{
	public String process(String txt){
		String normalized = Normalizer.normalize(txt,  Normalizer.Form.NFD);
		normalized = normalized.replaceAll("[^A-Za-z0-9äöüÄÖÜßéèáàúùóòáéíóúãõàìèòùçâîêôû]", " ");
		return normalized;
	}

}
