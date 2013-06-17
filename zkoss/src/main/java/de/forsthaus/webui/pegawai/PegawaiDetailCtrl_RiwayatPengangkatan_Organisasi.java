package de.forsthaus.webui.pegawai;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.googlecode.genericdao.search.Filter;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.TrOrganisasiDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TrOrganisasi;
import de.forsthaus.backend.model.TrOrganisasi;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.pegawai.model.RiwayatOrganisasiListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;

public class PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi extends GFCBaseListCtrl<TrOrganisasi> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi.class);

	protected Window windowPegawaiDetail_RiwayatPengangkatan_Organisasi;
	private Borderlayout borderLayout_organisasi;
	private Listbox listBox_organisasi;
	private Paging paging_organisasi;

	private Listheader listHeader_organisasi_tmt;

	private final String btnCtrl_clsPref = "btn_organisasi";
	protected ButtonStatusCtrl buttonCtrl;
	protected Button newEntry;
	protected Button edit;
	protected Button save;
	protected Button delete;
	protected Button cancel;

	private int countRows;
	
	private TrOrganisasi selectedOrganisasi;

	private AnnotateDataBinder binder;
	private PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan;
	
	private TrOrganisasiDAO trOrganisasiDAO;
	private HibernateSearchObject<TrOrganisasi> searchObj;

	public PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi() {
		super();
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if(arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_RiwayatPengangkatan((PegawaiDetailCtrl_RiwayatPengangkatan) arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_RiwayatPengangkatan().setPegawaiDetailCtrl_RiwayatPengangkatan_Organisasi(this);

			if(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected() != null) {
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected());
			}else {
				setSelected(null);
			}
		} else {
			setSelected(null);
		}
	}

	public void onCreate$windowPegawaiDetail_RiwayatPengangkatan_Organisasi(Event event) {
		this.buttonCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtrl_clsPref, true, null, null, null, null, null, null, newEntry, edit, delete, save, cancel, null);

		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		doFillList();
		this.binder.loadAll();
		
		listBox_organisasi.setItemRenderer(new RiwayatOrganisasiListModelItemRenderer());
		
		this.buttonCtrl.setInitNew();
	}

	public void doFillList() {
		doFitSize();

		// set the paging params
		paging_organisasi.setPageSize(getCountRows());
		paging_organisasi.setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listHeader_organisasi_tmt.setSortAscending(new FieldComparator("tMulai", true));
		listHeader_organisasi_tmt.setSortDescending(new FieldComparator("tMulai", false));
		
		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<TrOrganisasi>(TrOrganisasi.class, getCountRows());
		searchObj.addSort("tMulai", true);
		searchObj.addFilter(Filter.equal("nip", getSelected().getNip()));
		setSearchObj(searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBox_organisasi(), paging_organisasi);
		final BindingListModelList lml = (BindingListModelList) getListBox_organisasi().getModel();
		setRiwayatPengangkatanModelList_Organisasi(lml);

		// check if first time opened and init databinding for selectedBean
		if (getSelected() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBox_organisasi().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				TrOrganisasi selectedOrganisasi = (TrOrganisasi) lml.get(0);
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getPegawaiMainCtrl().getTpCpnsDAO().getByNip(selectedOrganisasi.getNip()));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBox_organisasi(), getSelected()));
			}
		}
	}

	public void onDoubleClicked(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		TrOrganisasi organisasi = getSelectedOrganisasi();

		if (organisasi != null) {
			setSelectedOrganisasi(organisasi);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("organisasi", organisasi);
			map.put("listbox", getListBox_organisasi());

			try {
				Executions.createComponents("/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_OrganisasiDialog.zul", null, map);
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

	public void doFitSize() {

		// normally 0 ! Or we have a i.e. a toolBar on top of the listBox.
		final int specialSize = 5;

		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - specialSize - 148;
		setCountRows((int) Math.round(maxListBoxHeight / 17.7));
		borderLayout_organisasi.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowPegawaiDetail_RiwayatPengangkatan_Organisasi.invalidate();
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	public TpCpns getSelected() {
		return getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected();
	}

	public void setSelected(TpCpns selected) {
		getPegawaiDetailCtrl_RiwayatPengangkatan().setSelected(selected);
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan getPegawaiDetailCtrl_RiwayatPengangkatan() {
		return pegawaiDetailCtrl_RiwayatPengangkatan;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan(
			PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan = pegawaiDetailCtrl_RiwayatPengangkatan;
	}

	public Listbox getListBox_organisasi() {
		return listBox_organisasi;
	}

	public void setListBox_organisasi(Listbox listBox_organisasi) {
		this.listBox_organisasi = listBox_organisasi;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Organisasi() {
		return getPegawaiDetailCtrl_RiwayatPengangkatan().getRiwayatPengangkatanModelList_Organisasi();
	}

	public void setRiwayatPengangkatanModelList_Organisasi(
			BindingListModelList riwayatPengangkatanModelList_Organisasi) {
		getPegawaiDetailCtrl_RiwayatPengangkatan().setRiwayatPengangkatanModelList_Organisasi(riwayatPengangkatanModelList_Organisasi);
	}

	public TrOrganisasiDAO getTrOrganisasiDAO() {
		return trOrganisasiDAO;
	}

	public void setTrOrganisasiDAO(TrOrganisasiDAO trOrganisasiDAO) {
		this.trOrganisasiDAO = trOrganisasiDAO;
	}

	public HibernateSearchObject<TrOrganisasi> getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(HibernateSearchObject<TrOrganisasi> searchObj) {
		this.searchObj = searchObj;
	}

	public TrOrganisasi getSelectedOrganisasi() {
		return selectedOrganisasi;
	}

	public void setSelectedOrganisasi(TrOrganisasi selectedOrganisasi) {
		this.selectedOrganisasi = selectedOrganisasi;
	}
}
