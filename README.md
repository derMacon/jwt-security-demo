# jwt-security
Demo for securing microservices via JWT

This project shows a simple authentication process using spring boot and jwt. It provides a multiple microservices:
* **token-provider**: generates a token for given user credentials (if they are already registered in the system). This service is also capable of registering new users to the system.
* **model-view**: provides a user interface for the user to login and view the secured resources
* **info-view**: secured resource. Depending on the role of the user, some resources may not be viewed by the logged in user

### Useful Ressources
* medium ([tutorial](https://medium.com/@xoor/jwt-authentication-service-44658409e12c), [repo](https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/security/JwtTokenFilter.java))
* ertan ([tutorial](https://ertan-toker.de/spring-boot-spring-security-jwt-token/), [repo](https://github.com/derMacon/spring-jwt-demo/blob/main/token-generator/src/main/java/com/dermacon/tokengenerator/repository/AccountRepository.java))
* [grobmeier](https://grobmeier.solutions/de/spring-security-5-jwt-basic-auth.html)
