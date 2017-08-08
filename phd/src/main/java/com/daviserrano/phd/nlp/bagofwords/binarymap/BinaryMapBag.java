package com.daviserrano.phd.nlp.bagofwords.binarymap;

import java.util.List;

import com.daviserrano.phd.nlp.bagofwords.Bag;
import com.daviserrano.phd.nlp.bagofwords.Document;
import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;

public class BinaryMapBag extends Bag<Document> {

	public BinaryMapBag(Tokenizer t) {
		super(t);
	}

	public Document string2Document(String str) {
		Document d = new Document(str, this.tokenizer);
		return d;
	}

	protected void processDocument(Document d) {
		// DO NOTHING
	}

	public double[] getVector(Document d) {
		if(this.maxIndex>=0){
			double[] docVector = new double[this.maxIndex+1];
			List<Token> tokens = d.getTokens();
			int pow = 0;
			for (Token token : tokens) {
				String word = token.getValue();
				Integer index = this.tokenIndexes.getOrDefault(word,-1);
				if(index>=0){
					docVector[index]+= Math.pow(2, pow);
					pow++;
				}
			}
			return docVector;
		}else{
			return null;
		}
	}
}
