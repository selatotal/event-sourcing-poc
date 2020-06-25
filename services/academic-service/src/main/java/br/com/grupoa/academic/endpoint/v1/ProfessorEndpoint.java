package br.com.grupoa.academic.endpoint.v1;


import br.com.grupoa.academic.model.Professor;
import br.com.grupoa.academic.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/professor")
public class ProfessorEndpoint {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorEndpoint(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> getAll(){
        return professorService.getAll();
    }

    @GetMapping("/{id}")
    public Professor getById(String id){
        return professorService.getById(id);
    }

    @PostMapping
    public Professor create(@RequestBody Professor input){
        return professorService.create(input);
    }

    @PutMapping("/{id}")
    public Professor update(String id, @RequestBody Professor input){
        return professorService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        professorService.delete(id);
    }

}
