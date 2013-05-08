package de.forsthaus.webui.wilayah.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import de.forsthaus.backend.model.SecTyp;
import de.forsthaus.backend.model.Wilayah;
import de.forsthaus.webui.security.right.model.SecRightSecTypListModelItemRenderer;

public class WilayahListModelItemRenderer implements ListitemRenderer,
		Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SecRightSecTypListModelItemRenderer.class);

	@Override
	public void render(Listitem item, Object data) throws Exception {

		final Wilayah typ = (Wilayah) data;

		final Listcell lc = new Listcell(typ.getNamaWilayah());
		lc.setParent(item);

		item.setAttribute("data", data);
	}

}
