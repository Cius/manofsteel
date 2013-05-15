package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import de.forsthaus.backend.dao.JabFungDAO;
import de.forsthaus.backend.model.JabFung;

public class JabFungDAOImpl extends BasisDAO<JabFung> implements JabFungDAO {

	@Override
	public JabFung getNew() {
		return new JabFung();
	}

	@Override
	public List<JabFung> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(JabFung.class);
		criteria.addOrder(Order.asc("kodeJabFung"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public JabFung getById(int id) {
		return get(JabFung.class, id);
	}

	@Override
	public JabFung getByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(JabFung.class);
		criteria.add(Restrictions.eq("kodeJabFung", kode));
		
		try {
			return ((List<JabFung>) getHibernateTemplate().findByCriteria(criteria)).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public int getCount() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from JabFung"));
	}

}
