package com.lachesis.support.common.util.service;

import java.util.List;

public interface CrudService <E>{
	void addEntity(E entity);
	void updateEntity(E entity);
	E retrieveEntityById(Object id);
	List<E> retrieveEntitiesByCriteria(E criteria);
	void deleteEntity(Object id);
}
