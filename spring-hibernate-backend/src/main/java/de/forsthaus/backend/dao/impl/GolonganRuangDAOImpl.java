package de.forsthaus.backend.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.Wilayah;

@Repository
public class GolonganRuangDAOImpl extends BasisDAO<GolonganRuang> implements GolonganRuangDAO, InitializingBean {

	@Override
	public GolonganRuang getNewGolonganRuang() {
		return new GolonganRuang();
	}

	@Override
	public GolonganRuang getGolonganRuangById(long id) {
		return get(GolonganRuang.class, Long.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GolonganRuang> getAllGolonganRuang() {
		DetachedCriteria criteria = DetachedCriteria.forClass(GolonganRuang.class);
		criteria.addOrder(Order.asc("kodeGolonganRuang"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int getCountAllGolonganRuangs() {
		return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from GolonganRuang"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// This is a test
		System.out.println("afterPropertiesSet" + this.toString());

	}

	@Override
	public List<GolonganRuang> getGolonganRuangLikeName(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GolonganRuang.class);
		criteria.add(Restrictions.like("namaGolonganRuang", string, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("kodeGolonganRuang"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<GolonganRuang> getGolonganRuangByKode(String string) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GolonganRuang.class);
		criteria.add(Restrictions.eq("kodeGolonganRuang", string));
		criteria.addOrder(Order.asc("kodeGolonganRuang"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

}
