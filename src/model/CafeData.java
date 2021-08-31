package model;

public class CafeData {
    private  int id;
    private  String adress;
    private  String directorname;

    public CafeData(int id, String adress, String directorname) {
        this.id = id;
        this.adress = adress;
        this.directorname = directorname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDirectorr() {
        return directorname;
    }

    public void setDirectorr(String directorr) {
        this.directorname = directorr;
    }
}
