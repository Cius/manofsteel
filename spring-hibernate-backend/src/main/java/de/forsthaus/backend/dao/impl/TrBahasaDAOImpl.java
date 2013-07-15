package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrBahasaDAO;
import de.forsthaus.backend.model.TrBahasa;

public class TrBahasaDAOImpl extends BasisDAO<TrBahasa> implements TrBahasaDAO {

	@Override
	public TrBahasa getNew() {
		return new TrBahasa();
	}

	@Override
	public List<TrBahasa> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrBahasa.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrBahasa getById(int id) {
		return get(TrBahasa.class, id);
	}

	@Override
	public TrBahasa getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrBahasa.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrBahasa) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrBahasa")); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBahasaByJenis(String jenisBahasa) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrBahasa.class);	
		criteria.add(Restrictions.eq("jBahasa", jenisBahasa));
		criteria.add(Restrictions.not(Restrictions.eq("nBahasa", "BAHASA INDONESIA")));
		criteria.setProjection(Projections.distinct(Projections.property("nBahasa")));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
