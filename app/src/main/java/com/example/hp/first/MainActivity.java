package com.example.hp.first;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Button getloc;
    TextView lati;
    TextView longi;
    TextView address;

    LocationManager location_manager;
    String getLatitude;
    String getLongitude;

    double x;
    double y;

    Geocoder geocoder;
    List<Address> addresses;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getloc = (Button) findViewById(R.id.getlocation);
        lati = (TextView) findViewById(R.id.latitude);
        longi = (TextView) findViewById(R.id.longitude);
        address = (TextView) findViewById(R.id.address);
        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getloc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub

                LocationListener listner = new MyLocationListner();
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
//                    return;
                    location_manager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 0, 0, listner);
                }


            }
        });

    }

    public class MyLocationListner implements LocationListener {

        @SuppressWarnings("static-access")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onLocationChanged(Location location) {
// TODO Auto-generated method stub

            getLatitude = "" + location.getLatitude();
            getLongitude = "" + location.getLongitude();

            lati.setText(getLatitude);
            longi.setText(getLongitude);

            x = location.getLatitude();
            y = location.getLongitude();

            try {
                geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                StringBuilder str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Toast.makeText(getApplicationContext(),
                            "geocoder present", Toast.LENGTH_SHORT).show();
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");

                    address.setText(str);
                    Toast.makeText(getApplicationContext(), str,
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "geocoder not present", Toast.LENGTH_SHORT).show();
                }

// } else {
// Toast.makeText(getApplicationContext(),
// "address not available", Toast.LENGTH_SHORT).show();
// }
            } catch (IOException e) {
// TODO Auto-generated catch block

                Log.e("tag", e.getMessage());
            }

        }

        @Override
        public void onProviderDisabled(String arg0) {
// TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String arg0) {
// TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
// TODO Auto-generated method stub

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
