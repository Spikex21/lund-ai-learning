import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class LogisticRegression {
	public static final int NUM_DATA_POINTS = 15;
	public static final String INPUT_FILENAME = "ai_data.txt";
	
	public static double round(double d) {
		d*=100000;
		return ((int)d)/100000.0;
	}
	
	public static double dot(double[] x, double[] y) {
		if(x.length != y.length) {
			throw new IllegalArgumentException("Both vectors must be the same length in order to find their dot product");
		}
		double result = 0;
		for(int i = 0; i < x.length; i++) {
			result += x[i]*y[i];
		}
		return result;
	}
	
	public static int getValue(String value) {
		return Integer.parseInt(value.substring(value.indexOf(':')+1));
	}
	
	public static void getInput(double[][] frenchData, double[][] englishData) {
		Scanner lineScan = null;
		try {
			lineScan = new Scanner(new File("./"+INPUT_FILENAME));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not Found");
		}
		String line;
		Scanner valueScan;
		
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 frenchData[0][i] = getValue(valueScan.next());
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 frenchData[1][i] = getValue(valueScan.next());
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 englishData[0][i] = getValue(valueScan.next());
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 englishData[1][i] = getValue(valueScan.next());
		  
	}
	
	public static void scaleData(double[][] frenchData, double[][] englishData, double scaleLetters, double scaleAs) {
		for(int i = 0; i < frenchData[0].length; i++) {
			frenchData[0][i] /= scaleLetters;
		}
		for(int i = 0; i < frenchData[1].length; i++) {
			frenchData[1][i] /= scaleAs;
		}
		for(int i = 0; i < englishData[0].length; i++) {
			englishData[0][i] /= scaleLetters;
		}
		for(int i = 0; i < englishData[0].length; i++) {
			englishData[1][i] /= scaleAs;
		}
	}
	
	public static double logistic(double[] x, double y, double[] w) {
		double alpha = -1000;
		return (1.0/(1+Math.pow(Math.E,alpha*(dot(x,w))))); 	/*"-z" or "-w(dot)x" is represented here by (w[0]+w[1]*x-y). 
															 * The sign is reversed because 0 should correspond to french which lies on top of the dividing line				
		 													*/
	}
	
	public static void main(String[] args) {
		double[][] frenchData = new double[2][NUM_DATA_POINTS];
		double[][] englishData= new double[2][NUM_DATA_POINTS];
		
		double[] w = {0,.1, -1};	//default line {b=0,m=.1}
		final double alpha = .0001;
		final double iterations = 100000;
		
		getInput(frenchData, englishData);
		scaleData(frenchData, englishData, 100000, 100000);
		
		Random gen = new Random();
		int count = 0;
		while(count < iterations) {
			
			int language = gen.nextInt(2); //0 for french, 1 for english
			double[][] dataSet = (language == 0)? frenchData: englishData;
//			System.out.print(language +"| ");
			int dataPoint = gen.nextInt(NUM_DATA_POINTS);
			double[] x = {1, dataSet[0][dataPoint], dataSet[1][dataPoint]};
			double change = 0;
			for(int i = 0; i < w.length; i++) {
				change = logistic(x, dataSet[1][dataPoint], w);
				w[i] += alpha*(language-change)*x[i];
			}
//			System.out.print("x: "+ round(x[1]) +" | y:" + round(dataSet[1][dataPoint])+ " | ");
//			System.out.println(change + "| "+"y = "+ round(w[0])+" + " + round(w[1])+"x");
			count++;

		}
		System.out.println("y = "+w[0]+" + " + w[1]+"x");
		System.out.println(w[0] + " + " + w[1] + "x1 + "+ w[2] + "x2");
		

	}

}
