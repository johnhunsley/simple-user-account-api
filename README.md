# simple-user-account-api
MVC REST controllers for the simple-user-account package which are intended for use with a Javascript front end

The API is secured with JWT implemented in Spring Security using a fork of Vladimir Stankovic's example -

https://github.com/johnhunsley/springboot-security-jwt

This REST API has no dependency on the underlying domain model or repository. The UserDetailsService is defined in the
interface package - simple-user-account. Currently, the only implementation defined is simple-user-account-jpa. In this
case the application should be booted with the active profile - 'jpa'

e.g. -Dspring.profiles.active=jpa

The build includes the spring-boot plugin. Run the API by executing the spring-boot:run goal.
