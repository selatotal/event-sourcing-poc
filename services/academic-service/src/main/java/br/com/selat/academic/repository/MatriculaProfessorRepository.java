package br.com.selat.academic.repository;

import br.com.selat.academic.model.MatriculaProfessor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaProfessorRepository extends CrudRepository<MatriculaProfessor, String> {
}
