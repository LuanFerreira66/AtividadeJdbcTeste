package com.example.AtvJdbc;

import com.example.AtvJdbc.Model.Curso;
import com.example.AtvJdbc.Model.Instrutor;
import com.example.AtvJdbc.Repositorios.CursoRepository;
import com.example.AtvJdbc.Repositorios.InstrutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoRepositoryTest {
    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void testeSaveAndFind() {
        // Salva instrutor
        Instrutor instrutor = new Instrutor(null, "email@email.com", "teste");
        instrutorRepository.save(instrutor);

        // Garante que o ID foi atribuído
        assertNotNull(instrutor.getId());

        // Salva curso com ID do instrutor criado
        Curso curso = new Curso(null, 30.0, "TesteCurso", instrutor.getId());
        cursoRepository.save(curso);

        // Busca curso
        List<Curso> cursos = cursoRepository.findAll();
        assertFalse(cursos.isEmpty());

        // Verifica se o curso salvo tem os dados corretos
        Curso cursoSalvo = cursos.get(0);
        assertEquals("TesteCurso", cursoSalvo.getTitulo());
        assertEquals(30.0, cursoSalvo.getDuracaoHoras());
        assertEquals(instrutor.getId(), cursoSalvo.getInstrutorId());
    }
}
