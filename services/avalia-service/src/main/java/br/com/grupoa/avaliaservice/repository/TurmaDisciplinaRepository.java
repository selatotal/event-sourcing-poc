package br.com.grupoa.avaliaservice.repository;

import br.com.grupoa.avaliaservice.model.GradeDisciplinaEntity;
import br.com.grupoa.avaliaservice.model.TurmaDisciplinaEntity;
import org.springframework.data.repository.CrudRepository;

public interface TurmaDisciplinaRepository extends CrudRepository<TurmaDisciplinaEntity, String> {
}
