package com.johnhunsley.user;

import com.johnhunsley.user.domain.*;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.jpa.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * todo - Move this out of this package into the JPA impl where it belongs as it ties this package to the jpa package which is what we are trying to avoid
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
        final String expected = "{\"@class\":\"UserJpaImpl\",\"id\":100,\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        User user = TestUtils.user();
        System.out.println(tester.write(user).getJson());
        assertThat(tester.write(user)).isEqualToJson(expected);
    }
}
