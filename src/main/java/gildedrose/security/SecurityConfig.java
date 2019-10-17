package gildedrose.security;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(0)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.jwt.audience}")
  private String audience;

  @Value("${security.jwt.issuer}")
  private String issuer;

  @Value("${security.jwt.secret}")
  private String secret;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtWebSecurityConfigurer
      .forHS256(audience, issuer, secret.getBytes("UTF-8"))
      .configure(http);

    http.authorizeRequests()
      .mvcMatchers("/").permitAll()
      .mvcMatchers("/users/myself").authenticated();
  }

}
