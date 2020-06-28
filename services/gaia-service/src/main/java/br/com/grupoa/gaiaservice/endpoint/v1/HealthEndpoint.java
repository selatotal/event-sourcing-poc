package br.com.grupoa.gaiaservice.endpoint.v1;

import br.com.grupoa.gaiaservice.contract.v1.output.HealthOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
public class HealthEndpoint {

    @GetMapping
    public HealthOutput health(){
        return new HealthOutput(200);
    }
}
