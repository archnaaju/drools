package hello.ruleprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import hello.models.Product;
import hello.config.DroolsConfigBean;

@Component
@Scope("prototype")
public class DroolsRuleProcessor {
	@Autowired
	private DroolsConfigBean droolsConfigBean;

	public String testProduct(){
		KieSession kieSession = null;
		Product prod = new Product();
		try {
			// Prepare the data required.
			
			prod.setType("gold");
			KieContainer cont=droolsConfigBean.kieContainer();
			kieSession = cont.newKieSession("test1session");
			kieSession.insert(prod);
			kieSession.fireAllRules();

			System.out.println("The discount for the product " + prod.getType()
					+ " is " + prod.getDiscount());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(kieSession != null) {
				kieSession.dispose();
			}
			
		}
		return prod.getDiscount()+"";
	}

}
