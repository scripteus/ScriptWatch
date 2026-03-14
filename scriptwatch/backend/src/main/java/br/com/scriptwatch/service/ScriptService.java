package br.com.scriptwatch.service;

import br.com.scriptwatch.dto.ScriptDTO;
import br.com.scriptwatch.entity.Script;
import br.com.scriptwatch.repository.ScriptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de negócio para Script.
 * Converte entidades em DTOs e vice-versa.
 */
@Service
public class ScriptService {

    private final ScriptRepository scriptRepository;

    public ScriptService(ScriptRepository scriptRepository) {
        this.scriptRepository = scriptRepository;
    }

    @Transactional
    public ScriptDTO create(ScriptDTO dto) {
        Script entity = toEntity(dto);
        entity = scriptRepository.save(entity);
        return toDTO(entity);
    }

    public List<ScriptDTO> findAll() {
        return scriptRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ScriptDTO findById(Long id) {
        Script entity = scriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Script não encontrado: " + id));
        return toDTO(entity);
    }

    @Transactional
    public ScriptDTO update(Long id, ScriptDTO dto) {
        Script entity = scriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Script não encontrado: " + id));
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setLinguagem(dto.getLinguagem());
        entity.setResponsavel(dto.getResponsavel());
        if (dto.getAtivo() != null) {
            entity.setAtivo(dto.getAtivo());
        }
        entity = scriptRepository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!scriptRepository.existsById(id)) {
            throw new RuntimeException("Script não encontrado: " + id);
        }
        scriptRepository.deleteById(id);
    }

    private Script toEntity(ScriptDTO dto) {
        Script s = new Script();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLinguagem(dto.getLinguagem());
        s.setResponsavel(dto.getResponsavel());
        s.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return s;
    }

    private ScriptDTO toDTO(Script entity) {
        return new ScriptDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getLinguagem(),
                entity.getResponsavel(),
                entity.getAtivo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
