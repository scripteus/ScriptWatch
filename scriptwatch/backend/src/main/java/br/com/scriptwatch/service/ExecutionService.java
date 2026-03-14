package br.com.scriptwatch.service;

import br.com.scriptwatch.dto.DashboardStatsDTO;
import br.com.scriptwatch.dto.ExecutionDTO;
import br.com.scriptwatch.entity.Execution;
import br.com.scriptwatch.entity.Execution.ExecutionStatus;
import br.com.scriptwatch.entity.Script;
import br.com.scriptwatch.repository.ExecutionRepository;
import br.com.scriptwatch.repository.ScriptRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de negócio para Execution e estatísticas do dashboard.
 */
@Service
public class ExecutionService {

    private final ExecutionRepository executionRepository;
    private final ScriptRepository scriptRepository;

    public ExecutionService(ExecutionRepository executionRepository, ScriptRepository scriptRepository) {
        this.executionRepository = executionRepository;
        this.scriptRepository = scriptRepository;
    }

    @Transactional
    public ExecutionDTO create(ExecutionDTO dto) {
        if (!scriptRepository.existsById(dto.getScriptId())) {
            throw new RuntimeException("Script não encontrado: " + dto.getScriptId());
        }
        Execution entity = toEntity(dto);
        // Calcula duração se término informado
        if (entity.getTermino() != null && entity.getInicio() != null) {
            long ms = java.time.Duration.between(entity.getInicio(), entity.getTermino()).toMillis();
            entity.setDuracaoMs(ms);
        }
        entity = executionRepository.save(entity);
        return toDTO(entity);
    }

    public List<ExecutionDTO> findAll(int limit) {
        return executionRepository.findAll(PageRequest.of(0, limit > 0 ? limit : 100))
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExecutionDTO findById(Long id) {
        Execution entity = executionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Execução não encontrada: " + id));
        return toDTO(entity);
    }

    public List<ExecutionDTO> findByScriptId(Long scriptId) {
        return executionRepository.findByScriptIdOrderByInicioDesc(scriptId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna estatísticas para o dashboard.
     */
    public DashboardStatsDTO getDashboardStats() {
        LocalDateTime inicioHoje = LocalDate.now().atStartOfDay();
        LocalDateTime fimHoje = LocalDate.now().atTime(LocalTime.MAX);

        long execucoesHoje = executionRepository.countByInicioBetween(inicioHoje, fimHoje);
        long errosHoje = executionRepository.countByInicioBetweenAndStatus(inicioHoje, fimHoje, ExecutionStatus.ERRO);

        Double avgMs = executionRepository.getAvgDuracaoMs();
        double tempoMedio = (avgMs != null) ? avgMs : 0.0;

        List<DashboardStatsDTO.ScriptCountDTO> topScripts = executionRepository
                .findTopScriptsByExecutionCount(inicioHoje)
                .stream()
                .map(row -> new DashboardStatsDTO.ScriptCountDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .collect(Collectors.toList());

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setExecucoesHoje(execucoesHoje);
        stats.setErrosHoje(errosHoje);
        stats.setScriptsMaisExecutados(topScripts);
        stats.setTempoMedioExecucaoMs(tempoMedio);
        return stats;
    }

    /**
     * Retorna lista de scripts com última execução para monitoramento.
     */
    public List<ExecutionDTO> getMonitoramentoScripts() {
        List<Script> scripts = scriptRepository.findAll();
        return scripts.stream()
                .map(s -> {
                    List<Execution> execs = executionRepository.findByScriptIdOrderByInicioDesc(
                            s.getId(), PageRequest.of(0, 1));
                    ExecutionDTO dto = new ExecutionDTO();
                    dto.setScriptId(s.getId());
                    dto.setScriptNome(s.getNome());
                    if (!execs.isEmpty()) {
                        Execution e = execs.get(0);
                        dto.setId(e.getId());
                        dto.setInicio(e.getInicio());
                        dto.setStatus(e.getStatus());
                        dto.setDuracaoMs(e.getDuracaoMs());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Execution toEntity(ExecutionDTO dto) {
        Execution e = new Execution();
        e.setScriptId(dto.getScriptId());
        e.setInicio(dto.getInicio() != null ? dto.getInicio() : LocalDateTime.now());
        e.setTermino(dto.getTermino());
        e.setDuracaoMs(dto.getDuracaoMs());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : ExecutionStatus.SUCESSO);
        e.setMensagemErro(dto.getMensagemErro());
        e.setLogExecucao(dto.getLogExecucao());
        return e;
    }

    private ExecutionDTO toDTO(Execution entity) {
        ExecutionDTO dto = new ExecutionDTO();
        dto.setId(entity.getId());
        dto.setScriptId(entity.getScriptId());
        if (entity.getScript() != null) {
            dto.setScriptNome(entity.getScript().getNome());
        } else {
            scriptRepository.findById(entity.getScriptId()).ifPresent(s -> dto.setScriptNome(s.getNome()));
        }
        dto.setInicio(entity.getInicio());
        dto.setTermino(entity.getTermino());
        dto.setDuracaoMs(entity.getDuracaoMs());
        dto.setStatus(entity.getStatus());
        dto.setMensagemErro(entity.getMensagemErro());
        dto.setLogExecucao(entity.getLogExecucao());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
