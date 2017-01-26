import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class Driver {
	public static void main( String args []) throws FileNotFoundException{
		System.out.println("Please enter the name of a file that includes a list of ages.");
		
		
		Scanner scan = new Scanner(System.in);
		
		String name = scan.nextLine();
		String num;
		String num2 = "";	
		int loop = 9;
		ArrayList<Double> list = new ArrayList<>();
		Scanner scan2 = new Scanner(new File(name));
		DecimalFormat df = new DecimalFormat("#.0000");
		
		while (scan2.hasNext()){
			num = scan2.next();
			//System.out.println(num);
			//num2 = num.replaceAll(",","");
			//int foo = Integer.parseInt(num2);
			
			String[] splitNum = num.split(",");
		    for (int i=0; i<splitNum.length; i++) {
		    	list.add(Double.parseDouble(splitNum[i]));
		    }
		    			
		}
//	    for (int i=0; i<list.size(); i++) {
//	    	System.out.println(list.get(i));
//		//readFile(name);
//		}
//		for(int i =0; i < 3; i++){
//			System.out.println(binBound(list, 3)[i]);
//		}
		
	
		
		try{
		    PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		    //DecimalFormat df = new DecimalFormat("#.0000");
			
		    writer.println("Mean: " + df.format(mean(list)));
		    writer.println("Midrange: " + df.format(midrange(list)));
		    
		    for(int i = 0; i < mode(list).size();i++){
				writer.println("Mode: " + df.format(mode(list).get(i)));
			}
		    
		    writer.println("Modality: " + modality(mode(list)));
		    
		    writer.println("Median: " + df.format(median(list)));
		    
		    writer.println("5 Number Summary: " ); 
		    writer.println("\tMin: " + df.format(fiveNumSum(list).get(0)));
		    writer.println("\tLower Quartile: " + df.format(fiveNumSum(list).get(1)));
		    writer.println("\tMedian: " + df.format(fiveNumSum(list).get(2)));
		    writer.println("\tUpper Quartile: " + df.format(fiveNumSum(list).get(3)));
		    writer.println("\tMax: " + df.format(fiveNumSum(list).get(4)));
		    
		    writer.println("Bin Means:");
		    for(int i = 0; i < loop; i++){
		    	writer.println("\tBin " + (i+1) +": " + (binMean(list,3)[i]));
		    }
		    
		    writer.println("Bin Bounds:");
		    for(int i = 0; i < loop; i++){
		    	//writer.println("\tBin " + (i+1) +": " + (binBound(list,3)[i]));
		    }
		    
		    writer.println("Min-Max Normalization:");
		    writer.print("\t");
		    for(int i= 0; i < list.size(); i ++){
				writer.print(df.format(minmaxNorm(list, 0.0, 1.0).get(i)));
				if (i < list.size()-1)
					writer.print(", ");
			}
			writer.println();
			
			writer.println("Z-Score Normalization:");
		    writer.print("\t");
		    for(int i= 0; i < list.size(); i ++){
				writer.print(df.format(zscore(list).get(i)));
				if (i < list.size()-1)
					writer.print(", ");
			}
			writer.println();
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		for(int i =0; i < 9; i++){
			System.out.println(binBound(list,3)[i]);
		}
		System.out.println("Output file created.");
	}
	
	/*private static ArrayList<Integer> readFile (String file) throws FileNotFoundException {
		String num;
		String num2 = "";		
		ArrayList<Integer> list = new ArrayList<>();
		Scanner scan = new Scanner(new File(file));
		
		while (scan.hasNext()){
			num = scan.next();
			//System.out.println(num);
			//num2 = num.replaceAll(",","");
			//int foo = Integer.parseInt(num2);
			
			String[] splitNum = num.split(",");
		    for (int i=0; i<splitNum.length; i++) {
		    	list.add(Integer.parseInt(splitNum[i]));
		    }
		    
			//list.add(foo);
			
		}
		//System.out.println(list);
		
		return list;
	} */
	
	public static double mean (ArrayList<Double> list){
		double sum = 0;
		for(int i = 0; i < list.size(); i++){
			sum += list.get(i);
		}
		double av = sum / list.size();
		return av;
	}
	
	public static double midrange(ArrayList<Double> list){
		double min = list.get(0);
		double max = list.get(list.size()-1);
		
		double midr = (min + max)/2;
		
		return midr;
		
	}
	
	public static ArrayList<Double> mode(ArrayList<Double> list){
		ArrayList<Double> modes = new ArrayList<Double>();
		Map<Double, Double> countMap = new HashMap<Double, Double>();

		double max = -1;

		for (double n : list) {
			double count = 0;

			if (countMap.containsKey(n)) {
				count = (int) (countMap.get(n) + 1);
			} else {
				count = 1;
			}

			countMap.put(n, count);

			if (count > max) {
				max = count;
			}
		}

		for (final Entry<Double, Double> tuple : countMap.entrySet()) {
			if (tuple.getValue() == max) {
				modes.add(tuple.getKey());
			}
		}

		return modes;

	}
	
	private static String modality(ArrayList<Double> list){
		if(list.size() == 1)
			return "Unimodal";
		else if(list.size() == 2)
			return "Bimodal";
		else if(list.size() == 3)
			return "Trimodal";
		else if(list.size() == 4)
			return "Quadmodal";
		else
			return "Multimodal";
	}

	private static double median(ArrayList<Double> list){
		int middle = list.size()/2;
		if (list.size()%2 == 1) {
			//System.out.printf("Median: %.4f\n",foo);
			return list.get(middle);
		} else {
			//System.out.printf("Median: %.4f\n",bar);
			return (list.get(middle - 1)+ list.get(middle)) / 2.0;
		}
	}

	private static ArrayList<Double> fiveNumSum(ArrayList<Double> list){
		ArrayList<Double> low = new ArrayList<Double>();
		ArrayList<Double> upp = new ArrayList<Double>();
		ArrayList<Double> res = new ArrayList<Double>();
		
		double med = median(list);
		double min = list.get(0);
		double max = list.get(list.size() - 1);
		double upmed = 0;
		double lowmed = 0;
		
		if((list.size() % 2) == 0){
			
		}else{
			for(int j =0; j < (list.size()/2); j++){
				low.add(list.get(j));
			}
			lowmed = median(low);
			
			for(int i = (int) ((list.size()/2) + 1); i < list.size(); i++){
				upp.add(list.get(i));
			}
			upmed = median(upp);
		}
			
			res.add(min);
			res.add(lowmed);
			res.add(med);
			res.add(upmed);
			res.add(max);
			
			return res;
	}
	
	private static ArrayList<Double>[] binMean(ArrayList<Double> list, int k){
		ArrayList<Double> inner = null;
		ArrayList<Double> meanlist = null;
		double x = Math.ceil(list.size()/k);
		ArrayList<Double>[] array = new ArrayList[(int) x];

	    DecimalFormat df = new DecimalFormat("#.0000");

		for( int i = 1; i < x + 1; i++){
			inner = new ArrayList<Double>();
			for(int j = (int) ((i * k) - k); j < (i * k); j++){
				inner.add((double)list.get(j));
			}
			array[i - 1] = inner;
		}
				
		for(int i = 0; i < x; i++){
			meanlist = new ArrayList<Double>();

			for(int j = 0; j < k; j ++){
				meanlist.add(Double.parseDouble(df.format(mean(array[i]))));
				//meanlist.add(mean(array[i]));
			}
				array[i] = meanlist;
		}
		return array;
	}
	
	private static ArrayList<Double>[] binBound(ArrayList<Double> list, int k){
		ArrayList<Double> inner = null;
		ArrayList<Double> boundlist = null;
		double x = Math.ceil(list.size()/k);
		ArrayList<Double>[] array = new ArrayList[(int) x];
		double min;
		double max;
	    DecimalFormat df = new DecimalFormat("#.0000");
	    
		for( int i = 1; i < x + 1; i++){
			inner = new ArrayList<Double>();
			
			for(int j = (int) ((i * k) - k); j < (i * k); j++){
				inner.add((double)list.get(j));
				//System.out.println(inner.get(j));
			}
			array[i - 1] = inner;
		}
		
		//System.out.println(array[0]);
		
		
		for(int i = 0; i < x; i++){
			boundlist = new ArrayList<Double>();
			min = array[i].get(0);
			max = array[i].get(inner.size()-1);
			//System.out.println(min);
			for(int j =0; j < k; j++){
				//min = array[i].get(0);
				//max = array[i].get(inner.size()-1);
				//System.out.println(min);
				if(j == 0){
					boundlist.add((double)min);
				}else if(j == k -1){
					boundlist.add((double)max);
				}else{
					if((inner.get(j) - min) <= (max - inner.get(j))){
						boundlist.add((double)min);
					}else{
						boundlist.add((double)max);
					}
				}
			}
			array[i] = boundlist;
		}
		return array;
	}
	
	private static ArrayList<Double>[] binMedian(ArrayList<Double> list, int k){
		
	double min, max, width;
	
	min = list.get(0);
	max = list.get(list.size()-1);
	width = (min - max) / k;
		
		return null;
	}
	
	private static ArrayList<Double> minmaxNorm(ArrayList<Double> list, double rangea, double rangeb){
		
		ArrayList<Double> normlist = new ArrayList<Double>();
		double num;
		double norm;
		double min,max;
	    DecimalFormat df = new DecimalFormat("#.0000");

		
		min = list.get(0);
		max = list.get(list.size()-1);
		
		for(int i =0; i < list.size(); i++){
			num = list.get(i);
			norm = ((num-min)/(max-min)*(rangeb - rangea) + rangea);
			normlist.add(norm);
			
		}
		return normlist;
	}
	
	private static ArrayList<Double> zscore(ArrayList<Double> list){
		
		ArrayList<Double> zlist = new ArrayList<Double>();
		double z;
		double dev;
		
		double mean = mean(list);
		double temp = 0;

	    for (int i = 0; i < list.size(); i++){
	        double val = list.get(i);
	        
	        double squrDiffToMean = Math.pow(val - mean, 2);

	        temp += squrDiffToMean;
	    }

	    double meanOfDiffs = (double) temp / (double) (list.size());

		dev = Math.sqrt(meanOfDiffs);
		//System.out.println(dev);
		
		for(int i = 0; i < list.size(); i ++){
			z = (list.get(i) - mean) / dev;
			zlist.add(z);
		}
		
		return zlist;
	}
}
