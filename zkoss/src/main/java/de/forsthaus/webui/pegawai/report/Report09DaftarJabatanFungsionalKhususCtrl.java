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

import de.forsthaus.backend.dao.MasterJabFungDAO;
import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.MasterJabFung;
import de.forsthaus.backend.model.MasterUnitKerja;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.backend.util.PegawaiUtil;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report09DaftarJabatanFungsionalKhususCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listRumpun;
	private Listbox listGolongan;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;	
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;
	private MasterJabFungDAO masterJabFungDAO;

	public Report09DaftarJabatanFungsionalKhususCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init rumpun
		List<MasterJabFung> listJabFung = masterJabFungDAO.findRumpun();
		MasterJabFung masterJabFung = null;
		for (Iterator<MasterJabFung> iterator = listJabFung.iterator(); iterator.hasNext();) {
			masterJabFung = (MasterJabFung) iterator.next();
			listRumpun.appendItemApi(masterJabFung.getNamaJabFung(), masterJabFung.getKodeJabFung());
		}
		listRumpun.appendItemApi(ConstantsText.SELURUH,ConstantsText.SELURUH);
		listRumpun.setSelectedIndex(listRumpun.getItemCount()-1);
		
		//init golongan
		String [] gols =ConstantsText.gols;
		String [] kodeGols =ConstantsText.kodeGols;
		for (int i = 0; i < kodeGols.length; i++) {
			listGolongan.appendItemApi(gols[i], kodeGols[i]);
		}
		listGolongan.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listGolongan.setSelectedIndex(listGolongan.getItemCount()-1);
		
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
		String sRumpun = listRumpun.getSelectedItemApi().getValue().toString();
		String sGolongan = listGolongan.getSelectedItemApi().getValue().toString();
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
		criterias.put(ConstantsText.RUMPUN, sRumpun);
		criterias.put(ConstantsText.GOLONGAN, sGolongan);
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.UNIT_KERJA, sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI, sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar09JabatanFungsionalKhusus(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/09daftar_jabatan_fungsional_khusus.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sRumpun = listRumpun.getSelectedItemApi().getValue().toString();
		String sGolongan = listGolongan.getSelectedItemApi().getValue().toString();
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
		
		if(!sRumpun.equals(ConstantsText.SELURUH)){
			title += "YANG MENDUDUKI JABATAN " + sRumpun;
		}
		
		if(!sGolongan.equals(ConstantsText.SELURUH)){
			title += "DENGAN GOL. RUANG "+PegawaiUtil.convertGolongan(sGolongan);
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
		//"DAFTAR NOMINATIF PNS "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") +"YANG MENDUDUKI JABATAN " + (!$P{PARAM_RUMPUN}.equals("")?$P{PARAM_RUMPUN}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")                                                                     
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

	public Listbox getListRumpun() {
		return listRumpun;
	}

	public void setListRumpun(Listbox listRumpun) {
		this.listRumpun = listRumpun;
	}

	public Listbox getListGolongan() {
		return listGolongan;
	}

	public void setListGolongan(Listbox listGolongan) {
		this.listGolongan = listGolongan;
	}

	public MasterJabFungDAO getMasterJabFungDAO() {
		return masterJabFungDAO;
	}

	public void setMasterJabFungDAO(MasterJabFungDAO masterJabFungDAO) {
		this.masterJabFungDAO = masterJabFungDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
