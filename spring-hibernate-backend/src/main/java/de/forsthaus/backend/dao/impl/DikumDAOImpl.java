package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.DikumDAO;
import de.forsthaus.backend.model.Dikum;

@Repository
public class DikumDAOImpl extends BasisDAO<Dikum> implements DikumDAO, InitializingBean {

	@Override
	public Dikum getNewDikum() {
		return new Dikum();
	}

	@Override
	public Dikum getDikumById(long id) {
		return get(Dikum.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dikum> getAllDikum() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dikum.class);
		criteria.addOrder(Order.asc("ktpu"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllDikums() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from Dikum"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@Override
	public List<Dikum> getDikumLikeName(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dikum.class);
		criteria.add(Restrictions.like("ndik", string, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("ktpu"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dikum> getDikumForTingkatPendidikan() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dikum.class);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("ktpu"))
															.add(Projections.groupProperty("ndik")));
		criteria.addOrder(Order.asc("ktpu"));

		return getHibernateTemplate().find("SELECT new Dikum(id, version, ktpu, ndik) from Dikum dikum group by dikum.ktpu");
	}

	@Override
	public Dikum getDikumByKtpu(String kodeTingkat) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dikum.class);
		criteria.add(Restrictions.eq("ktpu", kodeTingkat));
		criteria.addOrder(Order.asc("ndik"));
		try {
			return ((List<Dikum>) getHibernateTemplate().findByCriteria(criteria)).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}		 
	}

}
