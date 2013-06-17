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
import de.forsthaus.backend.dao.TrJasaDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TrJasa;
import de.forsthaus.backend.model.TrJasa;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.pegawai.model.RiwayatJasaListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseListCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;

public class PegawaiDetailCtrl_RiwayatPengangkatan_Jasa extends GFCBaseListCtrl<TrJasa> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_Jasa.class);

	protected Window windowPegawaiDetail_RiwayatPengangkatan_Jasa;
	private Borderlayout borderLayout_jasa;
	private Listbox listBox_jasa;
	private Paging paging_jasa;

	private Listheader listHeader_jasa_tmt;

	private final String btnCtrl_clsPref = "btn_jasa";
	protected ButtonStatusCtrl buttonCtrl;
	protected Button newEntry;
	protected Button edit;
	protected Button save;
	protected Button delete;
	protected Button cancel;

	private int countRows;
	
	private TrJasa selectedJasa;

	private AnnotateDataBinder binder;
	private PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan;
	
	private TrJasaDAO trJasaDAO;
	private HibernateSearchObject<TrJasa> searchObj;

	public PegawaiDetailCtrl_RiwayatPengangkatan_Jasa() {
		super();
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if(arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_RiwayatPengangkatan((PegawaiDetailCtrl_RiwayatPengangkatan) arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_RiwayatPengangkatan().setPegawaiDetailCtrl_RiwayatPengangkatan_Jasa(this);

			if(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected() != null) {
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getSelected());
			}else {
				setSelected(null);
			}
		} else {
			setSelected(null);
		}
	}

	public void onCreate$windowPegawaiDetail_RiwayatPengangkatan_Jasa(Event event) {
		this.buttonCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtrl_clsPref, true, null, null, null, null, null, null, newEntry, edit, delete, save, cancel, null);

		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		doFillList();
		this.binder.loadAll();
		
		listBox_jasa.setItemRenderer(new RiwayatJasaListModelItemRenderer());
		
		this.buttonCtrl.setInitNew();
	}

	public void doFillList() {
		doFitSize();

		// set the paging params
		paging_jasa.setPageSize(getCountRows());
		paging_jasa.setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listHeader_jasa_tmt.setSortAscending(new FieldComparator("tmtJab", true));
		listHeader_jasa_tmt.setSortDescending(new FieldComparator("tmtJab", false));
		
		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<TrJasa>(TrJasa.class, getCountRows());
		searchObj.addSort("tmtJab", true);
		searchObj.addFilter(Filter.equal("nip", getSelected().getNip()));
		setSearchObj(searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBox_jasa(), paging_jasa);
		final BindingListModelList lml = (BindingListModelList) getListBox_jasa().getModel();
		setRiwayatPengangkatanModelList_Jasa(lml);

		// check if first time opened and init databinding for selectedBean
		if (getSelected() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBox_jasa().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				TrJasa selectedJasa = (TrJasa) lml.get(0);
				setSelected(getPegawaiDetailCtrl_RiwayatPengangkatan().getPegawaiMainCtrl().getTpCpnsDAO().getByNip(selectedJasa.getNip()));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBox_jasa(), getSelected()));
			}
		}
	}

	public void onDoubleClicked(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		TrJasa jasa = getSelectedJasa();

		if (jasa != null) {
			setSelectedJasa(jasa);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("jasa", jasa);
			map.put("listbox", getListBox_jasa());

			try {
				Executions.createComponents("/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_JasaDialog.zul", null, map);
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
		borderLayout_jasa.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowPegawaiDetail_RiwayatPengangkatan_Jasa.invalidate();
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

	public Listbox getListBox_jasa() {
		return listBox_jasa;
	}

	public void setListBox_jasa(Listbox listBox_jasa) {
		this.listBox_jasa = listBox_jasa;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Jasa() {
		return getPegawaiDetailCtrl_RiwayatPengangkatan().getRiwayatPengangkatanModelList_Jasa();
	}

	public void setRiwayatPengangkatanModelList_Jasa(
			BindingListModelList riwayatPengangkatanModelList_Jasa) {
		getPegawaiDetailCtrl_RiwayatPengangkatan().setRiwayatPengangkatanModelList_Jasa(riwayatPengangkatanModelList_Jasa);
	}

	public TrJasaDAO getTrJasaDAO() {
		return trJasaDAO;
	}

	public void setTrJasaDAO(TrJasaDAO trJasaDAO) {
		this.trJasaDAO = trJasaDAO;
	}

	public HibernateSearchObject<TrJasa> getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(HibernateSearchObject<TrJasa> searchObj) {
		this.searchObj = searchObj;
	}

	public TrJasa getSelectedJasa() {
		return selectedJasa;
	}

	public void setSelectedJasa(TrJasa selectedJasa) {
		this.selectedJasa = selectedJasa;
	}
}
