package gildedrose.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

interface PurchaseService {

  Purchase buyItem(Long itemId, Integer price, String principal);

  Iterable<Purchase> findAllByPrincipal(String name);

  @Component
  @RequiredArgsConstructor
  class PurchaseRepositoryPurchaseService implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public Iterable<Purchase> findAllByPrincipal(String name) {
      return purchaseRepository.findAllByPrincipalEquals(
        name
      );
    }

    @Override
    public Purchase buyItem(Long itemId, Integer price, String principal) {
      return purchaseRepository.buyItem(itemId, price, principal);
    }
  }
}
