package com.example.pcremote;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


import android.os.AsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class Client extends Activity implements OnClickListener {

	private Socket client;
	private Boolean flag = false;
	private PrintWriter printwriter;
	private String message,str;
	EditText command,msg;
	Button b1,b2,exe,up,down,b6,b7,b8,b9;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_client);
	  command = (EditText) findViewById(R.id.editText1);
	  msg = (EditText) findViewById(R.id.editText2);
	  b1 = (Button) findViewById(R.id.button1);
	  b2 = (Button) findViewById(R.id.button2);
	  b6 = (Button) findViewById(R.id.button6);
	  b7 = (Button) findViewById(R.id.button7);
	  b8 = (Button) findViewById(R.id.button8);
	  b9 = (Button) findViewById(R.id.button9);
	  up = (Button) findViewById(R.id.button4);
	  down = (Button) findViewById(R.id.button5);
	  exe = (Button) findViewById(R.id.button3);
	  b1.setOnClickListener(this);
	  b2.setOnClickListener(this);
	  b6.setOnClickListener(this);
	  b7.setOnClickListener(this);
	  b8.setOnClickListener(this);
	  b9.setOnClickListener(this);
	  exe.setOnClickListener(this);
	  up.setOnClickListener(this);
	  down.setOnClickListener(this);
	  MainActivity obj = new MainActivity();
	  str = obj.ip;
	 }
	 
	 public void onClick(View v) {
		 
		  switch (v.getId()) {
		  case R.id.button1:
		  message = "shutdown.exe /s /t 00";
		   break;
		  case R.id.button2:
	      message = "shutdown.exe /r /t 00";
		   break;
		  case R.id.button4:
		   message = "up";
		   break;
		  case R.id.button5:
		   message = "down";
		   break;
		  case R.id.button6:
			   message = "Rundll32.exe User32.dll,LockWorkStation";
			   break;
		  case R.id.button7:
			   message = "rundll32.exe powrprof.dll,SetSuspendState 0,1,0";
			   break;
		  case R.id.button8:
			   message = "rundll32.exe PowrProf.dll,SetSuspendState";
			   break;
		  case R.id.button3:
			   message = command.getText().toString();
			   break;
		  case R.id.button9:
			   message ="?"+msg.getText().toString();
			   break;

		  }
		  if(flag==true)	{
			   Toast.makeText(Client.this,"Connection failed or Invalid IpAddress !!",Toast.LENGTH_LONG).show();
			  // msg.setText("Could not Connected or Invalid IpAddress !!");
			   flag = false;

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
				
				builder.setMessage("Connection failed or Invalid IpAddress !!")
				    .setCancelable(false)
				    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				    finish();
				    }
				    });
				 
				
				AlertDialog alert = builder.create();
				 alert.setTitle("AlertDialog");
				 alert.show();
				setContentView(R.layout.activity_client);
			   
		   }
	 
	   SendMessage sendMessageTask = new SendMessage();
	   sendMessageTask.execute();
	   
	 }
	 

		private class SendMessage extends AsyncTask<Void, Void, Void> {
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					//String str = new String("116.202.91.218");
					client = new Socket(str, 4444); // connect to the server
					printwriter = new PrintWriter(client.getOutputStream(), true);
					printwriter.write(message); // write the message to output stream
					printwriter.flush();
					printwriter.close();
					client.close(); // closing the connection

				} catch (Exception e) {
				flag=true;
				} 
				return null;
			}

		}

}

