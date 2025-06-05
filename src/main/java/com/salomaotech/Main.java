package com.salomaotech;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.salomaotech.models.Cliente;
import com.salomaotech.repositories.ClienteRepository;
import com.salomaotech.utils.JpaUtil;

public class Main {

    private static JpaUtil jpaUtil = new JpaUtil();
    private static ClienteRepository clienteRepository = new ClienteRepository(jpaUtil);

    private static byte[] carregarImagem(String imagem) {

        try {

            Path path = FileSystems.getDefault().getPath(imagem);
            byte[] dados = Files.readAllBytes(path);
            return dados;

        } catch (Exception ex) {

            return null;

        }

    }

    private static void exibirImagem(Cliente cliente) {

        byte[] dados = cliente.getImagemPerfil();

        try {

            BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(dados));

            // Calcula as novas dimensões mantendo a proporção
            int originalWidth = imagem.getWidth();
            int originalHeight = imagem.getHeight();
            int maxWidth = 600;
            int maxHeight = 600;

            // Calcula o ratio de redimensionamento
            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double ratio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (originalWidth * ratio);
            int newHeight = (int) (originalHeight * ratio);

            // Redimensiona a imagem mantendo a proporção
            Image scaledImage = imagem.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            JLabel img = new JLabel(new ImageIcon(scaledImage));
            JOptionPane.showMessageDialog(null, img);

        } catch (Exception ex) {

        }

    }

    private static long save() {

        Cliente cliente = new Cliente();
        cliente.setNome("Bulma");
        cliente.setTelefone("1234-1234");
        cliente.setImagemPerfil(carregarImagem("bulma.jpg"));

        Optional<Cliente> c = clienteRepository.save(cliente);

        if (c.isPresent()) {

            System.out.println("Cadastro bem sucedido!");
            return c.get().getId();

        } else {

            System.out.println("Cadastro mal sucedido!");
            return 0;

        }

    }

    private static long update(long id) {

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Chi Chi");
        cliente.setTelefone("62 4567-4567");
        cliente.setImagemPerfil(carregarImagem("chichi.jpg"));

        if (clienteRepository.update(cliente).isPresent()) {

            System.out.println("Cadastro atualizado!");
            return cliente.getId();

        } else {

            System.out.println("Cadastro não atualizado!");
            return 0;

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

    private static void getById(long id) {

        Optional<Cliente> cliente1 = clienteRepository.getById(id);

        if (cliente1.isPresent()) {

            System.out.println(cliente1.get().getNome());
            exibirImagem(cliente1.get());

        }

    }

    private static void remove(long id) {

        if (clienteRepository.remove(id)) {

            System.out.println("Registro removido!!!");

        } else {

            System.out.println("Registro não removido!!!");

        }

    }

    public static void main(String[] args) {

        long id = save();
        getById(id);
        id = update(id);
        getById(id);
        jpaUtil.close();
        System.exit(0);

    }

}
