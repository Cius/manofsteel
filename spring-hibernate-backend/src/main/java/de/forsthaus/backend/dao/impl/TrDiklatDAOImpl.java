package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrDiklatDAO;
import de.forsthaus.backend.model.TrDiklat;

public class TrDiklatDAOImpl extends BasisDAO<TrDiklat> implements TrDiklatDAO {

	@Override
	public TrDiklat getNew() {
		return new TrDiklat();
	}

	@Override
	public List<TrDiklat> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDiklat.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrDiklat getById(int id) {
		return get(TrDiklat.class, id);
	}

	@Override
	public TrDiklat getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDiklat.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrDiklat) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrDiklat")); 
	}

}
