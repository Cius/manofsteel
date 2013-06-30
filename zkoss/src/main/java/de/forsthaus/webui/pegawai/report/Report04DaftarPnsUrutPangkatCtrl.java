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
import de.forsthaus.backend.util.PegawaiUtil;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report04DaftarPnsUrutPangkatCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listKriteria;
	private Listbox listSubKriteria;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Label lblSubKriteria;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report04DaftarPnsUrutPangkatCtrl() {
		super();
	}
	
	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init kriteria
		lblSubKriteria.setVisible(false);
		listSubKriteria.setVisible(false);
		
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
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubKriteria = "";
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sKriteria.equals(ConstantsText.SELURUH)){
			sSubKriteria = listSubKriteria.getSelectedItemApi().getValue().toString();
			if(sKriteria.equals(ConstantsText.GOLONGAN)){
				sSubKriteria = PegawaiUtil.getRomanToDec(sSubKriteria);
			}else if(sKriteria.equals(ConstantsText.JABATAN)){
				sSubKriteria = PegawaiUtil.getKodeJabatan(sSubKriteria);
			}
		}
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[0];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[0];
		}
         
		Map<String,Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.KRITERIA, sKriteria);
		criterias.put(ConstantsText.SUB_KRITERIA, sSubKriteria);
		criterias.put(ConstantsText.UNIT_KERJA,sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI,sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar04PnsUrutPangkat(criterias);
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/04daftar_pns_urut_kepangkatan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubkriteria = "";
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		String title = "DAFTAR URUT KEPANGKATAN PEGAWAI NEGERI SIPIL BERDASARKAN ";
		if(!sKriteria.equals("")){
			title += sKriteria+" ";
		}
		if(!sKriteria.equals(ConstantsText.SELURUH)){
			sSubkriteria = listSubKriteria.getSelectedItemApi().getValue().toString();
			title += sSubkriteria;
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
		//"DAFTAR URUT KEPANGKATAN PEGAWAI NEGERI SIPIL BERDASARKAN " + (!$P{PARAM_OPSI_URUT}.equals("")?$P{PARAM_OPSI_URUT}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?$P{PARAM_GOLONGAN}:"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"UNIT ORGANISASI : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	
	public Listbox getListKriteria() {
		return listKriteria;
	}

	public void setListKriteria(Listbox listKriteria) {
		this.listKriteria = listKriteria;
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
