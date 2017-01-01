package com.lachesis.support.auth.demo.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.vo.Greeting;
import com.lachesis.support.common.util.service.CrudService;
import com.lachesis.support.restful.context.controller.AbstractRestController;

@RestController
@RequestMapping("/greetings")
public class GreetingController extends AbstractRestController<Greeting,Greeting>{
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping
	public Greeting getGreeting(@RequestParam(name="toname") String toName, HttpServletRequest request){
		
		return createNewGreeting(toName, "dummy");
	}

	
	private Greeting createNewGreeting(String counterparty, String owner){
		Greeting g = new Greeting();
		g.setId(String.valueOf(count.getAndIncrement()));
		g.setGreeting(UUID.randomUUID().toString());
		g.setCounterparty(counterparty);
		g.setOwner(owner);
		
		return g;
	}


	@Override
	protected CrudService<Greeting> getCrudService() {
		// TODO Auto-generated method stub
		return null;
	}
}
