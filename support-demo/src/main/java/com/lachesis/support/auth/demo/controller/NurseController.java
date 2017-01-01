package com.lachesis.support.auth.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.context.common.AuthContextConstants;
import com.lachesis.support.auth.demo.service.NurseService;
import com.lachesis.support.auth.demo.vo.SimpleUserVo;
import com.lachesis.support.objects.vo.SecurityContext;

@RestController
@RequestMapping(value="nurses")
public class NurseController{
	private static final Logger LOG = LoggerFactory.getLogger(NurseController.class);
	@Autowired
	private NurseService nurseService;
	
	@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method=RequestMethod.GET)
	public List<SimpleUserVo> listNurses(@RequestParam("deptid") String deptid,HttpServletRequest request){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			String localIpAddress = addr.getHostAddress();
			LOG.debug("LOCAL IP ADDR:"+localIpAddress);
			
			String remoteIpAddress = request.getRemoteAddr();
			LOG.debug("REMOTE IP ADDR:"+remoteIpAddress);
			
			
			LOG.debug("THREAD:"+Thread.currentThread().getName());
		} catch (UnknownHostException e) {
			LOG.error("errors", e);
		}
		
		SecurityContext ctx = (SecurityContext) request.getAttribute(AuthContextConstants.REQUEST_ATTR_SECURITY_CONTEXT);
		LOG.debug("GET FROM REQUEST:"+ctx.toString());
		return nurseService.listAllOfDepartment(deptid);
	}
	
	@RequestMapping(value="/{id}",produces={MediaType.APPLICATION_JSON_VALUE}, method=RequestMethod.GET)
	public SimpleUserVo findNurse(@PathVariable("id") String id,HttpServletRequest request){
		return nurseService.findOneUser(id);
		
	}
}
