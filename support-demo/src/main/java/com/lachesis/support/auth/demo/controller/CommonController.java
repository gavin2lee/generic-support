package com.lachesis.support.auth.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errors")
public class CommonController {
	
	@RequestMapping(value="unlogin")
	public ResponseEntity<String> unlogin(){
		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}
}
