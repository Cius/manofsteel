package de.forsthaus.webui.gabungan;

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

import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.JenisGuru;
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
public class JenisGuruMainCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(JenisGuruMainCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jenisGuruMainWindow; // autowired

	// Tabs
	protected Tabbox tabbox_JenisGuruMain; // autowired
	protected Tab tabJenisGuruList; // autowired
	protected Tab tabKelompokGuruList; // autowired
	protected Tabpanel tabPanelJenisGuruList; // autowired
	protected Tabpanel tabPanelKelompokGuruList; // autowired

	// Button controller for the CRUD buttons
	private final String btnCtroller_ClassPrefix = "button_JenisGuruDialog_";
	private ButtonStatusCtrl btnCtrlJenisGuru;
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

	private PagedListWrapper<JenisGuru> plwJenisGuru;

	/**
	 * default constructor.<br>
	 */
	public JenisGuruMainCtrl() {
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
	public void onCreate$jenisGuruMainWindow(Event event) throws Exception {

		// create the Button Controller. Disable not used buttons during working
		btnCtrlJenisGuru = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnPrint, btnFirst, btnPrevious, btnNext, btnLast, btnNew, btnEdit, btnDelete, btnSave,
				btnCancel, null);

		/**
		 * Initiate the first loading by selecting the customerList tab and
		 * create the components from the zul-file.
		 */
		tabKelompokGuruList.setSelected(true);
		if (tabPanelKelompokGuruList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelKelompokGuruList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/kelompokGuruList.zul");
		}
		
		// Set the buttons for editMode
		btnCtrlJenisGuru.setInitEdit();
	}

	/**
	 * When the tab 'tabPropinsiList' is selected.<br>
	 * Loads the zul-file into the tab.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void onSelect$tabJenisGuruList(Event event) throws IOException {
		// logger.debug(event.toString());

		if (tabPanelJenisGuruList.getFirstChild() != null) {
			tabJenisGuruList.setSelected(true);
			return;
		}

		if (tabPanelJenisGuruList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenisGuruList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/jenisGuruList.zul");
		}
	}
	
	public void onSelect$tabKelompokGuruList(Event event) throws IOException {
		// Check if the tabpanel is already loaded
		if (tabPanelKelompokGuruList.getFirstChild() != null) {
			tabKelompokGuruList.setSelected(true);
			return;
		}

		if (tabPanelKelompokGuruList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelKelompokGuruList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/kelompokGuruList.zul");
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

	public PagedListWrapper<JenisGuru> getPlwJenisGuru() {
		return plwJenisGuru;
	}

	public void setPlwJenisGuru(PagedListWrapper<JenisGuru> plwJenisGuru) {
		this.plwJenisGuru = plwJenisGuru;
	}

}
