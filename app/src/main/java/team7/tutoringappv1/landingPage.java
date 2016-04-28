package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class landingPage extends AppCompatActivity {
    public static String line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase mydatabase = openOrCreateDatabase("Users",MODE_PRIVATE,null);

//        mydatabase.execSQL("drop table userst");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS userst(" +
                "firstName VARCHAR,lastName VARCHAR,zipcode INT,phonenumber VARCHAR,isTutor BOOL, " +
                "email VARCHAR PRIMARY KEY, password VARCHAR, loggedIn BOOL, math BOOL, science BOOL, literature BOOL, history BOOL, musicInstrument BOOL, " +
                "musicTheory BOOL, t_math BOOL, t_science BOOL, t_literature BOOL, t_history BOOL, t_musicInstrument BOOL, t_musicTheory BOOL, tutorRate INT, rating FLOAT);");
        try {
            InputStream fis = getResources().getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String query = null;
            String b = "', '";
            while ((line = reader.readLine()) != null){
                Users c = new Users(line);
                System.out.println(c.getEmail());
                query = "INSERT INTO userst VALUES('" + c.getFirstName() + b + c.getLastName() + b + c.getZipCode() + b + c.getPhoneNumber() + b + c.getIsTutor() + b +
                        c.getEmail() + b + c.getPassword() + b + c.getLoggedIn() + b + c.isMath() + b + c.isScience() + b + c.isLiterature() + b +
                        c.isHistory() + b + c.isMusicInstrument() + b + c.isMusicTheory() + b + c.isT_math() + b + c.isT_science() + b + c.isT_literature() +
                        b + c.isT_history() + b + c.isT_musicInstrument() + b + c.isT_musicTheory() + b + c.getTutorRate() + b + c.getReviewRate() + "');";
                mydatabase.execSQL(query);
            }
        } catch (IOException|SQLiteConstraintException e) {
            System.out.println("table populated");
        }

        Cursor result = mydatabase.rawQuery("Select email from userst where loggedIn = '1'",null);
        if (result.getCount() == 1){
            Intent loggedIn = new Intent(this,PostLogin.class);
            startActivityForResult(loggedIn, 0);
            finish();
        }

        System.out.println(result.getCount());
        setContentView(R.layout.activity_landing_page);

        //ImageView icon = (ImageView) findViewById(R.id.imageView);
        //icon.setVisibility(View.VISIBLE);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(registerIntent, 0);
                finish();
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(loginIntent, 0);
                finish();
            }
        });


    }
}
