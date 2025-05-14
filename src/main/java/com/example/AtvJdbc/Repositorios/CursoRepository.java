package com.example.AtvJdbc.Repositorios;

import com.example.AtvJdbc.Model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create
    @Transactional(rollbackFor = Exception.class)
    public void save(Curso curso) {
        String sql = "INSERT INTO curso (titulo, duracaoHoras, instrutorId) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, curso.getTitulo());
            ps.setDouble(2, curso.getDuracaoHoras());
            ps.setObject(3, curso.getInstrutorId()); // pode ser null
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        // Atualiza o objeto com o ID gerado
        if (generatedId != null) {
            curso.setId(generatedId.longValue());
        }
        //inserir log
        saveLog(curso);
        //colocar exceção para caso precisar de um rollback
        if (curso.getDuracaoHoras() < 0) {
            throw new IllegalArgumentException("Duração negativa não é permitida.");
        }
    }

    //Cria log
    public void saveLog(Curso curso) {
        String sql = "INSERT INTO logCurso (cursoId, titulo, dataCriacao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,curso.getId(),curso.getTitulo(), LocalDateTime.now());
    }

    //Salvar em batch
    public void saveBatch(List<Curso> cursos){
        String sql = "INSERT INTO curso (titulo, duracaoHoras, instrutorId) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Curso c = cursos.get(i);
                ps.setString(1,c.getTitulo());
                ps.setDouble(2,c.getDuracaoHoras());
                ps.setLong(3,c.getInstrutorId());
            }

            @Override
            public int getBatchSize() {
                return cursos.size();
            }
        });
    }

    // Read (todos)
    public List<Curso> findAll() {
        String sql = "SELECT id, titulo, duracaoHoras, instrutorId FROM curso";
        return jdbcTemplate.query(sql, this::mapRowToCurso);
    }
    // Read (por ID)
    public Curso findById(Long id) {
        String sql = "SELECT id, titulo, duracaoHoras, instrutorId FROM curso WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToCurso, id);
    }

    //Read por titulo parcial - case sensitive
    public List<Curso> findByTitle(String titulo){
        String sql = "SELECT * FROM curso WHERE LOWER(titulo) LIKE CONCAT('%',LOWER(?),'%')";
        return jdbcTemplate.query(sql,this::mapRowToCurso,titulo);
    }

    //Read por duracao horas com min - max
    //Nota colocar null caso não tenha uma dura min or max
    public List<Curso> findByDuracao(Double duracaoMin, Double duracaoMax){
        StringBuilder sql = new StringBuilder("SELECT * FROM curso WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (duracaoMin != null) {
            sql.append(" AND duracaoHoras >= ?");
            params.add(duracaoMin);
        }

        if (duracaoMax != null) {
            sql.append(" AND duracaoHoras <= ?");
            params.add(duracaoMax);
        }

        return jdbcTemplate.query(sql.toString(), this::mapRowToCurso, params.toArray());

    }

    //Read instrutor Id
    public List<Curso> findByInstrutorId(Long id){
        String sql = "SELECT * FROM curso WHERE instrutorId = ?";
        return jdbcTemplate.query(sql,this::mapRowToCurso,id);
    }

    // Update
    public void update(Curso curso) {
        String sql = "UPDATE curso SET titulo = ?, duracaoHoras = ? WHERE id = ?";
        jdbcTemplate.update(sql, curso.getTitulo() , curso.getDuracaoHoras() , curso.getId());
    }


    // Delete
    public void delete(Long id) {
        // Primeiro, deleta os logs associados ao curso
        String sqlLog = "DELETE FROM logCurso WHERE cursoId = ?";
        jdbcTemplate.update(sqlLog, id);

        // Depois, deleta o curso
        String sqlCurso = "DELETE FROM curso WHERE id = ?";
        jdbcTemplate.update(sqlCurso, id);
    }

    // Mapeamento de ResultSet para Cursos
    private Curso mapRowToCurso(ResultSet rs, int rowNum) throws SQLException {
        return new Curso(
                rs.getLong("id"),
                rs.getDouble("duracaoHoras"),
                rs.getString("titulo"),
                rs.getLong("instrutorId")
        );
    }

}
