package com.johnhunsley.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnhunsley.user.Application;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.Exception;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 14:13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private UserRestController userRestController = new UserRestController();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        given(userDetailsService.getUserByUsername(anyString())).willReturn(null);
        given(userDetailsService.getById(anyLong())).willReturn(null);
        mockMvc.perform(get("/user/username/"+"any")).andExpect(status().isOk());
        mockMvc.perform(get("/user/id/"+100L)).andExpect(status().isOk());
    }

    @Test
    public void testWriteUser() throws Exception {
        final String json = "{\"id\":null,\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@tesst\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"id\":null,\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}\n";
        mockMvc.perform(put("/user").content(json).contentType("application/json")).andExpect(status().isOk());

    }


}
