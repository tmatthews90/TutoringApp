package team7.tutoringappv1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TutorsActivity extends Activity {

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    List<String> tutorNames = new ArrayList<>();
    String fName;
    String lName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);

        // builds tutors array of all users in txt file
        Tutors tutors = new Tutors(this);

        tutors.getTutorList().get(0).getFirstName();

        for (int i = 0; i < tutors.getTutorList().size(); i++){
            fName = tutors.getTutorList().get(i).getFirstName();
            lName = tutors.getTutorList().get(i).getLastName();
            name = fName + " " + lName;

            if (!name.equals("Test User")){
                tutorNames.add(name);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.actvity_listview, tutorNames);

        ListView listView = (ListView) findViewById(R.id.tutorsView);
        listView.setAdapter(adapter);

    }
}
