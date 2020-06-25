package br.com.grupoa.academic.repository;

import br.com.grupoa.academic.model.MatriculaProfessor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaProfessorRepository extends CrudRepository<MatriculaProfessor, String> {
    List<MatriculaProfessor> findAll();
}
