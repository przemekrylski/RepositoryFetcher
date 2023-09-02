package Configuration;

import Interceptor.HeaderRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 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");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.setInterceptors(Collections.singletonList(new HeaderRequestInterceptor(headers)));

        return restTemplate;
    }
}
