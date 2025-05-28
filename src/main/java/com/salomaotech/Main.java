package com.salomaotech;

import java.util.List;

import com.salomaotech.models.Cliente;
import com.salomaotech.repositories.ClienteRepository;
import com.salomaotech.utils.JpaUtil;

public class Main {

    private static JpaUtil jpaUtil = new JpaUtil();
    private static ClienteRepository clienteRepository = new ClienteRepository(jpaUtil);

    private static void save() {

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setTelefone("99999-9999");
        clienteRepository.save(cliente);

    }

    private static void findAll() {

        List<Cliente> clientes = clienteRepository.findAll();

        for (Cliente c : clientes) {

            System.out.println("ID: " + c.getId());
            System.out.println("Nome: " + c.getNome());
            System.out.println("Telefone: " + c.getTelefone());

        }

    }

    public static void main(String[] args) {

        save();
        findAll();
        jpaUtil.close();
        System.exit(0);

    }

}
