package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.ZksampleCommonUtils;

public class PegawaiMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572901298975748252L;
	private static final Logger logger = Logger.getLogger(PegawaiMainCtrl.class);
	
	protected Window windowPegawaiMain;
	
	protected Tabbox tabbox_PegawaiMain;
	protected Tab tab_PegawaiList;
	protected Tab tab_DataPokok;
	protected Tab tab_RiwayatPengangkatan;
	protected Tab tab_RiwayatPendidikan;
	protected Tab tab_RiwayatKeluarga;
	
	protected Tabpanel tabpanel_PegawaiList;
	protected Tabpanel tabpanel_DataPokok;
	protected Tabpanel tabpanel_RiwayatPengangkatan;
	protected Tabpanel tabpanel_RiwayatPendidikan;
	protected Tabpanel tabpanel_RiwayatKeluarga;
	
	protected Textbox txt_Nip;
	
	private final String btnCtrl_clsPref = "btn_Pegawai";
	protected ButtonStatusCtrl buttonCtrl;	
	protected Button search;
	protected Button newEntry;
	protected Button edit;
	protected Button save;
	protected Button delete;
	protected Button cancel;
	
	protected Button print;
	protected Button first;
	protected Button prev;
	protected Button next;
	protected Button last;
	
	private TpCpns selected;
	private BindingListModelList pegawaiModelList;
	
	private PegawaiListCtrl pegawaiListCtrl;
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	
	private TpCpnsDAO tpCpnsDAO;
	
	public PegawaiMainCtrl() {
		super();
	}
	
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);
	}
	
	public void onCreate$windowPegawaiMain(Event event) throws Exception {
		this.buttonCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtrl_clsPref, true, search, print, first, prev, next, last, newEntry, edit, delete, save, cancel, null);
		
		this.tab_PegawaiList.setVisible(true);
		if(this.tabpanel_PegawaiList != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabpanel_PegawaiList, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiList.zul");
		}
		
		this.buttonCtrl.setInitNew();
	}
	
	public void onSelect$tabpanel_DataPokok(Event event) {
		if (this.tabpanel_DataPokok.getFirstChild() != null) {
			this.tab_DataPokok.setSelected(true);

			// refresh the Binding mechanism
			getPegawaiDetailCtrl_DataPokok().setSelected(getSelected());
			getPegawaiDetailCtrl_DataPokok().getBinder().loadAll();
			return;
		}

		if (this.tabpanel_DataPokok != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabpanel_DataPokok, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_DataPokok.zul");
		}
	}

	public PegawaiListCtrl getPegawaiListCtrl() {
		return pegawaiListCtrl;
	}

	public void setPegawaiListCtrl(PegawaiListCtrl pegawaiListCtrl) {
		this.pegawaiListCtrl = pegawaiListCtrl;
	}

	public TpCpns getSelected() {
		return selected;
	}

	public void setSelected(TpCpns selected) {
		this.selected = selected;
	}

	public BindingListModelList getPegawaiModelList() {
		return pegawaiModelList;
	}

	public void setPegawaiModelList(BindingListModelList pegawaiModelList) {
		this.pegawaiModelList = pegawaiModelList;
	}

	public TpCpnsDAO getTpCpnsDAO() {
		return tpCpnsDAO;
	}

	public void setTpCpnsDAO(TpCpnsDAO tpCpnsDAO) {
		this.tpCpnsDAO = tpCpnsDAO;
	}

	public PegawaiDetailCtrl_DataPokok getPegawaiDetailCtrl_DataPokok() {
		return pegawaiDetailCtrl_DataPokok;
	}

	public void setPegawaiDetailCtrl_DataPokok(
			PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok) {
		this.pegawaiDetailCtrl_DataPokok = pegawaiDetailCtrl_DataPokok;
	}
}
