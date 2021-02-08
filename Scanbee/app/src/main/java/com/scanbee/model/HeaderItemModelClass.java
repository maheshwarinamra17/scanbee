package com.scanbee.model;

public class HeaderItemModelClass {

    Double amount;
    Double tax;
    Double cartValue;
    Double discount;

    public HeaderItemModelClass(Double amount, Double tax, Double cartValue, Double discount) {
       this.amount = amount;
       this.tax = tax;
       this.cartValue = cartValue;
       this.discount = discount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getCartValue() {
        return cartValue;
    }

    public void setCartValue(Double cartValue) {
        this.cartValue = cartValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
