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
import org.zkoss.zul.Label;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report03DaftarPnsUrutNipCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listKriteria;
	private Listbox listSubKriteria;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Label lblSubKriteria;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO TbMasterDAO;

	public Report03DaftarPnsUrutNipCtrl() {
		super();
	}
	
	public void onCreate$window_Report(Event event) throws InterruptedException {
		lblSubKriteria.setVisible(false);
		listSubKriteria.setVisible(false);
	}
	
	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sTahun = listSubKriteria.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
         
		Map<String,Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.KRITERIA, sKriteria);
		criterias.put(ConstantsText.TAHUN, sTahun);
		criterias.put(ConstantsText.UNIT_KERJA,sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI,sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftarPnsUrutNipCtrl(criterias);
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/03daftar_pns_urut_nip.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sTahun = listSubKriteria.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		String title = "DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title+=sJenisKelamin;
		}
		title += " BERDASARKAN URUT NIP / ";
		if(!sKriteria.equals("")){
			title += sKriteria+" ";
		}
		if(!sTahun.equals("")){
			title += sTahun;
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
		//"DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL " + (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + "BERDASARKAN URUT NIP / " + (!$P{PARAM_OPSI_URUT}.equals("")?$P{PARAM_OPSI_URUT}+" TAHUN "+$P{PARAM_TAHUN}:"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	
	public Listbox getListKriteria() {
		return listKriteria;
	}

	public void setListKriteria(Listbox listKriteria) {
		this.listKriteria = listKriteria;
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

	public TbMasterDAO getTbMasterDAO() {
		return TbMasterDAO;
	}

	public void setTbMasterDAO(TbMasterDAO TbMasterDAO) {
		this.TbMasterDAO = TbMasterDAO;
	}

	public Listbox getListSubKriteria() {
		return listSubKriteria;
	}

	public void setListSubKriteria(Listbox listSubKriteria) {
		this.listSubKriteria = listSubKriteria;
	}

	public Label getLblSubKriteria() {
		return lblSubKriteria;
	}

	public void setLblSubKriteria(Label lblSubKriteria) {
		this.lblSubKriteria = lblSubKriteria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
