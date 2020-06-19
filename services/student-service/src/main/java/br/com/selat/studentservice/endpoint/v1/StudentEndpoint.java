package br.com.selat.studentservice.endpoint.v1;

import br.com.selat.studentservice.contract.v1.StudentService;
import br.com.selat.studentservice.contract.v1.input.StudentInput;
import br.com.selat.studentservice.contract.v1.output.StudentOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/student")
public class StudentEndpoint {

    private final br.com.selat.studentservice.contract.v1.StudentService studentService;

    @Autowired
    public StudentEndpoint(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public StudentOutput getById(String id){
        return studentService.getById(id);
    }

    @PostMapping
    public StudentOutput create(@RequestBody StudentInput input){
        return studentService.create(input);
    }

    @PutMapping("/{id}")
    public StudentOutput update(String id, @RequestBody StudentInput input){
        return studentService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        studentService.delete(id);
    }

}
