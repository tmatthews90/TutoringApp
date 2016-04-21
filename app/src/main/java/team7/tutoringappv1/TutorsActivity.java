package team7.tutoringappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TutorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);

        Tutors tutors = new Tutors(this);

    }
}
