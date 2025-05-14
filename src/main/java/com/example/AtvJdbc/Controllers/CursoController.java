package com.example.AtvJdbc.Controllers;

import com.example.AtvJdbc.Model.Curso;
import com.example.AtvJdbc.Services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Curso curso){
        cursoService.salvar(curso);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> criarBatch(@RequestBody List<Curso> cursos){
        cursoService.salvarBatch(cursos);
        return  ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.listarPorId(id));
    }

    @GetMapping("/duracao/{duracaoMin}/{duracaoMax}")
    public ResponseEntity<List<Curso>> buscar(
            @PathVariable Double duracaoMin,
            @PathVariable Double duracaoMax) {
        return ResponseEntity.ok(cursoService.listarPorDuracao(duracaoMin,duracaoMax));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Curso>> buscarPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(cursoService.listarPorTitulo(titulo));
    }

    @GetMapping("/instrutorId/{id}")
    public ResponseEntity<List<Curso>> buscarPorIdInstrutor(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.listarPorInstrutor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Curso curso){
        curso.setId(id);
        cursoService.atualizar(curso);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        cursoService.deletar(id);
        return ResponseEntity.ok().build();
    }

}
