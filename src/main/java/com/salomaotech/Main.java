package com.salomaotech;

import com.salomaotech.models.Cliente;
import com.salomaotech.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Main {
    public static void main(String[] args) {

        JpaUtil jpaUtil = new JpaUtil();
        EntityManager em = jpaUtil.entityManager();

        System.out.println("Conexão com o banco realizada com sucesso!");

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setTelefone("99999-9999");

        em.persist(cliente);
        transaction.commit();
        System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());

        em.close();
        jpaUtil.close();

    }

}
