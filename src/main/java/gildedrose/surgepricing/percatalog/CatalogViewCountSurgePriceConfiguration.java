package gildedrose.surgepricing.percatalog;

import gildedrose.surgepricing.percatalog.views.CatalogViewingConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(
  value = "pricing.surge.strategy",
  havingValue = "per_catalog_view",
  matchIfMissing = false)
@ComponentScan("gildedrose.surgepricing.percatalog")
@Import(CatalogViewingConfiguration.class)
public class CatalogViewCountSurgePriceConfiguration {

  @Value("${gildedrose.pricing.surge.addon:0.10f}")
  private float rate;

}
