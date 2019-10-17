package gildedrose.surgepricing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
class ScheduledSurgePriceCheck {

  private SurgePriceManager surgePricer;

  @Scheduled(fixedRateString = "${pricing.surge.refresh}", initialDelay = 0)
  void updatePricing() {
    log.info("Pricing Check in process");
    surgePricer.performSurgePriceUpdate();
    log.debug("Pricing Check in completed");
  }
}
