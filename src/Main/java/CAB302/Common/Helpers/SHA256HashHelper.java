package CAB302.Common.Helpers;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Class contains the method used to generate hashed passwords.
 */
public class SHA256HashHelper {

    /**
     * Generates a SHA256 hash of the input string, used to create hashed passwords for secure communication and
     * storage.
     * @param string the string to be hashed
     * @return the SHA256 hash of the input string.
     */
    public static String generateHashedString(String string) {

        String sha256hex = Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();

        return sha256hex;
    }
}
