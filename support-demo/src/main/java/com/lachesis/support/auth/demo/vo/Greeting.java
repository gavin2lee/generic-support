package com.lachesis.support.auth.demo.vo;

import com.lachesis.support.restful.context.vo.RequestVO;

public class Greeting implements RequestVO{
	private String id;
	private String greeting;
	private String counterparty;
	private String owner;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(String counterparty) {
		this.counterparty = counterparty;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
