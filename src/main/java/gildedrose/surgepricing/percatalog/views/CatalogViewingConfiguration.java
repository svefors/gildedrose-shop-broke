package gildedrose.surgepricing.percatalog.views;

import gildedrose.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
@EnableJpaRepositories("gildedrose.surgepricing.percatalog.views")
@EnableTransactionManagement
@ComponentScan("gildedrose.surgepricing.percatalog.views")
@EntityScan("gildedrose.surgepricing.percatalog.views")
@AllArgsConstructor
public class CatalogViewingConfiguration {

  @Autowired
  private final CatalogViewRequestInterceptor catalogViewRequestInterceptor;

  @Bean
  public MappedInterceptor mappedCatalogViewRequestInterceptor() {
    return new MappedInterceptor(new String[]{"/" + ItemRepository.REST_PATH}, catalogViewRequestInterceptor);
  }


}
