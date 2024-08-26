package com.example.util;

import javax.persistence.*;

public class JPAUtil {
    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("meu-projeto");
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
