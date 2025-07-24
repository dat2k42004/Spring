package com.example.demo.config;

import org.apache.tomcat.util.net.DispatchType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.config.CustomSuccessHandler;
import com.example.demo.service.UserService;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public UserDetailsService userDetailsService(UserService userService) {
          return new CustomUserDetailsService(userService);
     }

     @Bean
     public DaoAuthenticationProvider authProvider(
               PasswordEncoder passwordEncoder,
               UserDetailsService userDetailsService) {

          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
          authProvider.setUserDetailsService(userDetailsService);
          authProvider.setPasswordEncoder(passwordEncoder);
          authProvider.setHideUserNotFoundExceptions(false);

          return authProvider;
     }

     // fix bug "//"
     @Bean
     public StrictHttpFirewall httpFirewall() {
          StrictHttpFirewall firewall = new StrictHttpFirewall();
          firewall.setAllowedHeaderValues((header) -> true); // Allow custom headers if needed
          firewall.setAllowUrlEncodedDoubleSlash(true); // Allow double slashes
          return firewall;
     }

     // @Bean
     // public FilterRegistrationBean<Filter>
     // customFirewallRegistration(StrictHttpFirewall firewall) {
     // FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
     // registration.setFilter(new StrictHttpFirewall());
     // registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
     // return registration;
     // }

     @Bean
     @Order(2147483642)
     SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
          http
                    .authorizeHttpRequests(authorize -> authorize
                              .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE).permitAll()
                              .requestMatchers("/", "/login", "/client/**", "/css/**", "/js/**", "/images/**",
                                        "/register",
                                        "/product/**")
                              .permitAll()
                              .requestMatchers("/admin/**").hasRole("ADMIN")
                              .anyRequest().authenticated())
                    .sessionManagement((sessionManagement) -> sessionManagement
                              .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                              .invalidSessionUrl("/logout?expired")
                              .maximumSessions(1)
                              .maxSessionsPreventsLogin(false))
                    .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                    .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                    .formLogin(formLogin -> formLogin.loginPage("/login")
                              .failureUrl("/login?error")
                              .successHandler(myAuthenticationSuccessHandler())
                              .permitAll())
                    .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

          return http.build();
     }

     @Bean
     public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
          return new CustomSuccessHandler();
     }

     @Bean
     public SpringSessionRememberMeServices rememberMeServices() {
          SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
          // optionally customize
          rememberMeServices.setAlwaysRemember(true);
          return rememberMeServices;
     }
}
