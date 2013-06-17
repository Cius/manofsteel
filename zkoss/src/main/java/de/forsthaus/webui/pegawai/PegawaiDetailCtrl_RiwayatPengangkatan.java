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
	protected Tab tab_PegawaiRiwayatPengangkatan_Organisasi;
	protected Tab tab_PegawaiRiwayatPengangkatan_Jasa;
	protected Tab tab_PegawaiRiwayatPengangkatan_LN;
	protected Tab tab_PegawaiRiwayatPengangkatan_Bahasa;
	protected Tab tab_PegawaiRiwayatPengangkatan_DPPP;
	protected Tab tab_PegawaiRiwayatPengangkatan_Hukuman;
	
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Kepangkatan;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Jabatan;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Organisasi;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Jasa;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_LN;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Bahasa;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_DPPP;
	protected Tabpanel tabPanel_PegawaiRiwayatPengangkatan_Hukuman;
	
	private BindingListModelList riwayatPengangkatanModelList_Kepangkatan;
	private BindingListModelList riwayatPengangkatanModelList_Jabatan;
	private BindingListModelList riwayatPengangkatanModelList_Organisasi;
	private BindingListModelList riwayatPengangkatanModelList_Jasa;	
	private BindingListModelList riwayatPengangkatanModelList_LN;	
	private BindingListModelList riwayatPengangkatanModelList_Bahasa;
	private BindingListModelList riwayatPengangkatanModelList_DPPP;	
	private BindingListModelList riwayatPengangkatanModelList_Hukuman;	
	
	private TpKgbPangkatDAO tpKgbPangkatDAO;
	
	private PegawaiMainCtrl pegawaiMainCtrl;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan pegawaiDetailCtrl_RiwayatPengangkatan_Kepangkatan;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Jabatan pegawaiDetailCtrl_RiwayatPengangkatan_Jabatan;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi pegawaiDetailCtrl_RiwayatPengangkatan_Organisasi;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Jasa pegawaiDetailCtrl_RiwayatPengangkatan_Jasa;
	private PegawaiDetailCtrl_RiwayatPengangkatan_LN pegawaiDetailCtrl_RiwayatPengangkatan_LN;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa pegawaiDetailCtrl_RiwayatPengangkatan_Bahasa;
	private PegawaiDetailCtrl_RiwayatPengangkatan_DPPP pegawaiDetailCtrl_RiwayatPengangkatan_DPPP;
	private PegawaiDetailCtrl_RiwayatPengangkatan_Hukuman pegawaiDetailCtrl_RiwayatPengangkatan_Hukuman;
		
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
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Organisasi(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Organisasi != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Organisasi, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Organisasi.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Jasa(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Jasa != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Jasa, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Jasa.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_LN(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_LN != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_LN, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_LN.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Bahasa(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Bahasa != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Bahasa, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Bahasa.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_DPPP(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_DPPP != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_DPPP, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_DPPP.zul");
		}
	}
	
	public void onSelect$tab_PegawaiRiwayatPengangkatan_Hukuman(Event event) throws Exception {
		if (this.tabPanel_PegawaiRiwayatPengangkatan_Hukuman != null) {
			ZksampleCommonUtils.createTabPanelContent(this.tabPanel_PegawaiRiwayatPengangkatan_Hukuman, this, "ModuleMainController", "/WEB-INF/pages/pegawai/pegawaiDetail_RiwayatPengangkatan_Hukuman.zul");
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

	public BindingListModelList getRiwayatPengangkatanModelList_Organisasi() {
		return riwayatPengangkatanModelList_Organisasi;
	}

	public void setRiwayatPengangkatanModelList_Organisasi(
			BindingListModelList riwayatPengangkatanModelList_Organisasi) {
		this.riwayatPengangkatanModelList_Organisasi = riwayatPengangkatanModelList_Organisasi;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi getPegawaiDetailCtrl_RiwayatPengangkatan_Organisasi() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Organisasi;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Organisasi(
			PegawaiDetailCtrl_RiwayatPengangkatan_Organisasi pegawaiDetailCtrl_RiwayatPengangkatan_Organisasi) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Organisasi = pegawaiDetailCtrl_RiwayatPengangkatan_Organisasi;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Jasa() {
		return riwayatPengangkatanModelList_Jasa;
	}

	public void setRiwayatPengangkatanModelList_Jasa(
			BindingListModelList riwayatPengangkatanModelList_Jasa) {
		this.riwayatPengangkatanModelList_Jasa = riwayatPengangkatanModelList_Jasa;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Jasa getPegawaiDetailCtrl_RiwayatPengangkatan_Jasa() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Jasa;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Jasa(
			PegawaiDetailCtrl_RiwayatPengangkatan_Jasa pegawaiDetailCtrl_RiwayatPengangkatan_Jasa) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Jasa = pegawaiDetailCtrl_RiwayatPengangkatan_Jasa;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Bahasa() {
		return riwayatPengangkatanModelList_Bahasa;
	}

	public void setRiwayatPengangkatanModelList_Bahasa(
			BindingListModelList riwayatPengangkatanModelList_Bahasa) {
		this.riwayatPengangkatanModelList_Bahasa = riwayatPengangkatanModelList_Bahasa;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa getPegawaiDetailCtrl_RiwayatPengangkatan_Bahasa() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Bahasa;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Bahasa(
			PegawaiDetailCtrl_RiwayatPengangkatan_Bahasa pegawaiDetailCtrl_RiwayatPengangkatan_Bahasa) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Bahasa = pegawaiDetailCtrl_RiwayatPengangkatan_Bahasa;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_DPPP() {
		return riwayatPengangkatanModelList_DPPP;
	}

	public void setRiwayatPengangkatanModelList_DPPP(
			BindingListModelList riwayatPengangkatanModelList_DPPP) {
		this.riwayatPengangkatanModelList_DPPP = riwayatPengangkatanModelList_DPPP;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_DPPP getPegawaiDetailCtrl_RiwayatPengangkatan_DPPP() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_DPPP;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_DPPP(
			PegawaiDetailCtrl_RiwayatPengangkatan_DPPP pegawaiDetailCtrl_RiwayatPengangkatan_DPPP) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_DPPP = pegawaiDetailCtrl_RiwayatPengangkatan_DPPP;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_LN() {
		return riwayatPengangkatanModelList_LN;
	}

	public void setRiwayatPengangkatanModelList_LN(
			BindingListModelList riwayatPengangkatanModelList_LN) {
		this.riwayatPengangkatanModelList_LN = riwayatPengangkatanModelList_LN;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_LN getPegawaiDetailCtrl_RiwayatPengangkatan_LN() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_LN;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_LN(
			PegawaiDetailCtrl_RiwayatPengangkatan_LN pegawaiDetailCtrl_RiwayatPengangkatan_LN) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_LN = pegawaiDetailCtrl_RiwayatPengangkatan_LN;
	}

	public BindingListModelList getRiwayatPengangkatanModelList_Hukuman() {
		return riwayatPengangkatanModelList_Hukuman;
	}

	public void setRiwayatPengangkatanModelList_Hukuman(
			BindingListModelList riwayatPengangkatanModelList_Hukuman) {
		this.riwayatPengangkatanModelList_Hukuman = riwayatPengangkatanModelList_Hukuman;
	}

	public PegawaiDetailCtrl_RiwayatPengangkatan_Hukuman getPegawaiDetailCtrl_RiwayatPengangkatan_Hukuman() {
		return pegawaiDetailCtrl_RiwayatPengangkatan_Hukuman;
	}

	public void setPegawaiDetailCtrl_RiwayatPengangkatan_Hukuman(
			PegawaiDetailCtrl_RiwayatPengangkatan_Hukuman pegawaiDetailCtrl_RiwayatPengangkatan_Hukuman) {
		this.pegawaiDetailCtrl_RiwayatPengangkatan_Hukuman = pegawaiDetailCtrl_RiwayatPengangkatan_Hukuman;
	}
}
