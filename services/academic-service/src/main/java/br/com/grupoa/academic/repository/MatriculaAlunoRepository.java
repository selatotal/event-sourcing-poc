package br.com.grupoa.academic.repository;

import br.com.grupoa.academic.model.MatriculaAluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaAlunoRepository extends CrudRepository<MatriculaAluno, String> {
    List<MatriculaAluno> findAll();
}
