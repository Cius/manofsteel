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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import de.forsthaus.backend.dao.DikumDAO;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.model.Dikum;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.Wilayah;

/**
 * Item renderer for listitems in the listbox.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 */
public class DikumListModelItemRenderer implements ListitemRenderer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DikumListModelItemRenderer.class);

	private transient DikumDAO dikumDAO;
	
	public DikumDAO getDikumDAO() {
		if (this.dikumDAO == null) {
			this.dikumDAO = (DikumDAO) SpringUtil.getBean("dikumDAO");
		}
		return this.dikumDAO;
	}

	public void setDikumDAO(DikumDAO dikumDAO) {
		this.dikumDAO = dikumDAO;
	}

	@Override
	public void render(Listitem item, Object data) throws Exception {

		final Dikum dikum = (Dikum) data;
		Listcell lc;

		lc = new Listcell(dikum.getNdik());
		lc.setStyle("padding-left: 5px");
		lc.setParent(item);

		item.setAttribute("data", data);

	}
}
