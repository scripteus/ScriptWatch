-- ============================================
-- ScriptWatch - Schema do Banco de Dados
-- PostgreSQL
-- ============================================

-- Remove tabelas se existirem (apenas para recriação limpa)
DROP TABLE IF EXISTS executions;
DROP TABLE IF EXISTS scripts;

-- ============================================
-- Tabela: scripts
-- Armazena os scripts cadastrados no sistema
-- ============================================
CREATE TABLE scripts (
    id              BIGSERIAL       PRIMARY KEY,
    nome            VARCHAR(255)    NOT NULL,
    descricao       TEXT,
    linguagem       VARCHAR(50)     NOT NULL,
    responsavel     VARCHAR(255)    NOT NULL,
    ativo           BOOLEAN         DEFAULT true,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

-- Índice para buscas por nome e status ativo
CREATE INDEX idx_scripts_nome ON scripts(nome);
CREATE INDEX idx_scripts_ativo ON scripts(ativo);

-- ============================================
-- Tabela: executions
-- Registra cada execução de um script
-- Relacionamento: scripts (1) → (N) executions
-- ============================================
CREATE TABLE executions (
    id              BIGSERIAL       PRIMARY KEY,
    script_id       BIGINT          NOT NULL,
    inicio          TIMESTAMP       NOT NULL,
    termino         TIMESTAMP,
    duracao_ms      BIGINT,
    status          VARCHAR(20)     NOT NULL CHECK (status IN ('SUCESSO', 'ERRO')),
    mensagem_erro   TEXT,
    log_execucao    TEXT,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_execution_script 
        FOREIGN KEY (script_id) REFERENCES scripts(id) ON DELETE CASCADE
);

-- Índices para consultas frequentes
CREATE INDEX idx_executions_script_id ON executions(script_id);
CREATE INDEX idx_executions_inicio ON executions(inicio);
CREATE INDEX idx_executions_status ON executions(status);

-- ============================================
-- Comentários nas tabelas
-- ============================================
COMMENT ON TABLE scripts IS 'Scripts cadastrados no sistema de monitoramento';
COMMENT ON TABLE executions IS 'Registro de execuções dos scripts';
COMMENT ON COLUMN executions.duracao_ms IS 'Duração em milissegundos';
