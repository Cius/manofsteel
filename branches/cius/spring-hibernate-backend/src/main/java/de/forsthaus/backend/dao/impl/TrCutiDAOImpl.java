package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrCutiDAO;
import de.forsthaus.backend.model.TrCuti;

public class TrCutiDAOImpl extends BasisDAO<TrCuti> implements TrCutiDAO {

	@Override
	public TrCuti getNew() {
		return new TrCuti();
	}

	@Override
	public List<TrCuti> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrCuti.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrCuti getById(int id) {
		return get(TrCuti.class, id);
	}

	@Override
	public TrCuti getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrCuti.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrCuti) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrCuti")); 
	}

}
