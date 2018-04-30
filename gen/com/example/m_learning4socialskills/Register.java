package com.example.m_learning4socialskills;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;


public class Register extends Activity implements OnClickListener {

	EditText fname, lname, uname, email, password;
	Button submit;
	RadioButton rb;
	RadioGroup genderRG, occupationRG;
	MainActivity myDb;
	SQLiteDatabase mlearning;
	String gender = "select", occupation = "select";
	ScrollView sv_view;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mlearning = openOrCreateDatabase("m_learning", Context.MODE_PRIVATE, null);
		mlearning.setVersion(1);
		mlearning.execSQL("CREATE TABLE IF NOT EXISTS register(fname VARCHAR NOT NULL, lname VARCHAR NOT NULL, uname VARCHAR NOT NULL, email VARCHAR NOT NULL, password VARCHAR NOT NULL, gender VARCHAR NOT NULL, occupation VARCHAR NOT NULL);");
		
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();
		sv_view= (ScrollView)findViewById(R.id.sv_view);
		fname = (EditText) findViewById(R.id.regfname);
		lname =  (EditText) findViewById(R.id.reglname);
		uname = (EditText) findViewById(R.id.reguname);
		email = (EditText) findViewById (R.id.regemail);
		password = (EditText) findViewById(R.id.regpassword);
		submit = (Button) findViewById(R.id.btnreg);
		submit.setOnClickListener(this);
		genderRG = (RadioGroup) findViewById(R.id.rgroup);
		occupationRG = (RadioGroup) findViewById(R.id.occupationRadios);
		
		OnCheckedChangeListener occupationListener = new OnCheckedChangeListener() 
		{	
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				// TODO Auto-generated method stub
				if( checkedId ==  R.id.adminRadio )
				{
					occupation = "Administrator";
				}
				else if( checkedId == R.id.lecRadio )
				{
					occupation = "Lecturer";
				}
				else if( checkedId == R.id.studentRadio )
				{
					occupation = "Student";
				}
				
			}
		};
		occupationRG.setOnCheckedChangeListener(occupationListener);
		
		OnCheckedChangeListener radioButtonListener = new OnCheckedChangeListener() 
		{	
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) 
			{
				// TODO Auto-generated method stub
				switch (arg1) 
				{
					case R.id.rbmale:
						gender = "male";
						break;
					case R.id.rbfemale:
						gender = "female";
						break;
				}
			}
		};
		genderRG.setOnCheckedChangeListener(radioButtonListener);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@SuppressLint("NewApi")
	private void setupActionBar() 
	{
//		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
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

	public void rbChecked(View v)
	{
		int radiobuttonid = genderRG.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton)findViewById(radiobuttonid);
		Toast.makeText(getBaseContext(),rb.getText(),Toast.LENGTH_LONG).show();	
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v == submit)
		{
			submitData(null);
		}
	}
	
	public boolean passwordIsLongEnough(String password)
	{
		if( password.length() < 8 || gender.equals("select") || occupation.equals("select"))
		{
			Toast.makeText(this, "Password must be at least 8 characters long. Select gender and occupation", Toast.LENGTH_SHORT).show();
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void submitData(View v)
	{
		if(passwordIsLongEnough(password.getText().toString()))
		{
			if(usernameExists(uname.getText().toString()))
			{
				Toast.makeText(this, "Username exists", Toast.LENGTH_LONG).show();
			}
			else
			{
				boolean okay = true;
				try
				{
					mlearning.execSQL("INSERT INTO register VALUES('"+fname.getText().toString()+"','"+lname.getText().toString()+"','"+uname.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"','"+gender+"','"+occupation+"');");
				}
				catch(Exception e)
				{
					okay = false;
					Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
				}
				finally
				{
					if(okay)
					{
						Toast.makeText(this, "SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
						if( occupation == "Administrator" )
						{
							startActivity(new Intent(Register.this,AdminLogin.class));
						}
						else if( occupation == "Lecturer" )
						{
							startActivity(new Intent(Register.this,LecturerLogin.class));
						}
						else if( occupation == "Student" )
						{
							startActivity(new Intent(Register.this,StudentLogin.class));
						}
						finish();
					}
				}
			}
		}
	}
	
	public boolean usernameExists(String user)
	{
		try
		{
			Cursor myCursor = mlearning.rawQuery("SELECT * FROM register WHERE uname='"+user+"'",null);
			int count = myCursor.getCount();
			if(count > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		return false;
	}
	public boolean allFieldsAreFIlled()
	{
		if(fname.getText().toString()=="" || lname.getText().toString()=="" || uname.getText().toString()=="" || email.getText().toString()=="" || gender == "")
		{
			Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_LONG).show();
			return false;
		}
		else
		{
			return true;
		}
	}
	public void onBackPressed()
	 {
//		 super.onBackPressed();
	 }
}