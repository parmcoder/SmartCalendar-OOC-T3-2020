package ooc.squishtable.main.configuration;

import ooc.squishtable.main.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests().antMatchers("/api/admin/**")
//                .access("hasRole('ROLE_ADMIN')");

        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // No session will be created or used by spring security
//        .and()
//            .httpBasic()
        .and()
            .authorizeRequests()
                // allow every URI, that begins with '/api/'
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/user/**").permitAll()
                // allow every URI with authentication, that begins with '/api/'
                .antMatchers("/api/secured")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/api/admin/**")
                .access("hasRole('ROLE_ADMIN')")// protect all other requests
                .and().logout().logoutUrl("/api/logout").logoutSuccessUrl("/")
                .and()
                //                .anyRequest().authenticated()

                .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!



    }

}
