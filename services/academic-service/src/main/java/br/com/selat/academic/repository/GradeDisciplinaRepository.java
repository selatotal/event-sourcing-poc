package br.com.selat.academic.repository;

import br.com.selat.academic.model.GradeDisciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeDisciplinaRepository extends CrudRepository<GradeDisciplina, String> {
}
