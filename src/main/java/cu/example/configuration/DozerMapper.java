package cu.example.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gloria R. Leyva Jerez
 * Create bean to map classes using dozer
 */
@Configuration
public class DozerMapper {
    @Bean
    public Mapper beanMapper() {
        return new DozerBeanMapper();
    }
}
