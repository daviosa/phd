package com.daviserrano.phd.dimensionality.reduction;

import java.util.LinkedList;
import java.util.Random;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.SingularValueDecomposition;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;


public class SVD {
	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf();
		sparkConf.setMaster("local[*]");
		sparkConf.setAppName("SVD Dimens. Reduction");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		
		double[][] array = new double[1000][1000];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				Random r = new Random();
				double d = r.nextInt(1001);
				array[i][j] = d;
			}
		}
	    LinkedList<Vector> rowsList = new LinkedList<Vector>();
	    for (int i = 0; i < array.length; i++) {
	      Vector currentRow = Vectors.dense(array[i]);
	      rowsList.add(currentRow);
	    }
	    
	    JavaRDD<Vector> rows = sc.parallelize(rowsList);

	    // Create a RowMatrix from JavaRDD<Vector>.
	    RowMatrix mat = new RowMatrix(rows.rdd());

	    // Compute the top 4 singular values and corresponding singular vectors.
	    SingularValueDecomposition<RowMatrix, Matrix> svd = mat.computeSVD(4, true, 1.0E-9d);
//	    RowMatrix U = svd.U();
//	    Vector s = svd.s();
	    Matrix V = svd.V();
	    System.out.println(V);
	}
}
