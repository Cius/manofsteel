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

import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report08DaftarJabatanStrukturalCtrl extends GFCBaseCtrl implements
		Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listEselonAwal;
	private Listbox listEselonAkhir;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;

	public Report08DaftarJabatanStrukturalCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {

	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sEselonAwal = listEselonAwal.getSelectedItemApi().getValue().toString();
		String sEselonAkhir = listEselonAkhir.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		// listTbMaster = TbMasterDAO.getByCriterias(criterias);
		listTbMaster = TbMasterDAO.getAll();

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/08daftar_jabatan_struktural.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sEselonAwal = listEselonAwal.getSelectedItemApi().getValue().toString();
		String sEselonAkhir = listEselonAkhir.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();

		String title = "DAFTAR NOMINATIF PNS ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title +=sJenisKelamin+" ";
		}
		title += "YANG MENDUDUKI JABATAN STRUKTURAL ";
		if(!sEselonAwal.equals("")){
			title += "ESELON "+sEselonAwal;
		}
		if(!sEselonAkhir.equals("")){
			title += " - "+sEselonAkhir;
		}
		title += "<br/>";
		if (!sUnitKerja.equals("")) {
			title += "DI LINGKUNGAN : " + sUnitKerja;
		} else if (!sUnitOrganisasi.equals("")) {
			title += "DI LINGKUNGAN : " + sUnitOrganisasi;
		} else {
			title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		}
		return title;
		//"DAFTAR NOMINATIF PNS "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") +"YANG MENDUDUKI JABATAN STRUKTURAL " + (!$P{PARAM_ESELON_AWAL}.equals("")?"ESELEON "+ $P{PARAM_ESELON_AWAL}+" - "+$P{PARAM_ESELON_AKHIR}:"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Listbox getListUnitOrganisasi() {
		return listUnitOrganisasi;
	}

	public void setListUnitOrganisasi(Listbox listUnitOrganisasi) {
		this.listUnitOrganisasi = listUnitOrganisasi;
	}

	public Listbox getListUnitKerja() {
		return listUnitKerja;
	}

	public Listbox getListEselonAwal() {
		return listEselonAwal;
	}

	public void setListEselonAwal(Listbox listEselonAwal) {
		this.listEselonAwal = listEselonAwal;
	}

	public Listbox getListEselonAkhir() {
		return listEselonAkhir;
	}

	public void setListEselonAkhir(Listbox listEselonAkhir) {
		this.listEselonAkhir = listEselonAkhir;
	}

	public Listbox getListJenisKelamin() {
		return listJenisKelamin;
	}

	public void setListJenisKelamin(Listbox listJenisKelamin) {
		this.listJenisKelamin = listJenisKelamin;
	}

	public void setListUnitKerja(Listbox listUnitKerja) {
		this.listUnitKerja = listUnitKerja;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
