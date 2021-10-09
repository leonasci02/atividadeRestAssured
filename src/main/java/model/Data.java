package model;

public class Data {
    private int id;
    private String name;
    private String gender;
    private String email;
    private String status;

    public Data(int id, String name, String gender, String email, String status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }

    public Data(String name, String gender, String email, String status) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}