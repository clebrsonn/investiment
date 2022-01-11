package br.com.hyteck.investiment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;

public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Stock API")
                        .version("1.0.0")
                        .description("Api usada para o desenvolvimento do  TCC, usada para calcular" +
                                " a melhor tecnologia a ser usada  de acordo com os dados recebidos"));
    }

}
