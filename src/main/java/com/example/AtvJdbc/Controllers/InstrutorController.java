package com.example.AtvJdbc.Controllers;

import com.example.AtvJdbc.Model.Instrutor;
import com.example.AtvJdbc.Services.InstrutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/instrutor")
public class InstrutorController {

    @Autowired
    InstrutorService instrutorService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Instrutor instrutor){
        instrutorService.salvar(instrutor);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Instrutor>> listar(){
        return ResponseEntity.ok(instrutorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> listarId(@PathVariable Long id){
        return ResponseEntity.ok(instrutorService.listarPorId(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Instrutor instrutor){
        instrutor.setId(id);
        instrutorService.atualizar(instrutor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        instrutorService.deletar(id);
        return ResponseEntity.ok().build();
    }

}
