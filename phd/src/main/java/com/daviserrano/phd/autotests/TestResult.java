package com.daviserrano.phd.autotests;

public class TestResult {
	
	private int correctPredicitons;
	private int wrongPredicitons;
	private int noPredicitons;
	
	public TestResult() {
		this.correctPredicitons = 0;
		this.wrongPredicitons = 0;
		this.noPredicitons = 0;
	}

	public void analysePrediciton(double prediction, double value) {
		if(prediction == value){
			this.correctPredicitons++;
		}else{
			wrongPredicitons++;
		}
	}

	public void noPredictionMade() {
		this.noPredicitons++;
	}
	
	@Override
	public String toString() {
		String print = System.getProperty("line.separator")+" "+correctPredicitons+" correct"+
				System.getProperty("line.separator")+" "+wrongPredicitons+" wrong"+
				System.getProperty("line.separator")+" "+noPredicitons+" no prediction";
		return print;
	}
}
