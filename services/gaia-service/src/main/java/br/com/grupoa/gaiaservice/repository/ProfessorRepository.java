package br.com.grupoa.gaiaservice.repository;

import br.com.grupoa.gaiaservice.model.ProfessorEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<ProfessorEntity, String> {
}
