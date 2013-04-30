package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.model.TpCpns;

public class TpCpnsDAOImpl extends BasisDAO<TpCpns> implements TpCpnsDAO {

	@Override
	public TpCpns getNew() {
		return new TpCpns();
	}

	@Override
	public List<TpCpns> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpCpns.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpCpns getById(int id) {
		return get(TpCpns.class, id);
	}

	@Override
	public TpCpns getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpCpns.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TpCpns) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpCpns")); 
	}
}
