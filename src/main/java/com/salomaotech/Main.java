package com.salomaotech;

import java.util.List;
import java.util.Optional;

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
        Optional<Cliente> c = clienteRepository.save(cliente);

        if (c.isPresent()) {

            System.out.println("Cadastro bem sucedido!");

        } else {

            System.out.println("Cadastro mal sucedido!");

        }

    }

    private static void findAll() {

        List<Cliente> clientes = clienteRepository.findAll();

        for (Cliente c : clientes) {

            System.out.println("ID: " + c.getId());
            System.out.println("Nome: " + c.getNome());
            System.out.println("Telefone: " + c.getTelefone());

        }

    }

    private static void remover(long id) {

        if (clienteRepository.remove(id)) {

            System.out.println("Registro removido!!!");

        } else {

            System.out.println("Registro não removido!!!");

        }

    }

    private static void getById(long id) {

        Optional<Cliente> cliente1 = clienteRepository.getById(id);

        if (cliente1.isPresent()) {

            System.out.println(cliente1.get().getNome());

        }

    }

    private static void update() {

        Cliente cliente = new Cliente();
        cliente.setId(6);
        cliente.setNome("Salomão F Silva");
        cliente.setTelefone("62 99999-9999");

        if (clienteRepository.update(cliente).isPresent()) {

            System.out.println("Cadastro atualizado!");

        } else {

            System.out.println("Cadastro não atualizado!");

        }

    }

    public static void main(String[] args) {

        save();
        findAll();
        remover(3);
        getById(7);
        update();
        jpaUtil.close();
        System.exit(0);

    }

}
