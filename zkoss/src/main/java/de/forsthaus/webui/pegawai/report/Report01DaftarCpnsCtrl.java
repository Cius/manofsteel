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

import de.forsthaus.backend.dao.TpCpnsReportDAO;
import de.forsthaus.backend.model.TpCpnsReport;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report01DaftarCpnsCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listMasaKerjaAwal;
	private Listbox listMasaKerjaAkhir;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Button btnCari;
	private Jasperreport report;
	
	private TpCpnsReportDAO tpCpnsReportDAO;

	public Report01DaftarCpnsCtrl() {
		super();
	}
	
	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TpCpnsReport> listTpCpnsReport = new ArrayList<TpCpnsReport>();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAwal = listMasaKerjaAwal.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAkhir = listMasaKerjaAkhir.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sMasaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
			int awal = Integer.parseInt(listMasaKerjaAwal.getSelectedItemApi().getValue().toString());
	         int akhir = Integer.parseInt(listMasaKerjaAkhir.getSelectedItemApi().getValue().toString());
	         if(awal>akhir){
	         	alert("Masa Kerja Awal harus lebih kecil daripada Masa Kerja Akhir");
	         	listMasaKerjaAwal.setSelectedIndex(0);
	         	listMasaKerjaAkhir.setSelectedIndex(0);
	         	return;
	         }
		}
         
		Map<String,Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.MASA_KERJA_AWAL, sMasaKerjaAwal);
		criterias.put(ConstantsText.MASA_KERJA_AKHIR, sMasaKerjaAkhir);
		criterias.put(ConstantsText.UNIT_KERJA,sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI,sUnitOrganisasi);
		listTpCpnsReport = tpCpnsReportDAO.getByCriterias(criterias);
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTpCpnsReport);
		report.setDatasource(jbs);
		report.setSrc("/reports/01daftar_calon_pns.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAwal = listMasaKerjaAwal.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAkhir = listMasaKerjaAkhir.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		String title = "DAFTAR NOMINATIF PEGAWAI ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title+=sJenisKelamin;
		}
		if(!sMasaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
			title += "DENGAN MASA KERJA "+sMasaKerjaAwal+" TAHUN";
			if(Integer.parseInt(sMasaKerjaAwal)<Integer.parseInt(sMasaKerjaAkhir)){
				title += " - "+sMasaKerjaAkhir+" TAHUN";
			}
		}
		title += " YANG BERSTATUS SEBAGAI CALON PNS ";
		title +="<br/>";
		if(!sUnitKerja.equals("")){
			title += "DI LINGKUNGAN : "+sUnitKerja;
		}else if(!sUnitOrganisasi.equals("")){
			title += "DI LINGKUNGAN : "+sUnitOrganisasi;
		}else{
			title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		}
		return title;
		//return "DAFTAR NOMINATIF PEGAWAI " + (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}:"") + " YANG BERSTATUS SEBAGAI CALON PNS " + (!$P{PARAM_KERJA_MIN}.equals("")?"DENGAN MASA KERJA "+$P{PARAM_KERJA_MIN}+" TAHUN - "+$P{PARAM_KERJA_MAX}+" TAHUN":"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU");
	}

	public Listbox getListMasaKerjaAwal() {
		return listMasaKerjaAwal;
	}

	public void setListMasaKerjaAwal(Listbox listMasaKerjaAwal) {
		this.listMasaKerjaAwal = listMasaKerjaAwal;
	}

	public Listbox getListMasaKerjaAkhir() {
		return listMasaKerjaAkhir;
	}

	public void setListMasaKerjaAkhir(Listbox listMasaKerjaAkhir) {
		this.listMasaKerjaAkhir = listMasaKerjaAkhir;
	}

	public Listbox getListJenisKelamin() {
		return listJenisKelamin;
	}

	public void setListJenisKelamin(Listbox listJenisKelamin) {
		this.listJenisKelamin = listJenisKelamin;
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

	public TpCpnsReportDAO getTpCpnsReportDAO() {
		return tpCpnsReportDAO;
	}

	public void setTpCpnsReportDAO(TpCpnsReportDAO TpCpnsReportDAO) {
		this.tpCpnsReportDAO = TpCpnsReportDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
