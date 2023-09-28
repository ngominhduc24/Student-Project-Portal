package swp.studentprojectportal.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {
    public static String getSiteURL() {
        UriComponents components=ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build();
        String siteUrl = components.toUriString();
        String path=components.getPath();
        return siteUrl.replace(path, "");
    }

    public static String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return Hex.encodeHexString(digest).toUpperCase();
    }
}