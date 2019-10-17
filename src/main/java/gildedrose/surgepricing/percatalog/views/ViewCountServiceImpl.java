package gildedrose.surgepricing.percatalog.views;

import gildedrose.surgepricing.percatalog.ViewCountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
class ViewCountServiceImpl implements ViewCountService {

  private final CatalogViewRepository catalogViewRepository;

  @Override
  public long countViewsInLast(Duration duration) {
    return catalogViewRepository.countByViewedAtAfter(
      LocalDateTime.now()
        .minus(
          duration.toMillis(),
          ChronoUnit.MILLIS
        )
    );
  }
}
