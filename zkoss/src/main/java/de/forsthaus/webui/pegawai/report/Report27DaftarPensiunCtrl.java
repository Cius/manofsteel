package de.forsthaus.webui.pegawai.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class Report27DaftarPensiunCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJenisBUP;
	private Listbox listTahunBUP;
	private Listbox listGolongan;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;	
	private Button btnCari;
	private Jasperreport report;

	String [] arrJenisBUP = {ConstantsText.SELURUH};
	String [] arrGols = ConstantsText.gols;
	String [] arrKodeGols = ConstantsText.kodeGols;
	
	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report27DaftarPensiunCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init jenis BUP
		for (int i = 0; i < arrJenisBUP.length; i++) {
			listJenisBUP.appendItemApi(arrJenisBUP[i],String.valueOf(i));
		}
		listJenisBUP.setSelectedIndex(0);
		
		//init tahun BUP
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		for (int i = year-5; i < year+5; i++) {
			listTahunBUP.appendItemApi(String.valueOf(i),String.valueOf(i));
		}
		listTahunBUP.setSelectedIndex(0);
				
		//init golongan
		for (int i = 0; i < arrGols.length; i++) {
			listGolongan.appendItemApi(arrGols[i],arrKodeGols[i]);
		}
		listGolongan.setSelectedIndex(0);
		
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
		String sJensiBUP = listJenisBUP.getSelectedItemApi().getValue().toString();
		String sTahunBUP = listTahunBUP.getSelectedItemApi().getValue().toString();
		String sGolongan = listGolongan.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[0];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[0];
		}

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JENIS, sJensiBUP);
		criterias.put(ConstantsText.TAHUN, sTahunBUP);
		criterias.put(ConstantsText.GOLONGAN, sGolongan);
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar27Pensiun(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/27daftar_pensiun.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJenisBUP = listJenisBUP.getSelectedItemApi().getValue().toString();
		String sTahunBUP = listTahunBUP.getSelectedItemApi().getValue().toString();
		String sGolongan = listGolongan.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		int iGolongan = listGolongan.getSelectedIndex();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}

		String title = "DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL ";
		if(!sGolongan.equals(ConstantsText.SELURUH)){
			title += "GOL. RUANG "+arrGols[iGolongan]+" ";
		}
		title += "YANG AKAN MENCAPAI BATAS USIA PENSIUN ";
		title += "TAHUN "+sTahunBUP;
		title += "<br/>";
		if (!sUnitKerja.equals("")) {
			title += "DI LINGKUNGAN : " + sUnitKerja;
		} else if (!sUnitOrganisasi.equals("")) {
			title += "DI LINGKUNGAN : " + sUnitOrganisasi;
		} else {
			title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		}
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
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

	public Listbox getListKelompok() {
		return listJenisBUP;
	}

	public void setListKelompok(Listbox listKelompok) {
		this.listJenisBUP = listKelompok;
	}

	public Listbox getListGolongan() {
		return listGolongan;
	}

	public void setListGolongan(Listbox listGolongan) {
		this.listGolongan = listGolongan;
	}

	public Listbox getListJenisBUP() {
		return listJenisBUP;
	}

	public void setListJenisBUP(Listbox listJenisBUP) {
		this.listJenisBUP = listJenisBUP;
	}

	public Listbox getListTahunBUP() {
		return listTahunBUP;
	}

	public void setListTahunBUP(Listbox listTahunBUP) {
		this.listTahunBUP = listTahunBUP;
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
