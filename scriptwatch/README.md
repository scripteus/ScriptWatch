# ScriptWatch

**Sistema de Monitoramento de Execução de Scripts**

Plataforma para monitorar execução de scripts e automações dentro de uma empresa. Registra execuções, status, logs e métricas de scripts que rodam em servidores ou tarefas automatizadas.

---

## Stack

| Camada      | Tecnologia                  |
|-------------|-----------------------------|
| Backend     | Java 17, Spring Boot 3.x    |
| Banco       | PostgreSQL                  |
| Frontend    | HTML, JavaScript ES6+, Tailwind CSS |
| API         | REST (JSON)                 |

---

## Estrutura do Projeto

```
scriptwatch/
├── backend/           # API Spring Boot
├── frontend/          # Páginas HTML + JS
├── database/          # Schema SQL
├── docs/              # Documentação
└── README.md
```

---

## Pré-requisitos

- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL 14+**
- Navegador moderno

---

## Configuração do Banco

1. Crie o banco de dados:

```sql
CREATE DATABASE scriptwatch;
```

2. Execute o schema:

```bash
psql -U postgres -d scriptwatch -f database/schema.sql
```

3. Configure credenciais em `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/scriptwatch
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

---

## Executando o Backend

```bash
cd backend
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## Executando o Frontend

O frontend é estático. Basta abrir os arquivos HTML em um servidor HTTP local ou diretamente no navegador.

### Opção 1: Servidor Python

```bash
cd frontend
python -m http.server 3000
```

Acesse: `http://localhost:3000`

### Opção 2: Extensão Live Server (VS Code)

Clique com botão direito em `index.html` → "Open with Live Server".

### Opção 3: Abrir direto

Abra `frontend/dashboard.html` no navegador. Pode haver restrições de CORS; use um servidor local recomendado.

---

## Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST   | `/api/scripts` | Cadastrar script |
| GET    | `/api/scripts` | Listar scripts |
| GET    | `/api/scripts/{id}` | Buscar script |
| PUT    | `/api/scripts/{id}` | Atualizar script |
| DELETE | `/api/scripts/{id}` | Remover script |
| POST   | `/api/executions` | Registrar execução |
| GET    | `/api/executions` | Listar execuções |
| GET    | `/api/executions/{id}` | Detalhes da execução |
| GET    | `/api/executions/script/{id}` | Execuções de um script |
| GET    | `/api/dashboard/stats` | Estatísticas do dashboard |
| GET    | `/api/dashboard/monitoramento` | Monitoramento por script |

---

## Funcionalidades

1. **Cadastro de scripts** – Nome, descrição, linguagem, responsável, ativo
2. **Registro de execuções** – Início, término, duração, status (SUCESSO/ERRO), log
3. **Dashboard** – Execuções hoje, erros, scripts mais executados, tempo médio
4. **Monitoramento** – Lista com última execução de cada script
5. **Histórico** – Tabela de execuções
6. **Detalhes** – Log completo e mensagem de erro por execução

---

## Exemplo: Registrar execução via API

```bash
curl -X POST http://localhost:8080/api/executions \
  -H "Content-Type: application/json" \
  -d '{
    "scriptId": 1,
    "inicio": "2025-03-13T10:00:00",
    "termino": "2025-03-13T10:00:05",
    "status": "SUCESSO",
    "logExecucao": "Script iniciado...\nProcessamento OK\nFinalizado."
  }'
```

---

## Documentação

Consulte `docs/arquitetura.md` para visão geral da arquitetura e diagrama das tabelas.

---

## Licença

Projeto interno / educacional.
