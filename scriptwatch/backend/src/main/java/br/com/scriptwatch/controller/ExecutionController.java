package br.com.scriptwatch.controller;

import br.com.scriptwatch.dto.ExecutionDTO;
import br.com.scriptwatch.service.ExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller para execuções e dashboard.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/executions")
    public ResponseEntity<ExecutionDTO> create(@RequestBody ExecutionDTO dto) {
        ExecutionDTO created = executionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/executions")
    public ResponseEntity<List<ExecutionDTO>> findAll(
            @RequestParam(defaultValue = "100") int limit) {
        return ResponseEntity.ok(executionService.findAll(limit));
    }

    @GetMapping("/executions/{id}")
    public ResponseEntity<ExecutionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(executionService.findById(id));
    }

    @GetMapping("/executions/script/{scriptId}")
    public ResponseEntity<List<ExecutionDTO>> findByScriptId(@PathVariable Long scriptId) {
        return ResponseEntity.ok(executionService.findByScriptId(scriptId));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(executionService.getDashboardStats());
    }

    @GetMapping("/dashboard/monitoramento")
    public ResponseEntity<List<ExecutionDTO>> getMonitoramento() {
        return ResponseEntity.ok(executionService.getMonitoramentoScripts());
    }
}
