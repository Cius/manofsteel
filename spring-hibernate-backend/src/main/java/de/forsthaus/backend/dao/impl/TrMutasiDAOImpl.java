package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrMutasiDAO;
import de.forsthaus.backend.model.TrMutasi;

public class TrMutasiDAOImpl extends BasisDAO<TrMutasi> implements TrMutasiDAO {

	@Override
	public TrMutasi getNew() {
		return new TrMutasi();
	}

	@Override
	public List<TrMutasi> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrMutasi.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrMutasi getById(int id) {
		return get(TrMutasi.class, id);
	}

	@Override
	public TrMutasi getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrMutasi.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrMutasi) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrMutasi")); 
	}

}
