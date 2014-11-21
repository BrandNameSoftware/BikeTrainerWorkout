package com.hudl.myfirstapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hudl.workout.datacontainer.WorkoutConstraints;
import com.hudl.workout.datacontainer.WorkoutSet;
import com.hudl.workout.utils.WorkoutMaths;

public class WorkoutAdapter extends BaseAdapter {

	private ArrayList<WorkoutSet> workoutSets = new ArrayList<WorkoutSet>();
	private Context context;
	private HashMap<Integer, WorkoutConstraints> workoutConstraints;
	public WorkoutAdapter(Context context, ArrayList<WorkoutSet> workoutSets, HashMap<Integer, WorkoutConstraints> workoutConstraints) {
		this.context = context;
		this.workoutSets = workoutSets;
		this.workoutConstraints = workoutConstraints;
	}

	@Override
	public int getCount() {
		return this.workoutSets.size();
	}

	@Override
	public WorkoutSet getItem(int position) {
		return workoutSets.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		
		if(convertView == null)
		{
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.activity_display_workout, null);
			
			TextView txtViewZone = (TextView) gridView.findViewById(R.id.txtViewZone);
			TextView txtViewRepTime = (TextView) gridView.findViewById(R.id.txtViewRepTime);
			TextView txtViewFTP = (TextView) gridView.findViewById(R.id.txtViewFTP);
			TextView txtViewReps = (TextView) gridView.findViewById(R.id.txtViewReps);
			/*TextView txtViewHR = (TextView) gridView.findViewById(R.id.txtViewHR);
			TextView txtViewRPM = (TextView) gridView.findViewById(R.id.txtViewRPM);*/

			txtViewZone.setText("Zone: " + workoutSets.get(position).getTargetZone());
			txtViewRepTime.setText("Time per rep: " + WorkoutMaths.formatMillisAsTime(workoutSets.get(position).getTimePerRep() * 1000));
			WorkoutConstraints currentConstraint = workoutConstraints.get(workoutSets.get(position).getTargetZone());
			txtViewFTP.setText("Power Zones: " + currentConstraint.getMinPower() + " - " + currentConstraint.getMaxPower());
			txtViewReps.setText("Reps: " + workoutSets.get(position).getNumberOfReps());
			/*
			txtViewHR.setText(workoutSets.get(position).getTargetZone());
			txtViewRPM.setText(workoutSets.get(position).getTargetZone());*/
		}
		else
		{
			gridView = (View) convertView;

		}
		
		return gridView;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
