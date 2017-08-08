package com.daviserrano.phd.nlp;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.daviserrano.phd.nlp.bagofwords.Bag;
import com.daviserrano.phd.nlp.bagofwords.Document;
import com.daviserrano.phd.nlp.bagofwords.binarymap.BinaryMapBag;
import com.daviserrano.phd.nlp.bagofwords.tfidf.TFIDFBag;
import com.daviserrano.phd.nlp.preprocessing.Capitalization;
import com.daviserrano.phd.nlp.preprocessing.Normalization;
import com.daviserrano.phd.nlp.preprocessing.IPreprocess;
import com.daviserrano.phd.nlp.preprocessing.RemovePonctuation;
import com.daviserrano.phd.nlp.tokenizer.Tokenizer;
import com.daviserrano.phd.nlp.tokenizer.WordUnigram;

public class SampleMain {
	
	public static final int TWEET_INDEX = 0;
	public static final int OPINION_INDEX = 1;
	public static final double TRAIN_PERCENT = 0.9;
	public static final double USE_PERCENT = 1;
	public static final double NUM_REPLICS = 15;
	
	public static void main(String[] args) {
		
		Tokenizer t = new WordUnigram();
		IPreprocess p1 = new Normalization();
		IPreprocess p2 = new Capitalization(Capitalization.LOWER_CASE);
		IPreprocess p3 = new RemovePonctuation();
		t.addPreProcessing(p1);
		t.addPreProcessing(p2);
		t.addPreProcessing(p3);
		
		String dataset = System.getProperty("user.dir")+"/dataset/tweetsTratados_base_dados.csv";
		try {
			CSVReader csv = new CSVReader(new FileReader(dataset), ';','"');
			List<String[]> tweets = csv.readAll();
			csv.close();
			
			System.out.println("================== STARTED ==================");
			System.out.println("Total replics to run = "+NUM_REPLICS);
			System.out.println("Using "+(USE_PERCENT*100)+"% of the dataset.");
			System.out.println("Using "+(TRAIN_PERCENT*100)+"% for training.");
			System.out.println("=============================================");
			String arffPath = System.getProperty("user.dir")+"/arff/";
			for (int i = 0; i < NUM_REPLICS; i++) {
				Collections.shuffle(tweets);
				
				System.out.println();System.out.println();
				System.out.println("========= BEGIN REPLIC "+(i+1)+" =========");
				System.out.println("========= BoW Davi "+(i+1)+" =========");
				BinaryMapBag bow = new BinaryMapBag(t);
				List<String> lines = runReplic(tweets,bow);
				Path file = Paths.get(arffPath+"BoW "+(i+1)+" Davi - "+(new Date()).toString()+".arff");
				Files.write(file, lines, Charset.forName("UTF-8"));
				System.out.println("========= BoW TFIDF "+(i+1)+" =========");
				TFIDFBag bowTfidf = new TFIDFBag(t);
				List<String> linesTfidf = runReplic(tweets,bowTfidf);
				Path fileTfidf = Paths.get(arffPath+"BoW "+(i+1)+" TFIDF - "+(new Date()).toString()+".arff");
				Files.write(fileTfidf, linesTfidf, Charset.forName("UTF-8"));
				System.out.println("========= END REPLIC "+(i+1)+"=========");
			}
			System.out.println();System.out.println();
			System.out.println("================== FINISHED ALL ==================");
			
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private static List<String> runReplic(List<String[]> tweets, Bag<? extends Document> bow) throws IOException {
		int trainSize = (int) (tweets.size()*TRAIN_PERCENT*USE_PERCENT);
		feedBoW(bow, tweets, trainSize);
		
		Map<String,Integer> m = bow.getTokenIndexes();
		
		List<String> lines = new ArrayList<String>();
		lines.add("@relation NLP");
		int keyI = 0;
		for (int i = 0; i < m.keySet().size(); i++) {
			lines.add("@attribute att_"+keyI+" numeric");
			keyI++;
		}
		lines.add("@attribute opinion numeric");
		lines.add("@data");
		lines.add("");
		
		return createArffLines(bow,tweets,trainSize,lines);
		
		
	}

	private static <D extends Document, T extends Bag<D>> List<String> createArffLines(T bow,
			List<String[]> tweets, int trainSize, List<String> lines) {
		for (int i = 0; i<tweets.size()*USE_PERCENT; i++) {
			String tweetText = tweets.get(i)[TWEET_INDEX];
			String tweetOpinion = tweets.get(i)[OPINION_INDEX];
			
			D d = bow.string2Document(tweetText);
			double[] vec = bow.getVector(d);
			String values = "";
			String sep = "";
			for (double e : vec) {
				values = values+sep+e;
				sep=",";
			}
			values = values+","+tweetOpinion;
			lines.add(values);
		}
		return lines;
	}
	
	private static void feedBoW(Bag<? extends Document> bow, List<String[]> tweets, int trainSize) {
		for (int i = 0; i < trainSize; i++) {
			bow.addDocumentAsString(tweets.get(i)[TWEET_INDEX]);
		}
	}
}
