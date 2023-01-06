package com.springboot.blog.config;

import com.springboot.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Enables method level security, to use @PreAuthorize in controller class
//Extend WebSecurityConfigurerAdapter to override and configure the default spring security implementation and behaviour
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService; //our custom UserDetailsService implementation

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    //password encoder to hash the plain text password
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //By default spring security authorizes all resources and has a default user with username:user and password printed in console
    //To configure and customise this behaviour, override the configure(HttpSecurity http) method to customize the http request such as formbased or basichttp,
    //and also to authorize only the provided url, either to all users or based on role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    //In a real application we dont want inmemory users, we need real users from DB, so instead of using default UserDetailsService provided by spring security,
    //we can create our CustomUserDetailsService and tell spring security configuration to use our CustomUserDetailsService by overriding configure(AuthenticationManagerBuilder auth)
    //and provide the type of passwordEncoder to be used.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //AuthenticationManager to be injected in to AuthController class to authenticate the user
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //By default spring security has only one user with username user, we can add multiple inmemory users using UserDetailsService provided by spring security
    //override userDetailsService() method and build users and return InMemoryUserDetailsManager object to create users in spring security
/*  @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder().username("oops")
                .password(passwordEncoder().encode("oops")).roles("USER").build();

        UserDetails user2 = User.builder().username("raimi")
                .password(passwordEncoder().encode("raimi")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
 */
}
