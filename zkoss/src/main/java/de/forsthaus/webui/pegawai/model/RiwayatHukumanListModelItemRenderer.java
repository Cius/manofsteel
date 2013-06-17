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
package de.forsthaus.webui.pegawai.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.TrHukumanDAO;
import de.forsthaus.backend.model.TrHukuman;
import de.forsthaus.backend.util.ConstantsText;

/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class RiwayatHukumanListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RiwayatHukumanListModelItemRenderer.class);

	private transient TrHukumanDAO trHukumanDAO;
	private transient GabunganDAO gabunganDAO;
	
	public GabunganDAO getGabunganDAO() {
		if (this.gabunganDAO == null) {
			this.gabunganDAO = (GabunganDAO) SpringUtil.getBean("gabunganDAO");
		}
		return this.gabunganDAO;
	}


	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.gabunganDAO = gabunganDAO;
	}


	public TrHukumanDAO getTrHukumanDAO() {
		if (this.trHukumanDAO == null) {
			this.trHukumanDAO = (TrHukumanDAO) SpringUtil.getBean("trHukumanDAO");
		}
		return this.trHukumanDAO;
	}


	public void setTrHukumanDAO(TrHukumanDAO trHukumanDAO) {
		this.trHukumanDAO = trHukumanDAO;
	}


	@Override
	public void render(Listitem item, Object data) throws Exception {

		final TrHukuman jabatan = (TrHukuman) data;
		Listcell lc;
		gabunganDAO = getGabunganDAO();
		
		lc = new Listcell(jabatan.getTkHukum());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		lc = new Listcell(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISHUK, jabatan.getJnsHukum()).getNama());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, jabatan.getKodePej()).getNama());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jabatan.getNumSkHukum());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jabatan.getTglSkHukum());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jabatan.getTglMulai());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jabatan.getTglAkhir());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		item.setAttribute("data", data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");
	}
}
