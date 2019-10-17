package gildedrose.surgepricing.percatalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatalogViewCountSurgePriceManagerTest {

  @Mock
  private ViewCountService viewCountService;

  @Mock
  private CatalogPricesService catalogPricesService;

  @Test
  public void it_should_surge_when_threshold_exceeded() {
    Threshold threshold = new Threshold(1, Duration.ofMinutes(1));
    float rate = 0.10f;
    CatalogViewCountSurgePriceManager catalogViewCountSurgePriceManager =
      new CatalogViewCountSurgePriceManager(viewCountService, threshold, catalogPricesService, rate);

    when(
      viewCountService.countViewsInLast(Duration.ofMinutes(1))
    ).thenReturn(
      threshold.getCount() + 1l
    );
    catalogViewCountSurgePriceManager.performSurgePriceUpdate();
    verify(catalogPricesService, atLeastOnce()).surgePricesWith(anyFloat());

  }

  @Test
  public void it_should_use_base_prices_when_threshold_is_not_exceeded() {
    Threshold threshold = new Threshold(1, Duration.ofMinutes(1));
    float rate = 0.10f;
    CatalogViewCountSurgePriceManager catalogViewCountSurgePriceManager =
      new CatalogViewCountSurgePriceManager(viewCountService, threshold, catalogPricesService, rate);

    when(
      viewCountService.countViewsInLast(Duration.ofMinutes(1))
    ).thenReturn(
      (long) threshold.getCount()
    );
    catalogViewCountSurgePriceManager.performSurgePriceUpdate();
    verify(catalogPricesService, never()).surgePricesWith(anyFloat());

  }
}
