package com.sapient.bookstore.service;

import com.sapient.bookstore.domain.User;
import com.sapient.bookstore.domain.security.Role;
import com.sapient.bookstore.domain.security.UserRole;
import com.sapient.bookstore.utility.SecurityUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Test
    public void createUser()
    {
        try {
            User user1 = new User();
            user1.setFirstName("adminName");
            user1.setLastName("adminLastName");
            user1.setUsername("adminUser");
            user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
            user1.setEmail("pankaj.sharma6@apient.com");
            Set<UserRole> userRoles = new HashSet<>();
            Role role1 = new Role();
            role1.setRoleId(1);
            role1.setName("ROLE_USER");
            userRoles.add(new UserRole(user1, role1));
            userService.createUser(user1, userRoles);
            Assert.assertEquals(user1.getUsername(),userService.findByUsername(user1.getUsername()).getUsername());
        }
        catch (Exception e)
        {
            Assert.fail("Unable to creat new user : Part setup Data");
        }
    }

    @Test
    public void updateUser()
    {
        User user=userService.findByUsername("adminUser");
        user.setFirstName("Changed");
        userService.updateUser(user);
        Assert.assertEquals("Changed",userService.findByUsername(user.getUsername()).getFirstName());
    }
}
