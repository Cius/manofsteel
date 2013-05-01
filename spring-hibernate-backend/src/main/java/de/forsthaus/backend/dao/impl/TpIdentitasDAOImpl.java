package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TpIdentitasDAO;
import de.forsthaus.backend.model.TpIdentitas;

public class TpIdentitasDAOImpl extends BasisDAO<TpIdentitas> implements TpIdentitasDAO {

	@Override
	public TpIdentitas getNew() {
		return new TpIdentitas();
	}

	@Override
	public List<TpIdentitas> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpIdentitas.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TpIdentitas getById(int id) {
		return get(TpIdentitas.class, id);
	}

	@Override
	public TpIdentitas getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TpIdentitas.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TpIdentitas) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TpIdentitas")); 
	}

}
