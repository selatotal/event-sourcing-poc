package br.com.grupoa.academic.repository;

import br.com.grupoa.academic.model.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {
    List<Professor> findAll();
}
