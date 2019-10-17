package gildedrose.surgepricing.percatalog;

import java.time.Duration;

public interface ViewCountService {

  long countViewsInLast(Duration duration);

}
