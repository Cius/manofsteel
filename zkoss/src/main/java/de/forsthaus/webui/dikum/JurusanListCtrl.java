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
package de.forsthaus.webui.dikum;

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
import de.forsthaus.backend.dao.DikumDAO;
import de.forsthaus.backend.model.Dikum;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.dikum.model.JurusanGolRuangListModelItemRenderer;
import de.forsthaus.webui.unitkerja.model.UnitKerjaListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/jurusanList.zul file.<br>
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
public class JurusanListCtrl extends GFCBaseListCtrl<Dikum> implements Serializable {


	private static final long serialVersionUID = 8328380361242901716L;

	private static final Logger logger = Logger.getLogger(JurusanListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jurusanListWindow; // autowired
	protected Panel panel_JurusanList; // autowired


	// listbox jurusanList
	protected Borderlayout borderLayout_JurusanList; // autowired
	protected Paging paging_JurusanList; // aurowired
	protected Listbox listBoxJurusan; // aurowired
	protected Listheader listheader_JurusanList_Kode; // autowired
	protected Listheader listheader_JurusanList_Nama; // autowired
	protected Listheader listheader_JurusanList_Gawal; // autowired
	protected Listheader listheader_JurusanList_Gakhir; // autowired
	protected Listheader listheader_JurusanList_Rumpun; // autowired

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient DikumDAO dikumDAO;

	private Bandbox bandbox_JenjangPendidikanSearch;
	private Textbox tb_JenjangPendidikan;
	private Listbox listBoxJenjangPendidikanSearch;
	private Paging paging_JenjangPendidikanSearchList;
	private PagedListWrapper<Dikum> plwJenjangPendidikan;
	private Checkbox checkbox_JurusanList_ShowAll;

	private Listheader listheader_Kode;
	private Listheader listheader_Nama;
	private Listheader listheader_Gawal;
	private Listheader listheader_Gakhir;
	
	/**
	 * default constructor.<br>
	 */
	public JurusanListCtrl() {
		super();
	}

	public void onCreate$jurusanListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_JurusanList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_JurusanList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_JurusanList_Kode.setSortAscending(new FieldComparator("kjur", true));
		listheader_JurusanList_Kode.setSortDescending(new FieldComparator("kjur", false));
		listheader_JurusanList_Nama.setSortAscending(new FieldComparator("njur", true));
		listheader_JurusanList_Nama.setSortDescending(new FieldComparator("njur", false));
		listheader_JurusanList_Gawal.setSortAscending(new FieldComparator("gawal", true));
		listheader_JurusanList_Gawal.setSortDescending(new FieldComparator("gawal", false));
		listheader_JurusanList_Gakhir.setSortAscending(new FieldComparator("gakhir", true));
		listheader_JurusanList_Gakhir.setSortDescending(new FieldComparator("gakhir", false));
		listheader_JurusanList_Rumpun.setSortAscending(new FieldComparator("krumpun", true));
		listheader_JurusanList_Rumpun.setSortDescending(new FieldComparator("krumpun", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Dikum> soJurusan = new HibernateSearchObject<Dikum>(Dikum.class, getCountRows());
		soJurusan.addSort("kjur", false);

		// set the paging params
		paging_JurusanList.setPageSize(getCountRows());
		paging_JurusanList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soJurusan, listBoxJurusan, paging_JurusanList);
		// set the itemRenderer
		listBoxJurusan.setItemRenderer(new JurusanGolRuangListModelItemRenderer());

	}
	
	public void onClick$button_bbox_UnitOrganisasi_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}
	
	public void onClick$button_bbox_UnitOrganisasi_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_JenjangPendidikanSearch.close();
	}
	
	private void doSearch() {
		HibernateSearchObject<Dikum> searchObj = new HibernateSearchObject<Dikum>(Dikum.class, 20);
		searchObj.addFilterEqual("tunit", "2");
		
		// check which field have input
		if (StringUtils.isNotEmpty(tb_JenjangPendidikan.getValue())) {
			searchObj.addFilterLike("nunker", "%" + tb_JenjangPendidikan.getValue() + "%");
		}

		// Set the ListModel.
		getPlwJenjangPendidikan().init(searchObj, listBoxJenjangPendidikanSearch, paging_JenjangPendidikanSearchList);
	}
	
	public void onSelect$listBoxUnitKerjaSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = listBoxJenjangPendidikanSearch.getSelectedItem();
		ListModelList lml = null;
		Dikum uk = (Dikum) item.getAttribute("data");
		bandbox_JenjangPendidikanSearch.setValue(uk.getNjur());
		HibernateSearchObject<Dikum> soOrder = new HibernateSearchObject<Dikum>(Dikum.class, 20);
		soOrder.addSort("kunker", false);
				
		if (item != null) {
			lml = (ListModelList)listBoxJurusan.getModel();
			lml.clear();
//			soOrder.addFilterLike("kunker", uk.getKunker().substring(0, 5) + "%");
			getPagedListWrapper().init(soOrder, listBoxJurusan, paging_JurusanList);
		}

		checkbox_JurusanList_ShowAll.setChecked(false);
		// close the bandbox
		bandbox_JenjangPendidikanSearch.close();

	}
	
	public void onCheck$checkbox_JurusanList_ShowAll(Event event) {

		// empty the text search boxes
		bandbox_JenjangPendidikanSearch.setValue(""); // clear

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Dikum> soUnitKerja = new HibernateSearchObject<Dikum>(Dikum.class, getCountRows());
		soUnitKerja.addSort("kjur", false);

		// Set the ListModel.
		getPagedListWrapper().init(soUnitKerja, listBoxJurusan, paging_JurusanList);

	}
	
	public void onOpen$bandbox_UnitKerjaSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		listheader_Kode.setSortAscending(new FieldComparator("kunker", true));
		listheader_Kode.setSortDescending(new FieldComparator("kunker", false));
		listheader_Nama.setSortAscending(new FieldComparator("nunker", true));
		listheader_Nama.setSortDescending(new FieldComparator("nunker", false));
		listheader_Gawal.setSortAscending("");
		listheader_Gawal.setSortDescending("");
		listheader_Gakhir.setSortAscending("");
		listheader_Gakhir.setSortDescending("");

		// set the paging params
		paging_JenjangPendidikanSearchList.setPageSize(20);
		paging_JenjangPendidikanSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		HibernateSearchObject<Dikum> searchObject = new HibernateSearchObject<Dikum>(Dikum.class, 20);
		searchObject.addSort("kjur", false);

		// Set the ListModel.
		getPlwJenjangPendidikan().init(searchObject, listBoxJenjangPendidikanSearch, paging_JenjangPendidikanSearchList);
		// set the itemRenderer
		listBoxJenjangPendidikanSearch.setItemRenderer(new UnitKerjaListModelItemRenderer());
	}

	/**
	 * Call the Jurusan dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: de.forsthaus.webui.branch.model.BranchListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onDoubleClickedJurusanItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxJurusan.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			Dikum aRight = (Dikum) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the Jurusan dialog with a new empty entry. <br>
	 */
	public void onClick$button_JurusanList_New(Event event) throws Exception {

		// create a new right object
		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		Dikum golongan = getDikumDAO().getNewDikum();
		showDetailView(golongan);

	}

	/**
	 * Opens the detail view. <br>
	 * Overhanded some params in a map if needed. <br>
	 * 
	 * @param jurusan
	 * @throws Exception
	 */
	private void showDetailView(Dikum jurusan) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jurusan", jurusan);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxJurusan", listBoxJurusan);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/dikum/jurusanDialog.zul", null, map);
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

		Events.postEvent("onCreate", jurusanListWindow, event);
		jurusanListWindow.invalidate();
	}

//	/**
//	 * when the print button is clicked.
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$button_JurusanList_PrintRightList(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new JurusanSimpleDJReport(win);
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

	public PagedListWrapper<Dikum> getPlwJenjangPendidikan() {
		return plwJenjangPendidikan;
	}

	public void setPlwJenjangPendidikan(PagedListWrapper<Dikum> plwUnitKerja) {
		this.plwJenjangPendidikan = plwUnitKerja;
	}

	public DikumDAO getDikumDAO() {
		return dikumDAO;
	}

	public void setDikumDAO(DikumDAO dikumDAO) {
		this.dikumDAO = dikumDAO;
	}

}
