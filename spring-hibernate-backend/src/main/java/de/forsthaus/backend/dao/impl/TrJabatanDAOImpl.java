package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrJabatanDAO;
import de.forsthaus.backend.model.TrJabatan;

public class TrJabatanDAOImpl extends BasisDAO<TrJabatan> implements TrJabatanDAO {

	@Override
	public TrJabatan getNew() {
		return new TrJabatan();
	}

	@Override
	public List<TrJabatan> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrJabatan.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrJabatan getById(int id) {
		return get(TrJabatan.class, id);
	}

	@Override
	public TrJabatan getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrJabatan.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrJabatan) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrJabatan")); 
	}

}
