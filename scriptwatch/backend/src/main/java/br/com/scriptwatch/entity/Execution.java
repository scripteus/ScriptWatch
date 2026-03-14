package br.com.scriptwatch.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma execução de script.
 * Cada registro representa uma corrida de um script em um determinado momento.
 * Relacionamento: muitos executions para um script.
 */
@Entity
@Table(name = "executions")
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "script_id", nullable = false)
    private Long scriptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id", insertable = false, updatable = false)
    private Script script;

    @Column(nullable = false)
    private LocalDateTime inicio;

    private LocalDateTime termino;

    @Column(name = "duracao_ms")
    private Long duracaoMs;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    @Column(name = "mensagem_erro", columnDefinition = "TEXT")
    private String mensagemErro;

    @Column(name = "log_execucao", columnDefinition = "TEXT")
    private String logExecucao;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum ExecutionStatus {
        SUCESSO,
        ERRO
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScriptId() { return scriptId; }
    public void setScriptId(Long scriptId) { this.scriptId = scriptId; }

    public Script getScript() { return script; }
    public void setScript(Script script) { this.script = script; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getTermino() { return termino; }
    public void setTermino(LocalDateTime termino) { this.termino = termino; }

    public Long getDuracaoMs() { return duracaoMs; }
    public void setDuracaoMs(Long duracaoMs) { this.duracaoMs = duracaoMs; }

    public ExecutionStatus getStatus() { return status; }
    public void setStatus(ExecutionStatus status) { this.status = status; }

    public String getMensagemErro() { return mensagemErro; }
    public void setMensagemErro(String mensagemErro) { this.mensagemErro = mensagemErro; }

    public String getLogExecucao() { return logExecucao; }
    public void setLogExecucao(String logExecucao) { this.logExecucao = logExecucao; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
