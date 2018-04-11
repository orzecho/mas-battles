package eu.mdabrowski.battles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import eu.mdabrowski.battles.security.OidcUserServiceWithAutorities;
import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Autowired
    OidcUserServiceWithAutorities oidcUserServiceWithAutorities;

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurer(@Value("${keycloak-client.realm}") final String realm) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .and()
//                        .csrf().disable()
                        .authorizeRequests().anyRequest().permitAll()
                        .and()
                        .oauth2Login()
                        .loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + realm)
                        .userInfoEndpoint().oidcUserService(oidcUserServiceWithAutorities);
            }
        };
    }
}