package com.brandNameSoftware.bikeTrainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.brandNameSoftware.bikeTrainer.adapters.ZoneAdapter;
import com.brandNameSoftware.workoutGenerator.ImportPrefs;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutConstraints;
import com.brandNameSoftware.workoutGenerator.datacontainer.WorkoutPrefs;

public class MainActivity extends ActionBarActivity {
	
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

                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                
                view.setSelected(true);
                
                //deselect the previous selection
                if(selectedZone > 0)
                {
                	zoneList.getChildAt(selectedZone - 1).setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                
                selectedZone = position + 1; //zones aren't 0 indexed
            	zoneList.getChildAt(selectedZone - 1).setBackgroundColor(getResources().getColor(R.color.accent));
                
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
        
        EditText editText = (EditText) findViewById(R.id.editTxtTime);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(v.getId() == R.id.editTxtTime && !hasFocus) {
		            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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


