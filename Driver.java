import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
	public static void main( String args []) throws FileNotFoundException{
		System.out.println("Please enter the name of a file that includes a list of ages.");


		Scanner scan = new Scanner(System.in);

		String name = scan.nextLine();
		String num;
		String num2 = "";		
		ArrayList<Integer> list = new ArrayList<>();
		Scanner scan2 = new Scanner(new File(name));

		while (scan2.hasNext()){
			num = scan2.next();
			//System.out.println(num);
			//num2 = num.replaceAll(",","");
			//int foo = Integer.parseInt(num2);

			String[] splitNum = num.split(",");
			for (int i=0; i<splitNum.length; i++) {
				list.add(Integer.parseInt(splitNum[i]));
			}

			//list.add(foo);

		}
		//	    for (int i=0; i<list.size(); i++) {
		//
		//	    	System.out.println(list.get(i));
		//		//readFile(name);
		//		}


		//System.out.println(mean(list));
		mean(list);
		//System.out.println(median(list));
		median(list);

		try{
			PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
			DecimalFormat df = new DecimalFormat("#.0000");

			writer.println("Mean: " + df.format(mean(list)));
			writer.println("Median: " + df.format(median(list)));
			writer.println("5 Number Summary: " + df.format(fiveNumSum(list)));
			writer.println("Lower Quartile: " + df.format(quartile(list,.25)) + ", Upper Quartile: " + df.format(quartile(list,.75)));
			writer.close();
		} catch (IOException e) {
			// do something
		}


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

	public static double mean (ArrayList<Integer> list){
		double sum = 0;
		for(int i = 0; i < list.size(); i++){
			sum += list.get(i);
		}
		double av = sum / list.size();
		return av;
	}

	private static double median(ArrayList<Integer> list){
		int middle = list.size()/2;
		if (list.size()%2 == 1) {
			//System.out.printf("Median: %.4f\n",foo);
			return list.get(middle);
		} else {
			//System.out.printf("Median: %.4f\n",bar);
			return (list.get(middle - 1)+ list.get(middle)) / 2.0;
		}
	}

	private static double fiveNumSum(ArrayList<Integer> list){
		double med = 0;
		double min = list.get(0);
		double max = list.get(list.size() - 1);
		double lowq = (int) Math.round(list.size() * .25 / 100);
		double highq = (int) Math.round(list.size() * .75 / 100);

		int middle = list.size()/2;
		if (list.size()%2 == 1) {
			//System.out.printf("Median: %.4f\n",foo);
			med =  list.get(middle);
		} else {
			//System.out.printf("Median: %.4f\n",bar);
			med =  (list.get(middle - 1)+ list.get(middle)) / 2.0;
		}

		return min + max + lowq + highq + med;

	}

	public static double quartile(ArrayList<Integer> list, double percent) {
		double med = 0;
		int middle = list.size()/2;
		if (list.size()%2 == 1) {
			//System.out.printf("Median: %.4f\n",foo);
			med =  list.get(middle);
		} else {
			//System.out.printf("Median: %.4f\n",bar);
			med =  (list.get(middle - 1)+ list.get(middle)) / 2.0;
		}

		int n = (int) Math.round(list.size() * percent / 100);


		return n;

	}


}
