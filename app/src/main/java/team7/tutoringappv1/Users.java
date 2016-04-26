package team7.tutoringappv1;

import java.util.Arrays;
import java.util.List;

public class Users {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String zipCode;
    private String phoneNumber;
    private String isTutor;
    private String loggedIn;

    private boolean math;
    private boolean science;
    private boolean literature;
    private boolean history;
    private boolean musicInstrument;
    private boolean musicTheory;
    private boolean t_math;
    private boolean t_science;
    private boolean t_literature;
    private boolean t_history;
    private boolean t_musicInstrument;
    private boolean t_musicTheory;

    private int tutorRate;
    private float reviewRate;
    //class category

    public Users(){
        this.setFirstName("N/A");
        this.setLastName("N/A");
        this.setEmail("N/A");
        this.setPassword("N/A");
        this.setZipCode("N/A");
        this.setPhoneNumber("N/A");
        this.setIsTutor("N/A");
        this.setLoggedIn("false");
    }

    //login constructor
    public Users(String userName, String password){
        this.setPassword(password);
        this.setEmail(userName);
    }

    // tutors constructor
    public Users(String line){
        List<String> tokens = Arrays.asList(line.split(":"));
        this.setFirstName(tokens.get(0));
        this.setLastName(tokens.get(1));
        this.setZipCode(tokens.get(2));
        this.setPhoneNumber(tokens.get(3));
        this.setIsTutor(tokens.get(4));
        this.setEmail(tokens.get(5));
        this.setPassword(tokens.get(6));
        this.setLoggedIn(tokens.get(7));
        this.setMath(Boolean.parseBoolean(tokens.get(8)));
        this.setScience(Boolean.parseBoolean(tokens.get(9)));
        this.setLiterature(Boolean.parseBoolean(tokens.get(10)));
        this.setHistory(Boolean.parseBoolean(tokens.get(11)));
        this.setMusicInstrument(Boolean.parseBoolean(tokens.get(12)));
        this.setMusicTheory(Boolean.parseBoolean(tokens.get(13)));
        this.setT_math(Boolean.parseBoolean(tokens.get(14)));
        this.setT_science(Boolean.parseBoolean(tokens.get(15)));
        this.setT_literature(Boolean.parseBoolean(tokens.get(16)));
        this.setT_history(Boolean.parseBoolean(tokens.get(17)));
        this.setT_musicInstrument(Boolean.parseBoolean(tokens.get(18)));
        this.setT_musicTheory(Boolean.parseBoolean(tokens.get(19)));
        this.setTutorRate(Integer.parseInt(tokens.get(20)));
        this.setReviewRate(Float.parseFloat(tokens.get(21)));
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsTutor() {
        return isTutor;
    }

    public void setIsTutor(String isTutor) {
        this.isTutor = isTutor;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isMath() {
        return math;
    }

    public void setMath(boolean math) {
        this.math = math;
    }

    public boolean isScience() {
        return science;
    }

    public void setScience(boolean science) {
        this.science = science;
    }

    public boolean isLiterature() {
        return literature;
    }

    public void setLiterature(boolean literature) {
        this.literature = literature;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isMusicInstrument() {
        return musicInstrument;
    }

    public void setMusicInstrument(boolean musicInstrument) {
        this.musicInstrument = musicInstrument;
    }

    public boolean isMusicTheory() {
        return musicTheory;
    }

    public void setMusicTheory(boolean musicTheory) {
        this.musicTheory = musicTheory;
    }

    public boolean isT_math() {
        return t_math;
    }

    public void setT_math(boolean t_math) {
        this.t_math = t_math;
    }

    public boolean isT_science() {
        return t_science;
    }

    public void setT_science(boolean t_science) {
        this.t_science = t_science;
    }

    public boolean isT_literature() {
        return t_literature;
    }

    public void setT_literature(boolean t_literature) {
        this.t_literature = t_literature;
    }

    public boolean isT_history() {
        return t_history;
    }

    public void setT_history(boolean t_history) {
        this.t_history = t_history;
    }

    public boolean isT_musicInstrument() {
        return t_musicInstrument;
    }

    public void setT_musicInstrument(boolean t_musicInstrument) {
        this.t_musicInstrument = t_musicInstrument;
    }

    public boolean isT_musicTheory() {
        return t_musicTheory;
    }

    public void setT_musicTheory(boolean t_musicTheory) {
        this.t_musicTheory = t_musicTheory;
    }

    public int getTutorRate() {
        return tutorRate;
    }

    public void setTutorRate(Integer tutorRate) {
        this.tutorRate = tutorRate;
    }

    public float getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(Float reviewRate) {
        this.reviewRate = reviewRate;
    }
}
