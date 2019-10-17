package gildedrose.surgepricing;

import gildedrose.surgepricing.percatalog.CatalogViewCountSurgePriceConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@AllArgsConstructor
@Import({CatalogViewCountSurgePriceConfiguration.class})
@ComponentScan("gildedrose.surgepricing")
public class SurgePricingConfiguration {


}
