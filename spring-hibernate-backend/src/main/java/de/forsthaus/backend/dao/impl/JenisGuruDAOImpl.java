package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.JenisGuruDAO;
import de.forsthaus.backend.model.JenisGuru;

@Repository
public class JenisGuruDAOImpl extends BasisDAO<JenisGuru> implements JenisGuruDAO, InitializingBean {

	@Override
	public JenisGuru getNewJenisGuru() {
		return new JenisGuru();
	}

	@Override
	public JenisGuru getJenisGuruById(long id) {
		return get(JenisGuru.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JenisGuru> getAllJenisGuru() {
		DetachedCriteria criteria = DetachedCriteria.forClass(JenisGuru.class);
		criteria.addOrder(Order.asc("kjnsGuru"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllJenisGurus() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from JenisGuru"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@Override
	public List<JenisGuru> getJenisGuruLikeName(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(JenisGuru.class);
		criteria.add(Restrictions.like("njnsGuru", string, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("kjnsGuru"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
