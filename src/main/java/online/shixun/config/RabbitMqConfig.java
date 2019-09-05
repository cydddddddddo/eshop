package online.shixun.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cy
 * @data 2019/9/4 - 20:26
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue seckillQueue(){
        return new Queue("transmit");
    }
}
