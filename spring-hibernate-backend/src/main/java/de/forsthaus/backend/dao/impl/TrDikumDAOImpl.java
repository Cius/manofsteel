package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrDikumDAO;
import de.forsthaus.backend.model.TrDikum;

public class TrDikumDAOImpl extends BasisDAO<TrDikum> implements TrDikumDAO {

	@Override
	public TrDikum getNew() {
		return new TrDikum();
	}

	@Override
	public List<TrDikum> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDikum.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrDikum getById(int id) {
		return get(TrDikum.class, id);
	}

	@Override
	public TrDikum getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDikum.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrDikum) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrDikum")); 
	}

}
