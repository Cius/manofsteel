package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.dao.TpIdentitasDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.webui.util.GFCBaseListCtrl;

public class PegawaiListCtrl extends GFCBaseListCtrl<TpCpns> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PegawaiListCtrl.class);

	protected Window windowPegawaiList;
	private Borderlayout borderLayout_PegawaiList;

	private Paging paging_PegawaiList;
	private Listbox listBox_PegawaiList;
	private Listheader listHeader_PegawaiList_nip;
	private Listheader listHeader_PegawaiList_name;

	private int countRows;

	private HibernateSearchObject<TpCpns> searchObj;

	private AnnotateDataBinder binder;
	private PegawaiMainCtrl pegawaiMainCtrl;

	private TpCpnsDAO tpCpnsDAO;
//	private TpIdentitasDAO tpIdentitasDAO;

	public PegawaiListCtrl() {
		super();
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);

		if(arg.containsKey("ModuleMainController")) {
			setPegawaiMainCtrl((PegawaiMainCtrl) arg.get("ModuleMainController"));

			getPegawaiMainCtrl().setPegawaiListCtrl(this);

			if(getPegawaiMainCtrl().getSelected() != null) {
				setSelected(getPegawaiMainCtrl().getSelected());
			}else {
				setSelected(null);
			}
		}
		setSelected(null);
	}

	public void onCreate$windowPegawaiList(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		doFillList();
		this.binder.loadAll();
	}

	public void doFillList() {
		doFitSize();

		// set the paging params
		paging_PegawaiList.setPageSize(getCountRows());
		paging_PegawaiList.setDetailed(true);

		// not used listheaders must be declared like ->
		// lh.setSortAscending(""); lh.setSortDescending("")
		listHeader_PegawaiList_nip.setSortAscending(new FieldComparator("nip", true));
		listHeader_PegawaiList_nip.setSortDescending(new FieldComparator("nip", false));

		// ++ create the searchObject and init sorting ++//
		// get customers and only their latest address
		searchObj = new HibernateSearchObject<TpCpns>(TpCpns.class, getCountRows());
		searchObj.addSort("nip", false);
		setSearchObj(searchObj);

		// Set the BindingListModel
		getPagedBindingListWrapper().init(searchObj, getListBox_PegawaiList(), paging_PegawaiList);
		final BindingListModelList lml = (BindingListModelList) getListBox_PegawaiList().getModel();
		setPegawaiModelList(lml);

		// check if first time opened and init databinding for selectedBean
		if (getSelected() == null) {
			// init the bean with the first record in the List
			if (lml.getSize() > 0) {
				final int rowIndex = 0;
				// only for correct showing after Rendering. No effect as an
				// Event
				// yet.
				getListBox_PegawaiList().setSelectedIndex(rowIndex);
				// get the first entry and cast them to the needed object
				setSelected((TpCpns) lml.get(0));

				// call the onSelect Event for showing the objects data in the
				// statusBar
				Events.sendEvent(new Event("onSelect", getListBox_PegawaiList(), getSelected()));
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
		borderLayout_PegawaiList.setHeight(String.valueOf(maxListBoxHeight) + "px");

		windowPegawaiList.invalidate();
	}

	public Listbox getListBox_PegawaiList() {
		return listBox_PegawaiList;
	}

	public void setListBox_PegawaiList(Listbox listBox_PegawaiList) {
		this.listBox_PegawaiList = listBox_PegawaiList;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	public HibernateSearchObject<TpCpns> getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(HibernateSearchObject<TpCpns> searchObj) {
		this.searchObj = searchObj;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public PegawaiMainCtrl getPegawaiMainCtrl() {
		return pegawaiMainCtrl;
	}

	public void setPegawaiMainCtrl(PegawaiMainCtrl pegawaiMainCtrl) {
		this.pegawaiMainCtrl = pegawaiMainCtrl;
	}

	public TpCpnsDAO getTpCpnsDAO() {
		return tpCpnsDAO;
	}

	public void setTpCpnsDAO(TpCpnsDAO tpCpnsDAO) {
		this.tpCpnsDAO = tpCpnsDAO;
	}

	public TpCpns getSelected() {
		return getPegawaiMainCtrl().getSelected();
	}

	public void setSelected(TpCpns selected) {
		getPegawaiMainCtrl().setSelected(selected);
	}

	public BindingListModelList getPegawaiModelList() {
		return getPegawaiMainCtrl().getPegawaiModelList();
	}

	public void setPegawaiModelList(BindingListModelList pegawaiModelList) {
		getPegawaiMainCtrl().setPegawaiModelList(pegawaiModelList);
	}

//	public TpIdentitasDAO getTpIdentitasDAO() {
//		return tpIdentitasDAO;
//	}
//
//	public void setTpIdentitasDAO(TpIdentitasDAO tpIdentitasDAO) {
//		this.tpIdentitasDAO = tpIdentitasDAO;
//	}
}
