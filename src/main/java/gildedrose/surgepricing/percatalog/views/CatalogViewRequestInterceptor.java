package gildedrose.surgepricing.percatalog.views;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
@Slf4j
class CatalogViewRequestInterceptor extends HandlerInterceptorAdapter {

  private final LogCatalogViewService logCatalogViewService;

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if (HttpMethod.GET.matches(request.getMethod())) {
      logCatalogViewService.logViewing();
    }
  }
}
