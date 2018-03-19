package com.example.admin.runannex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ACH extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    List<Phone> ach = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ach);
        /*ach.add(new Phone ("Пробежать 1 км", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 5 км", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 10 км", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 20 км",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Сжечь 10 калорий", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Сжечь 50 калорий",R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Сжечь 100 калорий", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Сжечь 500 калорий", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Набрать с/c 1км/м",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Набрать с/c 1км/м",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Набрать с/c 5км/м", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Набрать с/c 10км/м",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Набрать с/c 20км/м",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 1 км",  R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 5 км", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 10 км", R.drawable.ic_clear_black_24dp));
        ach.add(new Phone ("Пробежать 20 км",  R.drawable.ic_clear_black_24dp));*/
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        int a = 0;
        int dis = 15;
        int cal = 105;
        int v = 2;
        int time = 45;
        final TextView selection = (TextView) findViewById(R.id.selection);
        //dis = extras.getInt("time");
        if(dis>1){
            ach.add(new Phone ("Пробежать 1 км", R.drawable.ic_done_black_24dp));
            a++;}
            else {
                ach.add(new Phone ("Пробежать 1 км", R.drawable.ic_clear_black_24dp));
            }
        if(dis>5){
            ach.add(new Phone ("Пробежать 5 км", R.drawable.ic_done_black_24dp));
            a++;}
            else{
                ach.add(new Phone ("Пробежать 5 км", R.drawable.ic_clear_black_24dp));
            }
        if(dis>10){
            ach.add(new Phone ("Пробежать 10 км", R.drawable.ic_done_black_24dp));
            a++;}
            else{
            ach.add(new Phone ("Пробежать 10 км", R.drawable.ic_clear_black_24dp));
        }
        if(dis>20){
            ach.add(new Phone ("Пробежать 20 км", R.drawable.ic_done_black_24dp));
            a++;}
            else{
            ach.add(new Phone ("Пробежать 20 км", R.drawable.ic_clear_black_24dp));
        }
        if(cal>10){
            ach.add(new Phone ("Сжечь 10 калорий", R.drawable.ic_done_black_24dp));
            a++;}
            else {
            ach.add(new Phone ("Сжечь 10 калорий", R.drawable.ic_clear_black_24dp));
        }
        if(cal>50){
            ach.add(new Phone ("Сжечь 50 калорий", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Сжечь 50 калорий", R.drawable.ic_clear_black_24dp));
        }
        if(cal>100){
            ach.add(new Phone ("Сжечь 100 калорий", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Сжечь 100 калорий", R.drawable.ic_clear_black_24dp));
        }
        if(cal>500){
            ach.add(new Phone ("Сжечь 500 калорий", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Сжечь 500 калорий", R.drawable.ic_clear_black_24dp));
        }
        if(v>1){
            ach.add(new Phone ("Набрать с/c 10км/м", R.drawable.ic_done_black_24dp));
            a++;}
            else {
            ach.add(new Phone ("Набрать с/c 10км/м", R.drawable.ic_clear_black_24dp));
        }
        if(v>5){
            ach.add(new Phone ("Набрать с/c 5км/м", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Набрать с/c 5км/м", R.drawable.ic_clear_black_24dp));
        }
        if(v>10){
            ach.add(new Phone ("Набрать с/c 10км/м", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Набрать с/c 10км/м", R.drawable.ic_clear_black_24dp));
        }
        if(v>20){
            ach.add(new Phone ("Набрать с/c 20км/м", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Набрать с/c 20км/м", R.drawable.ic_clear_black_24dp));
        }
        if(time>10){
            ach.add(new Phone ("Заниматься спортом 10 минут", R.drawable.ic_done_black_24dp));
            a++;}
            else {
            ach.add(new Phone ("Заниматься спортом 10 минут", R.drawable.ic_clear_black_24dp));
        }
        if(time>30){
            ach.add(new Phone ("Заниматься спортом 30 минут", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Заниматься спортом 30 минут", R.drawable.ic_clear_black_24dp));
        }
        if(time>60){
            ach.add(new Phone ("Заниматься спортом 1 час ", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("Заниматься спортом 1 час ", R.drawable.ic_clear_black_24dp));
        }
        if(time>120){
            ach.add(new Phone ("Заниматься спортом 2 часа", R.drawable.ic_done_black_24dp));
            a++;}
        else{
            ach.add(new Phone ("ПЗаниматься спортом 2 часа", R.drawable.ic_clear_black_24dp));
        }




        selection.setText("Всего выполнено " + a + " из 16" );


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        DataAdapter adapter = new DataAdapter(this, ach);
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:

                return true;
            case R.id.action_problem:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"slavafeatzhdos@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ошибки");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, "Выбирите почту..."));
                    Toast.makeText(ACH.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ACH.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.info:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("О приложении");
                builder.setMessage("Lorem ipsum dolor ....");
                builder.setPositiveButton("OK", null);
                builder.setIcon(R.drawable.ic_launcher);

                builder.show();
                return true;


        }
        return super.onOptionsItemSelected(item);

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.train) {Intent i = new Intent(this, Training.class);
            startActivity(i);

        } else if (id == R.id.ach) {


        } else if (id == R.id.stat) {

        } else if (id == R.id.plane) {

        } else if (id == R.id.alltrain) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
