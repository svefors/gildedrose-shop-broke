package gildedrose.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import({AppConfiguration.class})
@Slf4j
public class GildedRoseShopApplication {

  public static void main(String[] args) {
    SpringApplication.run(GildedRoseShopApplication.class, args);
  }

}
