# Lista de Tarefas - Domain Driven Design com JDBC e Quarkus

> 📚 Projeto educacional desenvolvido para demonstrar a aplicação prática dos conceitos de **Domain Driven Design (DDD)** utilizando **JDBC puro** e **Quarkus Framework**.

## 🎯 Sobre o Projeto

Este projeto implementa um **CRUD completo de gerenciamento de tarefas** (Task Manager) seguindo rigorosamente os princípios do Domain Driven Design. O objetivo é demonstrar como construir uma aplicação bem arquitetada, separando claramente as responsabilidades entre domínio, infraestrutura e aplicação.

### Por que este projeto é importante?

- **Aprender os fundamentos**: Utiliza JDBC puro para entender os conceitos básicos antes de abstrações como JPA
- **Arquitetura limpa**: Demonstra separação de camadas e inversão de dependências
- **Prática hands-on**: Código funcional que pode ser executado e testado imediatamente
- **Base sólida**: Fundação para evoluir para conceitos mais avançados

## 🏗️ Arquitetura em Camadas

O projeto está organizado seguindo os princípios do DDD:

```
src/main/java/com/exemplo/tarefas/
│
├── domain/                    # 🧠 Camada de Domínio
│   ├── Task.java             # Entidade: representa uma tarefa no contexto de negócio
│   └── TaskRepository.java   # Interface: contrato definido pelo domínio
│
├── infrastructure/            # 🔧 Camada de Infraestrutura
│   ├── TaskRepositoryJDBC.java    # Implementação com JDBC
│   ├── DatabaseConfig.java        # Configuração de conexão
│   └── DatabaseInitializer.java   # Inicialização do banco
│
└── application/               # 🌐 Camada de Aplicação
    └── TaskResource.java     # API REST: expõe funcionalidades
```

## 🚀 Tecnologias Utilizadas

- **Java 17+**: Linguagem de programação
- **Quarkus 3.x**: Framework supersônico e subatômico
- **JDBC**: API Java para acesso a banco de dados
- **H2 Database**: Banco em memória para desenvolvimento
- **Gradle**: Gerenciamento de dependências e build
- **Jakarta REST (JAX-RS)**: APIs RESTful
- **CDI (Contexts and Dependency Injection)**: Injeção de dependências

## 📦 Estrutura do Banco de Dados

```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL
);
```

### Status Possíveis
- `PENDENTE`: tarefa criada mas não concluída
- `CONCLUIDA`: tarefa finalizada

## 🎮 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Gradle (ou use o wrapper incluído)

### Executar em Modo Dev (Hot Reload)
```bash
./gradlew quarkusDev
```

A aplicação estará disponível em: `http://localhost:8080`

### Build para Produção
```bash
./gradlew build
java -jar build/quarkus-app/quarkus-run.jar
```