package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.EselonDAO;
import de.forsthaus.backend.model.Eselon;

@Repository
public class EselonDAOImpl extends BasisDAO<Eselon> implements EselonDAO, InitializingBean {

	@Override
	public Eselon getNewEselon() {
		return new Eselon();
	}

	@Override
	public Eselon getEselonById(long id) {
		return get(Eselon.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Eselon> getAllEselon() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Eselon.class);
		criteria.addOrder(Order.asc("kEselon"));

		return getHibernateTemplate().findByCriteria(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eselon> getEselonsLikeName(String string) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Eselon.class);
		criteria.add(Restrictions.ilike("kEselon", string, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllEselons() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from Eselon"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Eselon> getEselonByType(String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Eselon.class);
		criteria.add(Restrictions.ilike("tipeEselon", type, MatchMode.ANYWHERE));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public Eselon getEselonByKode(String kode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Eselon.class);
		criteria.add(Restrictions.eq("kodeEselon", kode));

		return (Eselon)getHibernateTemplate().findByCriteria(criteria).get(0);
	}

}
