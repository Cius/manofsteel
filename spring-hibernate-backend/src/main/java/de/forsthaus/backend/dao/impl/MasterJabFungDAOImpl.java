
package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.forsthaus.backend.dao.MasterJabFungDAO;
import de.forsthaus.backend.model.MasterJabFung;
import de.forsthaus.backend.model.MasterUnitKerja;

public class MasterJabFungDAOImpl extends BasisDAO<MasterUnitKerja> implements MasterJabFungDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterJabFung> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MasterJabFung.class);
		criteria.addOrder(Order.asc("kodeJabFung"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterJabFung> findRumpun() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MasterJabFung.class);
		criteria.addOrder(Order.asc("kodeJabFung"));
		criteria.add(Restrictions.or(Restrictions.like("kodeJabFung", "___00"),Restrictions.like("kodeJabFung", "___")));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
