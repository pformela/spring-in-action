package demo.rozdzial1.web;

import demo.rozdzial1.security.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepositoryUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SecurityConfig(UserRepositoryUserDetailsService userDetailsService,
                          BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                if (username.equals("sa") && password.equals("sa")) {
                    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
                }
                throw new AuthenticationServiceException("Invalid credentials.");
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // reguła zadeklarowana jako pierwsza jest ważniejsza niż druga
        http
            .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')") // można tutaj dodawać więcej warunków dla uwierzytelnienia użytkownika
                .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
//                .failureUrl("/login?error=true") // zdefiniowanie strony logowania
//                .loginProcessingUrl("/authenticate") // nasłuchiwanie żądań w podanej ścieżce dostępu, aby obsługiwać logowanie
                // udane logowanie domyślnie przenosi na stronę, na której użytkownik znajdował się przed koniecznością zalogowania się - w przeciwnym wypadku na stronę główną
//                .defaultSuccessUrl("/design", true) // zmiana domyślnej strony wyświetlanej po zalogowaniu się
//                .defaultSuccessUrl("/design", true) - zmiana domyślnej strony wyświetlanej po zalogowaniu się, nawet w przypadku gdy użytkownik znajdował się na innej stronie przed logowaniem
//                .usernameParameter("username")
//                .passwordParameter("password")
            .and()
                .logout() // ustalenie strony, na którą użytkownik zostaje przeniesiony po wylogowaniu się
                .logoutSuccessUrl("/");

    }

    // magazyn danych w pamięci
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("buzz")
//                .password("infinity")
//                .authorities("ROLE_USER")
//            .and()
//                .withUser("woody")
//                .password("bullseye")
//                .authorities("ROLE_USER");
//    }

    // magazyn danych oparty na jdbc
//    final DataSource dataSource;
//
//    @Autowired
//    public SecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities where username=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    // magazyn danych oparty na LDAP (lightweight directory access protocol)


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare() // uwierzytelnienie za pomocą porównania hasła z wartością atrybutu userPassword
//                .passwordEncoder(new BCryptPasswordEncoder()) // szyfrowanie w celu uniemożliwienia przechwycenia hasła przy wysyłaniu na serwer
//                .passwordAttribute("passcode") // zmiana nazwy atrybutu, z którym porównywane jest hasło
//            .and()
//                .contextSource()
////                .url("ldap://tacocloud.com:389/dc=tacocloud.dc=com") // ręczna konfiguracja położenia serwera LDAP
//                .root("dc=tacocloud,dc=com"); // osadzony serwer LDAP
//    }

}
