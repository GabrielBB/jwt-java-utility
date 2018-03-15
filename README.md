# jwt-java-utility
## JSON Web Token Utility Class example implemented in Java using the library JJWT

### Requirements:

Add the library JJWT as a dependency in your project:

Maven:

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    compile 'io.jsonwebtoken:jjwt:0.9.0'
}
```

### The Models

Let's see the two model classes used in this example.

#### User.java

```java
public class User {
    private String name;
    private Role role;
}
```

#### Role.java

```java
public class Role {
    private int id;
    private String description;
}
```



### The Utility Class

```java
package com.github.gabrielbb;

import com.github.gabrielbb.models.Role;
import com.github.gabrielbb.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gabriel Basilio Brito on 3/13/2018.
 */
public class JWTUtility {

    private final static int EXPIRATION_HOURS = 1;

    private final byte[] signatureKeyBytes;

    public JWTUtility(String signatureKey) { // Pass your signature key to make an instance. Use a random secret string
        this.signatureKeyBytes = signatureKey.getBytes();
    }

    public String getToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getId()) // Subject is the unique identifier, this is usually an user ID or unique username
                .signWith(SignatureAlgorithm.HS512, signatureKeyBytes)
                .claim("role", user.getRole().getDescription()) // Add more claims according to your model class
                .setIssuedAt(now)
                .setExpiration(getExpirationTime(now))
                .compact();
    }

    public User getUser(String token) throws SignatureException {
        Claims claims = Jwts.parser()
        .setSigningKey(signatureKeyBytes)
        .parseClaimsJws(token)
        .getBody();

        Role role = new Role();
        role.setDescription(claims.get("role").toString());
        
        /* 
        You can insert a List as a claim if you need to, just cast it while getting it back. For Example:

        List<Permissions> permissions = (List) claims.get("permissions");
        */

        User user = new User();
        user.setId(claims.getSubject());
        user.setRole(role);

        return user;
    }

    private Date getExpirationTime(Date now) {
        Long expireInMillis = TimeUnit.HOURS.toMillis(EXPIRATION_HOURS);
        return new Date(expireInMillis + now.getTime());
    }
}
```

### Testing it

Testing this is very easy. You can check the unit testing in the code and run it. 
We just have to instance User and generate a token from it. With your generated token, try to get back an User object and validate it has the same values in the properties.
