
# Projeto: Hibernate CRUD Básico

Este projeto demonstra um CRUD básico utilizando Hibernate com Maven e banco de dados MySQL.

---

## Requisitos básicos

- Java JDK 17 instalado  
- Maven instalado  
- MySQL instalado e rodando na porta `3307`  
- Banco de dados chamado `estudos` criado no MySQL  
- Usuário MySQL `salomao` com senha `123456` configurado e com permissão no banco `estudos`

---

## Etapa 01 - Configurar o `pom.xml`

O arquivo `pom.xml` gerencia as dependências do projeto, incluindo Hibernate e o driver do MySQL, além dos plugins para compilar e executar o projeto com Maven.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
    ...
    <dependencies>
        <!-- Hibernate -->
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.6.5.Final</version>
        </dependency>

        <!-- Driver MySQL -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Plugin de compilação -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>

            <!-- Plugin de execução -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.salomaotech.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## Etapa 02 - Configurar o arquivo `persistence.xml`

O arquivo `persistence.xml` configura a conexão com o banco MySQL, incluindo usuário, senha, URL e driver. Ele deve estar em `src/main/resources/META-INF/`.

```xml
<persistence version="2.2" ...>
    <persistence-unit name="Conexao" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3307/estudos?zeroDateTimeBehavior=CONVERT_TO_NULL" />
            <property name="javax.persistence.jdbc.user" value="salomao" />
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="javax.persistence.jdbc.password" value="123456" />
            <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create" />
        </properties>
    </persistence-unit>
</persistence>
```

> **Importante:** Certifique-se que o MySQL está ativo na porta `3307` e que o banco `estudos` existe. Caso use outra porta ou usuário, altere as propriedades adequadamente.

> **Importante:** Não use 'drop-and-create' em produção prefira update!

---

## Etapa 03 - Criar a classe de conexão `JpaUtil`

Esta classe cria e gerencia a instância do `EntityManager` que faz a conexão com o banco via Hibernate.

```java
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
```

---

## Etapa 04 - Criar a entidade `Cliente`

A entidade `Cliente` representa a tabela no banco de dados.

```java
package com.salomaotech.models;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String telefone;

    // Getters e setters omitidos para brevidade
}
```

---

## Etapa 05 - Criar a classe `Main` para rodar o projeto e cadastrar um cliente

Classe principal que instancia a conexão, cadastra um cliente e valida a conexão com o banco.

```java
package com.salomaotech;

import com.salomaotech.utils.JpaUtil;
import com.salomaotech.models.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Main {
    public static void main(String[] args) {
        JpaUtil jpaUtil = new JpaUtil();
        EntityManager em = jpaUtil.entityManager();

        System.out.println("Conexão com o banco realizada com sucesso!");

        // Iniciar transação para cadastrar um cliente
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setTelefone("99999-9999");

        em.persist(cliente);  // Salva o cliente no banco

        transaction.commit();  // Confirma a transação

        System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());

        em.close();
        jpaUtil.close();
    }
}
```

---

## Etapa 06 - Rodar o projeto

No terminal, na pasta do projeto, execute:

```bash
mvn compile exec:java
```

Isso compila o projeto, executa a classe principal, valida a conexão com o banco e cadastra um cliente.

---

Se todas as etapas forem seguidas corretamente, você terá um CRUD básico com Hibernate rodando localmente e já terá criado um registro no banco.

---
