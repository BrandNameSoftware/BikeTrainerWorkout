package com.brandNameSoftware.bikeTrainer.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandNameSoftware.bikeTrainer.R;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutSet;
import com.brandNameSoftware.workoutGenerator.utils.WorkoutMaths;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {

	private ArrayList<WorkoutSet> workoutSets = new ArrayList<WorkoutSet>();
	private Context context;
	private HashMap<Integer, WorkoutConstraints> workoutConstraints;
	
	public WorkoutAdapter(Context context, ArrayList<WorkoutSet> workoutSets, HashMap<Integer, WorkoutConstraints> workoutConstraints) {
		this.context = context;
		this.workoutSets = workoutSets;
		this.workoutConstraints = workoutConstraints;
	}

	@Override
	public int getItemCount() {
		return this.workoutSets.size();
	}

	/*@Override
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
			gridView = inflater.inflate(R.layout.layout_workout_set, parent, false);
			
			TextView txtViewZone = (TextView) gridView.findViewById(R.id.txtViewZone);
			TextView txtViewRepTime = (TextView) gridView.findViewById(R.id.txtViewRepTime);
			TextView txtViewFTP = (TextView) gridView.findViewById(R.id.txtViewFTP);
			TextView txtViewReps = (TextView) gridView.findViewById(R.id.txtViewReps);

			txtViewZone.setText("Zone: " + workoutSets.get(position).getTargetZone());
			txtViewRepTime.setText("Time per rep: " + WorkoutMaths.formatMillisAsTime(workoutSets.get(position).getTimePerRep() * 1000));
			WorkoutConstraints currentConstraint = workoutConstraints.get(workoutSets.get(position).getTargetZone());
			txtViewFTP.setText("Power Zones: " + currentConstraint.getMinPower() + " - " + currentConstraint.getMaxPower());
			txtViewReps.setText("Reps: " + workoutSets.get(position).getNumberOfReps());
		}
		else
		{
			gridView = (View) convertView;

		}
		
		return gridView;
	}*/

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onBindViewHolder(WorkoutViewHolder viewHolder, int position) {
		viewHolder.txtViewZone.setText(Integer.toString(workoutSets.get(position).getTargetZone()));
		viewHolder.txtViewRepTime.setText(WorkoutMaths.formatMillisAsTime(workoutSets.get(position).getTimePerRep() * 1000));
		
		WorkoutConstraints currentConstraint = workoutConstraints.get(workoutSets.get(position).getTargetZone());
		viewHolder.txtViewFTP.setText(currentConstraint.getMinPower() + " - " + currentConstraint.getMaxPower());
		viewHolder.txtViewReps.setText(Integer.toString(workoutSets.get(position).getNumberOfReps()));
		viewHolder.txtViewHR.setText(currentConstraint.getHRRangeString());
	}
	
	@Override
	public WorkoutViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_workout_set, viewGroup, false);
        return new WorkoutViewHolder(v);
	}
}
