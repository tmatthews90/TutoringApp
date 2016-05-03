package team7.tutoringappv1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.Math;

import java.text.DecimalFormat;
import java.util.List;

public class TutorProfileActivity extends AppCompatActivity implements LocationListener {

    SQLiteDatabase db;
    String selectedTutor;

    String name;
    String subject;
    int rate;
    String rateMoneySign;
    String wholeStar = "★";
    String partialStar = "✩";
    int rating;
    String ratingString = "";

    String startCoords;
    String targetCoords;
    float startLatitude = 32.7310991f;
    float startlongitude = -97.1177082f;
    float targetLatitude = 32.6890010f;
    float targetLongitude = -97.6331130f;

    TextView fieldName;
    TextView fieldEmail;
    TextView fieldPhone;
    TextView fieldSubject;
    TextView fieldRate;
    TextView fieldOverallRating;
    TextView fieldZipCode;
    TextView fieldDistance;
    int callback;

    Users tempUser;

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        callback);
            }
            catch (Exception e){

            }
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        setTextFields();
        startCoords = String.format("%f, %f", startLatitude, startlongitude);

        getCoorFromZip();


        Button btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tempUser.getPhoneNumber()));
                System.out.println("call");
                startActivity(callIntent);
            }
        });

        Button btnText = (Button) findViewById(R.id.btnText);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + tempUser.getPhoneNumber()));
                sendIntent.putExtra("sms_body", "Hi, I Found you on TutorMe, would like to meet up for some tutoring?\n\n");
                startActivity(sendIntent);
            }
        });

        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        assert btnEmail != null;
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { tempUser.getEmail() });
                intent.putExtra(Intent.EXTRA_SUBJECT, "TUTORING NEEDED");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi, I Found you on the tutoring app and would like to meet up for some tutoring.\n\n");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        assert btnMap != null;
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mapping logic here
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?" + "saddr=" + startCoords + "&daddr=" + targetCoords);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

    public void setTextFields(){
        fieldName = (TextView) findViewById(R.id.fieldName);
        fieldEmail = (TextView) findViewById(R.id.fieldEmail);
        fieldPhone = (TextView) findViewById(R.id.fieldPhone);
        fieldSubject = (TextView) findViewById(R.id.fieldSubject);
        fieldRate = (TextView) findViewById(R.id.fieldRate);
        fieldOverallRating = (TextView) findViewById(R.id.fieldOverallRating);
        fieldZipCode = (TextView) findViewById(R.id.fieldZipCode);
        fieldDistance = (TextView) findViewById(R.id.fieldDistance);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedTutor = extras.getString("selectedTutor");
        }

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor dbEntry = db.rawQuery("SELECT firstName, lastName, rating, tutorRate, phonenumber, email, t_math, t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, zipcode FROM userst WHERE email = '" + selectedTutor + "';", null);
        dbEntry.moveToFirst();

        if (dbEntry.getCount() == 1) {

            System.out.println("Found user: " + selectedTutor);

            tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setPhoneNumber(dbEntry.getString(4));
            tempUser.setEmail(dbEntry.getString(5));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(11)));
            tempUser.setZipCode(dbEntry.getString(12));

            name = tempUser.getFirstName() + " " + tempUser.getLastName();
            rate = tempUser.getTutorRate();

            if (tempUser.isT_math()) {
                subject = "Math";
            } else if (tempUser.isT_science()) {
                subject = "Science";
            } else if (tempUser.isT_literature()) {
                subject = "Literature";
            } else if (tempUser.isT_history()) {
                subject = "History";
            } else if (tempUser.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tempUser.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            switch(rate) {
                case 1:
                    rateMoneySign = "$";
                    break;
                case 2:
                    rateMoneySign = "$$";
                    break;
                case 3:
                    rateMoneySign = "$$$";
                    break;
                default:
                    rateMoneySign = "-";
                    break;
            }

            rating = Math.round(tempUser.getReviewRate());

            for (int i = 0; i < rating; i++) {
                ratingString += wholeStar;
            }

            if (rating != 5) {
                for (int i = 0; i < 5 - rating; i++) {
                    ratingString += partialStar;
                }
            }

            Location loc1 = new Location("");
            Location loc2 = new Location("");

            getCoorFromZip();

            loc1.setLatitude(startLatitude);
            loc1.setLongitude(startlongitude);
            loc2.setLatitude(targetLatitude);
            loc2.setLongitude(targetLongitude);

            float distanceInMeters = loc1.distanceTo(loc2);
            float distanceInMiles = distanceInMeters * 0.000621371f;
            String distance = String.format("%.2f", distanceInMiles) + " miles";


            fieldName.setText(name);
            fieldEmail.setText(tempUser.getEmail());
            fieldPhone.setText(tempUser.getPhoneNumber());
            fieldSubject.setText(subject);
            fieldRate.setText(rateMoneySign);
            fieldOverallRating.setText(ratingString);
            fieldZipCode.setText(tempUser.getZipCode());
            fieldDistance.setText(distance);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Latitude: " + location.getLatitude() + ", Longitude:" + location.getLongitude());
        startCoords = String.format("%f, %f", location.getLatitude(), location.getLongitude());
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

    public void getCoorFromZip(){
        final Geocoder geocoder = new Geocoder(this);
        final String zip = tempUser.getZipCode();
        try {
            List<Address> addresses = geocoder.getFromLocationName(zip, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                targetCoords = String.format("%f, %f", address.getLatitude(), address.getLongitude());
                targetLatitude = (float)address.getLatitude();
                targetLongitude = (float)address.getLongitude();
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // handle exception
        }
    }

}
