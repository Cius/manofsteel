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

public class Report19DaftarPendidikanKursusNonKedinasanCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listKursus;
	private Button btnCari;
	private Jasperreport report;

	String [] arrKursus={"AGRIBISNIS JAGUNG"};
	String [] arrKodeKursus={"1"};
	
	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report19DaftarPendidikanKursusNonKedinasanCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init kursus
		for (int i = 0; i < arrKursus.length; i++) {
			listKursus.appendItemApi(arrKursus[i], arrKodeKursus[i]);
		}
		listKursus.setSelectedIndex(0);
		
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sKursus = listKursus.getSelectedItemApi().getValue().toString();

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.KURSUS, sKursus);
		listTbMaster = TbMasterDAO.getDaftar19PendidikanKursusNonKedinasan(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/19daftar_pendidikan_kursus_non_kedinasan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sKursus = listKursus.getSelectedItemApi().getValue().toString();
		int iKursus = listKursus.getSelectedIndex();

		String title = "DAFTAR NOMINATIF PNS YANG TELAH MENGIKUTI KURSUS NON KEDINASAN ";
		title +=arrKursus[iKursus];
		
		title += "<br/>";
		title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Listbox getListKursus() {
		return listKursus;
	}

	public void setListKursus(Listbox listKursus) {
		this.listKursus = listKursus;
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
