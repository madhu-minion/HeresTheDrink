package com.greymatter.heresthedrink.model;

public class Drink {

    private String name,description,blueprint,image;
    private int id,category_id,price;
    private boolean stock_status;

    public Drink() {
    }

    public Drink(String name, String description, String blueprint, String image, int id, int category_id, int price, boolean stock_status) {
        this.name = name;
        this.description = description;
        this.blueprint = blueprint;
        this.image = image;
        this.id = id;
        this.category_id = category_id;
        this.price = price;
        this.stock_status = stock_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(String blueprint) {
        this.blueprint = blueprint;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStock_status() {
        return stock_status;
    }

    public void setStock_status(boolean stock_status) {
        this.stock_status = stock_status;
    }
}
