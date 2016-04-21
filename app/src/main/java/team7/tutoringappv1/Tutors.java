package team7.tutoringappv1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tutors {

    private String line;
    private Context context;
    int lineCount = 0;

    ArrayList<Users> tutorList = new ArrayList<>();

    public Tutors(Context current) {
        this.context = current;
        try {
            InputStream fis = context.getResources().getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((line = reader.readLine()) != null) {
                if (lineCount == 0) {
                    lineCount++;
                }
                else {
                    Users tempUser = new Users(line);
                    tutorList.add(tempUser);
                }
            }
        } catch (IOException e) {
            System.out.println("file error");
        }
    }


//    try {
//        //FileOutputStream fos = openFileOutput("users.txt", Context.MODE_PRIVATE);
//        InputStream fis = getResources().getAssets().open("users.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//        //while ((line = reader.readLine()) != null){
//        //  List<String> tokens = Arrays.asList(line.split(":"));
//        //}
//    } catch (IOException e) {
//        System.out.println("file error");
//    }



}
