package tzz.currentlocationdemo;


import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

//must implements following and the corresponding methods
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //CHECK MANIFEST - ADD PERMISSIONS!
    //In SDK Manager update Google Repository
    //in gradle file (OF MODULE) add this line in dependencies
        //compile 'com.google.android.gms:play-services:11.2.0'

    //if it throws an error, add this in gradle file (OF PROJECT) under allprojects() - repositories
        /*maven {
            url "https://maven.google.com"
        }*/

    private GoogleApiClient gApiClient;
    Button btn;
    private TextView txt;
    private Location lastlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        btn = (Button)findViewById(R.id.btn);
        txt = (TextView)findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if this throws error and you have Marshmallow, remember to check the permission settings of this app on your device
                txt.setText(lastlocation.toString());
            }
        });


    }

    //for connection

    @Override
    protected void onStart() {
        super.onStart();
        if (gApiClient != null) {
            gApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        gApiClient.disconnect();
        super.onStop();
    }

    //needed because of implements

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("ADVSEARCH","Error on connection failed");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w("ADVSEARCH","Error on connection suspended");
    }

    @Override
    public void onConnected(Bundle bundle) {
        //check permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lastlocation = LocationServices.FusedLocationApi.getLastLocation(gApiClient);
        }
    }

}
