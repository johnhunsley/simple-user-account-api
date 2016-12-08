package com.johnhunsley.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnhunsley.user.Application;
import com.johnhunsley.user.domain.*;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.apache.commons.beanutils.PropertyUtils;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.Exception;
import java.lang.reflect.Field;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 14:13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class, secure = false)
public class UserRestControllerTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;
//
//    @InjectMocks
//    private UserRestController userRestController = new UserRestController();

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
//    }

    @Test
    public void testGetUser() throws Exception {
        final String expected = "{\"@class\":\"UserJpaImpl\",\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        Hash hash = new Hash(Hash.SHA1_TYPE);
        Role role = new RoleJpaImpl("TEST_ROLE");
        Account account = new AccountJpaImpl();
        User user = new UserJpaImpl("test", "test@test", "test", "test", YNEnum.Y, hash.hash("passwordTest"));

        Field field = ReflectionUtils.findField(UserJpaImpl.class, new ReflectionUtils.DescribedFieldFilter() {
            @Override
            public String getDescription() {
                return "id";
            }

            @Override
            public boolean matches(Field field) {
                return field.getName().equals("id");
            }
        });

        ReflectionUtils.setField(field, user, 100L);

        user.addRole(role);
        user.setAccount(account);
        given(userDetailsService.getUserByUsername(anyString())).willReturn(user);
        given(userDetailsService.getById(anyLong())).willReturn(user);

        MvcResult result0 = mockMvc.perform(get("/user/username/" + "any"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
        System.out.println(result0.getResponse().getContentAsString());

        MvcResult result1 = mockMvc.perform(get("/user/id/"+100L))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
        System.out.println(result1.getResponse().getContentAsString());
    }

    @Test
    public void testWriteUser() throws Exception {
        final String json = "{\"@class\":\"UserJpaImpl\",\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        mockMvc.perform(put("/user").content(json).contentType("application/json")).andExpect(status().isOk());

    }


}
