package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.GajiPokokDAO;
import de.forsthaus.backend.model.GajiPokok;

@Repository
public class GajiPokokDAOImpl extends BasisDAO<GajiPokok> implements GajiPokokDAO, InitializingBean {

	@Override
	public GajiPokok getNewGajiPokok() {
		return new GajiPokok();
	}

	@Override
	public GajiPokok getGajiPokokById(long id) {
		return get(GajiPokok.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GajiPokok> getAllGajiPokok() {
		DetachedCriteria criteria = DetachedCriteria.forClass(GajiPokok.class);
		criteria.addOrder(Order.asc("golonganRuang.kodeGolonganRuang"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllGajiPokoks() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from GajiPokok"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@Override
	public List<GajiPokok> getGajiPokokLikeName(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GajiPokok.class);
		criteria.add(Restrictions.like("namaGajiPokok", string, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("kodeGajiPokok"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
