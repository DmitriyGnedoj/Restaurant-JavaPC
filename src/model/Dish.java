package model;

public class Dish {
    private  int id;
    private  String name;
    private float price;
    private String type;
    private  float pricewithnacenka;
    private float procent;

    public float getPricewithnacenka() {
        return pricewithnacenka;
    }

    public void setPricewithnacenka(float pricewithnacenka) {
        this.pricewithnacenka = pricewithnacenka;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



    public Dish(int id, String name, float price, String type, float pricewithnacenka, float procent) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.pricewithnacenka = pricewithnacenka;
        this.procent = procent;
    }

    public float getProcent() {
        return procent;
    }

    public void setProcent(float procent) {
        this.procent = procent;
    }

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
}
