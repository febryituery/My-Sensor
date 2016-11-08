package com.id.festudio.mysensor;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Button btn = (Button)findViewById(R.id.btn1);
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(MainActivity.this);
                info.setTitle("My Sensor");
                String s = "";
                s += "\n   OS Version: " +Build.VERSION.RELEASE;
                s += "\n   OS API Level: " + android.os.Build.VERSION.SDK_INT;
                s += "\n   Device: " + Build.BRAND.toUpperCase()+" "+Build.DEVICE;
                s += "\n   Model: " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
                s += "\n   Resolution: " +getWindow().getWindowManager().getDefaultDisplay().getWidth()+" x "+getWindow().getWindowManager().getDefaultDisplay().getHeight();
                TextView teks = new TextView(MainActivity.this);
                teks.setText(s);
                teks.setGravity(Gravity.LEFT);
                info.setView(teks);
                info.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder about = new AlertDialog.Builder(MainActivity.this);
                about.setTitle("My Sensor");
                TextView teks = new TextView(MainActivity.this);
                teks.setText("\nDeveloped By :\nFebry Dwi Putra\n\nfebryituery@gmail.com\nF.E. Studio Indonesia\n2016");
                teks.setGravity(Gravity.CENTER_HORIZONTAL);
                about.setView(teks);
                about.setNegativeButton(getResources().getString(R.string.rate), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        }
                    }
                }).show();
            }
        });
        SensorManager sm = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
        List<Sensor> slist = sm.getSensorList(Sensor.TYPE_ALL);

        List<String> sn = new ArrayList();
        for (int i = 0; i < slist.size(); i++) {
            sn.add("Vendor  : "+slist.get(i).getVendor()+"\nName    : "+slist.get(i).getName()+"\nVersion : "+slist.get(i).getVersion());
        }

        setListAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,sn));
        getListView().setTextFilterEnabled(true);
    }
}
