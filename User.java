package services;


public class User {
    String name;


    public User(String name) {
        this.name = name;
    }


    public void update(String message){
        System.out.println(message);
    }
}
