package com.salomaotech.repositories;

import java.util.List;
import com.salomaotech.models.Cliente;
import com.salomaotech.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ClienteRepository {

    private JpaUtil jpaUtil;

    public ClienteRepository(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Cliente save(Cliente cliente) {

        // Prepara transação
        EntityManager manager = jpaUtil.entityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // Persiste
        manager.persist(cliente);
        transaction.commit();
        manager.close();

        return cliente;

    }

    public List<Cliente> findAll() {

        EntityManager manager = jpaUtil.entityManager();

        TypedQuery<Cliente> query = manager.createQuery("select obj from " + Cliente.class.getSimpleName() + " obj",
                Cliente.class);

        List<Cliente> clientes = query.getResultList();
        manager.close();
        return clientes;

    }

}
