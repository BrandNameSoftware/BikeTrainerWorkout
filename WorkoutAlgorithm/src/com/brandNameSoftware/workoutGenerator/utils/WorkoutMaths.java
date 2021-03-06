package com.brandNameSoftware.workoutGenerator.utils;

import java.util.Random;

public class WorkoutMaths
{

    public static Random rand = new Random();
	
	public static int randInt(int min, int max)
	{
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	


	public static String formatMillisAsTime(long millis)
	{
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		return time;
	}

}
