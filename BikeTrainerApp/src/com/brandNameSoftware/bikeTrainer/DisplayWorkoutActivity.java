package com.brandNameSoftware.bikeTrainer;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.brandNameSoftware.bikeTrainer.adapters.WorkoutAdapter;
import com.brandNameSoftware.workoutGenerator.WorkoutGenerator;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutPrefs;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutSet;
import com.brandNameSoftware.workoutGenerator.utils.WorkoutMaths;

public class DisplayWorkoutActivity extends ActionBarActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_workout);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		Intent intent = getIntent();
		WorkoutPrefs workoutPrefs = (WorkoutPrefs) intent.getSerializableExtra(MainActivity.WORKOUT_PREFERENCES);
		HashMap<Integer, WorkoutConstraints>  workoutConstraints = (HashMap<Integer, WorkoutConstraints> ) intent.getSerializableExtra(MainActivity.WORKOUT_CONSTRAINTS);
		
		ArrayList<WorkoutSet> mainSets = generateWorkout(workoutPrefs, workoutConstraints);
		
		TextView totalCounTextView = (TextView) findViewById(R.id.txtViewTotalCountdown);
		totalCounTextView.setText(Integer.toString(workoutPrefs.getTime()));

		RecyclerView listViewWorkoutSets = (RecyclerView) findViewById(R.id.listViewWorkoutSets);
		
		listViewWorkoutSets.setLayoutManager(new LinearLayoutManager(this));
		listViewWorkoutSets.setItemAnimator(new DefaultItemAnimator());
		
		listViewWorkoutSets.setAdapter(new WorkoutAdapter(this, mainSets, workoutConstraints));
		
		int totalWorkoutTime = 0;
		for (WorkoutSet mainSet : mainSets) 
		{
			System.out.println(mainSet.toString());
			totalWorkoutTime += mainSet.getTotalSetTime();
		}
		System.out.println("Total workout time is: " + totalWorkoutTime/60 + " minutes");
		
		/*TextView setCountTextView = (TextView) findViewById(R.id.txtViewRepCountdown);
		setCountTextView.setText(Integer.toString(mainSets.get(0).getTotalSetTime()));*/
		
		CountDownTimer totalWorkoutTimer = setupTimer(mainSets);
		totalWorkoutTimer.start();
	}

	private CountDownTimer setupTimer(ArrayList<WorkoutSet> workoutSets)
	{
		int totalWorkoutTime = 0;
		
		for (WorkoutSet currentSet : workoutSets)
		{
			totalWorkoutTime += currentSet.getTotalSetTime();
		}
		
		WorkoutCountDownTimer totalWorkoutTimer = new WorkoutCountDownTimer(totalWorkoutTime * 1000, 1000, workoutSets);
		
		return totalWorkoutTimer;
	}
	
	private ArrayList<WorkoutSet> generateWorkout(WorkoutPrefs workoutPrefs, HashMap<Integer, WorkoutConstraints> workoutConstraints)
	{
		WorkoutGenerator generator = new WorkoutGenerator(workoutConstraints, workoutPrefs);
		ArrayList<WorkoutSet> mainSets = generator.generateMainSets();
		
		int totalWorkoutTime = 0;
		for (WorkoutSet mainSet : mainSets) 
		{
			totalWorkoutTime += mainSet.getTotalSetTime();
		}
		
		return mainSets;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_workout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class WorkoutCountDownTimer extends CountDownTimer
	{
		ArrayList<WorkoutSet> mainSets = new ArrayList<WorkoutSet>();
		WorkoutSet currentSet = null;
		private Long timeLeftExcludingCurrentRep;
		private int currentSetIndex = 0;
		private boolean isWorkingSet = true;
		private int currentRepNum = 1;
		private Long currentRepTimeMillis = 0L;
		
		public WorkoutCountDownTimer(long millisInFuture, long countDownInterval, ArrayList<WorkoutSet> mainSets) {
			super(millisInFuture, countDownInterval);
			this.mainSets = mainSets;
			this.timeLeftExcludingCurrentRep = millisInFuture;
			currentSet = mainSets.get(0);
			currentRepTimeMillis = Long.valueOf(currentSet.getTimePerRep()) * 1000;
			TextView totalCounTextView = (TextView) findViewById(R.id.txtViewTotalCountdown);
			totalCounTextView.setText(WorkoutMaths.formatMillisAsTime(millisInFuture));
			TextView repCountTextView = (TextView) findViewById(R.id.txtViewRepCountdown);
			repCountTextView.setText(WorkoutMaths.formatMillisAsTime(currentSet.getTimePerRep()));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			TextView totalCounTextView = (TextView) findViewById(R.id.txtViewTotalCountdown);
			totalCounTextView.setText(WorkoutMaths.formatMillisAsTime(millisUntilFinished));

			TextView repCountTextView = (TextView) findViewById(R.id.txtViewRepCountdown);
			Long remainingRepTimeMillis = currentRepTimeMillis - (timeLeftExcludingCurrentRep - Long.valueOf(millisUntilFinished));
			
			if(remainingRepTimeMillis <= 0)
			{
				if(isWorkingSet)
				{
					//switch to the resting portion
					this.timeLeftExcludingCurrentRep -= this.currentSet.getTimePerRep() * 1000;
					remainingRepTimeMillis = Long.valueOf(currentSet.getRestTimePerRep()) * 1000;
					currentRepTimeMillis = Long.valueOf(currentSet.getRestTimePerRep()) * 1000;
					isWorkingSet = false;
				}
				else
				{
					this.timeLeftExcludingCurrentRep -= this.currentSet.getRestTimePerRep() * 1000;
					isWorkingSet = true;

					currentRepNum++;
					if(currentRepNum <= currentSet.getNumberOfReps())
					{
						//still working through this set's reps
						remainingRepTimeMillis = Long.valueOf(currentSet.getTimePerRep()) * 1000;
						currentRepTimeMillis = Long.valueOf(currentSet.getTimePerRep()) * 1000;
					}
					else
					{
						//switch to the next set because we're done with the reps
						currentSetIndex++;
						currentRepNum = 1;
						//just a safety precaution. This should never happen
						if(mainSets.size() > currentSetIndex)
						{
							currentSet = mainSets.get(currentSetIndex);
							remainingRepTimeMillis = Long.valueOf(currentSet.getTimePerRep()) * 1000;
							currentRepTimeMillis = Long.valueOf(currentSet.getTimePerRep()) * 1000;
							/*CardView cardView = (CardView)findViewById(R.id.card_view);
							View nextSet = cardView.getChildAt(currentSetIndex);
							cardView.scrollTo((int)nextSet.getX(), (int)nextSet.getY());*/
						}
					}
				}
			}
			
			repCountTextView.setText(WorkoutMaths.formatMillisAsTime(remainingRepTimeMillis));
		}
		
		@Override
		public void onFinish() {
			TextView totalCounTextView = (TextView) findViewById(R.id.txtViewTotalCountdown);
			totalCounTextView.setText("Done!");
		    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		}

		public ArrayList<WorkoutSet> getMainSets() {
			return mainSets;
		}

		public void setMainSet(ArrayList<WorkoutSet> mainSets) {
			this.mainSets = mainSets;
		}

	}
}
