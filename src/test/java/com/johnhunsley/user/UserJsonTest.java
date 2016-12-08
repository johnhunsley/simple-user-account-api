package com.johnhunsley.user;

import com.johnhunsley.user.domain.*;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 15:52
 */
@JsonTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserJsonTest {

    @Autowired
    private JacksonTester<UserJpaImpl> tester;

    @Test
    public void testSerialize() throws Exception {
        final String expected = "{\"@class\":\"UserJpaImpl\",\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@test\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"roles\":[{\"authority\":\"TEST_ROLE\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        Hash hash = new Hash(Hash.SHA1_TYPE);
        Role role = new RoleJpaImpl("TEST_ROLE");
        Account account = new AccountJpaImpl();
        UserJpaImpl user = new UserJpaImpl("test", "test@test", "test", "test", YNEnum.Y, hash.hash("passwordTest"));
        user.addRole(role);
        user.setAccount(account);
        System.out.println(tester.write(user).getJson());
        assertThat(tester.write(user)).isEqualToJson(expected);
    }
}
