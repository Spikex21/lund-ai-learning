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
		 valueScan.close();
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 frenchData[1][i] = getValue(valueScan.next());
		 valueScan.close();
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 englishData[0][i] = getValue(valueScan.next());
		 valueScan.close();
		 
		 line = lineScan.nextLine();
		 valueScan = new Scanner(line);
		 valueScan.next();
		 for(int i = 0; i < NUM_DATA_POINTS; i++)
			 englishData[1][i] = getValue(valueScan.next());
		 valueScan.close();
		 lineScan.close();
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
	
	public static double logistic(double[] x, double[] w) {
		return (1.0/(1+Math.pow(Math.E,-(dot(x,w)))));
	}
	
	public static double gradient(double[][] frenchData, double[][] englishData, double[] w) {
		double averageGradient=0;
		double squareSums = 0;
		for(int i = 0; i < frenchData[0].length; i++) {
			squareSums = 0;
			double[] x = {1, frenchData[0][i], frenchData[1][i]};
			for(int j = 0; j < w.length; j++) {
				squareSums += Math.pow((0 - logistic(x, w))*x[j], 2);
			}
			averageGradient += Math.sqrt(squareSums);
			
		}
		
		for(int i = 0; i < englishData[0].length; i++) {
			squareSums = 0;
			double[] x = {1, englishData[0][i], englishData[1][i]};
			for(int j = 0; j < w.length; j++) {
				squareSums += Math.pow((1 - logistic(x, w))*x[j], 2);
			}
			averageGradient += Math.sqrt(squareSums);
		}
		
		return averageGradient /= (NUM_DATA_POINTS*2);
		
		
	}
	
	public static void main(String[] args) {
		System.out.println("Working...");
		
		final double alpha = .3;		//alpha value
		final double maxGradient = .46;	//minimum gradient to continue the gradient ascent process

		double[][] frenchData = new double[2][NUM_DATA_POINTS];
		double[][] englishData= new double[2][NUM_DATA_POINTS];
		
		
		getInput(frenchData, englishData);					//gathers input from file at INPUT_FILENAME
		scaleData(frenchData, englishData, 100000, 100000);	//scales the data to fit in 0..1
		
		double[] w = {0, .1, -1};	// contains the weights of the dividing surface
		
		double gradient = Integer.MAX_VALUE;		//current gradient in the gradient ascent process
		
		Random gen = new Random();	//generates the random dataPoint to compare
		
		int language;				// 0 = French, 1 = English
		double[][] dataSet;			// either frenchData or englishData
		int dataPoint;				// 0..NUM_DATA_POINTS
		double[] x = new double[3];	// holds the randomly chosen data point in vector form
		
		while( gradient > maxGradient) {
				
			//random selection of a data point
			language = gen.nextInt(2); //0 for french, 1 for english
			dataSet = (language == 0)? frenchData: englishData;
			dataPoint = gen.nextInt(NUM_DATA_POINTS);		

			//setting x vector to hold the point
			x[0] = 1;
			x[1] = dataSet[0][dataPoint];
			x[2] = dataSet[1][dataPoint];
			//= {1, dataSet[0][dataPoint], dataSet[1][dataPoint]};
			
			//adjusting dividing surface's weights
			for(int i = 0; i < w.length; i++) {
				w[i] +=  alpha*(language - logistic(x, w))*x[i];
			}
			
			//checking gradient
			gradient = gradient(frenchData, englishData, w);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("w = [" + w[0] + ", " + w[1] + ", " + w[2] + "]");
		System.out.println("or...  y = " + -w[0]/w[2] + " + " + -w[1]/w[2] + "x");

	}
}
