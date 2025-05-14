package com.example.AtvJdbc.Repositorios;

import com.example.AtvJdbc.Model.Curso;
import com.example.AtvJdbc.Model.Instrutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InstrutorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Create
    public void save(Instrutor instrutor){
        String sql = "INSERT INTO instrutor (nome , email) VALUES (? , ?)";
        jdbcTemplate.update(sql,instrutor.getNome(),instrutor.getEmail());
    }

    //Read (todos)
    public List<Instrutor> findAll(){
        String sql = "SELECT id , nome , email FROM instrutor";
        return jdbcTemplate.query(sql, this::mapRowToInstrutor);
    }

    //Read id
    public Instrutor findById(Long id){
        String sql = "SELECT id, nome , email FROM instrutor WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,this::mapRowToInstrutor,id);
    }

    //Update
    public void update(Instrutor instrutor){
        String sql = "UPDATE instrutor SET nome = ? , email = ? ";
        jdbcTemplate.update(sql,instrutor.getNome(),instrutor.getEmail());
    }

    //Delete
    public void delete(Long id){
        String sql = "DELETE FROM instrutor WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    //Mapeamento Instrutor
    private Instrutor mapRowToInstrutor(ResultSet rs, int rowNum) throws SQLException {
        return new Instrutor(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("nome")
        );
    }
}
