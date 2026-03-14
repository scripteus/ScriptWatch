package br.com.scriptwatch.dto;

import java.util.List;

/**
 * DTO com estatísticas para o dashboard.
 */
public class DashboardStatsDTO {

    private long execucoesHoje;
    private long errosHoje;
    private List<ScriptCountDTO> scriptsMaisExecutados;
    private double tempoMedioExecucaoMs;

    public long getExecucoesHoje() { return execucoesHoje; }
    public void setExecucoesHoje(long execucoesHoje) { this.execucoesHoje = execucoesHoje; }

    public long getErrosHoje() { return errosHoje; }
    public void setErrosHoje(long errosHoje) { this.errosHoje = errosHoje; }

    public List<ScriptCountDTO> getScriptsMaisExecutados() { return scriptsMaisExecutados; }
    public void setScriptsMaisExecutados(List<ScriptCountDTO> scriptsMaisExecutados) {
        this.scriptsMaisExecutados = scriptsMaisExecutados;
    }

    public double getTempoMedioExecucaoMs() { return tempoMedioExecucaoMs; }
    public void setTempoMedioExecucaoMs(double tempoMedioExecucaoMs) {
        this.tempoMedioExecucaoMs = tempoMedioExecucaoMs;
    }

    public static class ScriptCountDTO {
        private Long scriptId;
        private String scriptNome;
        private long quantidade;

        public ScriptCountDTO(Long scriptId, String scriptNome, long quantidade) {
            this.scriptId = scriptId;
            this.scriptNome = scriptNome;
            this.quantidade = quantidade;
        }

        public Long getScriptId() { return scriptId; }
        public String getScriptNome() { return scriptNome; }
        public long getQuantidade() { return quantidade; }
    }
}
