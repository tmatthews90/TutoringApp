package team7.tutoringappv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tutors {

    private String line;
    private Context context;
    int lineCount = 0;

    SQLiteDatabase mydatabase;

    private ArrayList<Users> tutorList = new ArrayList<>();

    public Tutors(Context current) {
        this.context = current;

        try {
            InputStream fis = context.getResources().getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((line = reader.readLine()) != null) {
                if (lineCount < 2) {
                    lineCount++;
                }
                else {
                    Users tempUser = new Users(line);
                    if (tempUser.getIsTutor().equals("1")){
                        getTutorList().add(tempUser);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("file error");
        }
    }

    public ArrayList<Users> getTutorList() {
        return tutorList;
    }

}
