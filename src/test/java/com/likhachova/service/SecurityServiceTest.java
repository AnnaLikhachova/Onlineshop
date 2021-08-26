package com.likhachova.service;

import com.likhachova.web.security.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
@WebAppConfiguration
public class SecurityServiceTest {

    private MockMvc mockMvc;

    @Mock
    private SecurityService securityService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("securityService"));
    }

    @Test
    @DisplayName("Service returns token for user")
    public void test_serviceReturnsTokenForUser() {
        String token = securityService.login("user", "password");
        assertNotNull(token);
    }

    @Test
    @DisplayName("Service returns session for user")
    public void test_serviceReturnsSessionForUser() {
        String token = securityService.login("user", "password");
        Session session = securityService.getSession(token);
        assertNotNull(session);
    }

    @Test
    @DisplayName("Service check if user is admin")
    public void test_serviceChecksIfUserIsAdmin() {
        boolean isUserAdmin = securityService.isUserAdmin("admin", "admin");
        assertTrue(isUserAdmin);
    }
}
