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
package de.forsthaus.webui.unitkerja;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.UnitKerjaDAO;
import de.forsthaus.backend.model.UnitKerja;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.unitkerja.model.SatuanKerjaListModelItemRenderer;
import de.forsthaus.webui.unitkerja.model.UnitKerjaListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/satuanKerjaList.zul file.<br>
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
public class SatuanKerjaListCtrl extends GFCBaseListCtrl<UnitKerja> implements Serializable {


	private static final long serialVersionUID = 8328380361242901716L;

	private static final Logger logger = Logger.getLogger(SatuanKerjaListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window satuanKerjaListWindow; // autowired
	protected Panel panel_SatuanKerjaList; // autowired


	// listbox satuanKerjaList
	protected Borderlayout borderLayout_SatuanKerjaList; // autowired
	protected Paging paging_SatuanKerjaList; // aurowired
	protected Listbox listBoxSatuanKerja; // aurowired
	protected Listheader listheader_SatuanKerjaList_Kode; // autowired
	protected Listheader listheader_SatuanKerjaList_Nama; // autowired
	protected Listheader listheader_SatuanKerjaList_Eselon; // autowired

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient UnitKerjaDAO unitKerjaDAO;

	private Bandbox bandbox_UnitKerjaSearch;
	private Textbox tb_UnitKerja;
	private Listbox listBoxUnitKerjaSearch;
	private Paging paging_UnitKerjaSearchList;
	private PagedListWrapper<UnitKerja> plwUnitKerja;
	private Checkbox checkbox_SatuanKerjaList_ShowAll;

	private Listheader listheader_Kode;
	private Listheader listheader_Nama;
	private Listheader listheader_Eselon;
	private Listheader listheader_Kecamatan;
	
	/**
	 * default constructor.<br>
	 */
	public SatuanKerjaListCtrl() {
		super();
	}

	public void onCreate$satuanKerjaListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_SatuanKerjaList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_SatuanKerjaList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_SatuanKerjaList_Kode.setSortAscending(new FieldComparator("kunker", true));
		listheader_SatuanKerjaList_Kode.setSortDescending(new FieldComparator("kunker", false));
		listheader_SatuanKerjaList_Nama.setSortAscending(new FieldComparator("nunker", true));
		listheader_SatuanKerjaList_Nama.setSortDescending(new FieldComparator("nunker", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<UnitKerja> soSatuanKerja = new HibernateSearchObject<UnitKerja>(UnitKerja.class, getCountRows());
		soSatuanKerja.addFilterEqual("tunit", "3");
		soSatuanKerja.addSort("kunker", false);

		// set the paging params
		paging_SatuanKerjaList.setPageSize(getCountRows());
		paging_SatuanKerjaList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soSatuanKerja, listBoxSatuanKerja, paging_SatuanKerjaList);
		// set the itemRenderer
		listBoxSatuanKerja.setItemRenderer(new SatuanKerjaListModelItemRenderer());

	}
	
	public void onClick$button_bbox_UnitOrganisasi_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}
	
	public void onClick$button_bbox_UnitOrganisasi_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_UnitKerjaSearch.close();
	}
	
	private void doSearch() {
		HibernateSearchObject<UnitKerja> searchObj = new HibernateSearchObject<UnitKerja>(UnitKerja.class, 10);
		searchObj.addFilterEqual("tunit", "2");
		
		// check which field have input
		if (StringUtils.isNotEmpty(tb_UnitKerja.getValue())) {
			searchObj.addFilterLike("nunker", "%" + tb_UnitKerja.getValue() + "%");
		}

		// Set the ListModel.
		getPlwUnitKerja().init(searchObj, listBoxUnitKerjaSearch, paging_UnitKerjaSearchList);
	}
	
	public void onSelect$listBoxUnitKerjaSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = listBoxUnitKerjaSearch.getSelectedItem();
		ListModelList lml = null;
		UnitKerja uk = (UnitKerja) item.getAttribute("data");
		bandbox_UnitKerjaSearch.setValue(uk.getNunker());
		HibernateSearchObject<UnitKerja> soOrder = new HibernateSearchObject<UnitKerja>(UnitKerja.class, 20);
		soOrder.addSort("kunker", false);
				
		if (item != null) {
			lml = (ListModelList)listBoxSatuanKerja.getModel();
			lml.clear();
			soOrder.addFilterEqual("tunit", "3");
			soOrder.addFilterLike("kunker", uk.getKunker().substring(0, 5) + "%");
			getPagedListWrapper().init(soOrder, listBoxSatuanKerja, paging_SatuanKerjaList);
		}

		checkbox_SatuanKerjaList_ShowAll.setChecked(false);
		// close the bandbox
		bandbox_UnitKerjaSearch.close();

	}
	
	public void onCheck$checkbox_SatuanKerjaList_ShowAll(Event event) {

		// empty the text search boxes
		bandbox_UnitKerjaSearch.setValue(""); // clear

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<UnitKerja> soUnitKerja = new HibernateSearchObject<UnitKerja>(UnitKerja.class, getCountRows());
		soUnitKerja.addFilterEqual("tunit", "3");
		soUnitKerja.addSort("kunker", false);

		// Set the ListModel.
		getPagedListWrapper().init(soUnitKerja, listBoxSatuanKerja, paging_SatuanKerjaList);

	}
	
	public void onOpen$bandbox_UnitKerjaSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		listheader_Kode.setSortAscending(new FieldComparator("kunker", true));
		listheader_Kode.setSortDescending(new FieldComparator("kunker", false));
		listheader_Nama.setSortAscending(new FieldComparator("nunker", true));
		listheader_Nama.setSortDescending(new FieldComparator("nunker", false));
		listheader_Eselon.setSortAscending(new FieldComparator("eselon.nEselon", true));
		listheader_Eselon.setSortDescending(new FieldComparator("eselon.nEselon", false));
		listheader_Kecamatan.setSortAscending(new FieldComparator("kota", true));
		listheader_Kecamatan.setSortDescending(new FieldComparator("kota", false));

		// set the paging params
		paging_UnitKerjaSearchList.setPageSize(20);
		paging_UnitKerjaSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		HibernateSearchObject<UnitKerja> searchObject = new HibernateSearchObject<UnitKerja>(UnitKerja.class, 20);
		searchObject.addFilterEqual("tunit", "2");
		searchObject.addSort("kunker", false);

		// Set the ListModel.
		getPlwUnitKerja().init(searchObject, listBoxUnitKerjaSearch, paging_UnitKerjaSearchList);
		// set the itemRenderer
		listBoxUnitKerjaSearch.setItemRenderer(new UnitKerjaListModelItemRenderer());
	}

	/**
	 * Call the SatuanKerja dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: de.forsthaus.webui.branch.model.BranchListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onDoubleClickedSatuanKerjaItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxSatuanKerja.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			UnitKerja aRight = (UnitKerja) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the SatuanKerja dialog with a new empty entry. <br>
	 */
	public void onClick$button_SatuanKerjaList_New(Event event) throws Exception {

		// create a new right object
		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		UnitKerja golongan = getUnitKerjaDAO().getNewUnitKerja();
		showDetailView(golongan);

	}

	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 * 
	 * @param satuanKerja
	 * @throws Exception
	 */
	private void showDetailView(UnitKerja satuanKerja) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("satuanKerja", satuanKerja);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxSatuanKerja", listBoxSatuanKerja);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/unitkerja/satuanKerjaDialog.zul", null, map);
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

		Events.postEvent("onCreate", satuanKerjaListWindow, event);
		satuanKerjaListWindow.invalidate();
	}

//	/**
//	 * when the print button is clicked.
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$button_SatuanKerjaList_PrintRightList(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new SatuanKerjaSimpleDJReport(win);
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

	public UnitKerjaDAO getUnitKerjaDAO() {
		return this.unitKerjaDAO;
	}

	public void setUnitKerjaDAO(UnitKerjaDAO satuanKerjaDAO) {
		this.unitKerjaDAO = satuanKerjaDAO;
	}

	public PagedListWrapper<UnitKerja> getPlwUnitKerja() {
		return plwUnitKerja;
	}

	public void setPlwUnitKerja(PagedListWrapper<UnitKerja> plwUnitKerja) {
		this.plwUnitKerja = plwUnitKerja;
	}

}
