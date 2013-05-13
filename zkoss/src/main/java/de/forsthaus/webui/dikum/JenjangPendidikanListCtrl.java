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
import de.forsthaus.backend.model.SecRight;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.dikum.model.JenjangPendidikanGolRuangListModelItemRenderer;
import de.forsthaus.webui.dikum.model.TingkatPendidikanListModelItemRenderer;
import de.forsthaus.webui.unitkerja.model.UnitOrganisasiListModelItemRenderer;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/jenjangPendidikanList.zul file.<br>
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
public class JenjangPendidikanListCtrl extends GFCBaseListCtrl<Dikum> implements Serializable {


	private static final long serialVersionUID = 8328380361242901716L;

	private static final Logger logger = Logger.getLogger(JenjangPendidikanListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jenjangPendidikanListWindow; // autowired
	protected Panel panel_JenjangPendidikanList; // autowired


	// listbox jenjangPendidikanList
	protected Borderlayout borderLayout_JenjangPendidikanList; // autowired
	protected Paging paging_JenjangPendidikanList; // aurowired
	protected Listbox listBoxJenjangPendidikan; // aurowired
	protected Listheader listheader_JenjangPendidikanList_Kode; // autowired
	protected Listheader listheader_JenjangPendidikanList_Nama; // autowired
	protected Listheader listheader_JenjangPendidikanList_Gawal; // autowired
	protected Listheader listheader_JenjangPendidikanList_Gakhir; // autowired
	
	protected Bandbox bandbox_TingkatPendidikanSearch;
	protected Textbox tb_TingkatPendidikan;
	protected Listheader listheader_Kode;
	protected Listheader listheader_Nama;
	protected Paging paging_TingkatPendidikanSearchList;
	protected PagedListWrapper<Dikum> plwTingkatPendidikan;
	protected Listbox listBoxTingkatPendidikanSearch;
	protected Checkbox checkbox_JenjangPendidikanList_ShowAll;

	// row count for listbox
	private int countRows;

	// ServiceDAOs / Domain Classes
	private transient DikumDAO dikumDAO;

	/**
	 * default constructor.<br>
	 */
	public JenjangPendidikanListCtrl() {
		super();
	}

	public void onCreate$jenjangPendidikanListWindow(Event event) throws Exception {
		/**
		 * Calculate how many rows have been place in the listbox. Get the
		 * currentDesktopHeight from a hidden Intbox from the index.zul that are
		 * filled by onClientInfo() in the indexCtroller
		 */

		int panelHeight = 25;
		// TODO put the logic for working with panel in the ApplicationWorkspace
		panel_JenjangPendidikanList.setVisible(false);

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		height = height + panelHeight;
		final int maxListBoxHeight = height - 148;
		setCountRows(Math.round(maxListBoxHeight / 17));
		// System.out.println("MaxListBoxHeight : " + maxListBoxHeight);
		// System.out.println("==========> : " + getCountRows());

		borderLayout_JenjangPendidikanList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_JenjangPendidikanList_Kode.setSortAscending(new FieldComparator("kjur", true));
		listheader_JenjangPendidikanList_Kode.setSortDescending(new FieldComparator("kjur", false));
		listheader_JenjangPendidikanList_Nama.setSortAscending(new FieldComparator("njur", true));
		listheader_JenjangPendidikanList_Nama.setSortDescending(new FieldComparator("njur", false));
		listheader_JenjangPendidikanList_Gawal.setSortAscending(new FieldComparator("gawal", true));
		listheader_JenjangPendidikanList_Gawal.setSortDescending(new FieldComparator("gawal", false));
		listheader_JenjangPendidikanList_Gakhir.setSortAscending(new FieldComparator("gakhir", true));
		listheader_JenjangPendidikanList_Gakhir.setSortDescending(new FieldComparator("gakhir", false));

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Dikum> soJenjangPendidikan = new HibernateSearchObject<Dikum>(Dikum.class, getCountRows());
		soJenjangPendidikan.addFilterLike("kjur", "%00000");
		soJenjangPendidikan.addSort("kjur", false);

		// set the paging params
		paging_JenjangPendidikanList.setPageSize(getCountRows());
		paging_JenjangPendidikanList.setDetailed(true);

		// Set the ListModel.
		getPagedListWrapper().init(soJenjangPendidikan, listBoxJenjangPendidikan, paging_JenjangPendidikanList);
		// set the itemRenderer
		listBoxJenjangPendidikan.setItemRenderer(new JenjangPendidikanGolRuangListModelItemRenderer());

	}
	
	public void onClick$button_bbox_TingkatPendidikan_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}
	
	public void onClick$button_bbox_TingkatPendidikan_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_TingkatPendidikanSearch.close();
	}
	
	private void doSearch() {
		HibernateSearchObject<Dikum> searchObj = new HibernateSearchObject<Dikum>(Dikum.class, 10);
		
		// check which field have input
		if (StringUtils.isNotEmpty(tb_TingkatPendidikan.getValue())) {
			searchObj.addFilterLike("nunker", "%" + tb_TingkatPendidikan.getValue() + "%");
		}

		// Set the ListModel.
		getPlwTingkatPendidikan().init(searchObj, listBoxTingkatPendidikanSearch, paging_TingkatPendidikanSearchList);
	}
	
	public void onSelect$listBoxTingkatPendidikanSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = this.listBoxTingkatPendidikanSearch.getSelectedItem();
		ListModelList lml = null;
		Dikum uk = (Dikum) item.getAttribute("data");
		bandbox_TingkatPendidikanSearch.setValue(uk.getNjur());
		HibernateSearchObject<Dikum> soOrder = new HibernateSearchObject<Dikum>(Dikum.class, 20);
		soOrder.addSort("kjur", false);
				
		if (item != null) {
			lml = (ListModelList)listBoxJenjangPendidikan.getModel();
			lml.clear();
			soOrder.addFilterLike("kjur", "%00000");
			soOrder.addFilterLike("kjur", uk.getKjur().substring(0, 1) + "%");
			getPagedListWrapper().init(soOrder, listBoxJenjangPendidikan, paging_JenjangPendidikanList);
		}

		checkbox_JenjangPendidikanList_ShowAll.setChecked(false);
		// close the bandbox
		bandbox_TingkatPendidikanSearch.close();

	}
	
	public void onCheck$checkbox_JenjangPendidikanList_ShowAll(Event event) {

		// empty the text search boxes
		bandbox_TingkatPendidikanSearch.setValue(""); // clear

		// ++ create the searchObject and init sorting ++//
		HibernateSearchObject<Dikum> soJenjangPendidikan = new HibernateSearchObject<Dikum>(Dikum.class, getCountRows());
		soJenjangPendidikan.addSort("kjur", false);

		// Set the ListModel.
		getPagedListWrapper().init(soJenjangPendidikan, listBoxJenjangPendidikan, paging_JenjangPendidikanList);

	}
	
	public void onOpen$bandbox_TingkatPendidikanSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		listheader_Kode.setSortAscending(new FieldComparator("ktpu", true));
		listheader_Kode.setSortDescending(new FieldComparator("ktpu", false));
		listheader_Nama.setSortAscending(new FieldComparator("ndik", true));
		listheader_Nama.setSortDescending(new FieldComparator("ndik", false));

		// set the paging params
		paging_TingkatPendidikanSearchList.setPageSize(20);
		paging_TingkatPendidikanSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		HibernateSearchObject<Dikum> searchObject = new HibernateSearchObject<Dikum>(Dikum.class, 20);
		searchObject.addSort("ktpu", false);

		// Set the ListModel.
		getPlwTingkatPendidikan().init(searchObject, listBoxTingkatPendidikanSearch, paging_TingkatPendidikanSearchList);
		// set the itemRenderer
		listBoxTingkatPendidikanSearch.setItemRenderer(new TingkatPendidikanListModelItemRenderer());
	}

	/**
	 * Call the JenjangPendidikan dialog with the selected entry. <br>
	 * <br>
	 * This methode is forwarded from the listboxes item renderer. <br>
	 * see: de.forsthaus.webui.branch.model.BranchListModelItemRenderer.java <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onDoubleClickedJenjangPendidikanItem(Event event) throws Exception {

		// get the selected object
		Listitem item = this.listBoxJenjangPendidikan.getSelectedItem();

		if (item != null) {
			// CAST AND STORE THE SELECTED OBJECT
			Dikum aRight = (Dikum) item.getAttribute("data");

			showDetailView(aRight);
		}
	}

	/**
	 * Call the JenjangPendidikan dialog with a new empty entry. <br>
	 */
	public void onClick$button_JenjangPendidikanList_New(Event event) throws Exception {

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
	 * @param jenjangPendidikan
	 * @throws Exception
	 */
	private void showDetailView(Dikum jenjangPendidikan) throws Exception {

		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jenjangPendidikan", jenjangPendidikan);
		/*
		 * we can additionally handed over the listBox, so we have in the dialog
		 * access to the listbox Listmodel. This is fine for syncronizing the
		 * data in the customerListbox from the dialog when we do a delete, edit
		 * or insert a customer.
		 */
		map.put("listBoxJenjangPendidikan", listBoxJenjangPendidikan);

		// call the zul-file with the parameters packed in a map
		try {
			Executions.createComponents("/WEB-INF/pages/dikum/jenjangPendidikanDialog.zul", null, map);
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

		Events.postEvent("onCreate", jenjangPendidikanListWindow, event);
		jenjangPendidikanListWindow.invalidate();
	}

//	/**
//	 * when the print button is clicked.
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$button_JenjangPendidikanList_PrintRightList(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new JenjangPendidikanSimpleDJReport(win);
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

	public PagedListWrapper<Dikum> getPlwTingkatPendidikan() {
		return plwTingkatPendidikan;
	}

	public void setPlwTingkatPendidikan(PagedListWrapper<Dikum> plwTingkatPendidikan) {
		this.plwTingkatPendidikan = plwTingkatPendidikan;
	}

	public DikumDAO getDikumDAO() {
		return dikumDAO;
	}

	public void setDikumDAO(DikumDAO dikumDAO) {
		this.dikumDAO = dikumDAO;
	}

}
