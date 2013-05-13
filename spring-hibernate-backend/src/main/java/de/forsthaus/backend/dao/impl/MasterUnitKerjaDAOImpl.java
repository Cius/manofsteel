
package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.model.MasterUnitKerja;

public class MasterUnitKerjaDAOImpl extends BasisDAO<MasterUnitKerja> implements MasterUnitKerjaDAO {

	@Override
	public List<MasterUnitKerja> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MasterUnitKerja.class);
		criteria.addOrder(Order.asc("nunker"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUnitKerja(String tunit) {
		return getHibernateTemplate().findByNamedParam("select distinct muk.nunker from MasterUnitKerja muk where muk.tunit in (:kode_tunit) order by muk.nunker","kode_tunit",tunit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterUnitKerja> findbyUnitKerja(String tunit) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MasterUnitKerja.class);
		criteria.addOrder(Order.asc("nunker"));
		criteria.add(Restrictions.eq("tUnit", tunit));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
