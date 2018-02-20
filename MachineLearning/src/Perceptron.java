import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Perceptron {
	public static final int NUM_DATA_POINTS = 15;
	public static final String INPUT_FILENAME = "ai_data.txt";
	
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
	
	public static void scaleData(double[][] frenchData, double[][] englishData) {
		double scaleFactorLetters = 100000;
		double scaleFactorA = 100000;
		for(int i = 0; i < frenchData[0].length; i++) {
			frenchData[0][i] /= scaleFactorLetters;
		}
		for(int i = 0; i < frenchData[1].length; i++) {
			frenchData[1][i] /= scaleFactorA;
		}
		for(int i = 0; i < englishData[0].length; i++) {
			englishData[0][i] /= scaleFactorLetters;
		}
		for(int i = 0; i < englishData[0].length; i++) {
			englishData[1][i] /= scaleFactorA;
		}
	}
	
	public static int threshold(double x, double y, double[] w) {
		if (w[0] + w[1] * x + w[2] * y >= 0)
			return 0;
		else
			return 1;
	}
	
	public static void main(String[] args) {
		double[][] frenchData = new double[2][NUM_DATA_POINTS];
		double[][] englishData= new double[2][NUM_DATA_POINTS];
		
		Random gen = new Random();

		double[] w = {0,0,0};
		
		getInput(frenchData, englishData);
		scaleData(frenchData, englishData);
		
		int numMisclassified = -1;
		int count = 0;
		int maxCount = 1000;
		int maxMisclassified = 5;
		double alpha = 1;
		
		do {
			int language = gen.nextInt(2); //0 for french, 1 for english
			double[][] dataSet = (language == 0)? frenchData: englishData;
			int dataPoint = gen.nextInt(NUM_DATA_POINTS);
			double x = dataSet[0][dataPoint], y = dataSet[1][dataPoint];
						
			int thresholdVal = threshold(x, y, w);
			
			if (count == maxCount) {
				//System.out.println("num: " + numMisclassified + "  |   w = [" + w[0] + ", " + w[1] + ", " + w[2] + "]");
				count = 0;
				numMisclassified = 0;
			}
			
			if (language - thresholdVal != 0) {
				
				// ********************
				w[0] += alpha * (-language + thresholdVal);
				w[1] += alpha * (-language + thresholdVal) * x;
				w[2] += alpha * (-language + thresholdVal) * y;
				// ********************

				numMisclassified++;
				//System.out.println(language - thresholdVal);
			} 					
			
			count++;
			
		} while (numMisclassified > maxMisclassified || count < maxCount);
		
		int n = 0;
		for (int i = 0; i < 15; i++) {
			if (w[0] + frenchData[0][i] * w[1] + frenchData[1][i] * w[2] < 0) {
				n++;
			}
			
			if (w[0] + englishData[0][i] * w[1] + englishData[1][i] * w[2] >= 0) {
				n++;
			}
		}
				
		//System.out.println("n = " + n);
		
		System.out.println("w = [" + w[0] + ", " + w[1] + ", " + w[2] + "]");
	}
}
