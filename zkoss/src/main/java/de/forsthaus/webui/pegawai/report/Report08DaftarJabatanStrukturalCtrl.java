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
import org.zkoss.zul.Label;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.MasterUnitKerja;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report08DaftarJabatanStrukturalCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listEselonAwal;
	private Listbox listEselonAkhir;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Label lblSd;
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report08DaftarJabatanStrukturalCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init eselon
		String [] eselons = {"II.A","II.B","III.A","III.B","IV.A","IV.B","V.A"};
		String [] kodeEselon = {"21","22","31","32","41","42","51"};
		for (int i = 0; i < eselons.length; i++) {
			listEselonAwal.appendItemApi(eselons[i], kodeEselon[i]);
			if(i!=0){
				listEselonAkhir.appendItemApi(eselons[i], kodeEselon[i]);
			}
		}
		listEselonAwal.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listEselonAwal.setSelectedIndex(listEselonAwal.getItemCount()-1);
		listEselonAkhir.setSelectedIndex(0);
		lblSd.setVisible(false);
		listEselonAkhir.setVisible(false);
		
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
		String sEselonAwal = listEselonAwal.getSelectedItemApi().getValue().toString();
		String sEselonAkhir = "";
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sEselonAwal.equals(ConstantsText.SELURUH)){
			sEselonAkhir = listEselonAkhir.getSelectedItemApi().getValue().toString();
			if(!checkEselonAkhir()){
				return;
			}
		}
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[0];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[0];
		}

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.ESELON_AWAL, sEselonAwal);
		criterias.put(ConstantsText.ESELON_AKHIR, sEselonAkhir);
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftarJabatanStruktural(criterias);
		System.out.println("count:"+listTbMaster.size());

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

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
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}

		String title = "DAFTAR NOMINATIF PNS ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title +=sJenisKelamin+" ";
		}
		title += "YANG MENDUDUKI JABATAN STRUKTURAL ";
		
		if(!sEselonAwal.equals(ConstantsText.SELURUH)){
			title += "ESELON "+sEselonAwal;
			if(!sEselonAwal.equals(sEselonAkhir)){
				title += " - "+sEselonAkhir;
			}
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
	
	boolean checkEselonAkhir(){
		int iEselonAwal = Integer.parseInt(listEselonAwal.getSelectedItemApi().getValue().toString());
		int iEselonAkhir = Integer.parseInt(listEselonAkhir.getSelectedItemApi().getValue().toString());
		if(iEselonAwal>iEselonAkhir){
			alert("Eselon Awal harus lebih kecil dari Eselon Akhir");
			listEselonAwal.setSelectedIndex(0);
			listEselonAkhir.setSelectedIndex(0);
			return false;
		}
		return true;
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

	public MasterUnitKerjaDAO getMasterUnitKerjaDAO() {
		return masterUnitKerjaDAO;
	}

	public void setMasterUnitKerjaDAO(MasterUnitKerjaDAO masterUnitKerjaDAO) {
		this.masterUnitKerjaDAO = masterUnitKerjaDAO;
	}

	public Label getLblSd() {
		return lblSd;
	}

	public void setLblSd(Label lblSd) {
		this.lblSd = lblSd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
