package mainClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import algo1and2.FindLocation;
import algo1and2.Formulas;
import filtersPack.ChooseFilter;
import filtersPack.filters;
import objects.Location;
import objects.MacBig;

/**
 * This class check and use the classes of all of packages.
 * the class contains main.
 * @author 
 *
 */
public class main {

	public static void main(String[] args) throws IOException {


		// Matala zero+one: {
		//hadar
		//filtersPack.ChooseFilter.Decide();  
		
		// } end of Matala zero+one
		
		// Matala two: {
		
		String foldername1 = "boazFiles";
		File folder1 = new File(foldername1);
		
		String foldername2 = "matala two";
		File folder2=new File(foldername2);

		
		String locationAlgo1 = "C:\\Users\\���� ������\\Desktop\\Answer exampels\\Answer_Of_Matala_two_algo1.csv";
		FindLocation.Matala2_Algo1 (folder1, locationAlgo1);
		
		String locationAlgo2 = "C:\\Users\\���� ������\\Desktop\\Answer exampels\\Answer_Of_Matala_two_algo2.csv";
		FindLocation.Matala2_Algo2 (folder1,folder2,locationAlgo2);
//		
	    // } end of Matala two

	}
}


