package swp.studentprojectportal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import swp.studentprojectportal.utils.Validate;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ValidateTest {
    @Test
    public void validEmail() {
        boolean rs = Validate.validEmail("abc@abc@");
        Assertions.assertFalse(rs);
    }
}