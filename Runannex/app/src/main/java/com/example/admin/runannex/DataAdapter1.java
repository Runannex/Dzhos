package com.example.admin.runannex;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

class DataAdapter1 extends Adapter<DataAdapter1.ViewHolder> {

    private LayoutInflater inflater1;
    private List<Phone1> phones1;
    private int i = 0;

    DataAdapter1(Context context, List<Phone1> phones1) {
        this.phones1 = phones1;
        this.inflater1 = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater1.inflate(R.layout.layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Phone1 phone1 = phones1.get(position);
        holder.imageView.setImageBitmap(getImageFromSdCard(i));
        holder.nameView2.setText(phone1.getDate());
        holder.nameView1.setText(phone1.getInfo());

    }
    public Bitmap getImageFromSdCard(int imageNumb) {
        Bitmap bitmap = null;
        File path = Environment.getExternalStorageDirectory();
        int count = imageNumb+1;
        try {
            bitmap = BitmapFactory.decodeFile(path + "/.Runannex/"
                    + "Map" + count
                    + ".png");
        } catch (IllegalArgumentException e) {
            Log.e("Fucking error", "");
        }
        return bitmap;

    }


    @Override
    public int getItemCount() {
        return phones1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView1;
        final TextView nameView2;
                ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imageMap);
            nameView2 = (TextView) view.findViewById(R.id.date);
            nameView1 =  (TextView) view.findViewById(R.id.info);
        }
    }
}