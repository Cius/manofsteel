package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.TpKgbPangkatDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.ZksampleCommonUtils;

public class PegawaiDetailCtrl_RiwayatPengangkatan extends GFCBaseCtrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8152100945425893228L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan.class); 
	
	protected Window windowPegawaiDetail_RiwayatPengangkatan;
	private Borderlayout borderLayout_PegawaiRiwayatPengangkatan;
	
	private Tabbox tabbox_PegawaiRiwayatPengangkatan;
	protected Tab tab_PegawaiRiwayatPengangkatan_Kepangkatan;
	protected Tab tab_PegawaiRiwayatPengangkatan_Jabatan;
	protected Tab tab_PegawaiRiwayatPengangkatan_PengangkatanPNS;
	protected Tab tab_PegawaiRiwayatPengangkatan_Pangkat;
	protected Tab tab_PegawaiRiwayatPengangkatan_Jabatan2;
	protected Tab tab_PegawaiRiwayatPengangkatan_Gaji;
	protected Tab tab_PegawaiRiwayatPengangkatan_Pendidikan;
	protected Tab tab_PegawaiRiwayatPengangkatan_Instansi;
	
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Jabatan;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_PengangkatanPNS;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Pangkat;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Jabatan2;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Gaji;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Pendidikan;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Instansi;
	
	private BindingListModelList riwayatPengangkatanModelList_Kepangkatan;
	private BindingListModelList riwayatPengangkatanModelList_Jabatan;
	
	private TpKgbPangkatDAO tpKgbPangkatDAO;
	
	private PegawaiMainCtrl pegawaiMainCtrl;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan;
		
	public PegawaiDetailCtrl_RiwayatPengangkatan() {
		super();
	}
	
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
//		this.self.setAttribute("controller", this, false);
		
		if (this.arg.containsKey("ModuleMainController")) {
			setPegawaiMainCtrl((PegawaiMainCtrl) this.arg.get("ModuleMainController"));

			getPegawaiMainCtrl().setPegawaiDetailCtrl_RiwayatPengangkatan(this);

			if (getPegawaiMainCtrl().getSelected() != null) {
				setSelected(getPegawaiMainCtrl().getSelected());
			} else
				setSelected(null);
		} else {
			setSelected(null);
		}
	}
	
	public void onCreate$windowPegawaiDetail_RiwayatPengangkatan(Event event) throws Exception {
		this.tab_PegawaiRiwayatPengangkatan_Kepangkatan.setVisible(true);
		
		if(this.tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Kepangkatan.zul");
		}
		
		doFitSize(event);
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Kepangkatan(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan.getFirstChild() != null) {
			this.tab_PegawaiRiwayatPengangkatan_Kepangkatan.setSelected(true);

			// refresh the Binding mechanism
			getPegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan().setSelected(getSelected());
			getPegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan().getBinder().loadAll();
			return;
		}

		if (this.tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Kepangkatan.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Jabatan(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Jabatan != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Jabatan, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Jabatan.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_PengangkatanPNS(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_PengangkatanPNS != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_PengangkatanPNS, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_PengangkatanPNS.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Pangkat(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Pangkat != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Pangkat, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Pangkat.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Jabatan2(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Jabatan2 != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Jabatan2, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Jabatan2.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Gaji(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Gaji != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Gaji, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Gaji.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Pendidikan(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Pendidikan != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Pendidikan, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Pendidikan.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Instansi(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Instansi != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Instansi, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Instansi.zul");
		}
	}
	
	public void doFitSize(Event event) {
		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - 152;
		this.borderLayout_PegawaiRiwayatPengangkatan.setHeight(String.valueOf(maxListBoxHeight) + "px");

		this.windowPegawaiDetail_RiwayatPengangkatan.invalidate();
	}
	
	public void setSelected(TpCpns cpns) {
		getPegawaiMainCtrl().setSelected(cpns);
	}
	
	public TpCpns getSelected() {
		return getPegawaiMainCtrl().getSelected();
	}
	
	public void setOriginal(TpCpns cpns) {
		getPegawaiMainCtrl().setOriginal(cpns);
	}
	
	public TpCpns getOriginal() {
		return getPegawaiMainCtrl().getOriginal();
	}

	public PegawaiMainCtrl getPegawaiMainCtrl() {
		return pegawaiMainCtrl;
	}

	public void setPegawaiMainCtrl(PegawaiMainCtrl pegawaiMainCtrl) {
		this.pegawaiMainCtrl = pegawaiMainCtrl;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan getPegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan(
			PegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan = pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Kepangkatan() {
		return riwayatPengangkatanModelList_Kepangkatan;
	}

	public void setRiwayatPengangkatanModelList_Kepangkatan(
			BindingListModelList riwayatPengangkatanModelList_Kepangkatan) {
		this.riwayatPengangkatanModelList_Kepangkatan = riwayatPengangkatanModelList_Kepangkatan;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Jabatan() {
		return riwayatPengangkatanModelList_Jabatan;
	}

	public void setRiwayatPengangkatanModelList_Jabatan(
			BindingListModelList riwayatPengangkatanModelList_Jabatan) {
		this.riwayatPengangkatanModelList_Jabatan = riwayatPengangkatanModelList_Jabatan;
	}

	public TpKgbPangkatDAO getTpKgbPangkatDAO() {
		return tpKgbPangkatDAO;
	}

	public void setTpKgbPangkatDAO(TpKgbPangkatDAO tpKgbPangkatDAO) {
		this.tpKgbPangkatDAO = tpKgbPangkatDAO;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan getPegawaiDetailCtrl_RiwayatPengangkatan_Jabatan() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Jabatan(
			PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan = pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan;
	}
}
