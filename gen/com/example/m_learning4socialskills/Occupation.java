package com.example.m_learning4socialskills;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


@SuppressLint("NewApi")
public class Occupation extends Activity implements OnClickListener{
	
	Button lecturer, student, admin, next;
	TextView Register;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_occupation);
		
		lecturer = (Button) findViewById(R.id.btnlecturer);
		lecturer.setOnClickListener((OnClickListener) this);
		student = (Button) findViewById(R.id.btnstudent);
		student.setOnClickListener((OnClickListener) this);
		admin = (Button) findViewById(R.id.btnadmin);
		admin.setOnClickListener((OnClickListener) this);
		Register = (TextView) findViewById(R.id.register);
		Register.setText("Don't have an account? Click here to register");
			}
	
	private void setupActionBar() 
	{
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.occupation, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v){
		switch (v.getId()){
		case R.id.btnlecturer:
			startActivity(new Intent(Occupation.this, LecturerLogin.class));
			break;
			
		case R.id.btnstudent:
			startActivity(new Intent(Occupation.this, StudentLogin.class));
			break;
		
		case R.id.btnadmin:
			startActivity(new Intent(Occupation.this, AdminLogin.class));
			break;
			}
	}
	public void myMethod(View view){
		 Register.setTextColor(Color.MAGENTA);
		if (view == Register){
		 		 startActivity(new Intent (Occupation.this, Register.class));
					 }
	 }
}
