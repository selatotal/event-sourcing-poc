package br.com.selat.professorservice.contract.v1;

import br.com.selat.professorservice.contract.v1.input.ProfessorInput;
import br.com.selat.professorservice.contract.v1.output.ProfessorOutput;

public interface ProfessorService {

    ProfessorOutput getById(String id);
    ProfessorOutput create(ProfessorInput input);
    ProfessorOutput update(String id, ProfessorInput input);
    void delete(String id);
}
