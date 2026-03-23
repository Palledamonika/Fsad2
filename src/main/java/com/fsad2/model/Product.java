package com.fsad2.model;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    // ✅ Default constructor
    public Product() {}

    // ✅ Parameterized constructor
    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // ✅ Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // ✅ Setters
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // ✅ toString (added)
    @Override
    public String toString() {
        return id + " | " + name + " | " + description + " | " + price + " | " + quantity;
    }
}