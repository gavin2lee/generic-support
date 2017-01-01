package com.lachesis.support.auth.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lachesis.support.auth.demo.vo.Book;
import com.lachesis.support.auth.demo.vo.ErrorMsgVO;
import com.lachesis.support.common.util.service.CrudService;
import com.lachesis.support.restful.context.controller.AbstractRestController;
import com.lachesis.support.restful.context.vo.ResponseVO;

@RestController
@RequestMapping("books")
public class BookController extends AbstractRestController<Book,Book> {
	private AtomicLong count = new AtomicLong();
	
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVO getBooks(){
		List<Book> books = new ArrayList<Book>();
		
		for(int i=0; i<10; i++){
			books.add(create());
		}
		
		
		if(count.get() > 100){
			ErrorMsgVO errorMsgVO = new ErrorMsgVO();
			errorMsgVO.setCode("500.100");
			errorMsgVO.setMsg("internal error");
			ResponseVO errResp = ResponseVO.internalServerError(errorMsgVO);
			return errResp;
		}
		
		return new ResponseVO(books);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseVO getBook(@PathVariable("id")Long id){
		if(id == 1){
			return ResponseVO.ok(null);
		}else if(id == 2){
			return ResponseVO.notFound(null);
		}else if(id == 3){
			return ResponseVO.INTERNAL_SERVER_ERROR;
		}else{
			return ResponseVO.ok(create());
		}
	}
	
	private Book create(){
		Book b1 = new Book();
		b1.setId(count.incrementAndGet());
		b1.setAuthor("A");
		b1.setIssueDate(new Date());
		b1.setPrice(100000.000187438);
		b1.setName("企业文化");
		
		return b1;
	}

	@Override
	protected CrudService<Book> getCrudService() {
		// TODO Auto-generated method stub
		return null;
	}
}
