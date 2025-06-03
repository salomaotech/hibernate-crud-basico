package com.salomaotech.repositories;

import java.util.List;
import java.util.Optional;

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

    public Optional<Cliente> save(Cliente cliente) {

        // Prepara transação
        EntityManager manager = jpaUtil.entityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        try {

            // Persiste
            manager.persist(cliente);
            transaction.commit();
            manager.close();
            return Optional.of(cliente);

        } catch (Exception ex) {

            manager.close();
            return null;

        }

    }

    public List<Cliente> findAll() {

        // Prepara a pesquisa via JPQL
        EntityManager manager = jpaUtil.entityManager();
        TypedQuery<Cliente> query = manager.createQuery("select obj from " + Cliente.class.getSimpleName() + " obj",
                Cliente.class);

        // Carrega e retorna os resultados
        List<Cliente> clientes = query.getResultList();
        manager.close();
        return clientes;

    }

    public Optional<Cliente> getById(long id) {

        // Prepara transação
        EntityManager manager = jpaUtil.entityManager();

        try {

            Cliente cliente = manager.find(Cliente.class, id);
            manager.close();
            return Optional.of(cliente);

        } catch (Exception ex) {

            manager.close();
            return Optional.empty();

        }

    }

    public boolean remove(long id) {

        // Prepara transação
        EntityManager manager = jpaUtil.entityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // Carrega o registro
        Cliente cliente = manager.find(Cliente.class, id);

        try {

            manager.remove(cliente);
            transaction.commit();
            manager.close();
            return true;

        } catch (Exception ex) {

            manager.close();
            return false;

        }

    }

    public Optional<Cliente> update(Cliente cliente) {

        // Prepara transação
        EntityManager manager = jpaUtil.entityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        try {

            // Atualiza
            cliente = manager.merge(cliente);
            transaction.commit();
            manager.close();
            return Optional.of(cliente);

        } catch (Exception ex) {

            return Optional.empty();

        }

    }

}
