package br.com.grupoa.academic.endpoint.v1;


import br.com.grupoa.academic.model.Aluno;
import br.com.grupoa.academic.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/aluno")
public class AlunoEndpoint {

    private final AlunoService alunoService;

    @Autowired
    public AlunoEndpoint(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<Aluno> getAll(){
        return alunoService.getAll();
    }

    @GetMapping("/{id}")
    public Aluno getById(String id){
        return alunoService.getById(id);
    }

    @PostMapping
    public Aluno create(@RequestBody Aluno input){
        return alunoService.create(input);
    }

    @PutMapping("/{id}")
    public Aluno update(String id, @RequestBody Aluno input){
        return alunoService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        alunoService.delete(id);
    }

}
