package com.example.alarammanager;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	 AlarmManager am;
	 Thread myThread = null;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	 
	    
	    Button button  = (Button) findViewById(R.id.button1);
	    button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				 

				    Runnable runnable = new CountDownRunner();
				    myThread= new Thread(runnable);   
				    myThread.start();
			}
		});
	 
	  //setRepeatingAlarm();
	 }
	 
	 public void doWork() {
		    runOnUiThread(new Runnable() {
		        public void run() {
		            try{
		               // TextView txtCurrentTime= (TextView)findViewById(R.id.lbltime);
		                    Date dt = new Date();
		                    int hours = dt.getHours();
		                    int minutes = dt.getMinutes();
		                    int seconds = dt.getSeconds();
		                    String curTime = hours + ":" + minutes + ":" + seconds;
		                    
		                    TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);
		                    if(timePicker.getCurrentHour()==hours && timePicker.getCurrentMinute()==minutes)
		                    {
		                    	Toast.makeText(getApplicationContext(), "asdas", Toast.LENGTH_LONG).show();
		                    	 setOneTimeAlarm();
		                    	myThread.suspend();
		                    }
		              //      txtCurrentTime.setText(curTime);
		            }catch (Exception e) {}
		        }
		    });
		}
	 
	 

	 public void setOneTimeAlarm() {
	  Intent intent = new Intent(this, TimeAlarm.class);
	  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
	    intent, PendingIntent.FLAG_ONE_SHOT);
	  am.set(AlarmManager.RTC_WAKEUP,
	    System.currentTimeMillis() + (5 * 1000), pendingIntent);
	 
	 }

	 public void setRepeatingAlarm() {
	  Intent intent = new Intent(this, TimeAlarm.class);
	  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
	    intent, PendingIntent.FLAG_CANCEL_CURRENT);
	  am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
	    (5 * 1000), pendingIntent);
	 }
	 
	 class CountDownRunner implements Runnable{
		    // @Override
		    public void run() {
		            while(!Thread.currentThread().isInterrupted()){
		                try {
		                doWork();
		                    Thread.sleep(10000);
		                } catch (InterruptedException e) {
		                        Thread.currentThread().interrupt();
		                }catch(Exception e){
		                }
		            }
		    }
		}

	}

