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
        cfg.configure();
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // ✅ INSERT MORE DATA (Skill 3 requirement)
        session.save(new Product("Laptop", "Electronics", 50000, 10));
        session.save(new Product("Phone", "Electronics", 20000, 15));
        session.save(new Product("Shoes", "Fashion", 3000, 20));
        session.save(new Product("Watch", "Fashion", 5000, 5));
        session.save(new Product("TV", "Electronics", 40000, 7));
        session.save(new Product("Bag", "Accessories", 1500, 25));
        session.save(new Product("Headphones", "Electronics", 2500, 12));

        tx.commit();

        // ================================
        // ✅ SORT BY PRICE ASC
        System.out.println("\n--- Price ASC ---");
        List<Product> list1 = session.createQuery(
                "FROM Product p ORDER BY p.price ASC", Product.class).list();
        list1.forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        // ✅ SORT BY PRICE DESC
        System.out.println("\n--- Price DESC ---");
        List<Product> list2 = session.createQuery(
                "FROM Product p ORDER BY p.price DESC", Product.class).list();
        list2.forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        // ================================
        // ✅ SORT BY QUANTITY DESC
        System.out.println("\n--- Quantity DESC ---");
        List<Product> list3 = session.createQuery(
                "FROM Product p ORDER BY p.quantity DESC", Product.class).list();
        list3.forEach(p -> System.out.println(p.getName() + " - " + p.getQuantity()));

        // ================================
        // ✅ PAGINATION
        System.out.println("\n--- First 3 Products ---");
        List<Product> first3 = session.createQuery("FROM Product", Product.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .list();
        first3.forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Next 3 Products ---");
        List<Product> next3 = session.createQuery("FROM Product", Product.class)
                .setFirstResult(3)
                .setMaxResults(3)
                .list();
        next3.forEach(p -> System.out.println(p.getName()));

        // ================================
        // ✅ AGGREGATE FUNCTIONS

        Long total = session.createQuery("SELECT COUNT(*) FROM Product", Long.class)
                .uniqueResult();
        System.out.println("\nTotal Products: " + total);

        Long available = session.createQuery(
                "SELECT COUNT(*) FROM Product WHERE quantity > 0", Long.class)
                .uniqueResult();
        System.out.println("Available Products: " + available);

        System.out.println("\n--- Count by Description ---");
        List<Object[]> group = session.createQuery(
                "SELECT description, COUNT(*) FROM Product GROUP BY description")
                .list();

        for (Object[] row : group) {
            System.out.println(row[0] + " : " + row[1]);
        }

        Object[] minMax = (Object[]) session.createQuery(
                "SELECT MIN(price), MAX(price) FROM Product")
                .uniqueResult();

        System.out.println("\nMin Price: " + minMax[0]);
        System.out.println("Max Price: " + minMax[1]);

        // ================================
        // ✅ WHERE (PRICE RANGE)
        System.out.println("\n--- Price BETWEEN 2000 AND 40000 ---");
        List<Product> range = session.createQuery(
                "FROM Product WHERE price BETWEEN 2000 AND 40000", Product.class)
                .list();
        range.forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        // ================================
        // ✅ LIKE QUERIES

        System.out.println("\n--- Starts with P ---");
        session.createQuery("FROM Product WHERE name LIKE 'P%'", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Ends with e ---");
        session.createQuery("FROM Product WHERE name LIKE '%e'", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Contains 'a' ---");
        session.createQuery("FROM Product WHERE name LIKE '%a%'", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        System.out.println("\n--- Name length = 5 ---");
        session.createQuery("FROM Product WHERE LENGTH(name) = 5", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        // ================================
        session.close();
        factory.close();
    }
}