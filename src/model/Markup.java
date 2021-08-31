package model;

public class Markup {
    private  int id;
    private  String type;
    private  Float markup;


    public Markup(int id, String type, Float markup) {
        this.id = id;
        this.type = type;
        this.markup = markup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getMarkup() {
        return markup;
    }

    public void setMarkup(Float markup) {
        this.markup = markup;
    }
}
