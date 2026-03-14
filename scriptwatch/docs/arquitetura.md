# ScriptWatch - Arquitetura do Sistema

## Visão Geral

O **ScriptWatch** é uma plataforma para monitoramento de execução de scripts e automações dentro de uma empresa. O sistema registra execuções, status, logs e métricas de scripts que rodam em servidores ou tarefas automatizadas.

---

## Arquitetura do Sistema

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           CLIENTE (Browser)                               │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                    Frontend (HTML + JavaScript + Tailwind)            │ │
│  │  • dashboard.html  • scripts.html  • executions.html  • details.html  │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                      │
                                      │ HTTP/REST (JSON)
                                      ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        Backend (Spring Boot)                              │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                         Controllers (REST API)                        │ │
│  │         ScriptController          │        ExecutionController        │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│                                      │                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                           Services (Lógica de Negócio)                │ │
│  │         ScriptService          │        ExecutionService              │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│                                      │                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                     Repositories (JPA/Data Access)                    │ │
│  │         ScriptRepository       │       ExecutionRepository            │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                      │
                                      │ JDBC
                                      ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         PostgreSQL Database                              │
│  ┌──────────────────┐              ┌──────────────────┐                  │
│  │     scripts      │  1     N     │   executions     │                  │
│  └──────────────────┘──────────────└──────────────────┘                  │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Camadas da Aplicação

| Camada | Responsabilidade |
|--------|------------------|
| **Controller** | Recebe requisições HTTP, valida entrada, delega para Service, retorna resposta |
| **Service** | Regras de negócio, orquestração, validações |
| **Repository** | Acesso a dados, operações CRUD via JPA |
| **Entity** | Mapeamento objeto-relacional com o banco |
| **DTO** | Objetos de transferência de dados entre camadas |

---

## Diagrama das Tabelas do Banco

```
┌──────────────────────────────────────────────────────────────┐
│                         SCRIPTS                               │
├──────────────────────────────────────────────────────────────┤
│  PK  │ id              │ BIGSERIAL                            │
│      │ nome            │ VARCHAR(255)    NOT NULL              │
│      │ descricao       │ TEXT                                 │
│      │ linguagem       │ VARCHAR(50)     NOT NULL              │
│      │ responsavel     │ VARCHAR(255)    NOT NULL              │
│      │ ativo           │ BOOLEAN         DEFAULT true          │
│      │ created_at      │ TIMESTAMP       DEFAULT NOW()         │
│      │ updated_at      │ TIMESTAMP       DEFAULT NOW()         │
└──────────────────────────────────────────────────────────────┘
        │
        │ 1
        │
        │ N
        ▼
┌──────────────────────────────────────────────────────────────┐
│                       EXECUTIONS                              │
├──────────────────────────────────────────────────────────────┤
│  PK  │ id              │ BIGSERIAL                            │
│  FK  │ script_id       │ BIGINT          NOT NULL  → scripts  │
│      │ inicio          │ TIMESTAMP       NOT NULL              │
│      │ termino         │ TIMESTAMP                             │
│      │ duracao_ms      │ BIGINT                                │
│      │ status          │ VARCHAR(20)     NOT NULL (SUCESSO/ERRO)│
│      │ mensagem_erro   │ TEXT                                 │
│      │ log_execucao    │ TEXT                                 │
│      │ created_at      │ TIMESTAMP       DEFAULT NOW()         │
└──────────────────────────────────────────────────────────────┘
```

### Relacionamento

- **scripts (1) → (N) executions**
- Um script pode ter muitas execuções
- Cada execução pertence a um único script
- FK `script_id` referencia `scripts.id` com `ON DELETE CASCADE` (opcional) ou `RESTRICT`

---

## Endpoints da API REST

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/scripts` | Cadastra novo script |
| GET | `/api/scripts` | Lista todos os scripts |
| GET | `/api/scripts/{id}` | Busca script por ID |
| PUT | `/api/scripts/{id}` | Atualiza script |
| DELETE | `/api/scripts/{id}` | Remove script |
| POST | `/api/executions` | Registra nova execução |
| GET | `/api/executions` | Lista execuções (com filtros) |
| GET | `/api/executions/{id}` | Busca execução por ID |
| GET | `/api/executions/script/{scriptId}` | Lista execuções de um script |
| GET | `/api/dashboard/stats` | Estatísticas para o dashboard |

---

## Fluxo de Dados

### Cadastro de Script
1. Frontend envia POST com JSON do script
2. Controller valida e chama ScriptService
3. Service salva no banco via ScriptRepository
4. Retorna script criado com ID

### Registro de Execução
1. Frontend ou sistema externo envia POST com dados da execução
2. ExecutionService calcula duração (se termino informado)
3. Persiste em executions
4. Retorna execução criada

### Dashboard
1. Frontend chama GET /api/dashboard/stats
2. Backend agrega dados (COUNT, AVG) das tabelas
3. Retorna JSON com métricas

---

## Estrutura de Pastas

```
scriptwatch/
├── backend/                    # Projeto Spring Boot
│   ├── src/main/java/.../
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   └── ScriptWatchApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── pom.xml
│   └── README (no root)
├── frontend/
│   ├── css/
│   ├── js/
│   ├── dashboard.html
│   ├── scripts.html
│   ├── executions.html
│   └── details.html
├── database/
│   └── schema.sql
├── docs/
│   └── arquitetura.md
└── README.md
```

---

## Tecnologias

| Componente | Tecnologia |
|------------|------------|
| Backend | Java 17+, Spring Boot 3.x |
| Banco | PostgreSQL 14+ |
| ORM | Spring Data JPA |
| Frontend | HTML5, JavaScript ES6+, Tailwind CSS |
| API | REST, JSON |
