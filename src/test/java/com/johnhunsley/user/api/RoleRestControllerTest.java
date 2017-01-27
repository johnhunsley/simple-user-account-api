package com.johnhunsley.user.api;

import com.johnhunsley.user.TestUtils;
import com.johnhunsley.user.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
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
 *         Date : 27/01/2017
 *         Time : 19:02
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = RoleRestController.class, secure = false)
public class RoleRestControllerTest {

    @MockBean
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllRoles() throws Exception {
        final String expected = "[{\"id\":1,\"authority\":\"TEST_ROLE_1\"},{\"id\":2,\"authority\":\"TEST_ROLE_2\"}]";
        given(roleService.getAllRoles()).willReturn(TestUtils.roles());

        mockMvc.perform(get("/role"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andReturn();
    }

}
