package team7.tutoringappv1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TutorsActivity extends Activity {

    List<String> tutorNames = new ArrayList<>();
    String fName;
    String lName;
    String name;
    String subject;
    Float rating;
    int rate;
    String rateMoneySign;
    String entry;
    Tutors tutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);

        // builds tutors array of all users in txt file
        tutors = new Tutors(this);


        for (int i = 0; i < tutors.getTutorList().size(); i++){
            if (tutors.getTutorList().get(i).getIsTutor().equals("1")){
                fName = tutors.getTutorList().get(i).getFirstName();
                lName = tutors.getTutorList().get(i).getLastName();
                name = fName + " " + lName;
                subject = "Test";
                rating = tutors.getTutorList().get(i).getReviewRate();
                rate = tutors.getTutorList().get(i).getTutorRate();

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
                        rateMoneySign = "$$$";
                        break;
                }

                entry = name + "\n   Subject:  " + subject + "\n   Rating:    "+ rating + "\n   Rate:       " + rateMoneySign;


                if (!(fName + " " + lName).equals("Test User")){
                    tutorNames.add(entry);
                }
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.actvity_listview, tutorNames);

        ListView listView = (ListView) findViewById(R.id.tutorsView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("Item Clicked: " + position);
                System.out.println("Selected user: " + tutors.getTutorList().get(position).getFirstName() + " " + tutors.getTutorList().get(position).getLastName());

            }
        });

    }

}
