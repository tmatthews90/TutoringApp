package team7.tutoringappv1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class landingPage extends AppCompatActivity {

    public static String line = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            //FileOutputStream fos = openFileOutput("users.txt", Context.MODE_PRIVATE);
            InputStream fis = getResources().getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((line = reader.readLine()) != null){

            }
        }
        catch(IOException e) {
            System.out.println("file error");
        }
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
