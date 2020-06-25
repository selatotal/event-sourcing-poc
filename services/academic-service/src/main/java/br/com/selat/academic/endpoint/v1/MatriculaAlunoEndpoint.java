package br.com.selat.academic.endpoint.v1;


import br.com.selat.academic.model.MatriculaAluno;
import br.com.selat.academic.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/matriculaAluno")
public class MatriculaAlunoEndpoint {

    private final MatriculaAlunoService matriculaAlunoService;

    @Autowired
    public MatriculaAlunoEndpoint(MatriculaAlunoService matriculaAlunoService) {
        this.matriculaAlunoService = matriculaAlunoService;
    }

    @GetMapping
    public List<MatriculaAluno> getAll(){
        return matriculaAlunoService.getAll();
    }

    @GetMapping("/{id}")
    public MatriculaAluno getById(String id){
        return matriculaAlunoService.getById(id);
    }

    @PostMapping
    public MatriculaAluno create(@RequestBody MatriculaAluno input){
        return matriculaAlunoService.create(input);
    }

    @PutMapping("/{id}")
    public MatriculaAluno update(String id, @RequestBody MatriculaAluno input){
        return matriculaAlunoService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        matriculaAlunoService.delete(id);
    }

}
