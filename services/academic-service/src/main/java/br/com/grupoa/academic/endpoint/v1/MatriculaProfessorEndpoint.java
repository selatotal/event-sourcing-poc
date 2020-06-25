package br.com.grupoa.academic.endpoint.v1;


import br.com.grupoa.academic.model.MatriculaProfessor;
import br.com.grupoa.academic.service.MatriculaProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/matriculaProfessor")
public class MatriculaProfessorEndpoint {

    private final MatriculaProfessorService matriculaProfessorService;

    @Autowired
    public MatriculaProfessorEndpoint(MatriculaProfessorService matriculaProfessorService) {
        this.matriculaProfessorService = matriculaProfessorService;
    }

    @GetMapping
    public List<MatriculaProfessor> getAll(){
        return matriculaProfessorService.getAll();
    }

    @GetMapping("/{id}")
    public MatriculaProfessor getById(String id){
        return matriculaProfessorService.getById(id);
    }

    @PostMapping
    public MatriculaProfessor create(@RequestBody MatriculaProfessor input){
        return matriculaProfessorService.create(input);
    }

    @PutMapping("/{id}")
    public MatriculaProfessor update(String id, @RequestBody MatriculaProfessor input){
        return matriculaProfessorService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        matriculaProfessorService.delete(id);
    }

}
