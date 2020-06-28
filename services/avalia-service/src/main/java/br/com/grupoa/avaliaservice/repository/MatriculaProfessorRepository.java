package br.com.grupoa.avaliaservice.repository;

import br.com.grupoa.avaliaservice.model.MatriculaAlunoEntity;
import br.com.grupoa.avaliaservice.model.MatriculaProfessorEntity;
import org.springframework.data.repository.CrudRepository;

public interface MatriculaProfessorRepository extends CrudRepository<MatriculaProfessorEntity, String> {
}
