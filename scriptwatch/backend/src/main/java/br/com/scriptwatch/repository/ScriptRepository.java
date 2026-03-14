package br.com.scriptwatch.repository;

import br.com.scriptwatch.entity.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório de acesso aos dados da tabela scripts.
 */
@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    List<Script> findByAtivoTrue();
}
