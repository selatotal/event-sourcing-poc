package br.com.grupoa.avaliaservice.repository;

import br.com.grupoa.academic.model.Aluno;
import br.com.grupoa.avaliaservice.model.AlunoEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends CrudRepository<AlunoEntity, String> {
}
