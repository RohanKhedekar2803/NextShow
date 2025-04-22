import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EurekaUrlPrinter implements CommandLineRunner {

    @Value("${eureka.client.service-url.defaultZone}")
    private String defaultZone;

    @Override
    public void run(String... args) {
        System.out.println("Eureka Default Zone: " + defaultZone);
    }
}
