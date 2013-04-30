package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrDukDAO;
import de.forsthaus.backend.model.TrDPPP;
import de.forsthaus.backend.model.TrDuk;

public class TrDukDAOImpl extends BasisDAO<TrDuk> implements TrDukDAO {

	@Override
	public TrDuk getNew() {
		return new TrDuk();
	}

	@Override
	public List<TrDuk> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDuk.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrDuk getById(int id) {
		return get(TrDuk.class, id);
	}

	@Override
	public TrDuk getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDuk.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrDuk) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrDuk")); 
	}

}
