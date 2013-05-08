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
 * This is the controller class for the /WEB-INF/pages/kota/kotaList.zul
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
public class KotaListCtrl extends GFCBaseListCtrl<Wilayah> implements Serializable {

	private static final long serialVersionUID = 2038742641853727975L;
	private static final Logger logger = Logger.getLogger(KotaListCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window windowKotaList; // autowired

	// listbox kotas
	protected Borderlayout borderLayout_kotaList; // autowired
	private Paging paging_KotaList; // autowired
	protected Listbox listBoxKota; // autowired
	protected Listheader listheader_KotaList_Kode; // autowired
	protected Listheader listheader_KotaList_Nama; // autowired
	protected Listheader listheader_KotaList_Ibukota; // autowired

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
	public KotaListCtrl() {
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
			getPropinsiMainCtrl().setKotaListCtrl(this);

			// Get the selected object.
			// Check if this Controller if created on first time. If so,
			// than the selectedXXXBean should be null
			if (getPropinsiMainCtrl().getSelectedKota() != null) {
				setSelectedKota(getPropinsiMainCtrl().getSelectedKota());
			} else
				setSelectedKota(null);
		} else {
			setSelectedKota(null);
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++ Component Events ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	public void onCreate$windowKotaList(Event event) throws Exception {

		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

		doFillListbox();

		binder.loadAll();
	}

	public void doFillListbox() {

		doFitSize();

		// set the paging params
		getPaging_KotaList().setPageSize(getCountRows());
		getPaging_KotaList().setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_KotaList_Kode.setSortAscending(new FieldComparator("kodeWilayah", true));
		listheader_KotaList_Kode.setSortDescending(new FieldComparator("kodeWilayah", false));
		listheader_KotaList_Nama.setSortAscending(new FieldComparator("namaWilayah", true));
		listheader_KotaList_Nama.setSortDescending(new FieldComparator("namaWilayah", false));
		listheader_KotaList_Ibukota.setSortAscending(new FieldComparator("ibukota", true));
		listheader_KotaList_Ibukota.setSortDescending(new FieldComparator("ibukota", false));

		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<Wilayah>(Wilayah.class, getCountRows());
		searchObj.addFilterEqual("tipeWilayah", 2);
		searchObj.addSort("kodeWilayah", false);
		setSearchObj(this.searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBoxKota(), getPaging_KotaList());
		BindingListModelList lml = (BindingListModelList) getListBoxKota().getModel();
		setKotas(lml);

		// Now we would select and show the text of the first entry in the list.
		// We became not the first item FROM the listbox because it's NOT
		// RENDERED AT THIS TIME.
		// So we take the first entry from the MODEL (ListModelList) and set as
		// selected.
		// check if first time opened and init databinding for selectedBean
		if (getSelectedKota() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBoxKota().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				setSelectedKota((Wilayah) lml.get(0));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBoxKota(), getSelectedKota()));
			}
		}
	}

	/**
	 * Selects the object in the listbox and change the tab.<br>
	 * Event is forwarded in the corresponding listbox.
	 */
	public void onDoubleClickedKotaItem(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		Wilayah anKota = getSelectedKota();

		if (anKota != null) {
			setSelectedKota(anKota);
			setKota(anKota);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("wilayah", anKota);
			map.put("tabSelected", 1);
			map.put("listbox", getListBoxKota());

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
	public void onSelect$listBoxKota(Event event) {
//		// logger.debug(event.toString());
//
//		// selectedKota is filled by annotated databinding mechanism
//		Wilayah anKota = getSelectedKota();
//
//		if (anKota == null) {
//			return;
//		}
//
//		// check first, if the tabs are created
//		if (getKotaMainCtrl().getKotaDetailCtrl() == null) {
//			Events.sendEvent(new Event("onSelect", getKotaMainCtrl().tabKotaDetail, null));
//			// if we work with spring beanCreation than we must check a little
//			// bit deeper, because the Controller are preCreated ?
//		} else if (getKotaMainCtrl().getKotaDetailCtrl().getBinder() == null) {
//			Events.sendEvent(new Event("onSelect", getKotaMainCtrl().tabKotaDetail, null));
//		}
//
//		// INIT ALL RELATED Queries/OBJECTS/LISTS NEW
////		getKotaMainCtrl().getKotaDetailCtrl().setSelectedKota(anKota);
////		getKotaMainCtrl().getKotaDetailCtrl().setKota(anKota);
//
//		// store the selected bean values as current
//		getKotaMainCtrl().doStoreInitValues();
//
//		// show the objects data in the statusBar
////		final String str = Labels.getLabel("common.Kota") + ": " + anKota.getArtKurzbezeichnung();
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
		borderLayout_kotaList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowKotaList.invalidate();
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
	public Wilayah getKota() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getSelectedKota();
	}

	public void setKota(Wilayah kota) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setSelectedKota(kota);
	}

	public void setSelectedKota(Wilayah selectedKota) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setSelectedKota(selectedKota);
	}

	public Wilayah getSelectedKota() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getSelectedKota();
	}

	public void setKotas(BindingListModelList kotas) {
		// STORED IN THE module's MainController
		getPropinsiMainCtrl().setKotas(kotas);
	}

	public BindingListModelList getKotas() {
		// STORED IN THE module's MainController
		return getPropinsiMainCtrl().getKotas();
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	/* CONTROLLERS */
	public void setPropinsiMainCtrl(PropinsiMainCtrl kotaMainCtrl) {
		this.propinsiMainCtrl = kotaMainCtrl;
	}

	public PropinsiMainCtrl getPropinsiMainCtrl() {
		return this.propinsiMainCtrl;
	}

	/* SERVICES */
	public void setWilayahDAO(WilayahDAO kotaService) {
		this.wilayahDAO = kotaService;
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

	public Listbox getListBoxKota() {
		return this.listBoxKota;
	}

	public void setListBoxKota(Listbox listBoxKota) {
		this.listBoxKota = listBoxKota;
	}

	public Paging getPaging_KotaList() {
		return paging_KotaList;
	}

	public void setPaging_KotaList(Paging paging_KotaList) {
		this.paging_KotaList = paging_KotaList;
	}

}
