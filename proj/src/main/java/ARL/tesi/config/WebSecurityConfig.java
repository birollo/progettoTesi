package ARL.tesi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    //todo:rivedere pagine
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/register").hasRole("ADMIN")
                .antMatchers("/sectors").permitAll()
                .antMatchers("/professions").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/icons/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/roles/**").permitAll()
                .antMatchers("/privates/**").permitAll()
                .antMatchers("/companies/**").permitAll()
                .antMatchers("/search").hasRole("ADMIN")
                .antMatchers("/search").hasRole("COMPANY")
                .antMatchers("/favorites").hasRole("COMPANY")
                .antMatchers("/removeFavorite").hasRole("COMPANY")
                .antMatchers("/verifyemail").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/profilehome").authenticated()
                .antMatchers("/about").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/info/**").permitAll()
                .antMatchers("/messages").authenticated()
                .antMatchers("/login", "/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        auth.authenticationProvider(authProvider);
    }

}
