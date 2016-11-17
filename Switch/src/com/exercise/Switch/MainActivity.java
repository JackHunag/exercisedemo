package com.exercise.Switch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.exercise.Switch.ui.ToggleView;
import com.exercise.Switch.ui.ToggleView.OnSwitchStateUpdateListener;

public class MainActivity extends Activity {

	private ToggleView toggleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggleView = (ToggleView) findViewById(R.id.toggleView);
	
		toggleView.setOnSwitchStateUpdateListener(new OnSwitchStateUpdateListener() {
			
			@Override
			public void SwitchStateUpdateListener(boolean state) {
				
				Toast.makeText(getApplicationContext(),state+"" , 0).show();
				
			}
		});
	
	}

}
