package hello.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PreDestroy;

import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.compiler.kproject.models.KieModuleModelImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DroolsConfigBean {
	@Autowired
	private KieContainer kieConatiner;
	
	private String version="master";
	
	@Bean
	public KieContainer kieContainer() {
		if(kieConatiner != null) {
			System.out.println("not null kieConatiner="+kieConatiner);
			return kieConatiner;
		}
		
        String url = "http://localhost:8080/configserver/default/"+version+"/ProductModule.jar";
        //String url ="file:/" + System.getProperty("user.dir") + "/new.jar";
        System.out.println("URL========="+url);
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources()
                .newUrlResource(url);

        InputStream is = null;
		try {
			is = urlResource.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        KieModule kModule = kr.addKieModule(ks.getResources()
                .newInputStreamResource(is));
        System.out.println("kModule.getReleaseId()="+kModule.getReleaseId());
        System.out.println("before kieConatiner="+kieConatiner);
        kieConatiner = ks.newKieContainer(kModule.getReleaseId());
        System.out.println("after kieConatiner="+kieConatiner);
		return kieConatiner;
	}
	
	public void refreshContainer(String ver){
		closeDroolsContainer();
		kieConatiner=null;
		version=ver;
	}
	
	@Bean
	public KModuleBeanFactoryPostProcessor kiePostProcessor() {
		return new KModuleBeanFactoryPostProcessor();
	}
	

	
	@PreDestroy
	private void closeDroolsContainer() {
		try {
			kieConatiner.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
