import com.digrazia.dataIngestion.DataIngestionApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { DataIngestionApplication.class }) // Specifica le configurazioni necessarie
@SpringBootTest
public class ApplicationContextTest {

    @Test
    public void contextLoads() {
    }
}
