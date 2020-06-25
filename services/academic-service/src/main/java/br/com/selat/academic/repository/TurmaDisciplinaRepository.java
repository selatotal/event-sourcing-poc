package br.com.selat.academic.repository;

import br.com.selat.academic.model.TurmaDisciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaDisciplinaRepository extends CrudRepository<TurmaDisciplina, String> {
}
