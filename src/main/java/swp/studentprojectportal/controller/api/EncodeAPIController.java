package swp.studentprojectportal.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.utils.JwtTokenProvider;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/encode")
public class EncodeAPIController {

    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @GetMapping("/data")
    public String encode(@RequestBody String data) {

        return jwtTokenProvider.encodeData(data);

    }

}
