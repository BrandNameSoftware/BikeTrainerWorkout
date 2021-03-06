package com.brandNameSoftware.bikeTrainer.adapters;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brandNameSoftware.bikeTrainer.R;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;

public class ZoneAdapter extends BaseAdapter {

	private Context context;
	HashMap<Integer, WorkoutConstraints> workoutConstraints;
	public ZoneAdapter(Context context, HashMap<Integer, WorkoutConstraints> workoutConstraints) {
		this.context = context;
		this.workoutConstraints = workoutConstraints;
	}

	@Override
	public int getCount() {
		return workoutConstraints.size();
	}

	@Override
	public WorkoutConstraints getItem(int position) {
		return workoutConstraints.get(position + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listView;
		
		if(convertView == null)
		{
			listView = new View(context);
			listView = inflater.inflate(R.layout.layout_zone_list, parent, false);
			
			TextView txtViewZoneHeader = (TextView) listView.findViewById(R.id.txtViewZoneLine1);
			TextView txtViewZoneDetails = (TextView) listView.findViewById(R.id.txtViewZoneLine2);

			WorkoutConstraints currentConstraint = getItem(position);
			
			txtViewZoneHeader.setText("Zone " + currentConstraint.getZone());
			txtViewZoneDetails.setText("Power - " + currentConstraint.getMinPower() + "-" + currentConstraint.getMaxPower() + "% FTP    HR - " + 
					currentConstraint.getHRRangeString());
		}
		else
		{
			listView = (View) convertView;

		}
		
		return listView;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
