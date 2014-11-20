package com.hudl.workout.datacontainer;

import java.io.Serializable;

public class WorkoutPrefs implements Serializable {

	private int zone;
	private int time;
	
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
