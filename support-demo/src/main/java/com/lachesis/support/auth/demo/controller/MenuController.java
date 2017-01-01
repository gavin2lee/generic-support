package com.lachesis.support.auth.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.common.util.service.CrudService;
import com.lachesis.support.restful.context.controller.AbstractRestController;
import com.lachesis.support.restful.context.vo.RequestVO;

@RestController
@RequestMapping("menus")
public class MenuController extends AbstractRestController<RequestVO,RequestVO> {

	@Override
	protected CrudService<RequestVO> getCrudService() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
