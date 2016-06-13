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
    Double mrp;
    Double cp;
    String createdAt;
    String updatedAt;
    int quantity;


    public CartItemModelClass(int id, String brand, String title, float content, String contentItem, String catName, int carId, String itemId, Double mrp, Double cp, String createdAt, String updatedAt,int quantity) {
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
        this.quantity = quantity;
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

    public Double getMrp() {
        return mrp;
    }

    public Double getCp() {
        return cp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(float content) {
        this.content = content;
    }

    public void setContentItem(String contentItem) {
        this.contentItem = contentItem;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public void setCp(Double cp) {
        this.cp = cp;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
