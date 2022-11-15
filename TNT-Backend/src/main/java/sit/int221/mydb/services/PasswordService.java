package sit.int221.mydb.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65586;
    private static final int PARALLELISM = 1;

    private Argon2 getArgon2Instance() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 14, 29);
    }

    public String securePassword(final String  password) {
        final Argon2 argon2 = getArgon2Instance();
        return argon2.hash(ITERATIONS,MEMORY,PARALLELISM,password.toCharArray());
    }

    public boolean validatePassword(String hash, String userPassword){
        final Argon2 argon2 = getArgon2Instance();
        return argon2.verify(hash,userPassword.toCharArray());
    }
}
