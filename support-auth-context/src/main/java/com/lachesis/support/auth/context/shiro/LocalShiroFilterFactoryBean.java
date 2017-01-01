package com.lachesis.support.auth.context.shiro;

import java.io.FileInputStream;

import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.core.io.Resource;

public class LocalShiroFilterFactoryBean extends ShiroFilterFactoryBean {
	private Resource iniResourcePath;

	public void setIniResourcePath(Resource iniResourcePath) {
		this.iniResourcePath = iniResourcePath;
		processIniResouce();
	}
	
	protected void processIniResouce(){
		if(!iniResourcePath.exists()){
			throw new RuntimeException("cannot load "+iniResourcePath.getFilename());
		}
		
		if(!iniResourcePath.isReadable()){
			throw new RuntimeException("cannot read "+iniResourcePath.getFilename());
		}
		
		Ini ini = new Ini();
        try {
			ini.load(new FileInputStream(iniResourcePath.getFile()));
		} catch (Exception e) {
			throw new RuntimeException("fail to load "+iniResourcePath.getFilename(), e);
		}
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        setFilterChainDefinitionMap(section);
	}
	
	
}
