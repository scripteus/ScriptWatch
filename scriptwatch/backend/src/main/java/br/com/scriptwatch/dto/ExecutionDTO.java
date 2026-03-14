package br.com.scriptwatch.dto;

import br.com.scriptwatch.entity.Execution.ExecutionStatus;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados da Execution.
 */
public class ExecutionDTO {

    private Long id;
    private Long scriptId;
    private String scriptNome;  // Nome do script para exibição
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private Long duracaoMs;
    private ExecutionStatus status;
    private String mensagemErro;
    private String logExecucao;
    private LocalDateTime createdAt;

    public ExecutionDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScriptId() { return scriptId; }
    public void setScriptId(Long scriptId) { this.scriptId = scriptId; }

    public String getScriptNome() { return scriptNome; }
    public void setScriptNome(String scriptNome) { this.scriptNome = scriptNome; }

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
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
