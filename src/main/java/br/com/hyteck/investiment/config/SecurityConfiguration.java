package br.com.hyteck.investiment.config;

import br.com.hyteck.investiment.services.MyUserDetailsService;
import br.com.hyteck.investiment.users.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource).and().userDetailsService(userDetailsService).passwordEncoder(encoder);
                //.withDefaultSchema()
             //   .withUser("user").password(encoder.encode("password")).roles("USER")
              //  .and()
              //  .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");

        //auth.
           // .jdbcAuthentication()
           // .withDefaultSchema()
           // .withUser("user")
           // .password(encoder.encode("password"))
           // .roles("USER")
            //.and()
            //.withUser("admin")
            //.password(encoder.encode("admin"))
            //.roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }
}
