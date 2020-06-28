package br.com.grupoa.avaliaservice.repository;

import br.com.grupoa.avaliaservice.model.MatriculaProfessorEntity;
import br.com.grupoa.avaliaservice.model.ProfessorEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<ProfessorEntity, String> {
}
