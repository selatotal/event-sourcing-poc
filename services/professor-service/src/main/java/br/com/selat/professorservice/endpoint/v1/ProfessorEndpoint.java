package br.com.selat.professorservice.endpoint.v1;

import br.com.selat.professorservice.contract.v1.ProfessorService;
import br.com.selat.professorservice.contract.v1.input.ProfessorInput;
import br.com.selat.professorservice.contract.v1.output.ProfessorOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/professor")
public class ProfessorEndpoint {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorEndpoint(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ProfessorOutput getById(String id){
        return professorService.getById(id);
    }

    @PostMapping
    public ProfessorOutput create(@RequestBody ProfessorInput input){
        return professorService.create(input);
    }

    @PutMapping("/{id}")
    public ProfessorOutput update(String id, @RequestBody ProfessorInput input){
        return professorService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        professorService.delete(id);
    }

}
