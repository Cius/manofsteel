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
import de.forsthaus.backend.util.PegawaiUtil;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report05DaftarPnsUrutNamaCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listKriteria;
	private Listbox listSubkriteria;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO TbMasterDAO;

	public Report05DaftarPnsUrutNamaCtrl() {
		super();
	}
	
	public void onCreate$window_Report02(Event event) throws InterruptedException {
		
	}
	
	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubkriteria = listSubkriteria.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
         
		Map<String,Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.KRITERIA, sKriteria);
		criterias.put(ConstantsText.SUB_KRITERIA, sSubkriteria);
		criterias.put(ConstantsText.UNIT_KERJA,sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI,sUnitOrganisasi);
//		listTbMaster = TbMasterDAO.getByCriterias(criterias);
		listTbMaster = TbMasterDAO.getAll();
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/05daftar_pns_urut_nama.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubkriteria = listSubkriteria.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		String title = "DAFTAR URUT KEPANGKATAN PEGAWAI NEGERI SIPIL ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title +=sJenisKelamin+" ";
		}
		title +="BERDASARKAN ";
		if(!sKriteria.equals("")){
			title += sKriteria+" ";
		}
		if(!sSubkriteria.equals("")){
			if(sKriteria.equals(ConstantsText.ABJAD)){
				title += "/HURUF "+sSubkriteria;
			}
		}
		title +="<br/>";
		if(!sUnitKerja.equals("")){
			title += "DI LINGKUNGAN : "+sUnitKerja;
		}else if(!sUnitOrganisasi.equals("")){
			title += "DI LINGKUNGAN : "+sUnitOrganisasi;
		}else{
			title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		}
		return title;
		//"DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL BERDASARKAN " + (!$P{PARAM_OPSI_URUT}.equals("")?$P{PARAM_OPSI_URUT}+"/ HURUF "+$P{PARAM_HURUF}:"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	
	public Listbox getListKriteria() {
		return listKriteria;
	}

	public void setListKriteria(Listbox listKriteria) {
		this.listKriteria = listKriteria;
	}

	public Listbox getListTahun() {
		return listSubkriteria;
	}

	public void setListTahun(Listbox listTahun) {
		this.listSubkriteria = listTahun;
	}

	public Listbox getListSubkriteria() {
		return listSubkriteria;
	}

	public void setListSubkriteria(Listbox listSubkriteria) {
		this.listSubkriteria = listSubkriteria;
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

	public TbMasterDAO getTbMasterDAO() {
		return TbMasterDAO;
	}

	public void setTbMasterDAO(TbMasterDAO TbMasterDAO) {
		this.TbMasterDAO = TbMasterDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Listbox getListJenisKelamin() {
		return listJenisKelamin;
	}

	public void setListJenisKelamin(Listbox listJenisKelamin) {
		this.listJenisKelamin = listJenisKelamin;
	}

}
