package br.com.scriptwatch.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados do Script.
 * Usado nas requisições e respostas da API.
 */
public class ScriptDTO {

    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String descricao;
    @NotBlank(message = "Linguagem é obrigatória")
    private String linguagem;
    @NotBlank(message = "Responsável é obrigatório")
    private String responsavel;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScriptDTO() {}

    public ScriptDTO(Long id, String nome, String descricao, String linguagem,
                     String responsavel, Boolean ativo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.linguagem = linguagem;
        this.responsavel = responsavel;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLinguagem() { return linguagem; }
    public void setLinguagem(String linguagem) { this.linguagem = linguagem; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
