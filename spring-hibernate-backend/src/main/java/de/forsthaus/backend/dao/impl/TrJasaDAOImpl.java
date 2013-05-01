package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrJasaDAO;
import de.forsthaus.backend.model.TrJasa;

public class TrJasaDAOImpl extends BasisDAO<TrJasa> implements TrJasaDAO {

	@Override
	public TrJasa getNew() {
		return new TrJasa();
	}

	@Override
	public List<TrJasa> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrJasa.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrJasa getById(int id) {
		return get(TrJasa.class, id);
	}

	@Override
	public TrJasa getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrJasa.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrJasa) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrJasa")); 
	}

}
