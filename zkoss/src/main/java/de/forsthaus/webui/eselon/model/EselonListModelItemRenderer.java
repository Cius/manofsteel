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
package de.forsthaus.webui.eselon.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import de.forsthaus.backend.dao.EselonDAO;
import de.forsthaus.backend.model.Eselon;
import de.forsthaus.backend.model.Gabungan;

/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class EselonListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EselonListModelItemRenderer.class);

	private transient EselonDAO eselonDAO;

	public EselonDAO getEselonDAO() {
		if (this.eselonDAO == null) {
			this.eselonDAO = (EselonDAO) SpringUtil.getBean("eselonDAO");
		}
		return this.eselonDAO;
	}

	public void setEselonDAO(EselonDAO eselonDAO) {
		this.eselonDAO = eselonDAO;
	}

	@Override
	public void render(Listitem item, Object data) throws Exception {

		final Eselon eselon = (Eselon) data;
		Listcell lc;

		lc = new Listcell(eselon.getkEselon());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		lc = new Listcell(eselon.getnEselon());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		String gawal = eselon.getgAwal() == null ? "" : eselon.getgAwal().getNamaGolonganRuang();
		lc = new Listcell(gawal);
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		String gakhir = eselon.getgAkhir() == null ? "" : eselon.getgAkhir().getNamaGolonganRuang();
		lc = new Listcell(gakhir);
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		String tunJab = eselon.getTunJab() == null ? "" : String.valueOf(eselon.getTunJab());
		lc = new Listcell(tunJab);
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		
		String bup = eselon.getBup() == null ? "" : String.valueOf(eselon.getBup());
		lc = new Listcell(bup);
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		item.setAttribute("data", data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedEselonItem");

	}
}
