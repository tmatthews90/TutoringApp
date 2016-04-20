package team7.tutoringappv1;

public class Users {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String zipCode;
    private String phoneNumber;
    private String isTutor;
    private String loggedIn;
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
}
