package gildedrose.surgepricing.percatalog.views;

import gildedrose.app.AppConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
  AppConfiguration.class
})
@AutoConfigureMockMvc
public class CatalogViewsTest {


  @Autowired
  private MockMvc mvc;

  @Autowired
  private ViewCountServiceImpl viewCountService;

  @Autowired
  private CatalogViewRepository catalogViewRepository;

  @Before
  public void setup() {
    catalogViewRepository.deleteAll();
  }

  @After
  public void teardown() {
    catalogViewRepository.deleteAll();
  }

  @Test
  public void it_should_count_catalog_item_views() throws Exception {
    long lastSeenViews = viewCountService.countViewsInLast(Duration.ofHours(1));

    mvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
    assertThat(
      viewCountService
        .countViewsInLast(
          Duration.ofHours(1)
        )
    ).isEqualTo(lastSeenViews + 1);
  }
}
