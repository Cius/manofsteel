package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrHukumanDAO;
import de.forsthaus.backend.model.TrHukuman;

public class TrHukumanDAOImpl extends BasisDAO<TrHukuman> implements TrHukumanDAO {

	@Override
	public TrHukuman getNew() {
		return new TrHukuman();
	}

	@Override
	public List<TrHukuman> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrHukuman.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrHukuman getById(int id) {
		return get(TrHukuman.class, id);
	}

	@Override
	public TrHukuman getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrHukuman.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrHukuman) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrHukuman")); 
	}

}
