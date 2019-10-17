package gildedrose.surgepricing.percatalog.views;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
class LogCatalogViewServiceImpl implements LogCatalogViewService {

  private final CatalogViewRepository catalogViewRepository;

  @Override
  public void logViewing() {
    catalogViewRepository.save(
      CatalogViewingEntity.builder()
        .viewedAt(
          LocalDateTime.now()
        ).build()
    );
  }
}
