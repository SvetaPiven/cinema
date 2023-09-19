import org.example.configuration.JpaConfig;
import org.example.service.CategoryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableJpaRepositories
//@EnableTransactionManagement
//@ComponentScan(basePackages = "org.example")
public class MainApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
//        CategoryService categoryService = context.getBean("categoryServiceImpl", CategoryService.class);
//        categoryService.findAll();
//        context.close();
    }
}
