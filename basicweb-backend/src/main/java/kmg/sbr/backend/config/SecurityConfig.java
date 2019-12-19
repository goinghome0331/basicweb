package kmg.sbr.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import kmg.sbr.backend.filter.CorsFilter;
import kmg.sbr.backend.filter.CustomBasicFilter;
import kmg.sbr.backend.handler.RestAuthenticationEntryPoint;
import kmg.sbr.backend.user.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService us;


	@Autowired
	RestAuthenticationEntryPoint raep;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(us).passwordEncoder(passwordEncoder()); 

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	  http
	  	.csrf().disable()
	  	.exceptionHandling()
      	.authenticationEntryPoint(raep)
      	.and()
	  	.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/signin").permitAll()
		.antMatchers("/signup").permitAll()
		.antMatchers("/h2-console").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
	  

	  http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
      http.addFilterAfter(new CustomBasicFilter(), BasicAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
