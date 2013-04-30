package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpJabatanDAO;
import de.forsthaus.backend.model.TpJabatan;

public class TpJabatanDAOImpl extends BasisDAO<TpJabatan> implements TpJabatanDAO {

	@Override
	public TpJabatan getNew() {
		return new TpJabatan();
	}

	@Override
	public List<TpJabatan> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpJabatan.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpJabatan getById(int id) {
		return get(TpJabatan.class, id);
	}

	@Override
	public TpJabatan getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpJabatan.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TpJabatan) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpJabatan")); 
	}

}
