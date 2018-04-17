package com.example.admin.runannex;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;


public class Training extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String weight, year, growth, name;
    int Seconds, Minutes, MilliSeconds;
    Handler handler;
    Intent intent;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    ImageButton music;
    private final int SPORT_LIST = 1;
    private final int EXIT = 2;
    boolean ifjogging = true;
    boolean ifmaps = false;
    boolean ifrun = false;
    boolean ifpause = false;
    private static Context context;
    LocationManager locationManager;
    Context mContext;
    boolean ifmarked = false;
    boolean iffocused = false;
    boolean ifasked = false;
    int DistanceRunSum = 0;
    double latitude1 = 0;
    double longtitude1 = 0;
    double latitude2 = 0;
    double longtitude2 = 0;
    boolean loc = true;
    float distance123;
    int[] distanceArr = new int[100];
    int[] timeArr = new int[100];
    int[] caloriiArr = new int[100];
    int[] speedArr = new int[100];
    Bundle b = new Bundle();

    int caloriii;
    PolylineOptions line= new PolylineOptions().width(17).color(Color.BLUE);
    private Toolbar toolbar;
    public int time = 10;
    ImageView imageView2;
    String format = "jpg";
    String fileName = "FullScreenshot." + format;
    public int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        Integer counter = 0;
        File file = new File(path, "screen"+".jpg");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        //final ImageView imageView2 = (ImageView)findViewById(R.id.imageView2);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView)header.findViewById(R.id.textView);
        textView.setText(name);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        navigationView.setNavigationItemSelectedListener(this);
        final ImageView imageView = (ImageView)header.findViewById(R.id.imageView);
        if(f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        }else { imageView.setImageResource(R.drawable.ava);}

        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        ed = sPref.edit();
        weight = sPref.getString("weigh", "");
        name = sPref.getString("nam", "");
        growth = sPref.getString("growt", "");
        year = sPref.getString("yea", "");
        final Button start = (Button) findViewById(R.id.start);
        final Button pause = (Button) findViewById(R.id.pause);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button cont = (Button) findViewById(R.id.cont);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final Button ButtonMap = (Button) findViewById(R.id.Bmap);
        final ImageButton sport = (ImageButton) findViewById(R.id.sport);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView calorii = (TextView) findViewById(R.id.calorii);
        final TextView distance = (TextView) findViewById(R.id.distance);
        final TextView halfV = (TextView) findViewById(R.id.halfV);
        final TextView halfVr = (TextView) findViewById(R.id.halfVr);
        final TextView distancer = (TextView) findViewById(R.id.distancer);
        final TextView caloriir = (TextView) findViewById(R.id.caloriir);
        final View linemap = (View) findViewById(R.id.line);
        mContext = this;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                1, locationListenerGPS);
        final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(SPORT_LIST);
            }
        });


        handler = new Handler();
        mapFragment.getMapAsync(this);
        View.OnClickListener oclBtnMap = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setDrawerIndicatorEnabled(true);
                ButtonMap.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                sport.setVisibility(View.INVISIBLE);
                time.setVisibility(View.INVISIBLE);
                caloriir.setVisibility(View.INVISIBLE);
                halfV.setVisibility(View.INVISIBLE);
                halfVr.setVisibility(View.INVISIBLE);
                distancer.setVisibility(View.INVISIBLE);
                calorii.setVisibility(View.INVISIBLE);
                distance.setVisibility(View.INVISIBLE);
                music.setVisibility(View.INVISIBLE);
                linemap.setVisibility(View.INVISIBLE);
                Training.context = getApplicationContext();
                mapFragment.getMapAsync(Training.this);
                float width = 100;
                float height = 560;
                params.width = (int) convertDpToPx(context, width);
                params.height = (int) convertDpToPx(context, height);
                v.setLayoutParams(params);
                ifmaps = true;
                statusCheck();


            }
        };
        ButtonMap.setOnClickListener(oclBtnMap);

        music = (ImageButton) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 15) {
                    Intent music = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_MUSIC);
                    music.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(music);

                } else {
                    Intent music = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);

                    startActivity(music);
                }
            }
        });




        View.OnClickListener oclBtnStart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                ifrun = true;
                sport.setClickable(false);
            }
        };
        start.setOnClickListener(oclBtnStart);


        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Training.this, Result.class);
                startActivity(intent);
                ed.putInt("Min", Minutes);
                ed.putInt("Millis", MilliSeconds);
                ed.putInt("Sec", Seconds);
                ed.putInt("dist", DistanceRunSum);
                ed.putFloat("speed", distance123);
                ed.putInt("cali", caloriii);
                ed.commit();
                handler.removeCallbacks(runnable);
                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;



                stop.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                timer.setText(String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds));
                ifrun = false;
                ifpause = false;
                sport.setClickable(true);
                ifmarked = false;
                DistanceRunSum = 0;
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        line = null;
                    }
                });
                calorii.setText("0");
                halfV.setText("0");
                distance.setText("0");
            }
        };
        stop.setOnClickListener(oclBtnStop);


        View.OnClickListener oclBtnPause = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                pause.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.VISIBLE);
                ifpause = true;
            }
        };
        pause.setOnClickListener(oclBtnPause);

        View.OnClickListener oclBtnCont = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                cont.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
                ifpause = false;
            }
        };
        cont.setOnClickListener(oclBtnCont);

    }
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    private Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        //Toast.makeText(Training.this, "Success screenshot", Toast.LENGTH_LONG).show();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public Runnable runnable = new Runnable() {

        public void run() {
            final TextView timer = (TextView) findViewById(R.id.timer);
            final TextView calorii = (TextView) findViewById(R.id.calorii);
            final TextView halfV = (TextView) findViewById(R.id.halfV);
            int weightnum = Integer.parseInt(weight);
            int growthnum = Integer.parseInt(growth);
            int Seconds1 = 0;

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            timer.setText(String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            if (ifrun && DistanceRunSum > 10 && Seconds>1 && Seconds1 != Seconds) {
                Seconds1 = Seconds;
                distance123 = (DistanceRunSum*3600)/(Seconds*1000);
                halfV.setText((int)distance123 + "");
                if (ifjogging) {
                    caloriii = (int)(0.035*weightnum + 0.029*(distance123*distance123/growthnum)*weightnum);
                    calorii.setText(caloriii+"");
                }
                //if (!ifjogging) {calorii.setText(caloriii);}
            }
            handler.postDelayed(this, 0);
        }

    };


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onMapReady(final GoogleMap googleMap) {
        final Button ButtonMap = (Button) findViewById(R.id.Bmap);
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        if (ButtonMap.getVisibility() == View.INVISIBLE) {
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();
                    }
                    return false;
                }
            });

        } else {
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (!ifasked) {
                buildAlertMessageNoGps();
                ifasked = true;
            }

        }
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg) {
                double lat = arg.getLatitude();
                double lng = arg.getLongitude();
                LatLng latlng = new LatLng(lat, lng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));
            }
        });
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
                    Toast.makeText(Training.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Training.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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


    @Override
    protected Dialog onCreateDialog(final int id) {
        final ImageButton sport = (ImageButton) findViewById(R.id.sport);
        {
            switch (id) {
                case SPORT_LIST:
                    final String[] Sport = {"Бег", "Велосипед"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Выберите вид спорта");
                    builder.setItems(Sport, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            if (item == 0) {
                                sport.setImageResource(R.drawable.sport_running);
                                ifjogging = true;
                            }
                            if (item == 1) {
                                sport.setImageResource(R.drawable.sport_cycling);
                                ifjogging = false;
                            }

                            Toast.makeText(getApplicationContext(), "Выбранный спорт " + Sport[item], Toast.LENGTH_SHORT).show();

                        }

                    });
                    builder.setCancelable(true);
                    return builder.create();

                case EXIT:

                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                            Training.this);
                    quitDialog.setTitle("Вы уверены что хотите выйти?");

                    quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    quitDialog.setCancelable(true);
                    quitDialog.show();

                default:
                    return null;

            }
        }
    }

    public boolean onSupportNavigateUp() {
        final Button start = (Button) findViewById(R.id.start);
        final Button pause = (Button) findViewById(R.id.pause);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button cont = (Button) findViewById(R.id.cont);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final Button ButtonMap = (Button) findViewById(R.id.Bmap);
        final ImageButton sport = (ImageButton) findViewById(R.id.sport);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView calorii = (TextView) findViewById(R.id.calorii);
        final TextView distance = (TextView) findViewById(R.id.distance);
        final TextView halfV = (TextView) findViewById(R.id.halfV);
        final TextView halfVr = (TextView) findViewById(R.id.halfVr);
        final TextView distancer = (TextView) findViewById(R.id.distancer);
        final TextView caloriir = (TextView) findViewById(R.id.caloriir);
        final View linemap = (View) findViewById(R.id.line);
        final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        if (ifrun) {
            stop.setVisibility(View.VISIBLE);
            if (ifpause) cont.setVisibility(View.VISIBLE);
            else pause.setVisibility(View.VISIBLE);
        } else start.setVisibility(View.VISIBLE);

        ButtonMap.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        sport.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        caloriir.setVisibility(View.VISIBLE);
        halfV.setVisibility(View.VISIBLE);
        halfVr.setVisibility(View.VISIBLE);
        distancer.setVisibility(View.VISIBLE);
        calorii.setVisibility(View.VISIBLE);
        distance.setVisibility(View.VISIBLE);
        linemap.setVisibility(View.VISIBLE);
        music.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Training.context = getApplicationContext();
        float width = 100;
        float height = 335;
        params.width = (int) convertDpToPx(context, width);
        params.height = (int) convertDpToPx(context, height);
        View v = mapFragment.getView();
        v.setLayoutParams(params);
        ifmaps = false;
        mapFragment.getMapAsync(this);

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ifmaps) {


                final Button start = (Button) findViewById(R.id.start);
                final Button pause = (Button) findViewById(R.id.pause);
                final Button stop = (Button) findViewById(R.id.stop);
                final Button cont = (Button) findViewById(R.id.cont);
                final TextView timer = (TextView) findViewById(R.id.timer);
                final Button ButtonMap = (Button) findViewById(R.id.Bmap);
                final ImageButton sport = (ImageButton) findViewById(R.id.sport);
                final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                final TextView time = (TextView) findViewById(R.id.time);
                final TextView calorii = (TextView) findViewById(R.id.calorii);
                final TextView distance = (TextView) findViewById(R.id.distance);
                final TextView halfV = (TextView) findViewById(R.id.halfV);
                final TextView halfVr = (TextView) findViewById(R.id.halfVr);
                final View linemap = (View) findViewById(R.id.line);
                final TextView distancer = (TextView) findViewById(R.id.distancer);
                final TextView caloriir = (TextView) findViewById(R.id.caloriir);
                final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();

                if (ifrun) {
                    stop.setVisibility(View.VISIBLE);
                    if (ifpause) cont.setVisibility(View.VISIBLE);
                    else pause.setVisibility(View.VISIBLE);
                } else start.setVisibility(View.VISIBLE);

                ButtonMap.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                sport.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
                caloriir.setVisibility(View.VISIBLE);
                linemap.setVisibility(View.VISIBLE);
                halfV.setVisibility(View.VISIBLE);
                halfVr.setVisibility(View.VISIBLE);
                distancer.setVisibility(View.VISIBLE);
                calorii.setVisibility(View.VISIBLE);
                distance.setVisibility(View.VISIBLE);
                music.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                Training.context = getApplicationContext();
                float width = 100;
                float height = 335;
                params.width = (int) convertDpToPx(context, width);
                params.height = (int) convertDpToPx(context, height);
                View v = mapFragment.getView();
                v.setLayoutParams(params);
                ifmaps = false;
                mapFragment.getMapAsync(this);


            } else {

                showDialog(EXIT);

            }
        }
        return true;
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (!ifasked) {
                buildAlertMessageNoGps();
            }


        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Для работы необходимо включить GPS")
                .setCancelable(false)
                .setPositiveButton("Включить", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(final android.location.Location location) {
            int weightnum = Integer.parseInt(weight);
            int growthnum = Integer.parseInt(growth);
            final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            final TextView distancer = (TextView) findViewById(R.id.distance);
            final TextView calorii = (TextView) findViewById(R.id.calorii);
            final TextView halfV = (TextView) findViewById(R.id.halfV);
            final double[] latitude = {location.getLatitude()};
            final double[] longitude = {location.getLongitude()};
            final LatLng latLng = new LatLng(latitude[0], longitude[0]);
            if (line == null) {
                line = new PolylineOptions().width(17).color(Color.BLUE);
            }

            if (loc) {
                loc = false;
                latitude1 = location.getLatitude();
                longtitude1 = location.getLongitude();
                if (ifrun) {
                    if (latitude2 != 0) {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(latitude1);
                        locationA.setLongitude(longtitude1);
                        final LatLng latlng1 = new LatLng(latitude1, longtitude1);
                        final LatLng latlng2 = new LatLng(latitude2, longtitude2);
                        Location locationB = new Location("point B");
                        locationB.setLatitude(latitude2);
                        locationB.setLongitude(longtitude2);
                        if (!ifpause) {
                            DistanceRunSum += locationA.distanceTo(locationB);
                            distancer.setText(DistanceRunSum + "");
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    line.add(latlng1, latlng2);
                                    googleMap.addPolyline(line);
                                }
                            });

                        }
                    }
                }

            } else {
                loc = true;
                latitude2 = location.getLatitude();
                longtitude2 = location.getLongitude();
                if (ifrun && !ifpause) {
                    if (latitude1 != 0) {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(latitude2);
                        locationA.setLongitude(longtitude2);
                        final LatLng latlng1 = new LatLng(latitude1, longtitude1);
                        final LatLng latlng2 = new LatLng(latitude2, longtitude2);
                        Location locationB = new Location("point B");
                        locationB.setLatitude(latitude1);
                        locationB.setLongitude(longtitude1);
                        if (!ifpause) {
                            DistanceRunSum += locationA.distanceTo(locationB);

                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    line.add(latlng1, latlng2);
                                    googleMap.addPolyline(line);
                                }
                            });
                        }
                    }
                }

            }

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    CameraPosition camPos = new CameraPosition.Builder()
                            .target(new LatLng(latitude[0], longitude[0]))
                            .zoom(16)
                            .build();
                    CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                    googleMap.animateCamera(camUpd3);
                    if (!ifmarked && ifrun) {
                        googleMap.addMarker(new MarkerOptions()
                                .anchor(0.5f, 0.5f)
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.starterpoint)));
                        ifmarked = true;

                    }
                }
            });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ach) {
            if (ifrun) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                        Training.this);
                quitDialog.setTitle("Вы уверены что хотите покинуть тренировку?                                ");
                quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Training.this, Ach.class);
                        startActivity(i);
                        finish();

                    }
                });
                quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                quitDialog.show();

            } else {
                Intent a = new Intent(Training.this, Ach.class);
                startActivity(a);
                finish();
            }


        }
        /*if (id==R.id.stat){
            if (ifrun) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                        Training.this);
                quitDialog.setTitle("Вы уверены что хотите покинуть тренировку?                                ");
                quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Training.this, Stata.class);
                        startActivity(i);
                        finish();

                    }
                });
                quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                quitDialog.show();

            } else {
                Intent i = new Intent(this, Stata.class);
                startActivity(i);
            }


        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
