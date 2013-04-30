package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrDPPPDAO;
import de.forsthaus.backend.model.TrDPPP;

public class TrDPPPDAOImpl extends BasisDAO<TrDPPP> implements TrDPPPDAO {

	@Override
	public TrDPPP getNew() {
		return new TrDPPP();
	}

	@Override
	public List<TrDPPP> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDPPP.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrDPPP getById(int id) {
		return get(TrDPPP.class, id);
	}

	@Override
	public TrDPPP getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrDPPP.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrDPPP) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrDPPP")); 
	}

}
