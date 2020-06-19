package br.com.selat.professorservice.repository;

import br.com.selat.professorservice.model.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {
}
