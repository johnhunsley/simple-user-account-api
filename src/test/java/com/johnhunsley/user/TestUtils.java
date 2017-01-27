package com.johnhunsley.user;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.domain.YNEnum;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.svlada.security.crypto.password.Hash;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * todo - get rid of this into the jpa package
 * <p>
 *     Produces a populated {@link User} instance for testing
 * </p>
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
 *         Date : 08/12/2016
 *         Time : 19:08
 */
public class TestUtils {

    /**
     *
     * @return
     */
    public static User user() {
        Hash hash = new Hash(Hash.SHA1_TYPE);
        Role role = new RoleJpaImpl("TEST_ROLE");

        Account account = new AccountJpaImpl();
        //Set the id a-la-jpa style
        Field accountField = ReflectionUtils.findField(AccountJpaImpl.class, new ReflectionUtils.DescribedFieldFilter() {
            @Override
            public String getDescription() {
                return "id";
            }

            @Override
            public boolean matches(Field field) {
                return field.getName().equals("id");
            }
        });

        ReflectionUtils.setField(accountField, account, 100);
        account.setName("MyAccount");

        User user = new UserJpaImpl("test", "test@test", "test", "test", YNEnum.Y, hash.hash("password"));
        //Set the id a-la-jpa style
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
        user.setAccountId(account.getId());
        return user;
    }

    /**
     *
     * @return
     */
    public static Collection<Role> roles() {
        Collection<Role> roles = new ArrayList<>();
        Role role = new RoleJpaImpl("TEST_ROLE_1");
        Role role2 = new RoleJpaImpl("TEST_ROLE_2");

        Field field = ReflectionUtils.findField(RoleJpaImpl.class, new ReflectionUtils.DescribedFieldFilter() {
            @Override
            public String getDescription() {
                return "id";
            }

            @Override
            public boolean matches(Field field) {
                return field.getName().equals("id");
            }
        });

        ReflectionUtils.setField(field, role, 1);
        ReflectionUtils.setField(field, role2, 2);
        roles.add(role);
        roles.add(role2);
        return roles;
    }
}
