import org.example.configuration.JpaConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MainApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
    }
}
