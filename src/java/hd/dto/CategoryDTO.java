/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.dto;

import hd.entity.Category;
import java.io.Serializable;

/**
 *
 * @author LongVTHSE60972
 */
public class CategoryDTO implements Serializable {
    private Integer categoryID;
    private String categoryName;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDTO(Category categoryEntity) {
        this.categoryID = categoryEntity.getCategoryId();
        this.categoryName = categoryEntity.getCategoryName();        
    }
}
