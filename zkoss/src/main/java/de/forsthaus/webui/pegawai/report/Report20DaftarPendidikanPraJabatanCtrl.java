package de.forsthaus.webui.pegawai.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report20DaftarPendidikanPraJabatanCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJenis;
	private Listbox listJabatan;
	private Button btnCari;
	private Jasperreport report;
	
	String [] arrJenis = {"BELUM LATPRAJAB"};
	String [] arrKodeJenis = {"1"};
	String [] arrJabatan = {"LATPRAJAB II"};
	String [] arrKodeJabatan = {"1"};

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report20DaftarPendidikanPraJabatanCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init jenis
		for (int i = 0; i < arrJenis.length; i++) {
			listJenis.appendItemApi(arrJenis[i], arrKodeJenis[i]);
		}
		listJenis.setSelectedIndex(0);
		
		//init jabatan
		for (int i = 0; i < arrJabatan.length; i++) {
			listJabatan.appendItemApi(arrJabatan[i], arrKodeJabatan[i]);
		}
		listJabatan.setSelectedIndex(0);
		
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJenis = listJenis.getSelectedItemApi().getValue().toString();
		String sJabatan = listJabatan.getSelectedItemApi().getValue().toString();
		
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JENIS, sJenis);
		criterias.put(ConstantsText.JABATAN, sJabatan);
		listTbMaster = TbMasterDAO.getDaftar20PendidikanPraJabatan(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/20daftar_pendidikan_pra_jabatan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJenis = listJenis.getSelectedItemApi().getValue().toString();
		String sJabatan = listJabatan.getSelectedItemApi().getValue().toString();
		int iJenis = listJenis.getSelectedIndex();
		int iJabatan = listJabatan.getSelectedIndex();
		String [] titleJabatan = {"PRAJABATAN GOLONGAN II"};

		String title = "DAFTAR NOMINATIF CPNS YANG BELUM MENGIKUTI DIKLAT ";
		title += titleJabatan[iJabatan];
		
		title += "<br/>";
		title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Listbox getListJenis() {
		return listJenis;
	}

	public void setListJenis(Listbox listJenis) {
		this.listJenis = listJenis;
	}

	public Listbox getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(Listbox listJabatan) {
		this.listJabatan = listJabatan;
	}

	public Button getBtnCari() {
		return btnCari;
	}

	public void setBtnCari(Button btnCari) {
		this.btnCari = btnCari;
	}

	public Jasperreport getReport() {
		return report;
	}

	public void setReport(Jasperreport report) {
		this.report = report;
	}

	public TbMasterDAO getTbMasterDAO() {
		return TbMasterDAO;
	}

	public void setTbMasterDAO(TbMasterDAO TbMasterDAO) {
		this.TbMasterDAO = TbMasterDAO;
	}

	public MasterUnitKerjaDAO getMasterUnitKerjaDAO() {
		return masterUnitKerjaDAO;
	}

	public void setMasterUnitKerjaDAO(MasterUnitKerjaDAO masterUnitKerjaDAO) {
		this.masterUnitKerjaDAO = masterUnitKerjaDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
