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
public class JenisHukumanMainCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(JenisHukumanMainCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jenisHukumanMainWindow; // autowired

	// Tabs
	protected Tabbox tabbox_JenisHukumanMain; // autowired
	protected Tab tabJenisHukumanRinganList; // autowired
	protected Tab tabJenisHukumanSedangList; // autowired
	protected Tab tabJenisHukumanBeratList;
	protected Tabpanel tabPanelJenisHukumanRinganList; // autowired
	protected Tabpanel tabPanelJenisHukumanSedangList; // autowired
	protected Tabpanel tabPanelJenisHukumanBeratList; // autowired

	// Button controller for the CRUD buttons
	private final String btnCtroller_ClassPrefix = "button_JenisHukumanDialog_";
	private ButtonStatusCtrl btnCtrlJenisHukuman;
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

	private PagedListWrapper<Gabungan> plwJenisHukuman;

	/**
	 * default constructor.<br>
	 */
	public JenisHukumanMainCtrl() {
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
	public void onCreate$jenisHukumanMainWindow(Event event) throws Exception {

		// create the Button Controller. Disable not used buttons during working
		btnCtrlJenisHukuman = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnPrint, btnFirst, btnPrevious, btnNext, btnLast, btnNew, btnEdit, btnDelete, btnSave,
				btnCancel, null);

		/**
		 * Initiate the first loading by selecting the customerList tab and
		 * create the components from the zul-file.
		 */
		tabJenisHukumanRinganList.setSelected(true);
		if (tabPanelJenisHukumanRinganList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenisHukumanRinganList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/jenisHukumanRinganList.zul");
		}
		
		// Set the buttons for editMode
		btnCtrlJenisHukuman.setInitEdit();
	}

	/**
	 * When the tab 'tabPropinsiList' is selected.<br>
	 * Loads the zul-file into the tab.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void onSelect$tabJenisHukumanRinganList(Event event) throws IOException {
		// logger.debug(event.toString());

		if (tabPanelJenisHukumanRinganList.getFirstChild() != null) {
			tabJenisHukumanRinganList.setSelected(true);
			return;
		}

		if (tabPanelJenisHukumanRinganList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenisHukumanRinganList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/jenisHukumanRinganList.zul");
		}
	}
	
	public void onSelect$tabJenisHukumanSedangList(Event event) throws IOException {
		// Check if the tabpanel is already loaded
		if (tabPanelJenisHukumanSedangList.getFirstChild() != null) {
			tabJenisHukumanSedangList.setSelected(true);
			return;
		}

		if (tabPanelJenisHukumanSedangList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenisHukumanSedangList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/JenisHukumanSedangList.zul");
		}
	}
	
	public void onSelect$tabJenisHukumanBeratList(Event event) throws IOException {
		
		// Check if the tabpanel is already loaded
		if (tabPanelJenisHukumanBeratList.getFirstChild() != null) {
			tabJenisHukumanBeratList.setSelected(true);
			return;
		}

		if (tabPanelJenisHukumanBeratList != null) {
			ZksampleCommonUtils.createTabPanelContent(tabPanelJenisHukumanBeratList, this, "ModuleMainController", "/WEB-INF/pages/gabungan/jenisHukumanBeratList.zul");
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

	public PagedListWrapper<Gabungan> getPlwJenisHukuman() {
		return plwJenisHukuman;
	}

	public void setPlwJenisHukuman(PagedListWrapper<Gabungan> plwJenisHukuman) {
		this.plwJenisHukuman = plwJenisHukuman;
	}

}
