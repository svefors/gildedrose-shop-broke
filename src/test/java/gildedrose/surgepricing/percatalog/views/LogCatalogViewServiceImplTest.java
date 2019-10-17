package gildedrose.surgepricing.percatalog.views;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class LogCatalogViewServiceImplTest {


  @Mock
  private CatalogViewRepository catalogViewRepository;

  @Test
  public void it_should_increase_views_when_product_catalog_is_viewed() {
    LogCatalogViewServiceImpl logCatalogViewService = new LogCatalogViewServiceImpl(catalogViewRepository);
    logCatalogViewService.logViewing();
    verify(catalogViewRepository).save(any(CatalogViewingEntity.class));
  }
}
