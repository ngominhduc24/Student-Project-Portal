package swp.studentprojectportal.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

public class Utility {
    public static String getSiteURL() {
        UriComponents components=ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build();
        String siteUrl = components.toUriString();
        String path=components.getPath();
        return siteUrl.replace(path, "");
    }
}