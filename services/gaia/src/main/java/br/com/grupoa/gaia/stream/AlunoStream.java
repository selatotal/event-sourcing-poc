package br.com.grupoa.gaia.stream;

import br.com.grupoa.academic.model.Aluno;
import br.com.grupoa.academic.model.GradeDisciplina;
import br.com.grupoa.academic.model.TurmaDisciplina;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.Duration;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.String.format;

@Configuration
public class AlunoStream {

    private static final Logger logger = LoggerFactory.getLogger(AlunoStream.class);
    private static final Gson gson = new Gson();

    @Bean
    public Consumer<KStream<String, Aluno>> processAluno() {
        return input -> input.foreach((key, value) -> {
            logger.info(format("Key: %s, Value: %s", key, value));
        });
    }


    @Bean
    public BiFunction<KStream<String, GradeDisciplina>, KStream<String, TurmaDisciplina>, KStream<String, String>> joinProfessorTurmaDisciplina(){
        return (gradeDisciplinaStream, turmaDisciplinaStream) ->
            turmaDisciplinaStream.map((key, value) -> {
                logger.info("Received turmaDisciplinas" + key);
                return new KeyValue<>(key, value);
            }).toTable()
                    .join(
                            gradeDisciplinaStream.map((key, value) -> {
                                logger.info("Received gradeDisciplina" + key);
                                return new KeyValue<>(key, value);
                            }).toTable(),
                            TurmaDisciplina::getCodigoGradeDisciplina,
                            (value1, value2) -> {
                                String matched = "Matched " + value1.getCodigo() + " - " + value2.getCodigoGradeDisciplina();
                                logger.info("MATCHED" + matched);
                                return matched;
                            }
                    ).toStream();
    }

}
