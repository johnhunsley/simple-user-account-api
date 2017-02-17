package com.johnhunsley.user.api;

import com.johnhunsley.user.domain.Page;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     RESTFul API which has no dependency on the underlying implementation of the simple-user-account package
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
 *         Date : 02/12/2016
 *         Time : 19:01
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserRestController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity saveUser(@RequestBody User user) {
        userDetailsService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserByUsername(@PathVariable(name = "username") final String username) {
        return new ResponseEntity<>(userDetailsService.getUserByUsername(username), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") final Long id) {
        return new ResponseEntity<>(userDetailsService.getById(id), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/page/{pageSize}/{pageNumber}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<User>> pageAllUsers(@PathVariable(name = "pageSize") final int pageSize,
                                            @PathVariable(name = "pageNumber") final int pageNumber) {
        return new ResponseEntity<>(userDetailsService.pageAllUser(pageSize, pageNumber), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/search/{pageSize}/{pageNumber}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<User>> searchAllUsers(@RequestParam(name = "query") final String query,
                                                    @PathVariable(name = "pageSize") final int pageSize,
                                                    @PathVariable(name = "pageNumber") final int pageNumber) {
        System.out.println("hello");
        return new ResponseEntity<>(userDetailsService.searchAllUsers(query, pageSize, pageNumber), HttpStatus.OK);
    }
}
