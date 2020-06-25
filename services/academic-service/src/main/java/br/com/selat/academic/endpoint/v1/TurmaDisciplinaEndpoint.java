package br.com.selat.academic.endpoint.v1;

import br.com.selat.academic.model.TurmaDisciplina;
import br.com.selat.academic.service.TurmaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/turmaDisciplina")
public class TurmaDisciplinaEndpoint {

    private final TurmaDisciplinaService turmaDisciplinaService;

    @Autowired
    public TurmaDisciplinaEndpoint(TurmaDisciplinaService turmaDisciplinaService) {
        this.turmaDisciplinaService = turmaDisciplinaService;
    }

    @GetMapping
    public TurmaDisciplina getById(String id){
        return turmaDisciplinaService.getById(id);
    }

    @PostMapping
    public TurmaDisciplina create(@RequestBody TurmaDisciplina input){
        return turmaDisciplinaService.create(input);
    }

    @PutMapping("/{id}")
    public TurmaDisciplina update(String id, @RequestBody TurmaDisciplina input){
        return turmaDisciplinaService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        turmaDisciplinaService.delete(id);
    }

}
