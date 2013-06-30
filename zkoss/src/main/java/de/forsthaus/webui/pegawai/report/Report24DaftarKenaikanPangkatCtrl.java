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

public class Report24DaftarKenaikanPangkatCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJenis;
	private Listbox listPeriode;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;	
	private Button btnCari;
	private Jasperreport report;
	
	String [] arrJenisKepa = {"REGULER"};

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report24DaftarKenaikanPangkatCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init jenis
		for (int i = 0; i < arrJenisKepa.length; i++) {
			listJenis.appendItemApi(arrJenisKepa[i], String.valueOf(i));
		}
		listJenis.setSelectedIndex(0);
		
		//init periode
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int month;
		int year;
		for (int i = 0; i < 12; i++) {
			month = cal.get(Calendar.MONTH);
			year = cal.get(Calendar.YEAR);
			listPeriode.appendItemApi(ConstantsText.namaBulan[month] + " " + year, String.valueOf(month)+ConstantsText.SEPARATOR+year);
			cal.add(Calendar.MONTH, 1);
		}
		listPeriode.setSelectedIndex(0);

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
		String sJenis = listJenis.getSelectedItemApi().getValue().toString();
		String sPeriode = listPeriode.getSelectedItemApi().getValue().toString();
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
		criterias.put(ConstantsText.JENIS, sJenis);
		criterias.put(ConstantsText.PERIODE, sPeriode);
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar24KenaikanPangkat(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/24daftar_kenaikan_pangkat.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJenis = listJenis.getSelectedItemApi().getValue().toString();
		String sPeriode = listPeriode.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		int iJenis = listJenis.getSelectedIndex();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}

		String title = "DAFTAR NOMINATIF PNS YANG DAPAT DIPERTIMBANGKAN KENAIKAN PANGKAT ";
		title+=arrJenisKepa[iJenis];
		title +="<br/>";
		
		String [] date = sPeriode.split(ConstantsText.SEPARATOR); 
		title += "PERIODE "+date[0] + " "+ date[1];
		
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

	public Listbox getListJenis() {
		return listJenis;
	}

	public void setListJenis(Listbox listJenis) {
		this.listJenis = listJenis;
	}

	public Listbox getListPeriode() {
		return listPeriode;
	}

	public void setListPeriode(Listbox listPeriode) {
		this.listPeriode = listPeriode;
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
