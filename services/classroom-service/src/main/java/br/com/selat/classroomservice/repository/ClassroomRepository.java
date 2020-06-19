package br.com.selat.classroomservice.repository;

import br.com.selat.classroomservice.model.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, String> {
}
