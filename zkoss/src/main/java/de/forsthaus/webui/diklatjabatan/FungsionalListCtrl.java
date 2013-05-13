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
package de.forsthaus.webui.diklatjabatan;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.diklatjabatan.model.DiklatJabatanListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/gabunganList.zul file.<br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * 
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 *          07/24/2009: sge changes for clustering.<br>
 *          10/12/2009: sge changings in the saving routine.<br>
 *          11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 *          (GenericForwardComposer) for spring-managed creation.<br>
 *          03/09/2009: sge changed for allow repainting after resizing.<br>
 *          with the refresh button.<br>
 * 
 * @author bbruhns
 * @author sgerth
 */
public class FungsionalListCtrl extends GFCBaseListCtrl<Gabungan> implements Serializable {


	private static final long serialVersionUID = 8328380361242901716L;

	private static final Logger logger = Logger.getLogger(FungsionalListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window fungsionalListWindow; // autowired
	protected Panel panel_FungsionalList; // autowired


	// listbox gabunganList
	protected Borderlayout borderLayout_FungsionalList; // autowired
	protected Paging paging_FungsionalList; // aurowired
	protected Listbox listBoxFungsional; // aurowired
	protected Listheader listheader_FungsionalList_Kode; // autowired
	protected Listheader listheader_FungsionalList_Nama; // autowired

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient GabunganDAO gabunganDAO;

	/**
	 * default constructor.<br>
	 */
	public FungsionalListCtrl() {
		super();
	}

	public void onCreate$fungsionalListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_FungsionalList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_FungsionalList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_FungsionalList_Kode.setSortAscending(new FieldComparator("kode", true));
		listheader_FungsionalList_Kode.setSortDescending(new FieldComparator("kode", false));
		listheader_FungsionalList_Nama.setSortAscending(new FieldComparator("nama", true));
		listheader_FungsionalList_Nama.setSortDescending(new FieldComparator("nama", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Gabungan> soGabungan = new HibernateSearchObject<Gabungan>(Gabungan.class, getCountRows());
		soGabungan.addFilterEqual("kodeTabel", "15");
		soGabungan.addSort("kode", false);

		// set the paging params
		paging_FungsionalList.setPageSize(getCountRows());
		paging_FungsionalList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soGabungan, listBoxFungsional, paging_FungsionalList);
		// set the itemRenderer
		listBoxFungsional.setItemRenderer(new DiklatJabatanListModelItemRenderer());

	}

	/**
	 * Call the Gabungan dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: de.forsthaus.webui.branch.model.BranchListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onDoubleClickedDiklatJabatanItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxFungsional.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			Gabungan aRight = (Gabungan) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the Gabungan dialog with a new empty entry. <br>
	 */
	public void onClick$button_FungsionalList_New(Event event) throws Exception {

		// create a new right object
		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		Gabungan golongan = getGabunganDAO().getNewGabungan();
		showDetailView(golongan);

	}

	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 * 
	 * @param fungsional
	 * @throws Exception
	 */
	private void showDetailView(Gabungan fungsional) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fungsional", fungsional);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxGabungan", listBoxFungsional);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/diklatjabatan/fungsionalDialog.zul", null, map);
		} catch (final Exception e) {
			logger.error("onOpenWindow:: error opening window / " + e.getMessage());

			// Show a error box
			String msg = e.getMessage();
			String title = Labels.getLabel("message.Error");

			MultiLineMessageBox.doSetTemplate();
			MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);

		}

	}

	/**
	 * when the "help" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnHelp(Event event) throws InterruptedException {
		ZksampleMessageUtils.doShowNotImplementedMessage();
	}

	/**
	 * when the "refresh" button is clicked. <br>
	 * <br>
	 * Refreshes the view by calling the onCreate event manually.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnRefresh(Event event) throws InterruptedException {

		Events.postEvent("onCreate", fungsionalListWindow, event);
		fungsionalListWindow.invalidate();
	}

//	/**
//	 * when the print button is clicked.
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$button_GabunganList_PrintRightList(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new GabunganSimpleDJReport(win);
//	}

	/**
	 * Filter the rights list with 'like' RightName'. <br>
	 * We check additionally if something is selected in the right type listbox <br>
	 * for including in the search statement.<br>
	 */
	

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++ Getter / Setter +++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public int getCountRows() {
		return this.countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	public GabunganDAO getGabunganDAO() {
		return this.gabunganDAO;
	}

	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.gabunganDAO = gabunganDAO;
	}

}
