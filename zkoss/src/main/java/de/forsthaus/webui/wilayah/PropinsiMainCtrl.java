package de.forsthaus.webui.wilayah;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.googlecode.genericdao.search.Filter;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.Customer;
import de.forsthaus.backend.model.Order;
import de.forsthaus.backend.model.Orderposition;
import de.forsthaus.backend.model.SecRight;
import de.forsthaus.backend.model.Wilayah;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.backend.util.ZksampleBeanUtils;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleCommonUtils;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;
import de.forsthaus.webui.wilayah.model.PropinsiListModelItemRenderer;

/**
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * Main controller for the propinsi main module.<br>
 * <br>
 * zul-file: /WEB-INF/pages/propinsi/propinsiMain.zul.<br>
 * <br>
 * <b>WORKS with the annotated databinding mechanism.</b><br>
 * This class creates the Tabs + TabPanels. The components/data to all tabs are
 * created on demand on first time selecting the tab.<br>
 * This controller holds all getters/setters for the used databinding beans/sets
 * in all related tabs. In the child tabs controllers their databinding
 * setters/getters pointed to this mainTabController.<br>
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * <br>
 * 
 * @changes 07/03/2010: sge modified for zk5.x with complete Annotated
 *          Databinding.<br>
 * 
 *          Managed tabs:<br>
 *          - PropinsiListCtrl = Propinsi List / PropinsiListe<br>
 *          - PropinsiDetailCtrl = Propinsi Details / PropinsiDetails<br>
 *          Dialog window:<br>
 *          - PropinsiDialogCtrl = Propinsi Details as a Dialog / PropinsiDetails
 *          als Dialog<br>
 * 
 * @author bbruhns
 * @author Stephan Gerth
 */
public class PropinsiMainCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PropinsiMainCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window windowPropinsiMain; // autowired

	// Tabs
	protected Tabbox tabbox_PropinsiMain; // autowired
	protected Tab tabPropinsiList; // autowired
	protected Tab tabKotaList; // autowired
	protected Tab tabKecamatanList;
	protected Tab tabKelurahanList;
//	protected Tab tabPropinsiDetail; // autowired
	protected Tabpanel tabPanelPropinsiList; // autowired
	protected Tabpanel tabPanelKotaList; // autowired
	protected Tabpanel tabPanelKecamatanList; // autowired
	protected Tabpanel tabPanelKelurahanList; // autowired

	// filter components
//	protected Checkbox checkbox_PropinsiList_ShowAll; // autowired
//	protected Textbox tb_Propinsi_PropinsiID; // aurowired
	protected Textbox tb_Propinsi_Name; // aurowired

	// checkRights
//	protected Button button_PropinsiList_PrintList;
//	protected Button button_PropinsiList_SearchPropinsiID;
	protected Button button_PropinsiList_SearchName;

	// Button controller for the CRUD buttons
	private final String btnCtroller_ClassPrefix = "button_PropinsisDialog_";
	private ButtonStatusCtrl btnCtrlPropinsi;
	protected Button btnNew; // autowired
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired

	protected Button btnFirst; // autowire
	protected Button btnPrevious; // autowire
	protected Button btnNext; // autowire
	protected Button btnLast; // autowire

	protected Button btnPrint; // autowire

	protected Button btnHelp;

	// Tab-Controllers for getting access to their components/dataBinders
	private PropinsiListCtrl propinsiListCtrl;
	private KotaListCtrl kotaListCtrl;
	private KecamatanListCtrl kecamatanListCtrl;
	private KelurahanListCtrl kelurahanListCtrl;
	// The same Detail as a modal Dialog

	// Databinding
	// private Propinsi propinsi;
	private Wilayah selectedPropinsi;
	private BindingListModelList propinsis;
	private Wilayah selectedKota;
	private BindingListModelList kotas;
	private Wilayah selectedKecamatan;
	private BindingListModelList kecamatans;
	private Wilayah selectedKelurahan;
	private BindingListModelList kelurahans;

	// ServiceDAOs / Domain Classes
	private WilayahDAO wilayahDAO;

	// always a copy from the bean before modifying. Used for reseting
	private Wilayah originalPropinsi;
	
	// searching
	private Hbox hbox_WilayahSearch;
	private Label label_Search;
	private Label label_searchName;
	private Listheader listheader_Kode;
	private Listheader listheader_Nama;
	private Listheader listheader_Ibukota;
	private Paging paging_WilayahSearchList;
	private transient HibernateSearchObject<Wilayah> searchObjWilayah;
	private Listbox listBoxWilayahSearch;
	private PagedListWrapper<Wilayah> plwWilayah;
//	private Button button_bbox_Wilayah_Search;
	private Textbox tb_NamaWilayah;
	private Bandbox bandbox_WilayahSearch;

	/**
	 * default constructor.<br>
	 */
	public PropinsiMainCtrl() {
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
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++ Component Events ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Automatically called method from zk.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$windowPropinsiMain(Event event) throws Exception {

		// create the Button Controller. Disable not used buttons during working
		btnCtrlPropinsi = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnPrint, btnFirst, btnPrevious, btnNext, btnLast, btnNew, btnEdit, btnDelete, btnSave,
				btnCancel, null);

		doCheckRights();

		/**
		 * Initiate the first loading by selecting the customerList tab and
		 * create the components from the zul-file.
		 */
		tabPropinsiList.setSelected(true);
		if (tabPanelPropinsiList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelPropinsiList, this, "ModuleMainController", "/WEB-INF/pages/wilayah/propinsiList.zul");
		}
		bandbox_WilayahSearch.setVisible(false);
		label_Search.setVisible(false);

		// Set the buttons for editMode
		btnCtrlPropinsi.setInitEdit();
	}

	/**
	 * When the tab 'tabPropinsiList' is selected.<br>
	 * Loads the zul-file into the tab.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void onSelect$tabPropinsiList(Event event) throws IOException {
		// logger.debug(event.toString());

		bandbox_WilayahSearch.setVisible(false);
		label_Search.setVisible(false);
		label_searchName.setValue("Propinsi");
		// Check if the tabpanel is already loaded
		if (tabPanelPropinsiList.getFirstChild() != null) {
			tabPropinsiList.setSelected(true);
			return;
		}

		if (tabPanelPropinsiList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelPropinsiList, this, "ModuleMainController", "/WEB-INF/pages/wilayah/propinsiList.zul");
		}
	}
	
	public void onSelect$tabKotaList(Event event) throws IOException {
		// logger.debug(event.toString());
		
		bandbox_WilayahSearch.setVisible(true);
		label_Search.setVisible(true);
		label_Search.setValue("Propinsi");
		label_searchName.setValue("Kota");

		// Check if the tabpanel is already loaded
		if (tabPanelKotaList.getFirstChild() != null) {
			tabKotaList.setSelected(true);
			return;
		}

		if (tabPanelKotaList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelKotaList, this, "ModuleMainController", "/WEB-INF/pages/wilayah/kotaList.zul");
		}
	}
	
	public void onSelect$tabKecamatanList(Event event) throws IOException {
		// logger.debug(event.toString());
		
		bandbox_WilayahSearch.setVisible(true);
		label_Search.setVisible(true);
		label_Search.setValue("Kota");
		label_searchName.setValue("Kecamatan");
		// Check if the tabpanel is already loaded
		if (tabPanelKecamatanList.getFirstChild() != null) {
			tabKecamatanList.setSelected(true);
			return;
		}

		if (tabPanelKecamatanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelKecamatanList, this, "ModuleMainController", "/WEB-INF/pages/wilayah/kecamatanList.zul");
		}
	}
	
	public void onSelect$tabKelurahanList(Event event) throws IOException {
		// logger.debug(event.toString());

		bandbox_WilayahSearch.setVisible(true);
		label_Search.setVisible(true);
		label_Search.setValue("Kecamatan");
		label_searchName.setValue("Kelurahan");
		// Check if the tabpanel is already loaded
		if (tabPanelKelurahanList.getFirstChild() != null) {
			tabKelurahanList.setSelected(true);
			return;
		}

		if (tabPanelKelurahanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelKelurahanList, this, "ModuleMainController", "/WEB-INF/pages/wilayah/kelurahanList.zul");
		}
	}

	/**
	 * When the tab 'tabPanelPropinsiDetail' is selected.<br>
	 * Loads the zul-file into the tab.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void onSelect$tabPropinsiDetail(Event event) throws IOException {
		// logger.debug(event.toString());

//		// Check if the tabpanel is already loaded
//		if (tabPanelPropinsiDetail.getFirstChild() != null) {
//			tabPropinsiDetail.setSelected(true);
//
//			// refresh the Binding mechanism
////			getPropinsiDetailCtrl().setPropinsi(getSelectedPropinsi());
////			getPropinsiDetailCtrl().getBinder().loadAll();
//			return;
//		}

//		if (this.tabPanelPropinsiDetail != null) {
//			ZksampleCommonUtils.createTabPanelContent(this.tabPanelPropinsiDetail, this, "ModuleMainController", "/WEB-INF/pages/propinsi/propinsiDetail.zul");
//		}
	}

	 
	/**
	 * When the "search for propinsi No" button is clicked.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onClick$button_PropinsiList_SearchName(Event event) throws Exception {
		doPropinsiListSearchLikePropinsiName(event);
	}
	
	public void onClick$button_bbox_Wilayah_Search(Event event) {
		// logger.debug(event.toString());

		doSearch();
	}

	/**
	 * When the "help" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnHelp(Event event) throws InterruptedException {
		doHelp(event);
	}

	/**
	 * When the "new" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnNew(Event event) throws InterruptedException {
		doNew(event);
	}

	/**
	 * When the "save" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnSave(Event event) throws InterruptedException {
		doSave(event);
	}

	/**
	 * When the "cancel" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnEdit(Event event) throws InterruptedException {
		doEdit(event);
	}

	/**
	 * When the "delete" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnDelete(Event event) throws InterruptedException {
		doDelete(event);
	}

	/**
	 * When the "cancel" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnCancel(Event event) throws InterruptedException {
		doCancel(event);
	}

	/**
	 * when the "refresh" button is clicked. <br>
	 * <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnRefresh(Event event) throws InterruptedException {
		doResizeSelectedTab(event);
	}

	/**
	 * When the "print" button is clicked.<br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnPrint(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new PropinsiSimpleDJReport(win);
	}

	/**
	 * when the "go first record" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnFirst(Event event) throws InterruptedException {
		doSkip(event);
	}

	/**
	 * when the "go previous record" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnPrevious(Event event) throws InterruptedException {
		doSkip(event);
	}

	/**
	 * when the "go next record" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnNext(Event event) throws InterruptedException {
		doSkip(event);
	}

	/**
	 * when the "go last record" button is clicked.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnLast(Event event) throws InterruptedException {
		doSkip(event);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// +++++++++++++++++ Business Logic ++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //


	/**
	 * Filter the branch list with 'like propinsi name'. <br>
	 */
	private void doPropinsiListSearchLikePropinsiName(Event event) throws Exception {
		GFCBaseListCtrl<Wilayah> listCtrl = null;
		final Tab currentTab = this.tabbox_PropinsiMain.getSelectedTab();
		int type = 1;
		
		if (currentTab.equals(this.tabPropinsiList)) {
			listCtrl = getPropinsiListCtrl();
			type = 1;
		} else if (currentTab.equals(this.tabKotaList)) {
			listCtrl = getKotaListCtrl();
			type = 2;
		} else if (currentTab.equals(this.tabKecamatanList)) {
			listCtrl = getKecamatanListCtrl();
			type = 3;
		} else if (currentTab.equals(this.tabKelurahanList)) {
			listCtrl = getKelurahanListCtrl();
			type = 4;
		}

		// if not empty
		if (!tb_Propinsi_Name.getValue().isEmpty()) {
//			checkbox_PropinsiList_ShowAll.setChecked(false); // unCheck
//			tb_Propinsi_PropinsiID.setValue("");

			if (listCtrl != null) {

				// ++ create a searchObject and init sorting ++//
				final HibernateSearchObject<Wilayah> so = new HibernateSearchObject<Wilayah>(Wilayah.class, getPropinsiListCtrl().getCountRows());
				Filter f1 = new Filter("namaWilayah", "%" + tb_Propinsi_Name.getValue() + "%", Filter.OP_ILIKE);
				Filter f2 = new Filter("tipeWilayah", type, Filter.OP_EQUAL);
				so.addFilterAnd(f1, f2);
				so.addSort("namaWilayah", false);

				// Change the BindingListModel.
				listCtrl.getPagedBindingListWrapper().setSearchObject(so);
				currentTab.setSelected(true);
			}
		}
	}

	/**
	 * 1. Cancel the current action.<br>
	 * 2. Reset the values to its origin.<br>
	 * 3. Set UI components back to readonly/disable mode.<br>
	 * 4. Set the buttons in edit mode.<br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	private void doCancel(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		// check first, if the tabs are created
//		if (getPropinsiDetailCtrl().getBinder() != null) {
//
//			// reset to the original object
//			doResetToInitValues();
//
//			// refresh all dataBinder related controllers/components
//			getPropinsiDetailCtrl().getBinder().loadAll();
//
//			// set editable Mode
//			getPropinsiDetailCtrl().doReadOnlyMode(true);
//		}

		btnCtrlPropinsi.setInitEdit();
	}

	/**
	 * Sets all UI-components to writable-mode. Sets the buttons to edit-Mode.
	 * Checks first, if the NEEDED TABS with its contents are created. If not,
	 * than create it by simulate an onSelect() with calling Events.sendEvent()
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	private void doEdit(Event event) {
		// logger.debug(event.toString());

		// get the current Tab for later checking if we must change it
		final Tab currentTab = this.tabbox_PropinsiMain.getSelectedTab();

		// check first, if the tabs are created, if not than create it
//		if (getPropinsiDetailCtrl() == null) {
//			Events.sendEvent(new Event("onSelect", tabPropinsiDetail, null));
//			// if we work with spring beanCreation than we must check a little
//			// bit deeper, because the Controller are preCreated ?
//		} else if (getPropinsiDetailCtrl().getBinder() == null) {
//			Events.sendEvent(new Event("onSelect", tabPropinsiDetail, null));
//		}

		// check if the tab is one of the Detail tabs. If so do not change the
		// selection of it
//		if (!currentTab.equals(this.tabPropinsiDetail)) {
//			tabPropinsiDetail.setSelected(true);
//		} else {
//			currentTab.setSelected(true);
//		}
//
//		// remember the old vars
//		doStoreInitValues();
//
//		getPropinsiDetailCtrl().doReadOnlyMode(false);
//
//		btnCtrlPropinsi.setBtnStatus_Edit();
//
//		// refresh the UI, because we can click the EditBtn from every tab.
//		getPropinsiDetailCtrl().getBinder().loadAll();

		// set focus
//		getPropinsiDetailCtrl().txtb_artNr.focus();
	}

	/**
	 * Deletes the selected Bean from the DB.
	 * 
	 * @param event
	 * @throws InterruptedException
	 * @throws InterruptedException
	 */
	private void doDelete(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		// check first, if the tabs are created, if not than create them
//		if (getPropinsiDetailCtrl().getBinder() == null) {
//			Events.sendEvent(new Event("onSelect", tabPropinsiDetail, null));
//		}
//
//		// check first, if the tabs are created
//		if (getPropinsiDetailCtrl().getBinder() == null) {
//			return;
//		}
		
		int sel = tabbox_PropinsiMain.getSelectedIndex();
		Wilayah wil = null;
		if (sel == 0)
			wil = getSelectedPropinsi();
		else if (sel == 1)
			wil = getSelectedKota();
		else if (sel == 1)
			wil = getSelectedKecamatan();
		else if (sel == 2)
			wil = getSelectedKelurahan();
		
		final Wilayah anwil = wil;

		if (wil != null) {

			// Show a confirm box
			final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + wil.getNamaWilayah();
			final String title = Labels.getLabel("message.Deleting.Record");

			MultiLineMessageBox.doSetTemplate();
			if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
				@Override
				public void onEvent(Event evt) {
					switch (((Integer) evt.getData()).intValue()) {
					case MultiLineMessageBox.YES:
						try {
							deleteBean();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break; //
					case MultiLineMessageBox.NO:
						break; //
					}
				}

				// delete from database
				private void deleteBean() throws InterruptedException {
					try {
						getWilayahDAO().delete(anwil);
					} catch (DataAccessException e) {
						ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
					}
				}

			}

			) == MultiLineMessageBox.YES) {
			}

		}

		btnCtrlPropinsi.setInitEdit();

		setSelectedPropinsi(null);
		// refresh the list
		getPropinsiListCtrl().doFillListbox();

		// refresh all dataBinder related controllers
//		getPropinsiDetailCtrl().getBinder().loadAll();
		// the listController only because we have a textbox in it.
		getPropinsiListCtrl().getBinder().loadAll();
	}

	/**
	 * Saves all involved Beans to the DB.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	private void doSave(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		// save all components data in the several tabs to the bean
//		getPropinsiDetailCtrl().getBinder().saveAll();
//
//		try {
//			// save it to database
////			getWilayahDAO().saveOrUpdate(getPropinsiDetailCtrl().getPropinsi());
//			// if saving is successfully than actualize the beans as
//			// origins.
//			doStoreInitValues();
//			// refresh the list
//			getPropinsiListCtrl().doFillListbox();
//			// later refresh StatusBar
//			Events.postEvent("onSelect", getPropinsiListCtrl().getListBoxPropinsi(), getSelectedPropinsi());
//
//			// show the objects data in the statusBar
//			final String str = getSelectedPropinsi().getArtKurzbezeichnung();
//			EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));
//
//		} catch (DataAccessException e) {
//			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
//
//			// Reset to init values
//			doResetToInitValues();
//
//			return;
//
//		} finally {
//			btnCtrlPropinsi.setInitEdit();
//			getPropinsiDetailCtrl().doReadOnlyMode(true);
//		}
	}

	/**
	 * Sets all UI-components to writable-mode. Stores the current Beans as
	 * originBeans and get new Objects from the backend.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	private void doNew(Event event) throws InterruptedException {
		Wilayah wil = getWilayahDAO().getNewWilayah();
		/*
		 * We can call our Dialog zul-file with parameters. So we can call them
		 * with a object of the selected item. For handed over these parameter
		 * only a Map is accepted. So we put the object in a HashMap.
		 */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("wilayah", wil);
		map.put("tabSelected", tabbox_PropinsiMain.getSelectedIndex());
		
		int selected = tabbox_PropinsiMain.getSelectedIndex();
		if (selected == 0)
			map.put("listbox", getPropinsiListCtrl().getListBoxPropinsi());
		else if (selected == 1)
			map.put("listbox", getKotaListCtrl().getListBoxKota());
		else if (selected == 2)
			map.put("listbox", getKecamatanListCtrl().getListBoxKecamatan());
		else if (selected == 3)
			map.put("listbox", getKelurahanListCtrl().getListBoxKelurahan());
		

		// call the zul-file with the parameters packed in a map
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

	/**
	 * Skip/Leaf through the models data according the navigation buttons and
	 * selected the according row in the listbox.
	 * 
	 * @param event
	 */
	private void doSkip(Event event) {
		// get the model and the current selected record
		BindingListModelList blml = null;
		Wilayah selected = null;
		Listbox listbox = null;
		final Tab currentTab = this.tabbox_PropinsiMain.getSelectedTab();
		if (currentTab.equals(this.tabPropinsiList)) {
			listbox = getPropinsiListCtrl().getListBoxPropinsi();
			selected = getSelectedPropinsi();
		} else if (currentTab.equals(this.tabKotaList)) {
			listbox = getKotaListCtrl().getListBoxKota();
			selected = getSelectedKota();
		} else if (currentTab.equals(this.tabKecamatanList)) {
			listbox = getKecamatanListCtrl().getListBoxKecamatan();
			selected = getSelectedKecamatan();
		} else if (currentTab.equals(this.tabKelurahanList)) {
			listbox = getKelurahanListCtrl().getListBoxKelurahan();
			selected = getSelectedKelurahan();
		}
		
		blml = (BindingListModelList) listbox.getModel();
		// check if data exists
		if (blml == null || blml.size() < 1)
			return;

		int index = blml.indexOf(selected);

		// go back to selected tab
		currentTab.setSelected(true);

		// Check which button is clicked and calculate the rowIndex
		if (((ForwardEvent) event).getOrigin().getTarget() == btnNext) {
			if (index < (blml.size() - 1)) {
				index = index + 1;
			}
		} else if (((ForwardEvent) event).getOrigin().getTarget() == btnPrevious) {
			if (index > 0) {
				index = index - 1;
			}
		} else if (((ForwardEvent) event).getOrigin().getTarget() == btnFirst) {
			if (index != 0) {
				index = 0;
			}
		} else if (((ForwardEvent) event).getOrigin().getTarget() == btnLast) {
			if (index != blml.size()) {
				index = (blml.size() - 1);
			}
		}

		listbox.setSelectedIndex(index);
		if (currentTab.equals(this.tabPropinsiList)) {
			setSelectedPropinsi((Wilayah) blml.get(index));
		} else if (currentTab.equals(this.tabKotaList)) {
			setSelectedKota((Wilayah) blml.get(index));
		} else if (currentTab.equals(this.tabKecamatanList)) {
			setSelectedKecamatan((Wilayah) blml.get(index));
		} else if (currentTab.equals(this.tabKelurahanList)) {
			setSelectedKelurahan((Wilayah) blml.get(index));
		}
		

		// call onSelect() for showing the objects data in the statusBar
		Events.sendEvent(new Event(Events.ON_SELECT, listbox, (Wilayah) blml.get(index)));

		// refresh master-detail MASTERS data
//		getPropinsiDetailCtrl().getBinder().loadAll();

		// EXTRA: we have a longtext field under the listbox, so we must refresh
		// this binded component too
//		getPropinsiListCtrl().getBinder().loadComponent(getPropinsiListCtrl().longBoxArt_LangBeschreibung);
	}

	private void doSearch() {
		int sel = this.tabbox_PropinsiMain.getSelectedIndex();
		searchObjWilayah = new HibernateSearchObject<Wilayah>(Wilayah.class, 20);
		searchObjWilayah.addFilterEqual("tipeWilayah", sel);

		// check which field have input
		if (StringUtils.isNotEmpty(tb_NamaWilayah.getValue())) {
			searchObjWilayah.addFilterLike("namaWilayah", "%" + tb_NamaWilayah.getValue() + "%");
		}

		

		setSearchObjWilayah(this.searchObjWilayah);

		// Set the ListModel.
		getPlwWilayah().init(getSearchObjWilayah(), listBoxWilayahSearch, paging_WilayahSearchList);

	}
	
	public void onClick$button_bbox_Wilayah_Close(Event event) {
		// logger.debug(event.toString());

		bandbox_WilayahSearch.close();
	}
	
	public void onSelect$listBoxWilayahSearch(Event event) {
		// logger.debug(event.toString());

		Listitem item = this.listBoxWilayahSearch.getSelectedItem();
		ListModelList lml = null;
		int sel = tabbox_PropinsiMain.getSelectedIndex();
		Wilayah wil = (Wilayah) item.getAttribute("data");
		bandbox_WilayahSearch.setValue(wil.getNamaWilayah());
		HibernateSearchObject<Wilayah> soOrder = new HibernateSearchObject<Wilayah>(Wilayah.class, 20);
		soOrder.addSort("kodeWilayah", false);
		
		
		if (item != null) {

			/* clear the listboxes from older stuff */
			switch (sel) {
			case 1:
				lml = (ListModelList)getKotaListCtrl().getListBoxKota().getModel();
				lml.clear();
				soOrder.addFilterEqual("tipeWilayah", 2);
				soOrder.addFilterLike("kodeWilayah", wil.getKodeWilayah().substring(0, 2) + "%");
				getKotaListCtrl().getPagedBindingListWrapper().setSearchObject(soOrder);
				break;
			case 2:
				lml = (ListModelList)getKecamatanListCtrl().getListBoxKecamatan().getModel();
				lml.clear();
				soOrder.addFilterEqual("tipeWilayah", 3);
				soOrder.addFilterLike("kodeWilayah", wil.getKodeWilayah().substring(0, 4) + "%");
				getKecamatanListCtrl().getPagedBindingListWrapper().setSearchObject(soOrder);
				break;
			case 3:
				lml = (ListModelList)getKelurahanListCtrl().getListBoxKelurahan().getModel();
				lml.clear();
				soOrder.addFilterEqual("tipeWilayah", 4);
				soOrder.addFilterLike("kodeWilayah", wil.getKodeWilayah().substring(0, 6) + "%");
				getKelurahanListCtrl().getPagedBindingListWrapper().setSearchObject(soOrder);
				break;
			}
		}

		// close the bandbox
		bandbox_WilayahSearch.close();

	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/**
	 * Resizes the container from the selected Tab.
	 * 
	 * @param event
	 */
	public void doResizeSelectedTab(Event event) {
		final Tab currentTab = this.tabbox_PropinsiMain.getSelectedTab();
		if (currentTab.equals(this.tabPropinsiList)) {
			getPropinsiListCtrl().doFillListbox();
		} else if (currentTab.equals(this.tabKotaList)) {
			getKotaListCtrl().doFillListbox();
		} else if (currentTab.equals(this.tabKecamatanList)) {
			getKecamatanListCtrl().doFillListbox();
		} else if (currentTab.equals(this.tabKelurahanList)) {
			getKelurahanListCtrl().doFillListbox();
		}
	}

	/**
	 * Opens the help screen for the current module.
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	private void doHelp(Event event) throws InterruptedException {

		ZksampleMessageUtils.doShowNotImplementedMessage();

		// we stop the propagation of the event, because zk will call ALL events
		// with the same name in the namespace and 'btnHelp' is a standard
		// button in this application and can often appears.
		// Events.getRealOrigin((ForwardEvent) event).stopPropagation();
		event.stopPropagation();
	}

	/**
	 * Saves the selected object's current properties. We can get them back if a
	 * modification is canceled.
	 * 
	 * @see doResetToInitValues()
	 */
	public void doStoreInitValues() {

		if (getSelectedPropinsi() != null) {

			try {
				setOriginalPropinsi((Wilayah) ZksampleBeanUtils.cloneBean(getSelectedPropinsi()));
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (final InstantiationException e) {
				throw new RuntimeException(e);
			} catch (final InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (final NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Reset the selected object to its origin property values.
	 * 
	 * @see doStoreInitValues()
	 * 
	 */
	public void doResetToInitValues() {

		if (getOriginalPropinsi() != null) {

			try {
				setSelectedPropinsi((Wilayah) ZksampleBeanUtils.cloneBean(getOriginalPropinsi()));
				// TODO Bug in DataBinder??
				windowPropinsiMain.invalidate();

			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (final InstantiationException e) {
				throw new RuntimeException(e);
			} catch (final InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (final NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void onOpen$bandbox_WilayahSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listheader_Kode.setSortAscending(new FieldComparator("kodeWilayah", true));
		listheader_Kode.setSortDescending(new FieldComparator("kodeWilayah", false));
		listheader_Nama.setSortAscending(new FieldComparator("namaWilayah", true));
		listheader_Nama.setSortDescending(new FieldComparator("namaWilayah", false));
		listheader_Ibukota.setSortAscending(new FieldComparator("ibukota", true));
		listheader_Ibukota.setSortDescending(new FieldComparator("ibukota", false));

		// set the paging params
		paging_WilayahSearchList.setPageSize(20);
		paging_WilayahSearchList.setDetailed(true);

		// ++ create the searchObject and init sorting ++ //
		int sel = this.tabbox_PropinsiMain.getSelectedIndex();
		HibernateSearchObject<Wilayah> searchObject = new HibernateSearchObject<Wilayah>(Wilayah.class, 20);
		searchObject.addFilterEqual("tipeWilayah", sel);
		searchObject.addSort("kodeWilayah", false);
		setSearchObjWilayah(searchObject);

		// Set the ListModel.
		getPlwWilayah().init(getSearchObjWilayah(), listBoxWilayahSearch, paging_WilayahSearchList);
		// set the itemRenderer
		listBoxWilayahSearch.setItemRenderer(new PropinsiListModelItemRenderer());
	}


	/**
	 * User rights check. <br>
	 * Only components are set visible=true if the logged-in <br>
	 * user have the right for it. <br>
	 * 
	 * The rights are getting from the spring framework users
	 * grantedAuthority(). Remember! A right is only a string. <br>
	 */
	// TODO move it to the zul-file
	private void doCheckRights() {

		final UserWorkspace workspace = getUserWorkspace();

		// btnPrint.setVisible(workspace.isAllowed("button_BranchMain_PrintBranches"));
//		button_PropinsiList_SearchPropinsiID.setVisible(workspace.isAllowed("button_PropinsiList_SearchPropinsiID"));
		button_PropinsiList_SearchName.setVisible(workspace.isAllowed("button_PropinsiList_SearchName"));

	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //

	/* Master BEANS */
	public void setOriginalPropinsi(Wilayah originalPropinsi) {
		this.originalPropinsi = originalPropinsi;
	}

	public Wilayah getOriginalPropinsi() {
		return this.originalPropinsi;
	}

	public void setSelectedPropinsi(Wilayah selectedPropinsi) {
		this.selectedPropinsi = selectedPropinsi;
	}

	public Wilayah getSelectedPropinsi() {
		return this.selectedPropinsi;
	}

	public void setPropinsis(BindingListModelList propinsis) {
		this.propinsis = propinsis;
	}

	public BindingListModelList getPropinsis() {
		return this.propinsis;
	}

	public BindingListModelList getKelurahans() {
		return kelurahans;
	}

	public void setKelurahans(BindingListModelList kelurahans) {
		this.kelurahans = kelurahans;
	}

	public Wilayah getSelectedKota() {
		return selectedKota;
	}

	public void setSelectedKota(Wilayah selectedKota) {
		this.selectedKota = selectedKota;
	}

	public Wilayah getSelectedKecamatan() {
		return selectedKecamatan;
	}

	public void setSelectedKecamatan(Wilayah selectedKecamtan) {
		this.selectedKecamatan = selectedKecamtan;
	}

	public Wilayah getSelectedKelurahan() {
		return selectedKelurahan;
	}

	public void setSelectedKelurahan(Wilayah selectedKelurahan) {
		this.selectedKelurahan = selectedKelurahan;
	}

	public BindingListModelList getKecamatans() {
		return kecamatans;
	}

	public void setKecamatans(BindingListModelList kecamatans) {
		this.kecamatans = kecamatans;
	}

	public BindingListModelList getKotas() {
		return kotas;
	}

	public void setKotas(BindingListModelList kotas) {
		this.kotas = kotas;
	}

	/* CONTROLLERS */
	public void setPropinsiListCtrl(PropinsiListCtrl propinsiListCtrl) {
		this.propinsiListCtrl = propinsiListCtrl;
	}

	public PropinsiListCtrl getPropinsiListCtrl() {
		return this.propinsiListCtrl;
	}

//	public void setPropinsiDetailCtrl(PropinsiDetailCtrl propinsiDetailCtrl) {
//		this.propinsiDetailCtrl = propinsiDetailCtrl;
//	}
//
//	public PropinsiDetailCtrl getPropinsiDetailCtrl() {
//		return this.propinsiDetailCtrl;
//	}

	public KotaListCtrl getKotaListCtrl() {
		return kotaListCtrl;
	}

	public void setKotaListCtrl(KotaListCtrl kotaListCtrl) {
		this.kotaListCtrl = kotaListCtrl;
	}

	public KecamatanListCtrl getKecamatanListCtrl() {
		return kecamatanListCtrl;
	}

	public void setKecamatanListCtrl(KecamatanListCtrl kecamatanListCtrl) {
		this.kecamatanListCtrl = kecamatanListCtrl;
	}

	public KelurahanListCtrl getKelurahanListCtrl() {
		return kelurahanListCtrl;
	}

	public void setKelurahanListCtrl(KelurahanListCtrl kelurahanListCtrl) {
		this.kelurahanListCtrl = kelurahanListCtrl;
	}

	/* SERVICES */
	public void setWilayahDAO(WilayahDAO wilayahDAO) {
		this.wilayahDAO = wilayahDAO;
	}

	public WilayahDAO getWilayahDAO() {
		return this.wilayahDAO;
	}

	public HibernateSearchObject<Wilayah> getSearchObjWilayah() {
		return searchObjWilayah;
	}

	public void setSearchObjWilayah(HibernateSearchObject<Wilayah> searchObjWilayah) {
		this.searchObjWilayah = searchObjWilayah;
	}

	public PagedListWrapper<Wilayah> getPlwWilayah() {
		return plwWilayah;
	}

	public void setPlwWilayah(PagedListWrapper<Wilayah> plwWilayah) {
		this.plwWilayah = plwWilayah;
	}

	/* COMPONENTS and OTHERS */
}
