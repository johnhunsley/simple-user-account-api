package com.johnhunsley.user.api;

import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
 *         Time : 20:17
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/role")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<? extends Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Role> getRoleById(@PathVariable(name = "id") final int id) {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
        return new ResponseEntity(HttpStatus.OK);
    }
 }
