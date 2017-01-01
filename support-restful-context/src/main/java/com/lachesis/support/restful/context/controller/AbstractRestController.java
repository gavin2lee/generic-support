package com.lachesis.support.restful.context.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lachesis.support.common.util.service.CrudService;
import com.lachesis.support.restful.context.vo.ResponseVO;

public abstract class AbstractRestController<V,E> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRestController.class);
	
	@RequestMapping(method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVO createEntity(@RequestBody V requestObject, HttpServletRequest request){
		if(LOG.isDebugEnabled()){
			LOG.debug("RECV:"+requestObject);
		}
		return ResponseVO.ok(requestObject);
	}
	
	public ResponseVO updateEntity(){
		return ResponseVO.ok(null);
	}
	
	public ResponseVO deleteEntity(){
		return ResponseVO.ok(null);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVO retrieveEntity(@PathVariable("id") Object id){
		E entity = getCrudService().retrieveEntityById(id);
		return ResponseVO.ok(entity);
	}
	
	protected abstract CrudService<E> getCrudService();
}
