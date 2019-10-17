package gildedrose.purchase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseControllerTest {

  @Mock
  private PriceCheck priceCheck;

  @Mock
  private PurchaseService purchaseService;

  @Mock
  private Authentication authentication;

  @Test
  public void it_should_return_bad_request_when_item_not_found() {
    TestSecurityContextHolder.setAuthentication(
      authentication
    );
    final Long itemId = Long.MAX_VALUE;
    final PurchaseController purchaseController = new PurchaseController(purchaseService, priceCheck);
    when(
      priceCheck.findPriceForItem(itemId)
    ).thenReturn(Optional.empty());

    final ResponseEntity<?> responseEntity = purchaseController.buySingleItem(
      PurchaseController.BuySingleItem.builder()
        .itemId(itemId)
        .seenPrice(100000)
        .build()
    );
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

  @Test
  public void it_should_return_bad_request_if_price_is_too_high() {
    TestSecurityContextHolder.setAuthentication(
      authentication
    );
    final Long itemId = Long.MAX_VALUE;

    final PurchaseController purchaseController = new PurchaseController(purchaseService, priceCheck);
    when(
      priceCheck.findPriceForItem(itemId)
    ).thenReturn(
      Optional.of(
        110
      )
    );


    final int seenPrice = 100;
    final ResponseEntity<Resource<Purchase>> responseEntity = purchaseController.buySingleItem(
      PurchaseController.BuySingleItem.builder()
        .itemId(itemId)
        .seenPrice(seenPrice)
        .build()
    );
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

  @Test
  public void it_should_purchase_item_if_price_requested_is_ok() {
    TestSecurityContextHolder.setAuthentication(
      authentication
    );
    final Long itemId = Long.MAX_VALUE;

    final PurchaseController purchaseController = new PurchaseController(purchaseService, priceCheck);
    when(
      priceCheck.findPriceForItem(itemId)
    ).thenReturn(
      Optional.of(
        110
      )
    );
    when(
      purchaseService.buyItem(itemId, 110, authentication.getName())
    )
      .thenReturn(Purchase.builder().build());


    final int seenPrice = 110;
    final ResponseEntity<Resource<Purchase>> responseEntity = purchaseController.buySingleItem(
      PurchaseController.BuySingleItem.builder()
        .itemId(itemId)
        .seenPrice(seenPrice)
        .build()
    );

    verify(purchaseService).buyItem(itemId, seenPrice, authentication.getName());

  }

  @Test
  public void it_should_return_purchases() {
    TestSecurityContextHolder.setAuthentication(
      authentication
    );
    Purchase purchase = Purchase.builder().build();
    final Collection<Purchase> purchases = Collections.singleton(
      purchase
    );


    final PurchaseController purchaseController = new PurchaseController(purchaseService, priceCheck);
    when(
      purchaseService.findAllByPrincipal(authentication.getName())
    ).thenReturn(
      purchases
    );
    ResponseEntity<Resources<Purchase>> response = purchaseController.getMyItems();
    assertThat(response).isNotNull();
    assertThat(response.getBody().getContent()).containsExactly(purchase);
  }

}
