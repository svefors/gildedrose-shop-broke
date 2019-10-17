package gildedrose.surgepricing.percatalog.views;

import gildedrose.surgepricing.percatalog.views.CatalogViewRequestInterceptor;
import gildedrose.surgepricing.percatalog.views.LogCatalogViewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class CatalogViewRequestInterceptorTest {

//  @Rule
//  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private LogCatalogViewService logCatalogViewService;

  @Test
  public void it_should_log_get() throws Exception {
    CatalogViewRequestInterceptor catalogViewRequestInterceptor = new CatalogViewRequestInterceptor(logCatalogViewService);

    final MockHttpServletRequest request = new MockHttpServletRequest("GET","foofaaa");
    final MockHttpServletResponse response = null;
    catalogViewRequestInterceptor.afterCompletion(request, response, null, null);
    then(logCatalogViewService).should(times(1)).logViewing();

  }

  @Test
  public void it_should_not_log_post() throws Exception {
    CatalogViewRequestInterceptor catalogViewRequestInterceptor = new CatalogViewRequestInterceptor(logCatalogViewService);

    final MockHttpServletRequest request = new MockHttpServletRequest("POST","/asdf" );
    final MockHttpServletResponse response = null;
    catalogViewRequestInterceptor.afterCompletion(request, response, null, null);
    then(logCatalogViewService).should(never()).logViewing();

  }



}
