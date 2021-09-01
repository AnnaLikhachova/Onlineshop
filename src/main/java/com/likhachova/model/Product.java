package com.likhachova.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private int id;
    private String name;
    private int price;
    private String description;
    private LocalDate date;


    public Product(String name, int price,  String description, LocalDate date) {
        this.name = name;
        this.price = price;
        this.description=description;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                price == product.price &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(date, product.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, date);
    }
}
