package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpKgbPangkatDAO;
import de.forsthaus.backend.model.TpKgbPangkat;

public class TpKgbPangkatDAOImpl extends BasisDAO<TpKgbPangkat> implements TpKgbPangkatDAO {

	@Override
	public TpKgbPangkat getNew() {
		return new TpKgbPangkat();
	}

	@Override
	public List<TpKgbPangkat> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpKgbPangkat.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpKgbPangkat getById(int id) {
		return get(TpKgbPangkat.class, id);
	}

	@Override
	public TpKgbPangkat getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpKgbPangkat.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TpKgbPangkat) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpKgbPangkat")); 
	}

}
