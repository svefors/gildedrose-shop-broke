package gildedrose.app;

import gildedrose.item.Item;
import gildedrose.item.ItemConfiguration;
import gildedrose.item.ItemRepository;
import gildedrose.purchase.PurchaseConfiguration;
import gildedrose.security.SecurityConfig;
import gildedrose.surgepricing.SurgePricingConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.stream.IntStream;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableWebSecurity
@Import({
  ItemConfiguration.class
  , SurgePricingConfiguration.class
  , PurchaseConfiguration.class
  , SecurityConfig.class
})
@EntityScan({
  "gildedrose.shop"
  , "gildedrose.pricing.percatalog.views"
})
public class AppConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/", "/items", "/items/**", "/browser/**").permitAll()
      .anyRequest()
      .authenticated();
  }

  @Bean
  @Profile("dev")
  public CommandLineRunner demo(ItemRepository repository) {
    return (args) -> {
      IntStream.range(1, 10).mapToObj(
        i ->
          Item.builder()
            .name("Item_" + 1)
            .description("This is Item " + i)
            .basePrice(i * 1000)
            .price(i * 1000)
            .build()
      ).forEach(repository::save);
    };
  }

}
