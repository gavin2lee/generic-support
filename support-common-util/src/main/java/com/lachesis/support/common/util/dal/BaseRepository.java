package com.lachesis.support.common.util.dal;

import java.util.List;
import static com.lachesis.support.common.util.dal.SQLBuilder.*;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.lachesis.support.common.util.dal.SQLBuilder;

public interface BaseRepository<E> {
	@SelectProvider(type = SQLBuilder.class, method = FIND_ONE)
	E findOne(@Param(P_NAME_ID) Object id, @Param(P_NAME_TYPE) Class<E> type);

	@SelectProvider(type = SQLBuilder.class, method = FIND_LIST_BY_CRITERIA)
	List<E> findListByCriteria(@Param(P_NAME_ENTITY) E criteria, @Param(P_NAME_TYPE) Class<E> type);

	@InsertProvider(type = SQLBuilder.class, method = SAVE_ONE)
	Integer saveOne(@Param(P_NAME_ENTITY) E entity, @Param(P_NAME_TYPE) Class<E> type);

	@UpdateProvider(type = SQLBuilder.class, method = UPDATE_ONE)
	Integer updateOne(@Param(P_NAME_ENTITY) E entity, @Param(P_NAME_TYPE) Class<E> type);

	@DeleteProvider(type = SQLBuilder.class, method = DELETE_ONE)
	Integer deleteOne(@Param(P_NAME_ID) Object id, @Param(P_NAME_TYPE) Class<E> type);
}
