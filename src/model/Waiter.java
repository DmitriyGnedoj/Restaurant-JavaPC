package model;

public class Waiter {
    private int id;
    private String name;
    private String adress;
    private  String telephone;
    private  String role;
    private  String adresscafe;
    private  String director;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdresscafe() {
        return adresscafe;
    }

    public void setAdresscafe(String adresscafe) {
        this.adresscafe = adresscafe;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private  String login;
    private  String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Waiter(int id, String name, String adress, String telephone, String login, String password, String role,  String adresscafe, String director) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
        this.role = role;
        this.adresscafe = adresscafe;
        this.director = director;
    }


}
