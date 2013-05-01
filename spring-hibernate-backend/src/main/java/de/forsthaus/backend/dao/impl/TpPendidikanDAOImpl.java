package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpPendidikanDAO;
import de.forsthaus.backend.model.TpPendidikan;

public class TpPendidikanDAOImpl extends BasisDAO<TpPendidikan> implements TpPendidikanDAO {

	@Override
	public TpPendidikan getNew() {
		return new TpPendidikan();
	}

	@Override
	public List<TpPendidikan> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpPendidikan.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpPendidikan getById(int id) {
		return get(TpPendidikan.class, id);
	}

	@Override
	public TpPendidikan getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpPendidikan.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TpPendidikan) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpPendidikan")); 
	}

}
