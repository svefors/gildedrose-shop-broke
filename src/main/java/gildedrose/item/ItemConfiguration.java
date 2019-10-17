package gildedrose.item;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("gildedrose.item")
@EnableJpaRepositories("gildedrose.item")
@EntityScan("gildedrose.item")
@RequiredArgsConstructor
public class ItemConfiguration {


}
