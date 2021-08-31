package model;

public class IngradientDish {
    private int id;
    private String nameDish;
    private float count;
    private String ves;
    private float price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public IngradientDish(int id, String nameDish, float count, String ves, float price) {


        this.id = id;
        this.nameDish = nameDish;
        this.count = count;
        this.ves = ves;
        this.price = price;
    }
}
