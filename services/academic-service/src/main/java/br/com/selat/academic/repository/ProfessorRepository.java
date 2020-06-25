package br.com.selat.academic.repository;

import br.com.selat.academic.model.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {
    List<Professor> findAll();
}
