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
package de.forsthaus.webui.wilayah;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.Wilayah;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the /WEB-INF/pages/kecamatan/kecamatanList.zul
 * file.<br>
 * <b>WORKS with the annotated databinding mechanism.</b><br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * 
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 *          07/24/2009: sge changings for clustering.<br>
 *          11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 *          (GenericForwardComposer) for spring-managed creation.<br>
 *          03/09/2009: sge changed for allow repainting after resizing.<br>
 *          with the refresh button.<br>
 *          07/03/2010: sge modified for zk5.x with complete Annotated
 *          Databinding.<br>
 * 
 * @author bbruhns
 * @author Stephan Gerth
 */
public class KecamatanListCtrl extends GFCBaseListCtrl<Wilayah> implements Serializable {

	private static final long serialVersionUID = 2038742641853727975L;
	private static final Logger logger = Logger.getLogger(KecamatanListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window windowKecamatanList; // autowired

	// listbox kecamatans
	protected Borderlayout borderLayout_kecamatanList; // autowired
	private Paging paging_KecamatanList; // autowired
	protected Listbox listBoxKecamatan; // autowired
	protected Listheader listheader_KecamatanList_Kode; // autowired
	protected Listheader listheader_KecamatanList_Nama; // autowired
	protected Listheader listheader_KecamatanList_Ibukota; // autowired

	// textbox long description
	protected Textbox longBoxArt_LangBeschreibung; // autowired

	// count of rows in the listbox
	private int countRows;

	// NEEDED for ReUse in a SearchWindow
	private HibernateSearchObject<Wilayah> searchObj;

	// Databinding
	private AnnotateDataBinder binder;
	private PropinsiMainCtrl propinsiMainCtrl;

	// ServiceDAOs / Domain Classes
	private transient WilayahDAO wilayahDAO;

	/**
	 * default constructor.<br>
	 */
	public KecamatanListCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		/**
		 * 1. Set an 'alias' for this composer name to access it in the
		 * zul-file.<br>
		 * 2. Set the parameter 'recurse' to 'false' to avoid problems with
		 * managing more than one zul-file in one page. Otherwise it would be
		 * overridden and can ends in curious error messages.
		 */
		this.self.setAttribute("controller", this, false);

		/**
		 * 1. Get the overhanded MainController.<br>
		 * 2. Set this controller in the MainController.<br>
		 * 3. Check if a 'selectedObject' exists yet in the MainController.<br>
		 */
		if (arg.containsKey("ModuleMainController")) {
			setPropinsiMainCtrl((PropinsiMainCtrl) arg.get("ModuleMainController"));

			// SET THIS CONTROLLER TO THE module's Parent/MainController
			getPropinsiMainCtrl().setKecamatanListCtrl(this);

			// Get the selected object.
			// Check if this Controller if created on first time. If so,
			// than the selectedXXXBean should be null
			if (getPropinsiMainCtrl().getSelectedKecamatan() != null) {
				setSelectedKecamatan(getPropinsiMainCtrl().getSelectedKecamatan());
			} else
				setSelectedKecamatan(null);
		} else {
			setSelectedKecamatan(null);
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++ Component Events ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	public void onCreate$windowKecamatanList(Event event) throws Exception {

		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

		doFillListbox();

		binder.loadAll();
	}

	public void doFillListbox() {

		doFitSize();

		// set the paging params
		getPaging_KecamatanList().setPageSize(getCountRows());
		getPaging_KecamatanList().setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_KecamatanList_Kode.setSortAscending(new FieldComparator("kodeWilayah", true));
		listheader_KecamatanList_Kode.setSortDescending(new FieldComparator("kodeWilayah", false));
		listheader_KecamatanList_Nama.setSortAscending(new FieldComparator("namaWilayah", true));
		listheader_KecamatanList_Nama.setSortDescending(new FieldComparator("namaWilayah", false));
		listheader_KecamatanList_Ibukota.setSortAscending(new FieldComparator("ibukota", true));
		listheader_KecamatanList_Ibukota.setSortDescending(new FieldComparator("ibukota", false));

		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<Wilayah>(Wilayah.class, getCountRows());
		searchObj.addFilterEqual("tipeWilayah", 3);
		searchObj.addSort("kodeWilayah", false);
		setSearchObj(this.searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBoxKecamatan(), getPaging_KecamatanList());
		BindingListModelList lml = (BindingListModelList) getListBoxKecamatan().getModel();
		setKecamatans(lml);

		// Now we would select and show the text of the first entry in the list.
		// We became not the first item FROM the listbox because it's NOT
		// RENDERED AT THIS TIME.
		// So we take the first entry from the MODEL (ListModelList) and set as
		// selected.
		// check if first time opened and init databinding for selectedBean
		if (getSelectedKecamatan() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBoxKecamatan().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				setSelectedKecamatan((Wilayah) lml.get(0));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBoxKecamatan(), getSelectedKecamatan()));
			}
		}
	}

	/**
	 * Selects the object in the listbox and change the tab.<br>
	 * Event is forwarded in the corresponding listbox.
	 */
	public void onDoubleClickedKecamatanItem(Event event) throws InterruptedException {
		Wilayah anKecamatan = getSelectedKecamatan();

		if (anKecamatan != null) {
			setSelectedKecamatan(anKecamatan);
			setKecamatan(anKecamatan);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("wilayah", anKecamatan);
			map.put("tabSelected", 2);
			map.put("listbox", getListBoxKecamatan());

			try {
				Executions.createComponents("/WEB-INF/pages/wilayah/PropinsiDialog.zul", null, map);
			} catch (final Exception e) {
				logger.error("onOpenWindow:: error opening window / " + e.getMessage());

				// Show a error box
				String msg = e.getMessage();
				String title = Labels.getLabel("message.Error");

				MultiLineMessageBox.doSetTemplate();
				MultiLineMessageBox.show(msg, title, MultiLineMessageBox.OK, "ERROR", true);

			}
		}
	}

	/**
	 * When a listItem in the corresponding listbox is selected.<br>
	 * Event is forwarded in the corresponding listbox.
	 * 
	 * @param event
	 */
	public void onSelect$listBoxKecamatan(Event event) {
//		// logger.debug(event.toString());
//
//		// selectedKecamatan is filled by annotated databinding mechanism
//		Wilayah anKecamatan = getSelectedKecamatan();
//
//		if (anKecamatan == null) {
//			return;
//		}
//
//		// check first, if the tabs are created
//		if (getKecamatanMainCtrl().getKecamatanDetailCtrl() == null) {
//			Events.sendEvent(new Event("onSelect", getKecamatanMainCtrl().tabKecamatanDetail, null));
//			// if we work with spring beanCreation than we must check a little
//			// bit deeper, because the Controller are preCreated ?
//		} else if (getKecamatanMainCtrl().getKecamatanDetailCtrl().getBinder() == null) {
//			Events.sendEvent(new Event("onSelect", getKecamatanMainCtrl().tabKecamatanDetail, null));
//		}
//
//		// INIT ALL RELATED Queries/OBJECTS/LISTS NEW
////		getKecamatanMainCtrl().getKecamatanDetailCtrl().setSelectedKecamatan(anKecamatan);
////		getKecamatanMainCtrl().getKecamatanDetailCtrl().setKecamatan(anKecamatan);
//
//		// store the selected bean values as current
//		getKecamatanMainCtrl().doStoreInitValues();
//
//		// show the objects data in the statusBar
////		final String str = Labels.getLabel("common.Kecamatan") + ": " + anKecamatan.getArtKurzbezeichnung();
////		EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++++ Business Logic ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Recalculates the container size for this controller and resize them.
	 * 
	 * Calculate how many rows have been place in the listbox. Get the
	 * currentDesktopHeight from a hidden Intbox from the index.zul that are
	 * filled by onClientInfo() in the indexCtroller.
	 */
	public void doFitSize() {

		// normally 0 ! Or we have a i.e. a toolBar on top of the listBox.
		final int specialSize = 0;

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - specialSize - 152;
		setCountRows((int) Math.round(maxListBoxHeight / 18.4));
		borderLayout_kecamatanList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowKecamatanList.invalidate();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	/**
	 * Best Pratice Hint:<br>
	 * The setters/getters for the local annotated data binded Beans/Sets are
	 * administered in the module's mainController. Working in this way you have
	 * clean line to share this beans/sets with other controllers.
	 */
	/* Master BEANS */
	public Wilayah getKecamatan() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getSelectedKecamatan();
	}

	public void setKecamatan(Wilayah kecamatan) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setSelectedKecamatan(kecamatan);
	}

	public void setSelectedKecamatan(Wilayah selectedKecamatan) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setSelectedKecamatan(selectedKecamatan);
	}

	public Wilayah getSelectedKecamatan() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getSelectedKecamatan();
	}

	public void setKecamatans(BindingListModelList kecamatans) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setKecamatans(kecamatans);
	}

	public BindingListModelList getKecamatans() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getKecamatans();
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	/* CONTROLLERS */
	public void setPropinsiMainCtrl(PropinsiMainCtrl kecamatanMainCtrl) {
		this.propinsiMainCtrl = kecamatanMainCtrl;
	}

	public PropinsiMainCtrl getPropinsiMainCtrl() {
		return this.propinsiMainCtrl;
	}

	/* SERVICES */
	public void setWilayahDAO(WilayahDAO kecamatanService) {
		this.wilayahDAO = kecamatanService;
	}

	public WilayahDAO getWilayahDAO() {
		return this.wilayahDAO;
	}

	/* COMPONENTS and OTHERS */
	public int getCountRows() {
		return this.countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	public HibernateSearchObject<Wilayah> getSearchObj() {
		return this.searchObj;
	}

	public void setSearchObj(HibernateSearchObject<Wilayah> searchObj) {
		this.searchObj = searchObj;
	}

	public Listbox getListBoxKecamatan() {
		return this.listBoxKecamatan;
	}

	public void setListBoxKecamatan(Listbox listBoxKecamatan) {
		this.listBoxKecamatan = listBoxKecamatan;
	}

	public Paging getPaging_KecamatanList() {
		return paging_KecamatanList;
	}

	public void setPaging_KecamatanList(Paging paging_KecamatanList) {
		this.paging_KecamatanList = paging_KecamatanList;
	}

}
