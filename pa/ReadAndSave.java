package pa;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import objects.LineOfInfo;


/**
 * This class get folder of files, read them, sort them, and convert them to one CSV sorted file.
 * @author 
 *
 */

public class ReadAndSave {

	/**
	 * This function get folder of files and sort it for getting only relevant files of CSV type.
	 * for doing that the function call another functions from it's class and from another classes on this package.
	 * at the end , the function convert all of the sorted information to new CSV file.
	 * 
	 * https://stackoverflow.com/questions/11496700/how-to-use-printwriter-and-file-classes-in-java
	 * https://stackoverflow.com/questions/11485311/how-can-a-fileinputstream-get-the-content-of-file
	 * 
	 * @param folder
	 * @return
	 * @throws IOException
	 */

	public static ArrayList<String[]> readingFile(File folder)  {	

		ArrayList<String[]> answer = new ArrayList<String[]>();

		File[] listOfFiles = folder.listFiles();
		ArrayList<String[]> information = new ArrayList<String[]>();

		answer.add(MadeLine());
		int r=2;
		boolean flag = false;

		for (int i = 0; i < listOfFiles.length; i++) {
			flag = false;

			try {

				if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("csv")) {
					File f = new File(listOfFiles[i].getPath());
					FileInputStream fi = new FileInputStream(f);
					Scanner sc = new Scanner(fi);
					int m = 0;
					//					int row = 0;
					//					BufferedReader read = new BufferedReader(new FileReader(listOfFiles[i].getPath()));
					//					while (read.readLine() != null) {
					//						row++;
					//					}
					//					read.close();

					//information = new String[row][12];
					r = 2;

					while (sc.hasNext()) {
						String str = sc.nextLine();
						String []a = str.split(",");
						information.add(a) ;
						try {
							if ((information.get(0)[0].contains("WigleWifi-1.4")) && (!(information.get(0)[0].equals(null)))
									&& (!(information.get(0)[1].equals(null)))) {
								m++;
							}
							else{
								throw new IOException();
							}
						}
						catch (Exception e) {
							flag = true;
							System.err.println("The file " + listOfFiles[i].getName() + " is illegal!!");
						}
					}

					if(flag)
						continue;

					Save_info(information, answer);
					information = new ArrayList<String[]>(); 
					sc.close();
					fi.close();
				}

				else {
					throw new IOException(); 
				}
			}
			catch (Exception e) {
				System.err.println("File " + listOfFiles[i].getName() + " is not csv file!");
			}
		}
		//		System.out.println("---------------------------------------size:  "+answer.size());
		//		FindLocation.checkMac(answer);
		//return WriteToCsv(answer);
		return answer;
	}

	/**
	 * The function create line of the Headers of values
	 * @return the line with the Headers of values
	 */
	public static String[] MadeLine(){
		String[] line = new String[46];
		line[0] = "Time";
		line[1] = "ID";
		line[2] = "Lat";
		line[3] = "Lon";
		line[4] = "Alt";
		line[5] = "#WiFi networks";
		String helper1 = "SSID";
		String helper2 = "MAC";
		String helper3 = "Frequncy";
		String helper4 = "Signal";
		int hel = 1;

		for (int i = 6; i < line.length; i=i+4) {
			line[i] = helper1 + hel;
			hel++;
		}
		hel = 1;
		for (int i = 7; i < line.length; i=i+4) {
			line[i] = helper2 + hel;
			hel++;
		}
		hel = 1;
		for (int i = 8; i < line.length; i=i+4) {
			line[i] = helper3 + hel;
			hel++;
		}
		hel = 1;
		for (int i = 9; i < line.length; i=i+4) {
			line[i] = helper4 + hel;
			hel++;
		}

		return line;

	}

	/**
	 * The function accept array of String [] type and write this to csv file.
	 * The function return the matrix after if create csv file 
	 * @param answer
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String[]> WriteToCsv(ArrayList<String[]> answer,String location) throws IOException{

		ArrayList<String[]> Answer_One = new ArrayList<String[]>();
		Answer_One.addAll(answer);
		System.out.println(Answer_One.size()+ "  *******");
		FileWriter write = new FileWriter(location);
		PrintWriter pw = new PrintWriter(write);
		for (int i = 0; i < Answer_One.size(); i++) {
			for (int j = 0; j < Answer_One.get(i).length; j++) {
				write.append(Answer_One.get(i)[j]);
				write.append(",");
			}
			pw.println();
		}
		write.close();
		System.out.println("completed Csv");
		return Answer_One;
	}

	/**
	 * This function copying all of the relevant values from information matrix to answer.
	 * this function use arraylist of String[] type. so it call another functions for doing it.
	 * @param information
	 * @param answer
	 * @param r
	 */
	public static void Save_info(ArrayList<String[]> information,ArrayList<String[]> answer){
		int TimePlace = FindIndex.PlaceArticle(information,"FirstSeen",1);
		int LatPlace = FindIndex.PlaceArticle(information,"CurrentLatitude",1);
		int LonPlace = FindIndex.PlaceArticle(information,"CurrentLongitude",1);
		int WifiPlace = FindIndex.PlaceArticle(information,"Type",1);
		ArrayList<LineOfInfo> arrLineOfInfo = new ArrayList<LineOfInfo>();
		int r=1;
		while (r<information.size()){
			if ((information.get(r)[WifiPlace]).equals("WIFI")){
				if (r<information.size()-1&&(information.get(r+1)[WifiPlace]).equals("WIFI")&&information.get(r)[LatPlace].equals(information.get(r+1)[LatPlace])
						&& information.get(r)[LonPlace].equals(information.get(r+1)[LonPlace]) 
						&& information.get(r)[TimePlace].equals(information.get(r+1)[TimePlace])){

					LineOfInfo line= new LineOfInfo(information,r);
					arrLineOfInfo.add(line);

				}

//				else if (((r<=information.size()-1)&&(information.get(r)[WifiPlace]).equals("WIFI"))&&
//						(information.get(r)[LatPlace].equals(information.get(r+1)[LatPlace])
//								|| information.get(r)[LonPlace].equals(information.get(r+1)[LonPlace]) 
//								|| information.get(r)[TimePlace].equals(information.get(r+1)[TimePlace]))){
				else{
					LineOfInfo line= new LineOfInfo(information,r);
					arrLineOfInfo.add(line);

					if(arrLineOfInfo.size() >= 10){
						String infoofLine[]=Copying.CopyingToAnswerFirst(information,answer,r,arrLineOfInfo.size());
						Copying.CopyingToAnswer(arrLineOfInfo ,answer,r,10,arrLineOfInfo.size(),infoofLine);

						arrLineOfInfo.clear();
					}
					else{
						String infoofLine[]=Copying.CopyingToAnswerFirst(information,answer,r,arrLineOfInfo.size());
						Copying.CopyingToAnswer(arrLineOfInfo,answer,r,arrLineOfInfo.size(),arrLineOfInfo.size(),infoofLine);
						arrLineOfInfo.clear();

					}
				}
			}
			r++;
		}
	}
}