package com.johnhunsley.user.api;

import com.johnhunsley.user.TestUtils;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * todo - remove anything to do with the User implementation as it ties this entrie package to the jpa implemantation package which we are trying ot avoid
 * <p>
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 19:13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class, secure = false)
public class UserRestControllerTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUser() throws Exception {
        final String expected = "{\"class\":\"User\",\"id\":100,\"accountId\":100,\"username\":\"test\",\"password\":\"W6ph5Mm5Pz8GgiULbPgzG37mj9g=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        User user = TestUtils.user();
        given(userDetailsService.getUserByUsername(anyString())).willReturn(user);
        given(userDetailsService.getById(anyLong())).willReturn(user);

        mockMvc.perform(get("/user/username/" + "any"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();

        mockMvc.perform(get("/user/id/"+100L))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
    }

    @Test
    public void testWriteUser() throws Exception {
        final String json = "{\"class\":\"User\",\"id\":100,\"accountId\":100,\"username\":\"test\",\"password\":\"W6ph5Mm5Pz8GgiULbPgzG37mj9g=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"class\":\"Role\",\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        mockMvc.perform(put("/user").content(json).contentType("application/json")).andExpect(status().isOk());
    }


}
