package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.TrKeluargaDAO;
import de.forsthaus.backend.model.TrKeluarga;

public class TrKeluargaDAOImpl extends BasisDAO<TrKeluarga> implements TrKeluargaDAO {

	@Override
	public TrKeluarga getNew() {
		return new TrKeluarga();
	}

	@Override
	public List<TrKeluarga> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKeluarga.class);
		criteria.addOrder(Order.asc("nip"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public TrKeluarga getById(int id) {
		return get(TrKeluarga.class, id);
	}

	@Override
	public TrKeluarga getByNip(String nip) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TrKeluarga.class);
		criteria.add(Restrictions.eq("nip", nip));
		return (TrKeluarga) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("SELECT COUNT(nip) FROM TrKeluarga")); 
	}

}
