package br.com.selat.academic.endpoint.v1;

import br.com.selat.academic.model.GradeDisciplina;
import br.com.selat.academic.service.GradeDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/gradeDisciplina")
public class GradeDisciplinaEndpoint {

    private final GradeDisciplinaService gradeDisciplinaService;

    @Autowired
    public GradeDisciplinaEndpoint(GradeDisciplinaService gradeDisciplinaService) {
        this.gradeDisciplinaService = gradeDisciplinaService;
    }

    @GetMapping
    public GradeDisciplina getById(String id){
        return gradeDisciplinaService.getById(id);
    }

    @PostMapping
    public GradeDisciplina create(@RequestBody GradeDisciplina input){
        return gradeDisciplinaService.create(input);
    }

    @PutMapping("/{id}")
    public GradeDisciplina update(String id, @RequestBody GradeDisciplina input){
        return gradeDisciplinaService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        gradeDisciplinaService.delete(id);
    }

}
