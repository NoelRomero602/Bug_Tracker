package com.Bug_Tracker.configuration;

import com.Bug_Tracker.constant.SecurityConstant;
        import com.Bug_Tracker.filter.JWTAccessDeniedHandler;
        import com.Bug_Tracker.filter.JWTAuthenticationEntryPoint;
       import com.Bug_Tracker.filter.JWTAuthorizationFilter;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // allows us to have security at a method level
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JWTAuthorizationFilter JWTAuthorizationFilter;
    private JWTAccessDeniedHandler JWTAccessDeniedHandler;
    private JWTAuthenticationEntryPoint JWTAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsService userDetailsService2;
    @Autowired
    public SecurityConfiguration(JWTAuthorizationFilter jwtAuthorizationFilter,
                                 JWTAccessDeniedHandler jwtAccessDeniedHandler,
                                 JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("UserDetailService") UserDetailsService userDetailsService,
                                 @Qualifier("adminServiceImpl") UserDetailsService userDetailsService2
            , BCryptPasswordEncoder bCryptPasswordEncoder) {
        JWTAuthorizationFilter = jwtAuthorizationFilter;
        JWTAccessDeniedHandler = jwtAccessDeniedHandler;
        JWTAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService2 = userDetailsService2;
    }

     @Override
      @Order(1)
 		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
 		}
 		@Order(2)
 		protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
 		   auth.userDetailsService(userDetailsService2).passwordEncoder(bCryptPasswordEncoder);
 		}


    // "http.csrf().disable().cors()" disabling cross site forgery since were not using it, then enable cors which means if someone doesnt specify this domain they will be rejected
    // For stateless, we're not using session where it keeps track of logged in users so we use stateless, so '.sessionManagment()' is stateless and JWT is stateless
    // ".and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()" for these URLS we dont need to authenticate, thus permitting all directories specified in 'PUBLIC_URLS'
    // ".anyRequest().authenticated()" otherwise we want to authenticate
    // ".exceptionHandling().accessDeniedHandler(JWTAccessDeniedHandler)" whenever user is denied they will receive this handler we created
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(JWTAccessDeniedHandler)
                .authenticationEntryPoint(JWTAuthenticationEntryPoint)
                .and()
                .addFilterBefore(JWTAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // this filter happens before all other filters
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}