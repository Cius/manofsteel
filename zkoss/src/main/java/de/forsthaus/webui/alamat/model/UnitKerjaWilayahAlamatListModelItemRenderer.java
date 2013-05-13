/**
 * Copyright 2010 the original author or authors.
 * 
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package de.forsthaus.webui.alamat.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import de.forsthaus.backend.dao.UnitKerjaDAO;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.Eselon;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.SecTyp;
import de.forsthaus.backend.model.UnitKerja;
import de.forsthaus.backend.model.Wilayah;

/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class UnitKerjaWilayahAlamatListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UnitKerjaWilayahAlamatListModelItemRenderer.class);

	private transient UnitKerjaDAO unitKerjaDAO;
	private transient WilayahDAO wilayahDAO;

	public UnitKerjaDAO getUnitKerjaDAO() {
		if (this.unitKerjaDAO == null) {
			this.unitKerjaDAO = (UnitKerjaDAO) SpringUtil.getBean("unitKerjaDAO");
		}
		return this.unitKerjaDAO;
	}

	public void setUnitKerjaDAO(UnitKerjaDAO unitKerjaDAO) {
		this.unitKerjaDAO = unitKerjaDAO;
	}

	public WilayahDAO getWilayahDAO() {
		if (this.wilayahDAO == null) {
			this.wilayahDAO = (WilayahDAO) SpringUtil.getBean("wilayahDAO");
		}
		return this.wilayahDAO;
	}

	public void setWilayahDAO(WilayahDAO wilayahDAO) {
		this.wilayahDAO = wilayahDAO;
	}

	@Override
	public void render(Listitem item, Object data) throws Exception {

		final UnitKerja unitKerja = (UnitKerja) data;
		Listcell lc;

		lc = new Listcell(unitKerja.getNunker());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		lc = new Listcell(unitKerja.getAlamat());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(unitKerja.getKota());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(unitKerja.getTelp());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		item.setAttribute("data", data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedUnitKerjaAlamatItem");

	}

}