package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrKaryaTulisDAO;
import de.forsthaus.backend.model.TrKaryaTulis;

public class TrKaryaTulisDAOImpl extends BasisDAO<TrKaryaTulis> implements TrKaryaTulisDAO {

	@Override
	public TrKaryaTulis getNew() {
		return new TrKaryaTulis();
	}

	@Override
	public List<TrKaryaTulis> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKaryaTulis.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrKaryaTulis getById(int id) {
		return get(TrKaryaTulis.class, id);
	}

	@Override
	public TrKaryaTulis getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKaryaTulis.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrKaryaTulis) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrKaryaTulis")); 
	}

}
