package br.com.grupoa.academic.config;

import br.com.grupoa.academic.contract.v1.event.Event;
import br.com.grupoa.academic.model.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrapAddress;

    @Value("${kafka.transactionalId}")
    private String kafkaTransactionalId;


    private Map<String, Object> getProps(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, Event> producerFactoryEvent(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, Aluno> producerFactoryAluno(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, GradeDisciplina> producerFactoryGradeDisciplina(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, MatriculaAluno> producerFactoryMatriculaAluno(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, MatriculaProfessor> producerFactoryMatriculaProfessor(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, Professor> producerFactoryProfessor(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public ProducerFactory<String, TurmaDisciplina> producerFactoryTurmaDisciplina(){
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    @Bean
    public KafkaTemplate<String, Event> kafkaTemplateEvent(){
        return new KafkaTemplate<>(producerFactoryEvent());
    }

    @Bean
    public KafkaTemplate<String, Aluno> kafkaTemplateAluno(){
        return new KafkaTemplate<>(producerFactoryAluno());
    }

    @Bean
    public KafkaTemplate<String, GradeDisciplina> kafkaTemplateGradeDisciplina(){
        return new KafkaTemplate<>(producerFactoryGradeDisciplina());
    }

    @Bean
    public KafkaTemplate<String, MatriculaAluno> kafkaTemplateMatriculaAluno(){
        return new KafkaTemplate<>(producerFactoryMatriculaAluno());
    }

    @Bean
    public KafkaTemplate<String, MatriculaProfessor> kafkaTemplateMatriculaProfessor(){
        return new KafkaTemplate<>(producerFactoryMatriculaProfessor());
    }

    @Bean
    public KafkaTemplate<String, Professor> kafkaTemplateProfessor(){
        return new KafkaTemplate<>(producerFactoryProfessor());
    }

    @Bean
    public KafkaTemplate<String, TurmaDisciplina> kafkaTemplateTurmaDisciplina(){
        return new KafkaTemplate<>(producerFactoryTurmaDisciplina());
    }

}
