package gildedrose.purchase;

import gildedrose.item.Item;
import gildedrose.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

interface PriceCheck {

  Optional<Integer> findPriceForItem(final Long itemId);

  @Component
  @RequiredArgsConstructor
  class ItemRepositoryPriceCheck implements PriceCheck {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Integer> findPriceForItem(final Long itemId) {
      return itemRepository.findById(itemId).map(Item::getPrice);
    }
  }
}
