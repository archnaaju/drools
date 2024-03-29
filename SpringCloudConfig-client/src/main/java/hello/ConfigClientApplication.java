package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.config.DroolsConfigBean;
import hello.ruleprocessor.DroolsRuleProcessor;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}

@RefreshScope
@RestController
class MessageRestController {
	@Autowired
	DroolsRuleProcessor proc;
	
	@Autowired
	DroolsConfigBean drl;
	
    @Value("${message:test}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
    @RequestMapping("/test")
    String test() {
        return proc.testProduct();
    }
    @GetMapping("/refreshCont")
    void refreshCont(@RequestParam("version") String ver) {
        drl.refreshContainer(ver);
    }
    
}