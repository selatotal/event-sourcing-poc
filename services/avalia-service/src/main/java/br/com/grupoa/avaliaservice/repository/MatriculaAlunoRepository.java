package br.com.grupoa.avaliaservice.repository;

import br.com.grupoa.avaliaservice.model.GradeDisciplinaEntity;
import br.com.grupoa.avaliaservice.model.MatriculaAlunoEntity;
import org.springframework.data.repository.CrudRepository;

public interface MatriculaAlunoRepository extends CrudRepository<MatriculaAlunoEntity, String> {
}
