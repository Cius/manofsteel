package de.forsthaus.webui.dikum;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import de.forsthaus.backend.model.Dikum;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.ZksampleCommonUtils;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

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
public class DikumMainCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -4292373618041246989L;

	private static final Logger logger = Logger.getLogger(DikumMainCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window dikumMainWindow; // autowired

	// Tabs
	protected Tabbox tabbox_DikumMain; // autowired
	protected Tab tabTingkatPendidikanList; // autowired
	protected Tab tabJenjangPendidikanList; // autowired
	protected Tab tabJurusanList;
	protected Tab tabProgramStudiList;
	protected Tabpanel tabPanelTingkatPendidikanList; // autowired
	protected Tabpanel tabPanelJenjangPendidikanList; // autowired
	protected Tabpanel tabPanelJurusanList; // autowired
	protected Tabpanel tabPanelProgramStudiList; // autowired

	// Button controller for the CRUD buttons
	private final String btnCtroller_ClassPrefix = "button_DikumDialog_";
	private ButtonStatusCtrl btnCtrlDikum;
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

	private PagedListWrapper<Dikum> plwDikum;

	/**
	 * default constructor.<br>
	 */
	public DikumMainCtrl() {
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
	public void onCreate$dikumMainWindow(Event event) throws Exception {

		// create the Button Controller. Disable not used buttons during working
		btnCtrlDikum = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnPrint, btnFirst, btnPrevious, btnNext, btnLast, btnNew, btnEdit, btnDelete, btnSave,
				btnCancel, null);

		/**
		 * Initiate the first loading by selecting the customerList tab and
		 * create the components from the zul-file.
		 */
		tabTingkatPendidikanList.setSelected(true);
		if (tabPanelTingkatPendidikanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelTingkatPendidikanList, this, "ModuleMainController", "/WEB-INF/pages/dikum/tingkatPendidikanList.zul");
		}
		
		// Set the buttons for editMode
		btnCtrlDikum.setInitEdit();
	}

	/**
	 * When the tab 'tabPropinsiList' is selected.<br>
	 * Loads the zul-file into the tab.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void onSelect$tabTingkatPendidikanList(Event event) throws IOException {
		// logger.debug(event.toString());

		if (tabPanelTingkatPendidikanList.getFirstChild() != null) {
			tabTingkatPendidikanList.setSelected(true);
			return;
		}

		if (tabPanelTingkatPendidikanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelTingkatPendidikanList, this, "ModuleMainController", "/WEB-INF/pages/dikum/tingkatPendidikanList.zul");
		}
	}
	
	public void onSelect$tabJenjangPendidikanList(Event event) throws IOException {
		// Check if the tabpanel is already loaded
		if (tabPanelJenjangPendidikanList.getFirstChild() != null) {
			tabJenjangPendidikanList.setSelected(true);
			return;
		}

		if (tabPanelJenjangPendidikanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenjangPendidikanList, this, "ModuleMainController", "/WEB-INF/pages/dikum/jenjangPendidikanList.zul");
		}
	}
	
	public void onSelect$tabJurusanList(Event event) throws IOException {
		
		// Check if the tabpanel is already loaded
		if (tabPanelJurusanList.getFirstChild() != null) {
			tabJurusanList.setSelected(true);
			return;
		}

		if (tabPanelJurusanList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJurusanList, this, "ModuleMainController", "/WEB-INF/pages/dikum/jurusanList.zul");
		}
	}
	
	public void onSelect$tabProgramStudiList(Event event) throws IOException {
		
		// Check if the tabpanel is already loaded
		if (tabPanelProgramStudiList.getFirstChild() != null) {
			tabProgramStudiList.setSelected(true);
			return;
		}

		if (tabPanelProgramStudiList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelProgramStudiList, this, "ModuleMainController", "/WEB-INF/pages/dikum/programStudiList.zul");
		}
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
	 * When the "print" button is clicked.<br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnPrint(Event event) throws InterruptedException {
//		final Window win = (Window) Path.getComponent("/outerIndexWindow");
//		new PropinsiSimpleDJReport(win);
	}


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //


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

	public PagedListWrapper<Dikum> getPlwDikum() {
		return plwDikum;
	}

	public void setPlwDikum(PagedListWrapper<Dikum> plwDikum) {
		this.plwDikum = plwDikum;
	}

}
