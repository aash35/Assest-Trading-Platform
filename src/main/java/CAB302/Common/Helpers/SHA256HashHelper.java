package CAB302.Common.Helpers;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHA256HashHelper {
    public static String generateHashedString(String string) {

        String sha256hex = Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();

        return sha256hex;
    }
}
