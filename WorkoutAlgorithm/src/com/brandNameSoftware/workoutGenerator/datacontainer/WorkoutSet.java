package com.brandNameSoftware.workoutGenerator.datacontainer;

import java.io.Serializable;

public class WorkoutSet implements Serializable
{
	private int numberOfReps;
	private int timePerRep;
	private int restTimePerRep;
	private int totalSetTime;
	private int targetZone;
	
	public int getNumberOfReps() {
		return numberOfReps;
	}
	public void setNumberOfReps(int numberOfReps) {
		this.numberOfReps = numberOfReps;
	}
	public int getTimePerRep() {
		return timePerRep;
	}
	public void setTimePerRep(int timePerRep) {
		this.timePerRep = timePerRep;
	}
	public int getRestTimePerRep() {
		return restTimePerRep;
	}
	public void setRestTimePerRep(int restTimePerRep) {
		this.restTimePerRep = restTimePerRep;
	}
	public int getTotalSetTime() {
		this.totalSetTime = numberOfReps * (timePerRep + restTimePerRep);
		return totalSetTime;
	}
	
	public int getTargetZone() {
		return targetZone;
	}
	public void setTargetZone(int targetZone) {
		this.targetZone = targetZone;
	}
	public String toString()
	{
		String humanReadableWorkout = numberOfReps + " x " + (timePerRep) + " seconds in zone " + targetZone +  " with " + (restTimePerRep) + " seconds rest in between. Total set time is: " + (totalSetTime) + " seconds";
		return humanReadableWorkout;
	}
}
