package br.com.grupoa.gaiaservice.repository;

import br.com.grupoa.gaiaservice.model.AlunoEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends CrudRepository<AlunoEntity, String> {
}
