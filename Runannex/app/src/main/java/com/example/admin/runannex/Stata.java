package com.example.admin.runannex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.StringTokenizer;

public class Stata extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String  name;
    public int col,t,d,k= 0;
    int[] distanceArr = new int[101];
    int[]  timeArr = new int[101];
    int[]  caloriiArr = new int[101];
    int coli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stata);
        String path = Environment.getExternalStorageDirectory().toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.RunannexFont);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView)header.findViewById(R.id.textView);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        name = sPref.getString("nam", "");
        textView.setText(name);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");
        ImageView imageView = (ImageView)header.findViewById(R.id.imageView);
        if (f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        } else {
            imageView.setImageResource(R.drawable.ava);
        }
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        TextView caloric = (TextView)findViewById(R.id.caloric);
        TextView count = (TextView)findViewById(R.id.count);
        TextView distance = (TextView)findViewById(R.id.distance);
        TextView time = (TextView)findViewById(R.id.time);
        String savedString2 = sPref.getString("timearr", "");
        if (savedString2 != "") {
            StringTokenizer st2 = new StringTokenizer(savedString2, ",");
            for (int i = 0; i < 100; i++) {
                timeArr[i] = Integer.parseInt(st2.nextToken());
            }
        }
        String savedString1 = sPref.getString("caloriiarr", "");
        if (savedString1 != "") {
            StringTokenizer st1 = new StringTokenizer(savedString1, ",");
            for (int i = 0; i < 100; i++) {
                caloriiArr[i] = Integer.parseInt(st1.nextToken());
            }
        }
        String savedString = sPref.getString("distancearr", "");
        if (savedString != "") {
            StringTokenizer st = new StringTokenizer(savedString, ",");
            for (int i = 0; i < 100; i++) {
                distanceArr[i] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i<100; i++) {
            col++;
            if (distanceArr[i + 1] == 0) {
                break;
            }
        }
        for(int j = 0;j<col;j++) {
            t=+timeArr[j];
            d=+distanceArr[j];
            k+=caloriiArr[j];
        }
        caloric.setText("Сожжённые калории: "+k);
        distance.setText("Пройденная дистанция: "+d+"м.");
        time.setText("Время тренировок: "+(int)(k/60)+":" +(k-(int)k/60));
        count.setText("Количество тренировок: "+col);
        coli = sPref.getInt("colich",0);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_problem:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"slavafeatzhdos@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ошибки");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, "Выбирите почту..."));
                    Toast.makeText(Stata.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Stata.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.train) {
            Intent i = new Intent(this, Training.class);
            startActivity(i);

        } else if (id == R.id.ach) {
            Intent i = new Intent(this, Ach.class);
            startActivity(i);


        } else if (id == R.id.stat) {
            Intent i = new Intent(this, Stata.class);
            startActivity(i);

        } else if (id == R.id.alltrain) {
            if(coli==0){
                Toast.makeText(Stata.this, "Выполните хотя бы 1 тренировку", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(this, Alltrain.class);
                startActivity(i);
            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}