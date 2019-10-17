package gildedrose.purchase;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {


  @Override
  <S extends Purchase> S save(S s);

  @Override
  <S extends Purchase> Iterable<S> saveAll(Iterable<S> iterable);

  @Override
  Optional<Purchase> findById(Long aLong);

  @Override
  boolean existsById(Long aLong);

  @Override
  Iterable<Purchase> findAll();

  @Override
  Iterable<Purchase> findAllById(Iterable<Long> iterable);

  @Override
  long count();

  @Override
  void deleteById(Long aLong);

  @Override
  void delete(Purchase purchase);

  @Override
  void deleteAll(Iterable<? extends Purchase> iterable);

  @Override
  void deleteAll();

  Iterable<Purchase> findAllByPrincipalEquals(String principal);

  default Purchase buyItem(final Long itemId, final Integer price, final String principal) {
    return save(
      Purchase.builder()
        .itemId(itemId)
        .price(price)
        .principal(principal)
        .build()
    );
  }
}
