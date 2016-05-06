package com.scanbee.model;

/**
 * Created by kshitij on 4/28/2016.
 */
public class CartItemModelClass {
    int id;
    String brand;
    String title;
    float content;
    String contentItem;
    String catName;
    int carId;
    String itemId;
    float mrp;
    float cp;
    String createdAt;
    String updatedAt;

    public CartItemModelClass(int id, String brand, String title, float content, String contentItem, String catName, int carId, String itemId, float mrp, float cp, String createdAt, String updatedAt) {
        this.id = id;
        this.brand = brand;
        this.title = title;
        this.content = content;
        this.contentItem = contentItem;
        this.catName = catName;
        this.carId = carId;
        this.itemId = itemId;
        this.mrp = mrp;
        this.cp = cp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getTitle() {
        return title;
    }

    public float getContent() {
        return content;
    }

    public String getContentItem() {
        return contentItem;
    }

    public String getCatName() {
        return catName;
    }

    public int getCarId() {
        return carId;
    }

    public String getItemId() {
        return itemId;
    }

    public float getMrp() {
        return mrp;
    }

    public float getCp() {
        return cp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}