package com.brandNameSoftware.workoutGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutPrefs;

public class ImportPrefs {
	public static HashMap<Integer, WorkoutConstraints> ReadWorkoutconstraints(InputStream inputStream)
	{
		HashMap<Integer, WorkoutConstraints> workouts = new HashMap<Integer, WorkoutConstraints>();

		BufferedReader br = null;
		try
		{
			String sCurrentLine;
			
			br = new BufferedReader(new InputStreamReader(inputStream));
 
			int lineCount = 0;
			while ((sCurrentLine = br.readLine()) != null)
			{
				if(lineCount != 0)
				{
					WorkoutConstraints currentInfo = populateWorkoutInfoObject(sCurrentLine);
					workouts.put(currentInfo.getZone(), currentInfo);
				}
				lineCount++;
			}
 
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return workouts;
	}

	private static WorkoutConstraints populateWorkoutInfoObject(String sWorkoutInfo)
	{
		WorkoutConstraints workoutInfo = new WorkoutConstraints();
		String[] workoutVals = sWorkoutInfo.split(",");
		workoutInfo.setZone(Integer.parseInt(workoutVals[0]));
		workoutInfo.setDescription(workoutVals[1]);
		workoutInfo.setMinReps(Integer.parseInt(workoutVals[2]));
		workoutInfo.setMaxReps(Integer.parseInt(workoutVals[3]));
		workoutInfo.setMinRepTime(Integer.parseInt(workoutVals[4]));
		workoutInfo.setMaxRepTime(Integer.parseInt(workoutVals[5]));
		workoutInfo.setTargetRPM(Integer.parseInt(workoutVals[6]));
		workoutInfo.setMinPower(Integer.parseInt(workoutVals[7]));
		workoutInfo.setMaxPower(Integer.parseInt(workoutVals[8]));
		workoutInfo.setMinHR(Integer.parseInt(workoutVals[9]));
		workoutInfo.setMaxHR(Integer.parseInt(workoutVals[10]));
		workoutInfo.setRestRatio(Double.parseDouble(workoutVals[11]));
		workoutInfo.setMaxSetTimePerWorkout(Integer.parseInt(workoutVals[12]));
		return workoutInfo;
	}
	
	public static WorkoutPrefs ReadWorkoutPrefs()
	{
		WorkoutPrefs workoutPrefs = new WorkoutPrefs();
		/*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
			System.out.print("Enter desired workout time");
			workoutPrefs.setTime(Integer.parseInt(br.readLine()));
			System.out.print("Enter main target zone:");
			workoutPrefs.setZone(Integer.parseInt(br.readLine()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		workoutPrefs.setTime(3600);
		workoutPrefs.setZone(2);
		
		return workoutPrefs;
	}
}
