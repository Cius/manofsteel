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
import de.forsthaus.backend.dao.TrJasaDAO;
import de.forsthaus.backend.model.TrJasa;
import de.forsthaus.backend.util.ConstantsText;

/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class RiwayatJasaListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RiwayatJasaListModelItemRenderer.class);

	private transient TrJasaDAO trJasaDAO;
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


	public TrJasaDAO getTrJasaDAO() {
		if (this.trJasaDAO == null) {
			this.trJasaDAO = (TrJasaDAO) SpringUtil.getBean("trJasaDAO");
		}
		return this.trJasaDAO;
	}


	public void setTrJasaDAO(TrJasaDAO trJasaDAO) {
		this.trJasaDAO = trJasaDAO;
	}


	@Override
	public void render(Listitem item, Object data) throws Exception {

		final TrJasa jasa = (TrJasa) data;
		Listcell lc;
		gabunganDAO = getGabunganDAO();

		lc = new Listcell(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_TANDAJASA, jasa.getKodeBintang()).getNama());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jasa.getPemberi());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		lc = new Listcell(jasa.getThJasa());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);
		
		item.setAttribute("data", data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");
	}
}
