package com.deliveryapp;

import com.deliveryapp.entity.Customer;
import com.deliveryapp.entity.Delivery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("delivery-unit");

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {
            transaction.begin();

            Customer customer = new Customer("Avinash Kumar");

            Delivery delivery1 =
                    new Delivery("Patna, Bihar", "PENDING");

            Delivery delivery2 =
                    new Delivery("Delhi", "DELIVERED");

            customer.addDelivery(delivery1);
            customer.addDelivery(delivery2);

            /*
             * Because CascadeType.ALL is used,
             * persisting the customer will also
             * persist both delivery objects.
             */
            entityManager.persist(customer);

            transaction.commit();

            System.out.println("Customer and deliveries saved successfully.");

        } catch (Exception exception) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            exception.printStackTrace();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}