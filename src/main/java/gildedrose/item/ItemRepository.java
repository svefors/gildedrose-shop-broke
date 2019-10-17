package gildedrose.item;

import gildedrose.surgepricing.percatalog.CatalogPricesService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = ItemRepository.REST_PATH)
public interface ItemRepository extends CrudRepository<Item, Long>, CatalogPricesService {

  String REST_PATH = "items";

  @Override
  @RestResource(exported = false)
  void deleteById(Long aLong);

  @Override
  @RestResource(exported = false)
  void delete(Item item);

  @Override
  @RestResource(exported = false)
  void deleteAll(Iterable<? extends Item> iterable);

  @Override
  @RestResource(exported = false)
  void deleteAll();

  @Override
  @RestResource(exported = false)
  <S extends Item> S save(S s);

  @Override
  @RestResource(exported = false)
  <S extends Item> Iterable<S> saveAll(Iterable<S> iterable);

  @Override
  @Transactional
  @Modifying
  @Query("update Item item set price = basePrice ")
  void useBasePrices();

  @Override
  @Transactional
  @Modifying
  @Query(
    value = "update Item item set price = cast(round(cast(basePrice as float) * :rate) as int) ",
    nativeQuery = true
  )
  void surgePricesWith(float rate);
}
