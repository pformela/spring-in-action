package demo.rozdzial1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare() // uwierzytelnienie za pomocą porównania hasła z wartością atrybutu userPassword
                .passwordEncoder(new BCryptPasswordEncoder()) // szyfrowanie w celu uniemożliwienia przechwycenia hasła przy wysyłaniu na serwer
                .passwordAttribute("passcode") // zmiana nazwy atrybutu, z którym porównywane jest hasło
            .and()
                .contextSource()  // ręczna konfiguracja położenia serwera LDAP
                .url("ldap://tacocloud.com:389/dc=tacocloud.dc=com");
    }
}
