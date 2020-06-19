package br.com.selat.classroomservice.repository;

import br.com.selat.classroomservice.model.Professor;
import br.com.selat.classroomservice.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
}
