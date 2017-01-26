package com.johnhunsley.user;

import com.johnhunsley.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
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
 *         Time : 19:52
 */
@JsonTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserJsonTest {

    @Autowired
    private JacksonTester<User> tester;

    @Test
    public void testSerialize() throws Exception {
        final String expected = "{\"class\":\"User\",\"id\":100,\"accountId\":100,\"username\":\"test\",\"password\":\"W6ph5Mm5Pz8GgiULbPgzG37mj9g=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        User user = TestUtils.user();
        System.out.println(tester.write(user).getJson());
        assertThat(tester.write(user)).isEqualToJson(expected);
    }
}
