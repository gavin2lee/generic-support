package com.lachesis.support.common.util.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.lachesis.support.common.util.dal.BaseRepository;

public abstract class AbstractCrudService<E> implements CrudService<E> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudService.class);

	@Transactional
	public void addEntity(E entity) {
		if (entity == null) {
			LOG.error("entity must be specified");
			throw new IllegalArgumentException("entity must be specified");
		}

		Integer ret = getBaseRepository().saveOne(entity, getEntityType());

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%s saved and return %d", entity.getClass().getName(), ret));
		}
	}

	@Transactional
	public void updateEntity(E entity) {
		if (entity == null) {
			LOG.error("entity must be specified");
			throw new IllegalArgumentException("entity must be specified");
		}
		Integer ret = getBaseRepository().updateOne(entity, getEntityType());

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%s updated and return %d", entity.getClass().getName(), ret));
		}
	}

	@Override
	public E retrieveEntityById(Object id) {
		if (id == null) {
			LOG.error("id must be specified");
			throw new IllegalArgumentException("id must be specified");
		}
		E entity = getBaseRepository().findOne(id, getEntityType());

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%s retrieved.", entity == null ? "null" : entity.getClass().getName()));
		}

		return entity;
	}

	@Override
	public List<E> retrieveEntitiesByCriteria(E criteria) {
		if (criteria == null) {
			LOG.error("criteria must be specified");
			throw new IllegalArgumentException("criteria must be specified");
		}

		List<E> entities = getBaseRepository().findListByCriteria(criteria, getEntityType());

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%s size %d retrieved", criteria.getClass().getName(),
					(entities == null ? 0 : entities.size())));
		}
		return entities;
	}

	@Transactional
	public void deleteEntity(Object id) {
		if (id == null) {
			LOG.error("id must be specified");
			throw new IllegalArgumentException("id must be specified");
		}
		
		Integer ret = getBaseRepository().deleteOne(id, getEntityType());
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s deleted and return %d", getEntityType().getName(), ret));
		}
	}

	protected abstract Class<E> getEntityType();

	protected abstract BaseRepository<E> getBaseRepository();

}
