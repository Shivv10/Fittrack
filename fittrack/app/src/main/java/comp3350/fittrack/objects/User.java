package comp3350.fittrack.objects;

/** **************************************************
 CLASS NAME: User.java

 CLASS FUNCTION: Represents a user in the FitTrack system.

 Contains basic user data like name, age, height, weight,
 login credentials, and a unique database-generated ID.

 ************************************************** */

public class User{
    //unique id for each user
    private final int ID;
    private int age;
    private double height;
    private String name;
    private String username;
    private String password;
    private double weight;
      public User(int age, double weight, int id,  String password, double height, String username, String name) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.password = password;
        this.username = username;
        this.ID = id;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getHeight() {
        return height;
    }
    public double getWeight() {
        return this.weight;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getID() {
        return this.ID;
    }
}