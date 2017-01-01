package com.lachesis.support.auth.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lachesis.support.objects.entity.auth.Token;

public interface TokenRepository {
	List<Token> selectPage();
	
	@Select("select count(1) from T_TOKEN")
	Long selectSize();
	
	Token selectOne(@Param("tokenValue") String tokenValue);
	
	Integer insertOne(Token token);
	
	Integer insertBatch(List<Token> tokens);

	Integer updateOne(Token token);

	Integer deleteOne(@Param("tokenValue") String tokenValue);
	
	@Delete("delete from T_TOKEN")
	Long deleteAll();

	@Delete("delete from T_TOKEN where LAST_MODIFIED < #{expireTime}")
	Integer deleteExpiredInBatch(@Param("expireTime") Date expireTime);
	
	@Select("select count(1) from T_TOKEN where LAST_MODIFIED < #{expireTime}")
	Integer countExpired(@Param("expireTime") Date expireTime);
}
