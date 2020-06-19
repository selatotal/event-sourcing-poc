package br.com.selat.classroomservice.contract.v1;

import br.com.selat.classroomservice.contract.v1.input.ClassroomInput;
import br.com.selat.classroomservice.contract.v1.output.ClassroomOutput;

public interface ClassroomService {

    ClassroomOutput getById(String id);
    ClassroomOutput create(ClassroomInput input);
    ClassroomOutput update(String id, ClassroomInput input);
    void delete(String id);
}
