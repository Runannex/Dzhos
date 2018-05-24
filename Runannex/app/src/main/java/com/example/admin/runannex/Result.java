package com.example.admin.runannex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

public class Result extends AppCompatActivity {

    SharedPreferences sPref;
    int Seconds, Minutes, MilliSeconds,caloriii,distance,i;
    float speed;
    private Toolbar toolbar;
    int[] distanceArr = new int[100];
    int[]  timeArr = new int[101];
    int[]  caloriiArr = new int[101];
    int[]  speedArr = new int[101];
    String[] dateArr = new String[1000];
    public String data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.RunannexFont);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView speeder = (TextView) findViewById(R.id.halfV);
        final TextView caloriir = (TextView) findViewById(R.id.calorii);
        final TextView distancer = (TextView) findViewById(R.id.distance);
        final ImageView screen = (ImageView) findViewById(R.id.screen);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        String savedString2 = sPref.getString("timearr", "");
        if (savedString2 != "") {
            StringTokenizer st2 = new StringTokenizer(savedString2, ",");
            for (int i = 0; i < 100; i++) {
                timeArr[i] = Integer.parseInt(st2.nextToken());
            }
        }

        String savedString3 = sPref.getString("speedarr", "");
        if (savedString3 != "") {
            StringTokenizer st3 = new StringTokenizer(savedString3, ",");
            for (int i = 0; i < 100; i++) {
                speedArr[i] = Integer.parseInt(st3.nextToken());
            }
        }
        String savedString = sPref.getString("distancearr", "");
        if (savedString != "") {
            StringTokenizer st = new StringTokenizer(savedString, ",");
            for (int i = 0; i < 100; i++) {
                distanceArr[i] = Integer.parseInt(st.nextToken());
            }
        }
        String savedString4 = sPref.getString("datearr", "");
        if (savedString4 != "") {
            StringTokenizer st4 = new StringTokenizer(savedString4, ",");
            for (int i = 0; i < 100; i++) {
                dateArr[i] = String.valueOf(st4.nextToken());
            }
        }
        String savedString1 = sPref.getString("caloriiarr", "");
        if (savedString1 != "") {
            StringTokenizer st1 = new StringTokenizer(savedString1, ",");
            for (int i = 0; i < 100; i++) {
                caloriiArr[i] = Integer.parseInt(st1.nextToken());
            }
        }
        for (i = 0; i<100; i++) {
            if (distanceArr[i+1] == 0) {
                break;
            }
        }
        String s = i+"";
        Seconds = sPref.getInt("Sec",0);
        Minutes = sPref.getInt("Min",0);
        MilliSeconds = sPref.getInt("Millis",0);
        caloriii = sPref.getInt("cali",0);
        speed = sPref.getFloat("speed",0);
        distance = sPref.getInt("dist",0);
        timer.setText(String.format("%02d", Minutes) + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));
        caloriir.setText(caloriii+"");
        speeder.setText((int)speed+"");
        distancer.setText(distance+"");
        screen.setImageBitmap(getImageFromSdCard(s));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_problem:
                Intent i = new Intent(Intent.ACTION_SEND); i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] {"slavafeatzhdos@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ошибки");
                i.putExtra(Intent.EXTRA_TEXT,  "" );
                try { startActivity(Intent.createChooser(i, "Выбирите почту..."));
                    Toast.makeText(Result.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) { Toast.makeText(Result.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show(); }
                return true;
            case R.id.info:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("О приложении");
                builder.setMessage("\nДанное приложение позволяет поддерживать вашу физическую форму.\nГлавная его задача: отслеживание маршрута вашего бега по карте, вычисление средней скорости, времени, количество потраченных калорий и преодоленную дистанцию, также можно бег заменить ездой на велосипеде.\nЕсли вы часто занимаетесь пробежкой, вам не стоит обходить это приложение стороной.");
                builder.setPositiveButton("OK", null);
                builder.setIcon(R.drawable.ic_launcher);

                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public Bitmap getImageFromSdCard(String imageNumb) {
        Bitmap bitmap = null;
        File path = Environment.getExternalStorageDirectory();
        String count = imageNumb+"";
        try {
            bitmap = BitmapFactory.decodeFile(path + "/.Runannex/"
                    + "Map" + count
                    + ".png");
        } catch (IllegalArgumentException e) {
            Log.e("Fucking error", "");
        }
        return bitmap;

    }

}
