// TutorListActivity.java

package team7.tutoringappv1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.IOException;
import java.lang.Math;
import android.location.Location;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TutorListActivity extends ListActivity {

    List<String> tutorNames = new ArrayList<>();
    private ArrayList<Users> tutorList = new ArrayList<>();
    String fName;
    String lName;
    String name;
    String subject;
    Float rating;
    int rate;
    String rateMoneySign;
    String entry;
    String wholeStar = "★";
    String partialStar = "✩";
    int ratingCount;
    String ratingString = "";
    String distance;
    Bundle filters;
    Intent myIntent;
    String startCoords;
    String targetCoords;
    float startLatitude = 32.7310991f;
    float startlongitude = -97.1177082f;
    float targetLatitude;
    float targetLongitude;
    Location loc1;
    Location loc2;


    SQLiteDatabase mydatabase;
    Users tutor;

    public static Activity listActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);
        myIntent = getIntent();
        filters = myIntent.getExtras();
        loc1 = new Location("");
        loc2 = new Location("");
        updateDistanceTable();

        listActivity = this;


        if (filters == null || (!filters.containsKey("maxDistance") && !filters.containsKey("maxPrice") && !filters.containsKey("minRating"))) {
            createDefaultList();
        } else if ((filters.containsKey("maxDistance") && !filters.containsKey("maxPrice") && !filters.containsKey("minRating")) ||
                (!filters.containsKey("maxDistance") && filters.containsKey("maxPrice") && !filters.containsKey("minRating")) ||
                (!filters.containsKey("maxDistance") && !filters.containsKey("maxPrice") && filters.containsKey("minRating"))){

            if (filters.containsKey("maxDistance")) {
                String maxDistance = filters.getString("maxDistance");
                createListWithMaxFilter("distance", maxDistance);
            }
            if (filters.containsKey("maxPrice")) {
                String tutorRate = filters.getString("maxPrice");
                createListWithMaxFilter("tutorRate", tutorRate);
            }
            if (filters.containsKey("minRating")) {
                String rating = filters.getString("minRating");
                createListWithRatingFilter("rating", rating);
            }

            // if two filters are active
        } else if (!filters.containsKey("maxDistance") && filters.containsKey("maxPrice") && filters.containsKey("minRating")) {

            mydatabase.close();

            String tutorRate = filters.getString("maxPrice");
            String rating = filters.getString("minRating");
            createListWithMaxAndMinFilter("tutorRate", tutorRate, "rating", rating);
        }
        else if (filters.containsKey("maxDistance") && !filters.containsKey("maxPrice") && filters.containsKey("minRating")) {

            String maxDistance = filters.getString("maxDistance");
            String rating = filters.getString("minRating");
            createListWithMaxAndMinFilter("distance", maxDistance, "rating", rating);
        } else if (filters.containsKey("maxDistance") && filters.containsKey("maxPrice") && !filters.containsKey("minRating")) {

            String maxDistance = filters.getString("maxDistance");
            String tutorRate = filters.getString("maxPrice");
            createListWithMaxAndMaxFilter("distance", maxDistance, "tutorRate", tutorRate);
            // if all 3 filters are active
        } else if (filters.containsKey("maxDistance") && filters.containsKey("maxPrice") && filters.containsKey("minRating")) {

            String maxDistance = filters.getString("maxDistance");
            String tutorRate = filters.getString("maxPrice");
            String rating = filters.getString("minRating");

            createListWithThreeFilters("distance", maxDistance, "tutorRate", tutorRate, "rating", rating);
        }

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.actvity_listview, R.id.itemName,tutorNames));

        Button btnFilter = (Button) findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(view.getContext(), FilterActivity.class);
                startActivityForResult(filterIntent, 0);
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent tutorDisplayIntent = new Intent(view.getContext(), TutorProfileActivity.class);
                tutorDisplayIntent.putExtra("selectedTutor", tutorList.get(position).getEmail());
                startActivityForResult(tutorDisplayIntent, 0);

            }
        });

    }

    // all 3 filters are active
    public void createListWithThreeFilters(String maxFilter1, String maxFilterValue1, String maxFilter2, String maxFilterValue2, String minFilter, String minFilterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        tutorList.clear();
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        subjectsQuery.moveToFirst();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND " + maxFilter1 + " <= " + maxFilterValue1
                +
                " AND "
                +
                maxFilter2 + " <= " + maxFilterValue2
                +
                " AND "
                +
                minFilter + " >= " + minFilterValue
                + " ORDER BY " + minFilter, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));

            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();

            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";

            if (showTutor == true)
                tutorNames.add(entry);

        }
    }

    // this will create a list based on having 2 filters where we have 2 max restrictions (maxPrice &maxDistance)
    public void createListWithMaxAndMaxFilter(String maxFilter1, String maxFilterValue1, String maxFilter2, String maxFilterValue2) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        tutorList.clear();
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        subjectsQuery.moveToFirst();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND " + maxFilter1 + " <= " + maxFilterValue1
                +
                " AND "
                +
                maxFilter2 + " <= " + maxFilterValue2
                + " ORDER BY " + maxFilter1, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));

            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();

            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";

            if (showTutor == true)
                tutorNames.add(entry);
        }
    }

    // this will create a list based on having 2 filters where we have a max restriction (maxPrice/maxDistance)
    // and a minimum restriction (minRating)
    public void createListWithMaxAndMinFilter(String maxFilter, String maxFilterValue, String minFilter, String minFilterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        tutorList.clear();
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        subjectsQuery.moveToFirst();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND " + maxFilter + " <= " + maxFilterValue
                +
                " AND "
                +
                minFilter + " >= " + minFilterValue
                + " ORDER BY " + minFilter + " ASC, " + maxFilter + " ASC", null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));

            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();

            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";

            if (showTutor == true)
                tutorNames.add(entry);
        }
    }

    public void createListWithMaxFilter(String filter, String filterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        tutorList.clear();
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        subjectsQuery.moveToFirst();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND " + filter + " <= " + filterValue
                + " ORDER BY " + filter, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));

            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();

            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";

            if (showTutor == true)
                tutorNames.add(entry);
        }
    }

    public void createListWithRatingFilter(String filter, String filterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        subjectsQuery.moveToFirst();
        tutorList.clear();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND " + filter + " >= " + filterValue
                + " ORDER BY " + filter, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));
            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();
            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";

            if (showTutor == true)
                tutorNames.add(entry);
        }
    }

    public void createDefaultList() {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor subjectsQuery = mydatabase.rawQuery("SELECT math, science, literature, history, musicInstrument, musicTheory FROM userst WHERE loggedIn = '1'", null);
        tutorList.clear();
        subjectsQuery.moveToFirst();
        boolean subjects[] = new boolean[6];
        boolean showTutor = false;

        for (int i = 0; i < 6; i++) {
            if (subjectsQuery.getString(i).equals("true"))
                subjects[i] = true;
            else
                subjects[i] = false;
        }
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email, distance FROM userst WHERE isTutor = '1' AND distance <= 3 ORDER BY lastName", null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));
            tempUser.setDistance(dbEntry.getString(12));
            showTutor = false;
            if (tempUser.isT_math() && subjects[0]) {
                showTutor = true;
            } else if (tempUser.isT_science() && subjects[1]) {
                showTutor = true;
            } else if (tempUser.isT_literature() && subjects[2]) {
                showTutor = true;
            } else if (tempUser.isT_history() && subjects[3]) {
                showTutor = true;
            } else if (tempUser.isT_musicInstrument() && subjects[4]) {
                showTutor = true;
            } else if (tempUser.isT_musicTheory() && subjects[5]) {
                showTutor = true;
            }
            if (showTutor == true)
                tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();
            distance = tutor.getDistance();
            showTutor = false;
            if (tutor.isT_math() && subjects[0]) {
                subject = "Math ";
                showTutor = true;
            } else if (tutor.isT_science() && subjects[1]) {
                subject = "Science ";
                showTutor = true;
            } else if (tutor.isT_literature() && subjects[2]) {
                subject = "Literature ";
                showTutor = true;
            } else if (tutor.isT_history() && subjects[3]) {
                subject = "History ";
                showTutor = true;
            } else if (tutor.isT_musicInstrument() && subjects[4]) {
                subject = "Musical Instruments ";
                showTutor = true;
            } else if (tutor.isT_musicTheory() && subjects[5]) {
                subject = "Music Theory ";
                showTutor = true;
            } else {
                subject = "N/A";
            }

            switch (rate) {
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

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\nDistance:   " + distance + " miles";
            if (showTutor == true)
                tutorNames.add(entry);
        }
    }

    public void updateDistanceTable() {
        loc1.setLatitude(startLatitude);
        loc1.setLongitude(startlongitude);
        String userZipcode;
        String query;
        String distance;
        float distanceInMeters;
        float distanceInMiles;
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);

        Cursor dbEntry = mydatabase.rawQuery("select distinct zipcode from userst", null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {
            userZipcode = dbEntry.getString(0);
            getCoorFromZip(userZipcode);

            loc2.setLatitude(targetLatitude);
            loc2.setLongitude(targetLongitude);


            distanceInMeters = loc1.distanceTo(loc2);
            distanceInMiles = distanceInMeters * 0.000621371f;
            distance = String.format("%.2f", distanceInMiles);
            query = "UPDATE userst SET distance = " + distance + " WHERE zipcode = " + userZipcode;
            mydatabase.execSQL(query);
            dbEntry.move(1);

        }
    }

    public void getCoorFromZip(String zip){
        final Geocoder geocoder = new Geocoder(this);
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
