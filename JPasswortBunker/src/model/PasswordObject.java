package model;

public class PasswordObject {

    //create an object of SingleObject
    private static PasswordObject instance = new PasswordObject();
    private String password;

    //make the constructor private so that this class cannot be
    //instantiated
    private PasswordObject(){

    }

    //Get the only object available
    public static PasswordObject getInstance(){
        return instance;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
