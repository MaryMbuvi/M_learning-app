package com.example.m_learning4socialskills;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends Activity implements OnClickListener
{
	Button backupButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		backupButton = (Button) findViewById(R.id.backupButton);
		backupButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if( v  == backupButton )
		{
			Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();
			boolean ok = true;
			try 
			{
				backupDatabase();
			} 
			catch (IOException e) 
			{
				ok = false;
				e.printStackTrace();
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			finally
			{
				if( ok )
				{
					String path = Environment.getExternalStorageDirectory().toString();
					Toast.makeText(this, path, Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	
	public static void backupDatabase() throws IOException
	{
		boolean success = true;
		
		File file = null;
		file = new File(Environment.getExternalStorageDirectory()+"/MLearning/databases");
		if(file.exists())
		{
			success = true;
		}
		else
		{
			success = file.mkdir();
		}
		
		if(success)
		{
			String inFileName = "/data/data/com.example.m_learning4socialskills/databases/EPDB";
			File dbFile = new File(inFileName);
			FileInputStream fIs = new FileInputStream(dbFile);
			String outFileName = Environment.getExternalStorageDirectory()+"/MLearning/databases/EPDB";
			
			// Open the empty db as the output stream
			OutputStream output = new FileOutputStream(outFileName);
			
			// Transfer Bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while((length = fIs.read(buffer)) > 0 )
			{
				output.write(buffer, 0, length);
			}
			output.flush();
			output.close();
			fIs.close();
		}
	}

	
}
