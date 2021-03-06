package GUI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import filtersPack.AND_filter;
import filtersPack.DoFilter;
import filtersPack.Filter;
import filtersPack.OR_filter;
import filtersPack.filter_id;
import filtersPack.filter_location;
import filtersPack.filter_time;
import objects.MacBig_Container;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;



public class filters_writeAndRead {


	public static void write_filter(String fileName,Filter[]filters){

		FileOutputStream f = null;
		ObjectOutputStream o;

		try {
System.out.println("********");
			f = new FileOutputStream(fileName);
			o = new ObjectOutputStream(f);
			o.writeObject(filters);
			System.out.println("*-*-*-*-*");
			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**

	 * A function that get path and kind of operator as string and read

	 * the file with Serialization and cast the object to the correct operator

	 * @param path

	 * @param end

	 * @return

	 */

	public static Filter[] ReadObject(String path,Connect c)
	{
		FileInputStream fi;
		ObjectInputStream oi;
		Filter[] filters= new Filter[3];

		try {
			fi = new FileInputStream(path);
			oi = new ObjectInputStream(fi);
			filters = (Filter[])(oi.readObject());
			System.out.println(filters[0].toString());

			if (!((""+filters[1]).equals("null"))) {
				if (filters[1].getClass().getName().contains("AND_filter")) {
					c.and_filter(filters[0], filters[2]);
					}
				else if(filters[1].getClass().getName().contains("OR_filter")){
					c.OR_filter(filters[0], filters[2]);
				}
			}
			else if (!(""+filters[0]).equals("null") && (""+filters[1]).equals("null")) {
				if((""+filters[0]).toString().contains("filter_id")){
					DoFilter fl = new DoFilter(filters[0]);
					filters[0] = (filter_id)filters[0];
					DoFilter fil = new DoFilter(filters[0]);
					c.macs = fil.filtering(c.macs);
					//c.addfilter_ID(filters[0]);
				}
				else if((""+filters[0]).toString().contains("filter_location")){
					DoFilter fl = new DoFilter(filters[0]);
					filters[0] = (filter_location)filters[0];
					DoFilter fil = new DoFilter(filters[0]);
					c.macs = fil.filtering(c.macs);
				}
				else if((""+filters[0]).toString().contains("filter_time")){
					DoFilter fl = new DoFilter(filters[0]);
					filters[0] = (filter_time)filters[0];
					DoFilter fil = new DoFilter(filters[0]);
					c.macs = fil.filtering(c.macs);
				}
			}
//			DoFilter fl = new DoFilter(filters);
//			fl.filtering(macs);

			oi.close();
			fi.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

		return filters;

	}

}


