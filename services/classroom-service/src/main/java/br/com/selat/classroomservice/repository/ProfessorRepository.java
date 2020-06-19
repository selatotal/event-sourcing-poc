package br.com.selat.classroomservice.repository;

import br.com.selat.classroomservice.model.Classroom;
import br.com.selat.classroomservice.model.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String> {
}
