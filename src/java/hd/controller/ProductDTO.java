package hd.controller;

import hd.entity.Product;
import hd.entity.ProductPhoto;
import java.io.Serializable;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lê Đại An
 */
public class ProductDTO implements Serializable {

    Product product;
    String urlPhoto;
//    String category;
//    String style;

    public ProductDTO() {
        urlPhoto = "";
//        category = "";
//        style = "";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }


//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getStyle() {
//        return style;
//    }
//
//    public void setStyle(String style) {
//        this.style = style;
//    }

}
