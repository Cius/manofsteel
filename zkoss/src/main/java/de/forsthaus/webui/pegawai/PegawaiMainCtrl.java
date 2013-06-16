package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
	
	private TpCpns selected;
	private TpCpns original;
	private BindingListModelList pegawaiModelList;
	
	private PegawaiListCtrl pegawaiListCtrl;
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	private PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan;
	
	private TpCpnsDAO tpCpnsDAO;
	
	public PegawaiMainCtrl() {
		super();
	}
	
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);
	}
	
	public void onCreate$windowPegawaiMain(Event event) throws Exception {
		this.tab_PegawaiList.setVisible(true);
		if(this.tabpanel_PegawaiList != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabpanel_PegawaiList, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiList.zul");
		}
	}
	
	public void onSelect$tabpanel_DataPokok(Event event) {
		if (this.tabpanel_DataPokok.getFirstChild() != null) {
			this.tab_DataPokok.setSelected(true);

			// refresh the Binding mechanism
			getPegawaiDetailCtrl_DataPokok().setSelected(getSelected());
			getPegawaiDetailCtrl_DataPokok().getBinder().loadAll();
			
			Events.sendEvent(new Event("onSelect", getPegawaiDetailCtrl_DataPokok().tabPanel_PegawaiDataPokok_Identitas, getSelected()));
			return;
		}

		if (this.tabpanel_DataPokok != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabpanel_DataPokok, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_DataPokok.zul");
		}
	}
	
	public void onSelect$tab_RiwayatPengangkatan(Event event) {
		if (this.tabpanel_RiwayatPengangkatan.getFirstChild() != null) {
			this.tab_RiwayatPengangkatan.setSelected(true);

			// refresh the Binding mechanism
			getPegawaiDetailCtrl_DataPokok().setSelected(getSelected());
			getPegawaiDetailCtrl_DataPokok().getBinder().loadAll();
			
			Events.sendEvent(new Event("onSelect", getPegawaiDetailCtrl_RiwayatPengangkatan().tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan, getSelected()));
			return;
		}

		if (this.tabpanel_RiwayatPengangkatan != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabpanel_RiwayatPengangkatan, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan.zul");
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

	public TpCpns getOriginal() {
		return original;
	}

	public void setOriginal(TpCpns original) {
		this.original = original;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan getPegawaiDetailCtrl_RiwayatPengangkatan() {
		return pegawaiDetailCtrl_RiwayatPengangkatan;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan(
			PegawaiDetailCtrl_RiwayatPengangkatan pegawaiDetailCtrl_RiwayatPengangkatan) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan = pegawaiDetailCtrl_RiwayatPengangkatan;
	}
}
