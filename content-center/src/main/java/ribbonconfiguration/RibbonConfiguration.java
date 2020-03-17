package ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.stackingrule.contentcenter.configuration.NacosSameClusterWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosSameClusterWeightedRule();
    }
}
