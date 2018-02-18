package com.sissu;

public class product {
    private String product_name, product_mrp, product_rate, product_discount, product_weight, product_unit, product_info, product_location, product_brand, product_category, product_other, misc_id, misc_value;

    public product() {

    }

    public product(String product_name, String product_mrp, String product_rate, String product_discount, String product_weight, String product_unit, String product_info, String product_location, String product_brand, String product_category, String product_other, String misc_id, String misc_value) {
        this.product_name = product_name;
        this.product_mrp = product_mrp;
        this.product_rate = product_rate;
        this.product_discount = product_discount;
        this.product_weight = product_weight;
        this.product_unit = product_unit;
        this.product_info = product_info;
        this.product_location = product_location;
        this.product_brand = product_brand;
        this.product_category = product_category;
        this.product_other = product_other;
        this.misc_id = misc_id;
        this.misc_value = misc_value;
    }

    public product(String product_name, String product_mrp, String product_rate, String product_discount, String product_weight, String product_unit, String product_info, String product_brand, String product_category) {
        this.product_name = product_name;
        this.product_mrp = product_mrp;
        this.product_rate = product_rate;
        this.product_discount = product_discount;
        this.product_weight = product_weight;
        this.product_unit = product_unit;
        this.product_info = product_info;
        this.product_brand = product_brand;
        this.product_category = product_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mrp() {
        return product_mrp;
    }

    public void setProduct_mrp(String product_mrp) {
        this.product_mrp = product_mrp;
    }

    public String getProduct_rate() {
        return product_rate;
    }

    public void setProduct_rate(String product_rate) {
        this.product_rate = product_rate;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_info() {
        return product_info;
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }

    public String getProduct_location() {
        return product_location;
    }

    public void setProduct_location(String product_location) {
        this.product_location = product_location;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_other() {
        return product_other;
    }

    public void setProduct_other(String product_other) {
        this.product_other = product_other;
    }

    public String getMisc_id() {
        return misc_id;
    }

    public void setMisc_id(String misc_id) {
        this.misc_id = misc_id;
    }

    public String getMisc_value() {
        return misc_value;
    }

    public void setMisc_value(String misc_value) {
        this.misc_value = misc_value;
    }
}
