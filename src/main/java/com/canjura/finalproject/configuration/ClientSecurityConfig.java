package com.canjura.finalproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login().and().logout()
                .and().oauth2ResourceServer().jwt();
        http.csrf().disable();
    }

    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository){
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, auth2AuthorizedClientRepository);

        return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
    }
}
