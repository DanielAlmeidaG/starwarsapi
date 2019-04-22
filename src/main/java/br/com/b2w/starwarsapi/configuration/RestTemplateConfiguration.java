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

    private static final String USER_AGENT = "User-agent";
    private static final String CURL = "curl/7.59.0";

    @Bean
    public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().add(addRequestHeader(USER_AGENT, CURL));
        return restTemplate;
    }

    private ClientHttpRequestInterceptor addRequestHeader(String name, String value) {
        return (request, body, execution) -> {
            request.getHeaders().set(name, value);
            return execution.execute(request, body);
        };
    }
}
