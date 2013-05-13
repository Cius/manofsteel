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
package de.forsthaus.webui.tandajasa;

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

import com.googlecode.genericdao.search.Filter;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.SecRight;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.tandajasa.model.JenisTandaJasaListModelItemRenderer;
import de.forsthaus.webui.tandajasa.model.KelompokTandaJasaListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/jenisTandaJasaList.zul file.<br>
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
public class JenisTandaJasaListCtrl extends GFCBaseListCtrl<Gabungan> implements Serializable {


	private static final long serialVersionUID = 8328380361242901716L;

	private static final Logger logger = Logger.getLogger(JenisTandaJasaListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jenisTandaJasaListWindow; // autowired
	protected Panel panel_JenisTandaJasaList; // autowired


	// listbox jenisTandaJasaList
	protected Borderlayout borderLayout_JenisTandaJasaList; // autowired
	protected Paging paging_JenisTandaJasaList; // aurowired
	protected Listbox listBoxJenisTandaJasa; // aurowired
	protected Listheader listheader_JenisTandaJasaList_Kode; // autowired
	protected Listheader listheader_JenisTandaJasaList_Nama; // autowired
	
	protected Bandbox bandbox_KelompokTandaJasaSearch;
	protected Textbox tb_KelompokTandaJasa;
	protected Listheader listheader_Kode;
	protected Listheader listheader_Nama;
	protected Paging paging_KelompokTandaJasaSearchList;
	protected PagedListWrapper<Gabungan> plwKelompokTandaJasa;
	protected Listbox listBoxKelompokTandaJasaSearch;
	protected Checkbox checkbox_JenisTandaJasaList_ShowAll;

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient GabunganDAO GabunganDAO;

	/**
	 * default constructor.<br>
	 */
	public JenisTandaJasaListCtrl() {
		super();
	}

	public void onCreate$jenisTandaJasaListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_JenisTandaJasaList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_JenisTandaJasaList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_JenisTandaJasaList_Kode.setSortAscending(new FieldComparator("kode", true));
		listheader_JenisTandaJasaList_Kode.setSortDescending(new FieldComparator("kode", false));
		listheader_JenisTandaJasaList_Nama.setSortAscending(new FieldComparator("nama", true));
		listheader_JenisTandaJasaList_Nama.setSortDescending(new FieldComparator("nama", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Gabungan> soJenisTandaJasa = new HibernateSearchObject<Gabungan>(Gabungan.class, getCountRows());
		soJenisTandaJasa.addFilterEqual("kodeTabel", "13");
		Filter f = Filter.like("kode", "%00");
		soJenisTandaJasa.addFilterNot(f);
		soJenisTandaJasa.addSort("kode", false);

		// set the paging params
		paging_JenisTandaJasaList.setPageSize(getCountRows());
		paging_JenisTandaJasaList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soJenisTandaJasa, listBoxJenisTandaJasa, paging_JenisTandaJasaList);
		// set the itemRenderer
		listBoxJenisTandaJasa.setItemRenderer(new JenisTandaJasaListModelItemRenderer());

	}
	
	public void onClick$button_bbox_KelompokTandaJasa_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}
	
	public void onClick$button_bbox_KelompokTandaJasa_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_KelompokTandaJasaSearch.close();
	}
	
	private void doSearch() {
		HibernateSearchObject<Gabungan> searchObj = new HibernateSearchObject<Gabungan>(Gabungan.class, 10);
		searchObj.addFilterEqual("kodeTabel", "1");
		
		// check which field have input
		if (StringUtils.isNotEmpty(tb_KelompokTandaJasa.getValue())) {
			searchObj.addFilterLike("nama", "%" + tb_KelompokTandaJasa.getValue() + "%");
		}

		// Set the ListModel.
		getPlwKelompokTandaJasa().init(searchObj, listBoxKelompokTandaJasaSearch, paging_KelompokTandaJasaSearchList);
	}
	
	public void onSelect$listBoxKelompokTandaJasaSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = this.listBoxKelompokTandaJasaSearch.getSelectedItem();
		ListModelList lml = null;
		Gabungan uk = (Gabungan) item.getAttribute("data");
		bandbox_KelompokTandaJasaSearch.setValue(uk.getNama());
		HibernateSearchObject<Gabungan> soOrder = new HibernateSearchObject<Gabungan>(Gabungan.class, 20);
		soOrder.addSort("kode", false);
				
		if (item != null) {
			lml = (ListModelList)listBoxJenisTandaJasa.getModel();
			lml.clear();
			soOrder.addFilterEqual("kodeTabel", "13");
			soOrder.addFilterLike("kode", uk.getKode().substring(0, 1) + "%");
			Filter f = Filter.like("kode", "%00");
			soOrder.addFilterNot(f);
			getPagedListWrapper().init(soOrder, listBoxJenisTandaJasa, paging_JenisTandaJasaList);
		}

		checkbox_JenisTandaJasaList_ShowAll.setChecked(false);
		// close the bandbox
		bandbox_KelompokTandaJasaSearch.close();

	}
	
	public void onCheck$checkbox_JenisTandaJasaList_ShowAll(Event event) {

		// empty the text search boxes
		bandbox_KelompokTandaJasaSearch.setValue(""); // clear

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Gabungan> soJenisTandaJasa = new HibernateSearchObject<Gabungan>(Gabungan.class, getCountRows());
		soJenisTandaJasa.addFilterEqual("kodeTabel", "13");
		Filter f = Filter.like("kode", "%00");
		soJenisTandaJasa.addFilterNot(f);
		soJenisTandaJasa.addSort("kode", false);

		// Set the ListModel.
		getPagedListWrapper().init(soJenisTandaJasa, listBoxJenisTandaJasa, paging_JenisTandaJasaList);

	}
	
	public void onOpen$bandbox_KelompokTandaJasaSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		listheader_Kode.setSortAscending(new FieldComparator("kode", true));
		listheader_Kode.setSortDescending(new FieldComparator("kode", false));
		listheader_Nama.setSortAscending(new FieldComparator("nama", true));
		listheader_Nama.setSortDescending(new FieldComparator("nama", false));

		// set the paging params
		paging_KelompokTandaJasaSearchList.setPageSize(20);
		paging_KelompokTandaJasaSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		HibernateSearchObject<Gabungan> searchObject = new HibernateSearchObject<Gabungan>(Gabungan.class, 20);
		searchObject.addFilterEqual("kodeTabel", "13");
		searchObject.addFilterLike("kode", "%00");
		searchObject.addSort("kode", false);

		// Set the ListModel.
		getPlwKelompokTandaJasa().init(searchObject, listBoxKelompokTandaJasaSearch, paging_KelompokTandaJasaSearchList);
		// set the itemRenderer
		listBoxKelompokTandaJasaSearch.setItemRenderer(new KelompokTandaJasaListModelItemRenderer());
	}

	/**
	 * Call the JenisTandaJasa dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: de.forsthaus.webui.branch.model.BranchListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onDoubleClickedJenisTandaJasaItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxJenisTandaJasa.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			Gabungan aRight = (Gabungan) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the JenisTandaJasa dialog with a new empty entry. <br>
	 */
	public void onClick$button_JenisTandaJasaList_New(Event event) throws Exception {

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
	 * @param jenisTandaJasa
	 * @throws Exception
	 */
	private void showDetailView(Gabungan jenisTandaJasa) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jenisTandaJasa", jenisTandaJasa);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxJenisTandaJasa", listBoxJenisTandaJasa);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/tandajasa/jenisTandaJasaDialog.zul", null, map);
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

		Events.postEvent("onCreate", jenisTandaJasaListWindow, event);
		jenisTandaJasaListWindow.invalidate();
	}

//	/**
//	 * when the print button is clicked.
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$button_JenisTandaJasaList_PrintRightList(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new JenisTandaJasaSimpleDJReport(win);
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
		return this.GabunganDAO;
	}

	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.GabunganDAO = gabunganDAO;
	}

	public PagedListWrapper<Gabungan> getPlwKelompokTandaJasa() {
		return plwKelompokTandaJasa;
	}

	public void setPlwKelompokTandaJasa(PagedListWrapper<Gabungan> plwKelompokTandaJasa) {
		this.plwKelompokTandaJasa = plwKelompokTandaJasa;
	}

}
