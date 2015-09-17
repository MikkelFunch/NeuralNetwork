package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import network.Genome;

import com.google.gson.Gson;

public class IoHandler {
	private static Gson gson = new Gson();
	
	public static void saveGenome(Genome g){
		String json = gson.toJson(g);
		saveToFile(json, g.getNumber() + ""); // ID IS NOT IMPLEMENTED IN GENOME
	}
	
	
	private static void saveToFile(String s, String name){
		try {
			PrintWriter out = new PrintWriter(name);
			out.println(s);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
