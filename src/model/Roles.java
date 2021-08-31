package model;

public class Roles {
    private  int id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String role;

    public Roles(int id, String role) {
        this.id = id;
        this.role = role;
    }
}
