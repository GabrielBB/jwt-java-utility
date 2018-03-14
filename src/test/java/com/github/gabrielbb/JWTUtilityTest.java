package com.github.gabrielbb;

import com.github.gabrielbb.models.Role;
import com.github.gabrielbb.models.User;

import junit.framework.TestCase;

/**
 * Created by Gabriel Basilio Brito on 3/13/2018.
 */
public class JWTUtilityTest extends TestCase {

    private final static String SIGNATURE_KEY = "GITHUB";

    private final JWTUtility utility;

    public JWTUtilityTest(String testName) {
        super(testName);

        utility = new JWTUtility(SIGNATURE_KEY);
    }

    public void testEncryptAndDecrypt() {
        Role r = new Role();
        r.setId(1);
        r.setDescription("Developer");

        User u1 = new User();
        u1.setId("Gabriel");
        u1.setRole(r);

        // Encryption
        String token = utility.getToken(u1);

        // Decryption
        User u2 = utility.getUser(token);

        // Testing if decrypted token returns an equal Object
        assertTrue(u1.getId().equals(u2.getId())
                && u1.getRole().getDescription().equals(u2.getRole().getDescription()));
    }
}
