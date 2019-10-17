package gildedrose.surgepricing.percatalog;

import gildedrose.surgepricing.SurgePriceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class CatalogViewCountSurgePriceManager implements SurgePriceManager {

  private ViewCountService viewCountService;

  private Threshold threshold;

  private CatalogPricesService catalogPricesService;

  private float rate;

  CatalogViewCountSurgePriceManager(
    final ViewCountService viewCountService,
    final Threshold threshold,
    final CatalogPricesService catalogPricesService,
    @Value("${gildedrose.pricing.surge.addon:0.10f}") final float rate
  ) {
    this.viewCountService = viewCountService;
    this.threshold = threshold;
    this.catalogPricesService = catalogPricesService;
    this.rate = rate;
  }

  @Override
  public void performSurgePriceUpdate() {
    long viewCount = viewCountService.countViewsInLast(threshold.getDuration());
    if (viewCount > threshold.getCount()) {
      catalogPricesService.surgePricesWith(1.0f + rate);
    } else {
      catalogPricesService.useBasePrices();
    }
  }

}
