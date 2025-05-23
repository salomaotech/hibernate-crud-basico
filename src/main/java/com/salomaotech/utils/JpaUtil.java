package com.salomaotech.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory factory;

    static {

        factory = Persistence.createEntityManagerFactory("Conexao");

    }

    public EntityManager entityManager() {

        return factory.createEntityManager();

    }

    public void close() {

        factory.close();

    }

}
