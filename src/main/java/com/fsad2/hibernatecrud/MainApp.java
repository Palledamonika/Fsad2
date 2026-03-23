package com.fsad2.hibernatecrud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.fsad2.model.Product;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure(); // loads hibernate.cfg.xml
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // ---------- INSERT ----------
        Product p1 = new Product("Pen", "Blue pen", 10.5, 100);
        Product p2 = new Product("Notebook", "Ruled notebook", 50.0, 200);
        Product p3 = new Product("Pencil", "HB pencil", 5.0, 500);

        session.save(p1);
        session.save(p2);
        session.save(p3);

        tx.commit();
        System.out.println("Multiple Products Inserted!");

        // ---------- SELECT ----------
        session = factory.openSession();
        Product retrieved = session.get(Product.class, p1.getId());
        System.out.println("Product Retrieved: " + retrieved.getName() + " - " + retrieved.getPrice());

        // ---------- UPDATE ----------
        tx = session.beginTransaction();
        retrieved.setPrice(12.0);
        session.update(retrieved);
        tx.commit();
        System.out.println("Product Updated!");

        // ---------- DELETE ----------
        tx = session.beginTransaction();
        Product toDelete = session.get(Product.class, p3.getId());
        session.delete(toDelete);
        tx.commit();
        System.out.println("Product Deleted!");

        // ---------- LIST ALL ----------
        List<Product> products = session.createQuery("from Product", Product.class).list();
        for (Product p : products) {
            System.out.println("Product: " + p.getId() + " | " + p.getName() + " - " + p.getPrice());
        }

        // Close session and factory
        session.close();
        factory.close();
    }
}