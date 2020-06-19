package br.com.selat.classroomservice.endpoint.v1;

import br.com.selat.classroomservice.contract.v1.ClassroomService;
import br.com.selat.classroomservice.contract.v1.input.ClassroomInput;
import br.com.selat.classroomservice.contract.v1.output.ClassroomOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/classroom")
public class ClassroomEndpoint {

    private final ClassroomService studentService;

    @Autowired
    public ClassroomEndpoint(ClassroomService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ClassroomOutput getById(String id){
        return studentService.getById(id);
    }

    @PostMapping
    public ClassroomOutput create(@RequestBody ClassroomInput input){
        return studentService.create(input);
    }

    @PutMapping("/{id}")
    public ClassroomOutput update(String id, @RequestBody ClassroomInput input){
        return studentService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        studentService.delete(id);
    }

}
