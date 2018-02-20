import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class LogisticRegression {
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
	
	public static double threshold(double x, double y, double[] w) {
		return (1.0/(1+Math.pow(Math.E,-(w[0]+w[1]*x+w[2]*y))));
	}
	
	public static void main(String[] args) {
		double[][] frenchData = new double[2][NUM_DATA_POINTS];
		double[][] englishData= new double[2][NUM_DATA_POINTS];
		
		double[] w = {0,0,0};
		final double alpha = .01;
		final double epsilon = 1;
		double loss = 10;
		final double iterations = 10000;
		
		getInput(frenchData, englishData);
		scaleData(frenchData, englishData);
		
		Random gen = new Random();
		int count = 0;
		while(count < iterations) {
			
			int language = gen.nextInt(2); //0 for french, 1 for english
			double[][] dataSet = (language == 0)? frenchData: englishData;
			int dataPoint = gen.nextInt(NUM_DATA_POINTS);
			double x = dataSet[0][dataPoint], y = dataSet[1][dataPoint];
			
			double change = threshold(x, y, w);
			
			w[0] += alpha * (language-change);
			w[1] += alpha * (language-change) * x;
			w[2] += alpha * (language-change) * y;
					
			// Calculates average loss for all points
			double prevLoss = loss;
			loss = 0;

			double lossW1 = 0, lossW2 = 0;
			
			for (int i = 0; i < NUM_DATA_POINTS; i++) {
				double frenchX = frenchData[0][i], frenchY = frenchData[1][i],
						englishX = englishData[0][i], englishY = englishData[1][i];
				
				double	changeF = threshold(frenchX, frenchY, w), changeE = threshold(englishX, englishY, w),
						frenchDiff = 0 - changeF, englishDiff = 1 - changeE;
				
				lossW1 += 
			
				lossW2 += 
				
				// averages loss
				loss = Math.sqrt(Math.pow(lossW1, 2) + Math.pow(lossW2, 2));
			}
			
			System.out.println("loss diff: " + (loss - prevLoss));
			
			count++;
		}
		//System.out.println("w = [" + w[0] + ", " + w[1] + "]");
	}
}
