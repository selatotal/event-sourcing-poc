package br.com.selat.studentservice.contract.v1;

import br.com.selat.studentservice.contract.v1.input.StudentInput;
import br.com.selat.studentservice.contract.v1.output.StudentOutput;

public interface StudentService {

    StudentOutput getById(String id);
    StudentOutput create(StudentInput input);
    StudentOutput update(String id, StudentInput input);
    void delete(String id);
}
