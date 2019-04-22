package br.com.b2w.starwarsapi.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().add(addRequestHeader("User-agent", "curl/7.59.0"));
        return restTemplate;
    }

    private ClientHttpRequestInterceptor addRequestHeader(String name, String value) {
        return (request, body, execution) -> {
            request.getHeaders().set(name, value);
            return execution.execute(request, body);
        };
    }
}
