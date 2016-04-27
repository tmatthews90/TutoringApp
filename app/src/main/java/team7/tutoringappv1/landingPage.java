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
import java.util.Arrays;
import java.util.List;

public class landingPage extends AppCompatActivity {
    public static String line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase mydatabase = openOrCreateDatabase("Users",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS userst(firstName VARCHAR,lastName VARCHAR,zipcode INT,phonenumber VARCHAR,isTutor BOOL, email VARCHAR PRIMARY KEY, password VARCHAR);");
        try {
            InputStream fis = getResources().getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String query = null;
            String a = "INSERT INTO userst VALUES('";
            String b = "', '";
            int i = 0;
            while ((line = reader.readLine()) != null){
                Users c = new Users(line);
                System.out.println(c.getEmail());
                query = a + c.getFirstName() + b + c.getLastName() + b + c.getZipCode() + b + c.getPhoneNumber() + b + c.getIsTutor() + b + c.getEmail() + b + c.getPassword() + "');";
                mydatabase.execSQL(query);
            }
        } catch (IOException|SQLiteConstraintException e) {
            System.out.println("table error");
        }

        Cursor result = mydatabase.rawQuery("Select * from userst",null);
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
