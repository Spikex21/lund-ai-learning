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
		return (1.0/(1+Math.pow(Math.E,(w[0]+w[1]*x-y)))); 	/*"-z" or "-w(dot)x" is represented here by (w[0]+w[1]*x-y). 
															 * The sign is reversed because 0 should corespond to french which lies on top of the dividing line				
		 													*/
	}
	
	public static void main(String[] args) {
		double[][] frenchData = new double[2][NUM_DATA_POINTS];
		double[][] englishData= new double[2][NUM_DATA_POINTS];
		
		double[] w = {0,.1};	//default line {b=0,m=.1}
		final double alpha = .00001;
		final double iterations = 1000000;
		
		getInput(frenchData, englishData);
		scaleData(frenchData, englishData);
		
		Random gen = new Random();
		int count = 0;
		while(count < iterations) {
			
			int language = gen.nextInt(2); //0 for french, 1 for english
			double[][] dataSet = (language == 0)? frenchData: englishData;
			int dataPoint = gen.nextInt(NUM_DATA_POINTS);
			double[] x = {1, dataSet[0][dataPoint]};
			double change = 0;
			for(int i = 0; i < w.length; i++) {
				change = threshold(dataSet[0][dataPoint], dataSet[1][dataPoint], w);
				w[i] += alpha*(language-change)*x[i];
			}
			System.out.println(change + " |   "+"y = "+w[0]+" + " + w[1]+"x");
			count++;
			
			
		}
		System.out.println("y = "+w[0]+" + " + w[1]+"x");
		

	}

}
