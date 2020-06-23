package br.com.selat.classroomservice.endpoint.v1;

import br.com.selat.classroomservice.contract.v1.CourseService;
import br.com.selat.classroomservice.contract.v1.input.CourseInput;
import br.com.selat.classroomservice.contract.v1.output.CourseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/course")
public class CourseEndpoint {

    private final CourseService courseService;

    @Autowired
    public CourseEndpoint(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public CourseOutput getById(String id){
        return courseService.getById(id);
    }

    @PostMapping
    public CourseOutput create(@RequestBody CourseInput input){
        return courseService.create(input);
    }

    @PutMapping("/{id}")
    public CourseOutput update(String id, @RequestBody CourseInput input){
        return courseService.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(String id){
        courseService.delete(id);
    }

}
