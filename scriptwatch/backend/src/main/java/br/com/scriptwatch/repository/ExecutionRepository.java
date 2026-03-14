package br.com.scriptwatch.repository;

import br.com.scriptwatch.entity.Execution;
import br.com.scriptwatch.entity.Execution.ExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório de acesso aos dados da tabela executions.
 */
@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    List<Execution> findByScriptIdOrderByInicioDesc(Long scriptId, org.springframework.data.domain.Pageable pageable);

    List<Execution> findByScriptIdOrderByInicioDesc(Long scriptId);

    long countByInicioBetween(LocalDateTime start, LocalDateTime end);

    long countByInicioBetweenAndStatus(LocalDateTime start, LocalDateTime end, ExecutionStatus status);

    @Query("SELECT AVG(e.duracaoMs) FROM Execution e WHERE e.duracaoMs IS NOT NULL")
    Double getAvgDuracaoMs();

    @Query(value = "SELECT e.script_id, s.nome, COUNT(*) FROM executions e " +
           "JOIN scripts s ON e.script_id = s.id WHERE e.inicio >= :inicio " +
           "GROUP BY e.script_id, s.nome ORDER BY COUNT(*) DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopScriptsByExecutionCount(@Param("inicio") LocalDateTime inicio);
}
