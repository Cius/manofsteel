package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrOrganisasiDAO;
import de.forsthaus.backend.model.TrOrganisasi;

public class TrOrganisasiDAOImpl extends BasisDAO<TrOrganisasi> implements TrOrganisasiDAO {

	@Override
	public TrOrganisasi getNew() {
		return new TrOrganisasi();
	}

	@Override
	public List<TrOrganisasi> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrOrganisasi.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrOrganisasi getById(int id) {
		return get(TrOrganisasi.class, id);
	}

	@Override
	public TrOrganisasi getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrOrganisasi.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrOrganisasi) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrOrganisasi")); 
	}

}
