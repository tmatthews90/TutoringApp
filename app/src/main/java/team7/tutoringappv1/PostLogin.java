// PostLogin.java

package team7.tutoringappv1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PostLogin extends AppCompatActivity {

    TextView txtTitle;
    String usrName;
    SQLiteDatabase db;
    int callback;
    public static Activity postLoginInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        callback);
            }
            catch (Exception e){

            }
        }

        // used to finish instance when in account page.
        postLoginInstance = this;

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        txtTitle = (TextView) findViewById(R.id.title);

        txtTitle.setText("Welcome, " + getUserName() + "!");

        Button btnTutors = (Button) findViewById(R.id.btnBrowseTutors);
        assert btnTutors != null;
        btnTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(view.getContext(), TutorListActivity.class);
                startActivityForResult(registerIntent, 0);
            }
        });

        Button btnMyAccount = (Button) findViewById(R.id.btnMyAccount);
        assert btnMyAccount != null;
        btnMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), AccountActivity.class);
                startActivityForResult(loginIntent, 0);
            }
        });
    }

    public String getUserName(){
        Cursor fName = db.rawQuery("Select firstName from userst where loggedIn = '1'",null);
        Cursor lName = db.rawQuery("Select lastName from userst where loggedIn = '1'",null);
        fName.moveToFirst();
        lName.moveToFirst();
        usrName = fName.getString(0) + " " + lName.getString(0);
        return usrName;
    }


}
