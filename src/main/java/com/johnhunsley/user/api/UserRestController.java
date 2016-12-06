package com.johnhunsley.user.api;

import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 14:01
 */
@RestController
@RequestMapping(value = "/user")
public class UserRestController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity saveUser(@RequestBody User user) {
        userDetailsService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@PathVariable(name = "username") final String username) {
        return new ResponseEntity<>(userDetailsService.getUserByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") final Long id) {
        return new ResponseEntity<>(userDetailsService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/page/{pageSize}/{pageNumber}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<User>> pageAllUsers(@PathVariable(name = "pageSize") final int pageSize,
                                            @PathVariable(name = "pageNumber") final int pageNumber) {
        return new ResponseEntity<>(userDetailsService.pageAllUser(pageSize, pageNumber), HttpStatus.OK);
    }
}
