package com.brandNameSoftware.bikeTrainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.brandNameSoftware.bikeTrainer.adapters.ZoneAdapter;
import com.brandNameSoftware.workoutGenerator.ImportPrefs;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutPrefs;

public class MainActivity extends Activity {
	
	List<String> list;
	ListView zoneList;
	int selectedZone = 0;
	public final static String WORKOUT_PREFERENCES = "workoutPrefs";
	public final static String WORKOUT_CONSTRAINTS = "workoutConstraints";
	HashMap<Integer, WorkoutConstraints> workoutConstraints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		this.workoutConstraints = readWorkoutConstraints();
        
        zoneList=(ListView) findViewById(R.id.zoneList);
        zoneList.setAdapter(new ZoneAdapter(this, workoutConstraints));
        
        zoneList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3)
            {
                view.setSelected(true);
                selectedZone = position + 1; //zones aren't 0 indexed
                EditText txtDesiredTime = (EditText) findViewById(R.id.editTxtTime);
        		Button startWorkoutBtn = (Button) findViewById(R.id.btnStartWorkout);
        		String timeText = txtDesiredTime.getText().toString().trim();
            	if(!timeText.equalsIgnoreCase("") && Integer.valueOf(timeText) > 0)
            	{
            		startWorkoutBtn.setEnabled(true);
            	}
            	else
            	{
            		startWorkoutBtn.setEnabled(false);
            	}
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    /** Called when the user clicks the Send button */
    public void btnStartWorkout(View view)
    {
    	WorkoutPrefs prefs = new WorkoutPrefs();

		EditText txtDesiredTime = (EditText) findViewById(R.id.editTxtTime);
		int workouttime = Integer.valueOf(txtDesiredTime.getText().toString());
    	
		prefs.setTime(workouttime * 60);
		prefs.setZone(selectedZone);
    
    	
		
		//switch screens
		Intent intent = new Intent(this, DisplayWorkoutActivity.class);
		intent.putExtra(WORKOUT_PREFERENCES, prefs);
		intent.putExtra(WORKOUT_CONSTRAINTS, this.workoutConstraints);
		startActivity(intent);		
    }
	
	private HashMap<Integer, WorkoutConstraints> readWorkoutConstraints()
	{
		AssetManager assetManager = this.getAssets();
    	
    	HashMap<Integer, WorkoutConstraints> constraints = new HashMap<Integer, WorkoutConstraints>();
		try {
			constraints = ImportPrefs.ReadWorkoutconstraints(assetManager.open("WorkoutConfig.properties"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return constraints;
	}
}


