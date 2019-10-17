package gildedrose.surgepricing.percatalog.views;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;

import static gildedrose.surgepricing.percatalog.views.CatalogViewRepository.REST_PATH;

@RepositoryRestResource(path = REST_PATH, exported = false)
interface CatalogViewRepository extends CrudRepository<CatalogViewingEntity, Long> {

  String REST_PATH = "catalog-views";

  long countByViewedAtAfter(LocalDateTime localDateTime);

}
