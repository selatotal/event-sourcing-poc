package br.com.grupoa.academic.repository;

import br.com.grupoa.academic.model.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, String> {
    List<Aluno> findAll();

}
