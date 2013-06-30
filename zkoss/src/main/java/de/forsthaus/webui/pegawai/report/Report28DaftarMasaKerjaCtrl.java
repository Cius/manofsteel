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

public class Report28DaftarMasaKerjaCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJenisMasaKerja;
	private Listbox listEselon;
	private Listbox listMasaKerja;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;	
	private Button btnCari;
	private Jasperreport report;
	
	String [] arrJenisMasaKerja = {"JAB. STRUKTURAL"};
	String [] arrEselon = {"II.A","II.B","III.A","III.B","IV.A","IV.B","V.A","V.B"};
	String [] arrKodeEselon= {"21","22","31","32","41","42","51","52"};

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report28DaftarMasaKerjaCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init golongan
		System.out.println(arrJenisMasaKerja.length+" "+arrEselon.length+" "+arrKodeEselon.length);
		for (int i = 0; i < arrJenisMasaKerja.length; i++) {
			listJenisMasaKerja.appendItemApi(arrJenisMasaKerja[i], String.valueOf(i));
		}
		listJenisMasaKerja.setSelectedIndex(0);
		
		//init eselon
		for (int i = 0; i < arrEselon.length; i++) {
			listEselon.appendItemApi(arrEselon[i], arrKodeEselon[i]);
		}
		listEselon.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listEselon.setSelectedIndex(0);
		
		//init masa kerja
		for (int i = 0; i < arrEselon.length; i++) {
			listMasaKerja.appendItemApi(String.valueOf(i), String.valueOf(i));
		}
		listMasaKerja.setSelectedIndex(0);
		
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
		String sJenisMasaKerja = listJenisMasaKerja.getSelectedItemApi().getValue().toString();
		String sMasaKerja = listMasaKerja.getSelectedItemApi().getValue().toString();
		String sEselon = listEselon.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
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
		criterias.put(ConstantsText.MASA_KERJA_JENIS, sJenisMasaKerja);
		criterias.put(ConstantsText.ESELON, sEselon);
		criterias.put(ConstantsText.MASA_KERJA_JUMLAH, sMasaKerja);
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar28MasaKerja(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/28daftar_masa_kerja.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJenisMasaKerja = listJenisMasaKerja.getSelectedItemApi().getValue().toString();
		String sEselon = listEselon.getSelectedItemApi().getValue().toString();
		String sMasaKerja = listMasaKerja.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		String [] titleJenisMasaKerja = {"PEJABAT STRUKTURAL"};
		int iJenisMasaKerja = listJenisMasaKerja.getSelectedIndex();
		int iEselon = listEselon.getSelectedIndex();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}

		String title = "DAFTAR NOMINATIF ";
		title+=titleJenisMasaKerja[iJenisMasaKerja]+" ";
		
		if(!sEselon.equals(ConstantsText.SELURUH)){
			title += arrEselon[iEselon] + " ";
		}
		
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title +=sJenisKelamin+" ";
		}
		
		title+="BERDASARKAN MASA KERJA JABATAN";
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

	public Listbox getListJenisMasaKerja() {
		return listJenisMasaKerja;
	}

	public void setListJenisMasaKerja(Listbox listJenisMasaKerja) {
		this.listJenisMasaKerja = listJenisMasaKerja;
	}

	public Listbox getListEselon() {
		return listEselon;
	}

	public void setListEselon(Listbox listEselon) {
		this.listEselon = listEselon;
	}

	public Listbox getListMasaKerja() {
		return listMasaKerja;
	}

	public void setListMasaKerja(Listbox listMasaKerja) {
		this.listMasaKerja = listMasaKerja;
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
