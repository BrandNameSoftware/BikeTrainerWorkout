package com.brandNameSoftware.bikeTrainer.adapters;

import com.brandNameSoftware.bikeTrainer.R;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

public class WorkoutViewHolder extends ViewHolder {

	TextView txtViewZone;
	TextView txtViewRepTime;
	TextView txtViewFTP;
	TextView txtViewReps;
	TextView txtViewHR;
	
	public WorkoutViewHolder(View itemView) {
		super(itemView);
		txtViewZone = (TextView) itemView.findViewById(R.id.txtViewZone);
		txtViewRepTime = (TextView) itemView.findViewById(R.id.txtViewRepTime);
		txtViewFTP = (TextView) itemView.findViewById(R.id.txtViewFTP);
		txtViewReps = (TextView) itemView.findViewById(R.id.txtViewReps);
		txtViewHR = (TextView) itemView.findViewById(R.id.txtViewHR);
	}

}
