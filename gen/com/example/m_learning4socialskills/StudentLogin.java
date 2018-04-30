package com.example.m_learning4socialskills;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StudentLogin extends Activity implements OnClickListener
{
	
	EditText user_name, password;
	Button login, register;
	TextView Register;
	SQLiteDatabase mlearning;
	Cursor myCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_login);
		
		user_name = (EditText) findViewById(R.id.st_uname);
		password = (EditText) findViewById(R.id.login_pword);
		login = (Button) findViewById(R.id.btn_login);
//		Register = (TextView) findViewById(R.id.register);
		
		login.setOnClickListener(this);
//		Register.setText("Click Here to Register");
		
		mlearning = openOrCreateDatabase("m_learning", Context.MODE_PRIVATE, null);
		mlearning.setVersion(1);
		mlearning.execSQL("CREATE TABLE IF NOT EXISTS register(fname VARCHAR NOT NULL, lname VARCHAR NOT NULL, uname VARCHAR NOT NULL, email VARCHAR NOT NULL, password VARCHAR NOT NULL, gender VARCHAR NOT NULL);");
		
		myCursor = mlearning.rawQuery("SELECT * FROM register", null);
		int count = myCursor.getCount();
		if(count == 0)
		{
			startActivity(new Intent(StudentLogin.this,StudentHome.class));
			finish();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btn_login:
				checkLogin(null);
		}
	}
	
	 public void checkLogin(View v)
	 {
		 String username = user_name.getText().toString();
		 if(usernameExists(username))
		 {
			 login(null);
		 }
		 else
		 {
			 Toast.makeText(this, "user does not exist", Toast.LENGTH_SHORT).show();
		 }
	 }
	 public void login(View buttocks)
	 {
		 Cursor myCursor2 = null;
		 String username = user_name.getText().toString();
		 String pass_word = password.getText().toString();
		 try
		 {
			 myCursor2 = mlearning.rawQuery("SELECT * FROM register WHERE uname='"+username+"' AND password='"+pass_word+"'", null);
		 }
		 catch(Exception e)
		 {
			 Toast.makeText(this, "user does not exist", Toast.LENGTH_LONG).show();
		 }
		 if(myCursor2.getCount() == 1)
		 {
			 startActivity(new Intent(StudentLogin.this,StudentHome.class));
			 finish();
		 }
		 else
		 {
			 Toast.makeText(this, "password is wrong", Toast.LENGTH_SHORT).show();
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
	 @Override
	 public void onBackPressed()
	 {
//		 super.onBackPressed();
	 }
}
