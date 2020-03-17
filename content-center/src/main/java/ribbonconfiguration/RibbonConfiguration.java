package ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.stackingrule.contentcenter.configuration.NacosWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosWeightedRule();
    }
}
