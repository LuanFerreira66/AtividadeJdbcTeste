package com.example.AtvJdbc.Services;

import com.example.AtvJdbc.Model.Curso;
import com.example.AtvJdbc.Repositorios.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public void salvar(Curso curso){
        cursoRepository.save(curso);
    }

    public List<Curso> listarTodos(){
        return cursoRepository.findAll();
    }

    public void salvarBatch(List<Curso> cursos){
        cursoRepository.saveBatch(cursos);
    }

    public Curso listarPorId(Long id){
        return cursoRepository.findById(id);
    }

    public List<Curso> listarPorTitulo(String titulo){
        return cursoRepository.findByTitle(titulo);
    }

    public List<Curso> listarPorDuracao(Double duracaoMin, Double duracaoMax){
        return cursoRepository.findByDuracao(duracaoMin,duracaoMax);
    }

    public List<Curso> listarPorInstrutor(Long id){
        return cursoRepository.findByInstrutorId(id);
    }

    public void atualizar(Curso curso){
        cursoRepository.update(curso);
    }

    public void deletar(Long id){
        cursoRepository.delete(id);
    }
}
