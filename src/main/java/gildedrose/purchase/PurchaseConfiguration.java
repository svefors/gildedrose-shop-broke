package gildedrose.purchase;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("gildedrose.purchase")
@EnableJpaRepositories("gildedrose.purchase")
@EntityScan("gildedrose.purchase")
public class PurchaseConfiguration {

}
