package br.com.selat.classroomservice.contract.v1;

import br.com.selat.classroomservice.contract.v1.input.CourseInput;
import br.com.selat.classroomservice.contract.v1.output.CourseOutput;

public interface CourseService {

    CourseOutput getById(String id);
    CourseOutput create(CourseInput input);
    CourseOutput update(String id, CourseInput input);
    void delete(String id);
}
