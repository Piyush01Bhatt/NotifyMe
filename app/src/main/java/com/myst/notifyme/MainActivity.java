package com.myst.notifyme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Communicator {

    private android.support.design.widget.FloatingActionButton saveButton;

    Spinner days;
    TimeTableAdapter timeTableAdapter;
    TimeTable timeTable;
    HashMap<String,Integer> daysMap;
    NotifyMeService notifyMeService;
    SharedPreferences daysPref;
    SharedPreferences.Editor daysPrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = (android.support.design.widget.FloatingActionButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new SaveButtonOnclick());

        timeTableAdapter = new TimeTableAdapter(this);

        daysPref = getSharedPreferences("DaysPref",MODE_PRIVATE);
        daysPrefEdit = daysPref.edit();

        if(!daysPref.contains("Monday")){
            daysPrefEdit.putInt("Monday", 0);
            daysPrefEdit.commit();
        }
        if(!daysPref.contains("Tuesday")){
            daysPrefEdit.putInt("Tuesday", 0);
            daysPrefEdit.commit();
        }
        if(!daysPref.contains("Wednesday")){
            daysPrefEdit.putInt("Wednesday", 0);
            daysPrefEdit.commit();
        }
        if(!daysPref.contains("Thursday")){
            daysPrefEdit.putInt("Thursday", 0);
            daysPrefEdit.commit();
        }
        if(!daysPref.contains("Friday")){
            daysPrefEdit.putInt("Friday", 0);
            daysPrefEdit.commit();
        }
        if(!daysPref.contains("Saturday")){
            daysPrefEdit.putInt("Saturday", 0);
            daysPrefEdit.commit();
        }

        String day = String.valueOf(days.getSelectedItem());

        if(daysPref.contains(day)){
            if(daysPref.getInt(day,0)==1){
                String[] data = timeTableAdapter.getAllData(day);
                for(int k=1;k<=8;k++){
                    String[] parsedData = data[k].split("[=,\n]+");
                    String Lecture = parsedData[3];
                    String Time = parsedData[5];
                    String Teacher = parsedData[7];
                    String Classroom = parsedData[9];

                    if(parsedData[3].equals("None")){
                        Lecture = "";
                    }
                    if(parsedData[5].equals("None")){
                        Time = "";
                    }
                    if(parsedData[7].equals("None")){
                        Teacher = "";
                    }
                    if(parsedData[9].equals("None")){
                       Classroom  = "";
                    }
                    timeTable.lectureType[k-1].setText(Lecture);
                    timeTable.time[k-1].setText(Time);
                    timeTable.teacher[k-1].setText(Teacher);
                    timeTable.classroom[k-1].setText(Classroom);

                }
            }
        }

        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String day = String.valueOf(days.getSelectedItem());

                if(daysPref.contains(day)){
                    if(daysPref.getInt(day,0)==1){
                        String[] data = timeTableAdapter.getAllData(day);
                        for(int k=1;k<=8;k++){
                            String[] parsedData = data[k].split("[=,\n]+");
                            String Lecture = parsedData[3];
                            String Time = parsedData[5];
                            String Teacher = parsedData[7];
                            String Classroom = parsedData[9];

                            if(parsedData[3].equals("None")){
                                Lecture = "";
                            }
                            if(parsedData[5].equals("None")){
                                Time = "";
                            }
                            if(parsedData[7].equals("None")){
                                Teacher = "";
                            }
                            if(parsedData[9].equals("None")){
                                Classroom  = "";
                            }
                            timeTable.lectureType[k-1].setText(Lecture);
                            timeTable.time[k-1].setText(Time);
                            timeTable.teacher[k-1].setText(Teacher);
                            timeTable.classroom[k-1].setText(Classroom);

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.notify_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.StartNotification: startAlert();
                                            break;
            case R.id.StopNotification: stopAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setObject(Spinner days, TimeTable timeTable) {
        this.days = days;
        this.timeTable = timeTable;
    }

    public void stopAlert(){
        Intent intent = new Intent(this,MyReceiver.class);
        AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);

        PendingIntent i = PendingIntent.getBroadcast(this,22,intent,0);
        am.cancel(i);
    }

    public void startAlert(){
        Intent intent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),22,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        Calendar exit = Calendar.getInstance();
        exit.setTimeInMillis(System.currentTimeMillis());
        exit.set(Calendar.HOUR_OF_DAY,17);
        exit.set(Calendar.MINUTE,45);

        if(day.equals("Sunday")){
            showMessage("Today Is Sunday So No Classes Today, Enjoy");
        }

        else {
            if ((System.currentTimeMillis()>= calendar.getTimeInMillis()) && (System.currentTimeMillis() <= exit.getTimeInMillis())) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),AlarmManager.INTERVAL_HOUR, pendingIntent);
            } else {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
            }
        }
    }



    class SaveButtonOnclick implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            String day = String.valueOf(days.getSelectedItem());

            StringBuffer buffer = new StringBuffer();
            String[] data = new String[9];

            for(int i=0;i<8;i++){

                String lectureHour = timeTable.lectureHour[i].getText().toString();
                String lectureType = timeTable.lectureType[i].getText().toString();
                String time = timeTable.time[i].getText().toString();
                String teacher = timeTable.teacher[i].getText().toString();
                String classroom = timeTable.classroom[i].getText().toString();



                if(lectureHour.equals("")){
                    lectureHour = "None";
                }

                if(lectureType.equals("")){
                    lectureType ="None";
                }

                if(time.equals("")){
                    time = "None";
                }

                if(teacher.equals("")){
                    teacher = "None";
                }

                if(classroom.equals("")){
                    classroom = "None";
                }

                buffer.append("Lecture Hour = "+ lectureHour + "\n" +
                              "Lecture Type = "+ lectureType + "\n" +
                              "Starting Time = "+ time + "\n" +
                              "Teacher = "+ teacher + "\n"+
                              "Classroom = "+ classroom);
                data[i] = buffer.toString();
                buffer.delete(0,data[i].length());
            }

             data[8] = day;

            new DatabaseTask().execute(data);
        }
    }

    void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    class DatabaseTask extends AsyncTask<String,String,String>{

        long id;
        int count;

        @Override
        protected String doInBackground(String... strings) {

                if (daysPref.getInt(strings[8],0) == 0) {
                    id = timeTableAdapter.insertData(strings, strings[8]);
                    daysPrefEdit.putInt(strings[8], 1);
                    daysPrefEdit.commit();
                } else {
                    count = timeTableAdapter.update(strings, strings[8]);
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
           MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                        if (id < 0) {
                            showMessage("Unsuccessful");
                        } else {
                            showMessage("Successful");
                        }

                }
            });

        }
    }
}
