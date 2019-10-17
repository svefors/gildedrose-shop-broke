package gildedrose.surgepricing.percatalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@EnableConfigurationProperties
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "pricing.surge.per-catalog-view.threshold")
class Threshold {

  @Value("${count:10}")
  private int count;

  @Value("${duration:PT1H}")
  private Duration duration;

}
