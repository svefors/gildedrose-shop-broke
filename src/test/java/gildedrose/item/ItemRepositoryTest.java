package gildedrose.item;

import gildedrose.app.AppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = {
    AppConfiguration.class
  }
)
public class ItemRepositoryTest {

  @Autowired
  private ItemRepository itemRepository;

  @Test
  public void it_should_calculate_surge_price() {
    final Item testitem =
      itemRepository.save(
        Item.builder()
          .price(100)
          .basePrice(100)
          .build()
      );
    float rate = 1.10f;
    itemRepository.surgePricesWith(rate);
    Optional<Item> itemWithSurgedPrice = itemRepository.findById(testitem.getId());
    assertThat(itemWithSurgedPrice).isNotEmpty();
    assertThat(itemWithSurgedPrice.get().getPrice()).isEqualTo(110);

  }
}
