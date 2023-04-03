package com.prueba.api.usuario;

import com.prueba.api.usuario.auth.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class UsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);
	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		private final JWTAuthorizationFilter  jwtAuthorizationFilter;

		WebSecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
			this.jwtAuthorizationFilter = jwtAuthorizationFilter;
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
					.and().csrf().ignoringAntMatchers("/h2-console/**")
					.and().headers().frameOptions().sameOrigin();

			http.csrf().disable()
					.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/token").permitAll()
					.anyRequest().authenticated();

		}
	}
}
