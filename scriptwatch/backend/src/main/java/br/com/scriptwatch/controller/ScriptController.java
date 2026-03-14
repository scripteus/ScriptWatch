package br.com.scriptwatch.controller;

import br.com.scriptwatch.dto.ScriptDTO;
import br.com.scriptwatch.service.ScriptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller para CRUD de scripts.
 * Base path: /api/scripts
 */
@RestController
@RequestMapping("/api/scripts")
@CrossOrigin(origins = "*")
public class ScriptController {

    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping
    public ResponseEntity<ScriptDTO> create(@Valid @RequestBody ScriptDTO dto) {
        ScriptDTO created = scriptService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ScriptDTO>> findAll() {
        return ResponseEntity.ok(scriptService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScriptDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(scriptService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScriptDTO> update(@PathVariable Long id, @Valid @RequestBody ScriptDTO dto) {
        return ResponseEntity.ok(scriptService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scriptService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
