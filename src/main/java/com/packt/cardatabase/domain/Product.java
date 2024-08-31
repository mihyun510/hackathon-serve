package com.packt.cardatabase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    private String id;

    private String name;

    private String explanation;

    private Long price;

    private String thumbnail;

    @Column(nullable = true)
    private Long discount;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExplanation() {
        return explanation;
    }

    public Long getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getDiscount() {
        return discount;
    }
}
