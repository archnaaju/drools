package hello;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class DirectorConfig {

    private List<ComponentConfig> component;
    

	public List<ComponentConfig> getComponent() {
		return component;
	}
	public void setComponent(List<ComponentConfig> component) {
		System.out.println("1111111111");
		this.component = component;
	}

}
