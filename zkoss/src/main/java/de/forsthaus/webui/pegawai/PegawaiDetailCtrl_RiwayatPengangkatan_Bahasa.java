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
import de.forsthaus.backend.dao.TrBahasaDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TrBahasa;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.pegawai.model.RiwayatBahasaListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;

public class PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa extends GFCBaseListCtrl<TrBahasa> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa.class);

	protected Window windowPegawaiDetail_RiwayatPengangkatan_Bahasa;
	private Borderlayout borderLayout_bahasa;
	private Listbox listBox_bahasa;
	private Paging paging_bahasa;

	private Listheader listHeader_bahasa_tmt;

	private final String btnCtrl_clsPref = "btn_bahasa";
	protected ButtonStatusCtrl buttonCtrl;
	protected Button newEntry;
	protected Button edit;
	protected Button save;
	protected Button delete;
	protected Button cancel;

	private int countRows;
	
	private TrBahasa selectedBahasa;

	private AnnotateDataBinder binder;
	private PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan;
	
	private TrBahasaDAO trBahasaDAO;
	private HibernateSearchObject<TrBahasa> searchObj;

	public PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa() {
		super();
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if(arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_RiwayatPengangkatan((PegawaiDetailCtrl_RiwayatPengangkatan) arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_RiwayatPengangkatan().setPegawaiDetailCtrl_RiwayatPengangkatan_Bahasa(this);

			if(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected() != null) {
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected());
			}else {
				setSelected(null);
			}
		} else {
			setSelected(null);
		}
	}

	public void onCreate$windowPegawaiDetail_RiwayatPengangkatan_Bahasa(Event event) {
		this.buttonCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtrl_clsPref, true, null, null, null, null, null, null, newEntry, edit, delete, save, cancel, null);

		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		doFillList();
		this.binder.loadAll();
		
		listBox_bahasa.setItemRenderer(new RiwayatBahasaListModelItemRenderer());
		
		this.buttonCtrl.setInitNew();
	}

	public void doFillList() {
		doFitSize();

		// set the paging params
		paging_bahasa.setPageSize(getCountRows());
		paging_bahasa.setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listHeader_bahasa_tmt.setSortAscending(new FieldComparator("tmtJab", true));
		listHeader_bahasa_tmt.setSortDescending(new FieldComparator("tmtJab", false));
		
		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<TrBahasa>(TrBahasa.class, getCountRows());
		searchObj.addSort("numBahasa", true);
		searchObj.addFilter(Filter.equal("nip", getSelected().getNip()));
		setSearchObj(searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBox_bahasa(), paging_bahasa);
		final BindingListModelList lml = (BindingListModelList) getListBox_bahasa().getModel();
		setRiwayatPengangkatanModelList_Bahasa(lml);

		// check if first time opened and init databinding for selectedBean
		if (getSelected() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBox_bahasa().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				TrBahasa selectedBahasa = (TrBahasa) lml.get(0);
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getPegawaiMainCtrl().getTpCpnsDAO().getByNip(selectedBahasa.getNip()));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBox_bahasa(), getSelected()));
			}
		}
	}

	public void onDoubleClicked(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		TrBahasa bahasa = getSelectedBahasa();

		if (bahasa != null) {
			setSelectedBahasa(bahasa);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("bahasa", bahasa);
			map.put("listbox", getListBox_bahasa());

			try {
				Executions.createComponents("/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_BahasaDialog.zul", null, map);
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
		borderLayout_bahasa.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowPegawaiDetail_RiwayatPengangkatan_Bahasa.invalidate();
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

	public Listbox getListBox_bahasa() {
		return listBox_bahasa;
	}

	public void setListBox_bahasa(Listbox listBox_bahasa) {
		this.listBox_bahasa = listBox_bahasa;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Bahasa() {
		return getPegawaiDetailCtrl_RiwayatPengangkatan().getRiwayatPengangkatanModelList_Bahasa();
	}

	public void setRiwayatPengangkatanModelList_Bahasa(
			BindingListModelList riwayatPengangkatanModelList_Bahasa) {
		getPegawaiDetailCtrl_RiwayatPengangkatan().setRiwayatPengangkatanModelList_Bahasa(riwayatPengangkatanModelList_Bahasa);
	}

	public TrBahasaDAO getTrBahasaDAO() {
		return trBahasaDAO;
	}

	public void setTrBahasaDAO(TrBahasaDAO trBahasaDAO) {
		this.trBahasaDAO = trBahasaDAO;
	}

	public HibernateSearchObject<TrBahasa> getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(HibernateSearchObject<TrBahasa> searchObj) {
		this.searchObj = searchObj;
	}

	public TrBahasa getSelectedBahasa() {
		return selectedBahasa;
	}

	public void setSelectedBahasa(TrBahasa selectedBahasa) {
		this.selectedBahasa = selectedBahasa;
	}
}
