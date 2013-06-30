package de.forsthaus.webui.pegawai.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.MasterUnitKerja;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report23DaftarPendidikanAlumniJurusanCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Text textJurusan;
	private Listbox listPendidikan;	
	private Button btnCari;
	private Jasperreport report;
	
	String [] arrPendidikan ={"S.1/Sarjana"};
	String [] arrKodePendidikan ={"1"};

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report23DaftarPendidikanAlumniJurusanCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init jurusan
		for (int i = 0; i < arrPendidikan.length; i++) {
			listPendidikan.appendItemApi(arrPendidikan[i], arrKodePendidikan[i]);
		}
		listPendidikan.setSelectedIndex(0);
		
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJurusan = textJurusan.getValue().toString();
		String sPendidikan = listPendidikan.getSelectedItemApi().getValue().toString();

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JURUSAN, sJurusan);
		criterias.put(ConstantsText.PENDIDIKAN, sPendidikan);
		listTbMaster = TbMasterDAO.getDaftar23PendidikanAlumniJurusan(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/23daftar_pendidikan_alumni_jurusan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJurusan = textJurusan.getValue().toString();
		String sPendidikan = listPendidikan.getSelectedItemApi().getValue().toString();
		int iPendidikan = listPendidikan.getSelectedIndex();
		
		String title = "DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL BERDASARKAN PENDIDIKAN ";
		title+=arrPendidikan[iPendidikan];
		title +="<br/>";
		title +="YANG MEMILIKI LATAR BELAKANG PROGRAM STUDI ";
		title +=sJurusan.toUpperCase();
		title +="<br/>";
		title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Text getTextJurusan() {
		return textJurusan;
	}

	public void setTextJurusan(Text textJurusan) {
		this.textJurusan = textJurusan;
	}

	public Listbox getListPendidikan() {
		return listPendidikan;
	}

	public void setListPendidikan(Listbox listPendidikan) {
		this.listPendidikan = listPendidikan;
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
