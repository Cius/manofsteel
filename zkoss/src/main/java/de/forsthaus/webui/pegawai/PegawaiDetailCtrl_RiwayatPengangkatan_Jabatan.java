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
import de.forsthaus.backend.dao.TrJabatanDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TrJabatan;
import de.forsthaus.backend.model.TrKgbPangkat;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.pegawai.model.RiwayatJabatanListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;

public class PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan extends GFCBaseListCtrl<TrJabatan> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan.class);

	protected Window windowPegawaiDetail_RiwayatPengangkatan_Jabatan;
	private Borderlayout borderLayout_jabatan;
	private Listbox listBox_jabatan;
	private Paging paging_jabatan;

	private Listheader listHeader_jabatan_tmt;

	private final String btnCtrl_clsPref = "btn_jabatan";
	protected ButtonStatusCtrl buttonCtrl;
	protected Button newEntry;
	protected Button edit;
	protected Button save;
	protected Button delete;
	protected Button cancel;

	private int countRows;
	
	private TrKgbPangkat selectedPangkat;

	private AnnotateDataBinder binder;
	private PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan;
	
	private TrJabatanDAO trJabatanDAO;
	private HibernateSearchObject<TrJabatan> searchObj;

	public PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan() {
		super();
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if(arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_RiwayatPengangkatan((PegawaiDetailCtrl_RiwayatPengangkatan) arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_RiwayatPengangkatan().setPegawaiDetailCtrl_RiwayatPengangkatan_Jabatan(this);

			if(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected() != null) {
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected());
			}else {
				setSelected(null);
			}
		} else {
			setSelected(null);
		}
	}

	public void onCreate$windowPegawaiDetail_RiwayatPengangkatan_Jabatan(Event event) {
		this.buttonCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtrl_clsPref, true, null, null, null, null, null, null, newEntry, edit, delete, save, cancel, null);

		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		doFillList();
		this.binder.loadAll();
		
		listBox_jabatan.setItemRenderer(new RiwayatJabatanListModelItemRenderer());
		
		this.buttonCtrl.setInitNew();
	}

	public void doFillList() {
		doFitSize();

		// set the paging params
		paging_jabatan.setPageSize(getCountRows());
		paging_jabatan.setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listHeader_jabatan_tmt.setSortAscending(new FieldComparator("tmtJab", true));
		listHeader_jabatan_tmt.setSortDescending(new FieldComparator("tmtJab", false));
		
		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<TrJabatan>(TrJabatan.class, getCountRows());
		searchObj.addSort("tmtJab", true);
		searchObj.addFilter(Filter.equal("nip", getSelected().getNip()));
		setSearchObj(searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBox_jabatan(), paging_jabatan);
		final BindingListModelList lml = (BindingListModelList) getListBox_jabatan().getModel();
		setRiwayatPengangkatanModelList_Jabatan(lml);

		// check if first time opened and init databinding for selectedBean
		if (getSelected() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBox_jabatan().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				TrKgbPangkat selectedPangkat = (TrKgbPangkat) lml.get(0);
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getPegawaiMainCtrl().getTpCpnsDAO().getByNip(selectedPangkat.getNip()));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBox_jabatan(), getSelected()));
			}
		}
	}

	public void onDoubleClicked(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		TrKgbPangkat jabatan = getSelectedPangkat();

		if (jabatan != null) {
			setSelectedPangkat(jabatan);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("jabatan", jabatan);
			map.put("listbox", getListBox_jabatan());

			try {
				Executions.createComponents("/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_JabatanDialog.zul", null, map);
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
		borderLayout_jabatan.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowPegawaiDetail_RiwayatPengangkatan_Jabatan.invalidate();
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

	public Listbox getListBox_jabatan() {
		return listBox_jabatan;
	}

	public void setListBox_jabatan(Listbox listBox_jabatan) {
		this.listBox_jabatan = listBox_jabatan;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Jabatan() {
		return getPegawaiDetailCtrl_RiwayatPengangkatan().getRiwayatPengangkatanModelList_Jabatan();
	}

	public void setRiwayatPengangkatanModelList_Jabatan(
			BindingListModelList riwayatPengangkatanModelList_Jabatan) {
		getPegawaiDetailCtrl_RiwayatPengangkatan().setRiwayatPengangkatanModelList_Jabatan(riwayatPengangkatanModelList_Jabatan);
	}

	public TrJabatanDAO getTrJabatanDAO() {
		return trJabatanDAO;
	}

	public void setTrJabatanDAO(TrJabatanDAO trJabatanDAO) {
		this.trJabatanDAO = trJabatanDAO;
	}

	public HibernateSearchObject<TrJabatan> getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(HibernateSearchObject<TrJabatan> searchObj) {
		this.searchObj = searchObj;
	}

	public TrKgbPangkat getSelectedPangkat() {
		return selectedPangkat;
	}

	public void setSelectedPangkat(TrKgbPangkat selectedPangkat) {
		this.selectedPangkat = selectedPangkat;
	}
}
