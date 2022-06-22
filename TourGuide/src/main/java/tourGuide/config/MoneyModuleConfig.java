package tourGuide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class MoneyModuleConfig {
    
    @Bean
    public MoneyModule moneyModule() {
	return new MoneyModule();
    }

}
