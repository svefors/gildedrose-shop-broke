package gildedrose.security;

import gildedrose.app.AppConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
  AppConfiguration.class
})
public class SecurityConfigTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(springSecurity())
      .build();
  }

  @Test
  public void it_should_secure_users_myself() throws Exception{
    mvc.perform(get("/users/myself").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void it_should_allow_access_to_items() throws Exception{
///users/myself
    mvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}
