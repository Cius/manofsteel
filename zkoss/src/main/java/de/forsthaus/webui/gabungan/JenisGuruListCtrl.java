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
package de.forsthaus.webui.gabungan;

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
import de.forsthaus.backend.dao.JenisGuruDAO;
import de.forsthaus.backend.model.JenisGuru;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.gabungan.model.JenisGuruListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

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
public class JenisGuruListCtrl extends GFCBaseListCtrl<JenisGuru> implements Serializable {

	private static final long serialVersionUID = -6139454778139881103L;
	private static final Logger logger = Logger.getLogger(JenisGuruListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jenisGuruListWindow; // autowired
	protected Panel panel_JenisGuruList; // autowired


	// listbox gabunganList
	protected Borderlayout borderLayout_JenisGuruList; // autowired
	protected Paging paging_JenisGuruList; // aurowired
	protected Listbox listBoxJenisGuru; // aurowired
	protected Listheader listheader_JenisGuruList_Kode; // autowired
	protected Listheader listheader_JenisGuruList_Nama; // autowired

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient JenisGuruDAO jenisGuruDAO;
	
	private PagedListWrapper<JenisGuru> plwKelompokGuru;
	private Bandbox bandbox_KelompokGuruSearch;
	private Textbox tb_KelompokGuru;
	private Paging paging_KelompokGuruSearchList;
	private Listbox listBoxKelompokGuruSearch;
	private Checkbox checkbox_JenisGuruList_ShowAll;
	private Listheader listheader_Kode;
	private Listheader listheader_Nama;

	/**
	 * default constructor.<br>
	 */
	public JenisGuruListCtrl() {
		super();
	}

	public void onCreate$jenisGuruListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_JenisGuruList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_JenisGuruList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_JenisGuruList_Kode.setSortAscending(new FieldComparator("kjnsGuru", true));
		listheader_JenisGuruList_Kode.setSortDescending(new FieldComparator("kjnsGuru", false));
		listheader_JenisGuruList_Nama.setSortAscending(new FieldComparator("njnsGuru", true));
		listheader_JenisGuruList_Nama.setSortDescending(new FieldComparator("njnsGuru", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<JenisGuru> soGabungan = new HibernateSearchObject<JenisGuru>(JenisGuru.class, getCountRows());
		Filter f = Filter.like("kjnsGuru", "%00000");
		soGabungan.addFilterNot(f);
		soGabungan.addSort("kjnsGuru", false);

		// set the paging params
		paging_JenisGuruList.setPageSize(getCountRows());
		paging_JenisGuruList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soGabungan, listBoxJenisGuru, paging_JenisGuruList);
		// set the itemRenderer
		listBoxJenisGuru.setItemRenderer(new JenisGuruListModelItemRenderer());

	}
	
	public void onClick$button_bbox_KelompokGuru_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}
	
	public void onClick$button_bbox_KelompokGuru_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_KelompokGuruSearch.close();
	}
	
	private void doSearch() {
		HibernateSearchObject<JenisGuru> searchObj = new HibernateSearchObject<JenisGuru>(JenisGuru.class, 10);
		searchObj.addFilterEqual("tunit", "1");
		
		// check which field have input
		if (StringUtils.isNotEmpty(tb_KelompokGuru.getValue())) {
			searchObj.addFilterLike("nunker", "%" + tb_KelompokGuru.getValue() + "%");
		}

		// Set the ListModel.
		getPlwKelompokGuru().init(searchObj, listBoxKelompokGuruSearch, paging_KelompokGuruSearchList);
	}
	
	public void onSelect$listBoxKelompokGuruSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = this.listBoxKelompokGuruSearch.getSelectedItem();
		ListModelList lml = null;
		JenisGuru uk = (JenisGuru) item.getAttribute("data");
		bandbox_KelompokGuruSearch.setValue(uk.getNjnsGuru());
		HibernateSearchObject<JenisGuru> soOrder = new HibernateSearchObject<JenisGuru>(JenisGuru.class, 20);
		soOrder.addSort("kjnsGuru", false);
				
		if (item != null) {
			lml = (ListModelList)listBoxJenisGuru.getModel();
			lml.clear();
			soOrder.addFilterLike("kjnsGuru", uk.getKjnsGuru().substring(0, 1) + "%");
			Filter f = Filter.like("kjnsGuru", "%00000");
			soOrder.addFilterNot(f);
			getPagedListWrapper().init(soOrder, listBoxJenisGuru, paging_JenisGuruList);
		}

		checkbox_JenisGuruList_ShowAll.setChecked(false);
		// close the bandbox
		bandbox_KelompokGuruSearch.close();

	}
	
	public void onCheck$checkbox_JenisGuruList_ShowAll(Event event) {

		// empty the text search boxes
		bandbox_KelompokGuruSearch.setValue(""); // clear

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<JenisGuru> soUnitKerja = new HibernateSearchObject<JenisGuru>(JenisGuru.class, getCountRows());
		Filter f = Filter.like("kjnsGuru", "%00000");
		soUnitKerja.addFilterNot(f);
		soUnitKerja.addSort("kjnsGuru", false);

		// Set the ListModel.
		getPagedListWrapper().init(soUnitKerja, listBoxJenisGuru, paging_JenisGuruList);

	}
	
	public void onOpen$bandbox_KelompokGuruSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		listheader_Kode.setSortAscending(new FieldComparator("kjnsGuru", true));
		listheader_Kode.setSortDescending(new FieldComparator("kjnsGuru", false));
		listheader_Nama.setSortAscending(new FieldComparator("njnsGuru", true));
		listheader_Nama.setSortDescending(new FieldComparator("njnsGuru", false));

		// set the paging params
		paging_KelompokGuruSearchList.setPageSize(20);
		paging_KelompokGuruSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		HibernateSearchObject<JenisGuru> searchObject = new HibernateSearchObject<JenisGuru>(JenisGuru.class, 20);
		searchObject.addFilterLike("kjnsGuru", "%00000");
		searchObject.addSort("kjnsGuru", false);

		// Set the ListModel.
		getPlwKelompokGuru().init(searchObject, listBoxKelompokGuruSearch, paging_KelompokGuruSearchList);
		// set the itemRenderer
		listBoxKelompokGuruSearch.setItemRenderer(new JenisGuruListModelItemRenderer());
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
	public void onDoubleClickedJenisGuruItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxJenisGuru.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			JenisGuru aRight = (JenisGuru) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the Gabungan dialog with a new empty entry. <br>
	 */
	public void onClick$button_JenisGuruList_New(Event event) throws Exception {

		// create a new right object
		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		JenisGuru golongan = getJenisGuruDAO().getNewJenisGuru();
		showDetailView(golongan);

	}

	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 * 
	 * @param jenisGuru
	 * @throws Exception
	 */
	private void showDetailView(JenisGuru jenisGuru) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jenisGuru", jenisGuru);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxGabungan", listBoxJenisGuru);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/gabungan/jenisGuruDialog.zul", null, map);
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

		Events.postEvent("onCreate", jenisGuruListWindow, event);
		jenisGuruListWindow.invalidate();
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

	public JenisGuruDAO getJenisGuruDAO() {
		return jenisGuruDAO;
	}

	public void setJenisGuruDAO(JenisGuruDAO jenisGuruDAO) {
		this.jenisGuruDAO = jenisGuruDAO;
	}

	public PagedListWrapper<JenisGuru> getPlwKelompokGuru() {
		return plwKelompokGuru;
	}

	public void setPlwKelompokGuru(PagedListWrapper<JenisGuru> plwKelompokGuru) {
		this.plwKelompokGuru = plwKelompokGuru;
	}

}
