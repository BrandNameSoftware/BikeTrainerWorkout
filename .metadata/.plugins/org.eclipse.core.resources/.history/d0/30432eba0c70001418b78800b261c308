package com.hudl.workout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportPrefs {
	public static WorkoutInfo ReadWorkoutPrefs()
	{

		 BufferedReader br = null;
		try {
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\testing.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
