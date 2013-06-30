package de.forsthaus.webui.pegawai.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

public class Report02DaftarPnsCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listMasaKerjaAwal;
	private Listbox listMasaKerjaAkhir;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO tbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report02DaftarPnsCtrl() {
		super();
	}
	
	public void onCreate$window_Report(Event event) throws InterruptedException {
		//Init Masa Kerja Awal & Akhir
		for(int i=2;i<=43;i++){
			listMasaKerjaAwal.appendItemApi(i+" TAHUN", String.valueOf(i));
			listMasaKerjaAkhir.appendItemApi(i+" TAHUN", String.valueOf(i));
		}
		listMasaKerjaAwal.appendItemApi(ConstantsText.MASA_KERJA_SELURUH, ConstantsText.MASA_KERJA_SELURUH);
		listMasaKerjaAwal.setSelectedIndex(listMasaKerjaAwal.getItemCount()-1);
		listMasaKerjaAkhir.setDisabled(true);
		listMasaKerjaAkhir.setSelectedIndex(0);
		
		//Init Unit Organisasi & Unit Kerja
		List<MasterUnitKerja> listObjetUnitKerja = masterUnitKerjaDAO.findbyUnitKerja(ConstantsText.KODE_UNIT_KERJA);
		List<MasterUnitKerja> listObjetUnitOrganisasi = masterUnitKerjaDAO.findbyUnitKerja(ConstantsText.KODE_UNIT_ORGANISASI);
		MasterUnitKerja masterUnitKerja = null;
		listUnitKerja.appendItemApi(ConstantsText.PILIH_SALAH_SATU, "");
		listUnitKerja.setSelectedIndex(0);
		listUnitOrganisasi.appendItemApi(ConstantsText.PILIH_SALAH_SATU, "");
		listUnitOrganisasi.setSelectedIndex(0);
		if(listObjetUnitKerja!=null){
			for (Iterator<MasterUnitKerja> iterator = listObjetUnitKerja.iterator(); iterator.hasNext();) {
				masterUnitKerja = (MasterUnitKerja) iterator.next();
				listUnitKerja.appendItemApi(masterUnitKerja.getNunker(), masterUnitKerja.getKunker()+ConstantsText.SEPARATOR+masterUnitKerja.getNunker());
			}
		}
		if(listObjetUnitOrganisasi!=null){
			for (Iterator<MasterUnitKerja> iterator = listObjetUnitOrganisasi.iterator(); iterator.hasNext();) {
				masterUnitKerja = (MasterUnitKerja) iterator.next();
				listUnitOrganisasi.appendItemApi(masterUnitKerja.getNunker(), masterUnitKerja.getKunker()+ConstantsText.SEPARATOR+masterUnitKerja.getNunker());
			}
		}
	}	 
	
	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAwal = listMasaKerjaAwal.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAkhir = listMasaKerjaAkhir.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[0];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[0];
		}
		
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
		listTbMaster = tbMasterDAO.getDaftar02Pns(criterias);
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/02daftar_pns.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAwal = listMasaKerjaAwal.getSelectedItemApi().getValue().toString();
		String sMasaKerjaAkhir = listMasaKerjaAkhir.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}
		
		String title = "DAFTAR NOMINATIF PEGAWAI ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title+=sJenisKelamin;
		}
		title += " YANG BERSTATUS SEBAGAI PNS ";
		if(!sMasaKerjaAwal.equals(ConstantsText.MASA_KERJA_SELURUH)){
			title += "DENGAN MASA KERJA "+sMasaKerjaAwal+" TAHUN";
			if(Integer.parseInt(sMasaKerjaAwal)<Integer.parseInt(sMasaKerjaAkhir)){
				title += " - "+sMasaKerjaAkhir+" TAHUN";
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
		//"DAFTAR NOMINATIF PEGAWAI " + (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}:"") + " YANG BERSTATUS SEBAGAI PNS " + (!$P{PARAM_KERJA_MIN}.equals("")?"DENGAN MASA KERJA "+$P{PARAM_KERJA_MIN}+" TAHUN - "+$P{PARAM_KERJA_MAX}+" TAHUN":"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
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

	public TbMasterDAO getTbMasterDAO() {
		return tbMasterDAO;
	}

	public void setTbMasterDAO(TbMasterDAO TbMasterDAO) {
		this.tbMasterDAO = TbMasterDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MasterUnitKerjaDAO getMasterUnitKerjaDAO() {
		return masterUnitKerjaDAO;
	}

	public void setMasterUnitKerjaDAO(MasterUnitKerjaDAO masterUnitKerjaDAO) {
		this.masterUnitKerjaDAO = masterUnitKerjaDAO;
	}

}
