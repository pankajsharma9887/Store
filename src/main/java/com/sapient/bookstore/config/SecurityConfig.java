package com.sapient.bookstore.config;


import com.sapient.bookstore.utility.SecurityUtility;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// when spring boot initializes it will initialize this configuration
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/newUser",
			"/v2",
			"/v2/**",
			"/swagger-ui.html",
			"/newUser/**",
			"/h2",
			"/h2/**",
			"/book",
			"/searchBook"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		meaning - any request in PUBLIC_MATCHERS will be permitAll and for others we need authentication
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS)
			.permitAll()
			.anyRequest()
			.authenticated();
//		disable cross site restriction
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.formLogin()
			.failureUrl("/login?error")
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout")
			.deleteCookies("remember-me")
			.permitAll()
			.and()
			.rememberMe();
			http.authorizeRequests()
					.antMatchers("/**").access("hasRole('admin')").and().httpBasic();

		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).authorities("admin");
	//	auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
