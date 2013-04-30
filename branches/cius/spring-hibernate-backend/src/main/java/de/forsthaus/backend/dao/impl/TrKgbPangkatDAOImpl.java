package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrKgbPangkatDAO;
import de.forsthaus.backend.model.TrKgbPangkat;

public class TrKgbPangkatDAOImpl extends BasisDAO<TrKgbPangkat> implements TrKgbPangkatDAO {

	@Override
	public TrKgbPangkat getNew() {
		return new TrKgbPangkat();
	}

	@Override
	public List<TrKgbPangkat> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKgbPangkat.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrKgbPangkat getById(int id) {
		return get(TrKgbPangkat.class, id);
	}

	@Override
	public TrKgbPangkat getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKgbPangkat.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrKgbPangkat) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrKgbPangkat")); 
	}

}
