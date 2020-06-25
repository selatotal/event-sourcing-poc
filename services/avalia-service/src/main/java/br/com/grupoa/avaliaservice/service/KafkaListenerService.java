package br.com.grupoa.avaliaservice.service;

import br.com.grupoa.avaliaservice.contract.v1.event.Event;
import br.com.grupoa.avaliaservice.model.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Component
public class KafkaListenerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private static final String INVALID_EVENT_TYPE_MESSAGE = "Invalid Event Type: %s";

    private final Gson gson = new Gson();

    @KafkaListener(topics = "#{'${kafka.listenEventTopics}'.split(',')}")
    @Transactional
    public void listenEventTopic(Event event){
        logger.info(format("Message received: %s", new Gson().toJson(event)));
        switch (event.getEntity()){
            case ALUNO:
                processAlunoEvent(event);
                break;
            case GRADE_DISCIPLINA:
                processGradeDisciplinaEvent(event);
                break;
            case MATRICULA_ALUNO:
                processMatriculaAlunoEvent(event);
                break;
            case MATRICULA_PROFESSOR:
                processMatriculaProfessorEvent(event);
                break;
            case PROFESSOR:
                processProfessorEvent(event);
                break;
            case TURMA_DISCIPLINA:
                processTurmaDisciplinaEvent(event);
                break;
            default:
                logger.error(format("Invalid Event Entity: %s", event.getEntity()));
        }
    }

    private void processAlunoEvent(Event event) {
        Aluno aluno = gson.fromJson(event.getPayload(), Aluno.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("Aluno Saved: " + new Gson().toJson(aluno));
                break;
            case DELETE:
                logger.info("Aluno Removed: " + new Gson().toJson(aluno));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

    private void processGradeDisciplinaEvent(Event event) {
        GradeDisciplina gradeDisciplina = gson.fromJson(event.getPayload(), GradeDisciplina.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("GradeDisciplina Saved: " + new Gson().toJson(gradeDisciplina));
                break;
            case DELETE:
                logger.info("GradeDisciplina Removed: " + new Gson().toJson(gradeDisciplina));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

    private void processMatriculaAlunoEvent(Event event) {
        MatriculaAluno matriculaAluno = gson.fromJson(event.getPayload(), MatriculaAluno.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("MatriculaAluno Saved: " + new Gson().toJson(matriculaAluno));
                break;
            case DELETE:
                logger.info("MatriculaAluno Removed: " + new Gson().toJson(matriculaAluno));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

    private void processMatriculaProfessorEvent(Event event) {
        MatriculaProfessor matriculaProfessor = gson.fromJson(event.getPayload(), MatriculaProfessor.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("MatriculaProfessor Saved: " + new Gson().toJson(matriculaProfessor));
                break;
            case DELETE:
                logger.info("MatriculaProfessor Removed: " + new Gson().toJson(matriculaProfessor));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

    private void processProfessorEvent(Event event) {
        Professor professor = gson.fromJson(event.getPayload(), Professor.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("Professor Saved: " + new Gson().toJson(professor));
                break;
            case DELETE:
                logger.info("Professor Removed: " + new Gson().toJson(professor));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

    private void processTurmaDisciplinaEvent(Event event) {
        TurmaDisciplina turmaDisciplina = gson.fromJson(event.getPayload(), TurmaDisciplina.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                logger.info("TurmaDisciplina Saved: " + new Gson().toJson(turmaDisciplina));
                break;
            case DELETE:
                logger.info("TurmaDisciplina Removed: " + new Gson().toJson(turmaDisciplina));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }
    }

}

