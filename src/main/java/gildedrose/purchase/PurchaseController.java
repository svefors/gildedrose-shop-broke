package gildedrose.purchase;

import gildedrose.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
class PurchaseController {

  private final PurchaseService purchaseService;

  private final PriceCheck priceCheck;


  @PostMapping(value = "/users/myself/purchases")
  @ResponseBody
  public ResponseEntity<Resource<Purchase>> buySingleItem(@RequestBody final BuySingleItem buySingleItem) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final String currentPrincipalName = authentication.getName();

    final Optional<Integer> priceForItem = priceCheck.findPriceForItem(buySingleItem.getItemId());
    if (priceForItem.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    if (priceForItem.get() > buySingleItem.seenPrice) {
      return ResponseEntity.badRequest().build();
    }

    final Purchase purchase =
      purchaseService.buyItem(
        buySingleItem.itemId,
        buySingleItem.seenPrice,
        currentPrincipalName
      );
    return ResponseEntity.ok(
      new Resource(purchase)
    );
  }

  @GetMapping(value = "/users/myself/purchases")
  public ResponseEntity<Resources<Purchase>> getMyItems() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final String currentPrincipalName = authentication.getName();
    final Iterable<Purchase> purchases = purchaseService.findAllByPrincipal(currentPrincipalName);
    return ResponseEntity.ok(
      new Resources<>(purchases)
    );
  }

  @Data
  @Builder
  static class BuySingleItem {
    private Long itemId;
    private Integer seenPrice;
  }

  @Component
  static class AddBuyItemLinkResourceProcessor implements ResourceProcessor<Resource<Item>> {
    @Override
    public Resource<Item> process(final Resource<Item> resource) {
      final Link buyItemLink = linkTo(methodOn(PurchaseController.class).buySingleItem(
        BuySingleItem.builder()
          .itemId(resource.getContent().getId())
          .seenPrice(resource.getContent().getPrice())
          .build()
      )).withRel("buy-item");
      resource.add(buyItemLink);
      return resource;
    }
  }

}
