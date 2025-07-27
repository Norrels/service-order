package com.bytes.bytes.contexts.kitchen.domain.models;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String imgUrl;
    private BigDecimal price;
    private ProductCategory category;
    private String description;
    private Long createdById;
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void update(Product product) {
        this.name = product.getName();
        this.imgUrl = product.getImgUrl();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.description = product.getDescription();
        this.observation = product.getObservation();

        this.validate();
    }

    public Product(Long id, String name, String imgUrl, BigDecimal price, ProductCategory category, String description, String observation, Long createdById) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.category = category;
        this.description = description;
        this.observation = observation;
        this.createdById = createdById;
        this.validate();
    }

    public void validate() {
        if(this.name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("O nome do produto não pode ser nulo");
        }

        if(this.category == null){
            throw new IllegalArgumentException("A categoria do produto não pode ser nula");
        }

        if(price.compareTo(new BigDecimal("0")) < 1){
            throw new IllegalArgumentException("O preço do produto não pode ser inferior a 0");
        }

        if(createdById == null){
            throw new IllegalArgumentException("É preciso informa o id do usuário que está criou o produto");
        }
    }
}
