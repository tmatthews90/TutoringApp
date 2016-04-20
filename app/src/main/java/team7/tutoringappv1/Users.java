package team7.tutoringappv1;

public class Users {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int zipCode;
    private String phoneNumber;
    private boolean isTutor;
    //class category
    public Users(){
        this.setFirstName("N/A");
        this.setLastName("N/A");
        this.setEmail("N/A");
        this.setPassword("N/A");
        this.setZipCode(0);
        this.setPhoneNumber("N/A");
        this.setTutor(false);
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isTutor() {
        return isTutor;
    }

    public void setTutor(boolean tutor) {
        isTutor = tutor;
    }
}
