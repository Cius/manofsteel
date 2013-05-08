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

public class Report04DaftarPnsUrutPangkatCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listKriteria;
	private Listbox listSubkriteria;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO TbMasterDAO;

	public Report04DaftarPnsUrutPangkatCtrl() {
		super();
	}
	
	public void onCreate$window_Report02(Event event) throws InterruptedException {
		
	}
	
	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubkriteria = listSubkriteria.getSelectedItemApi().getValue().toString();
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
		report.setSrc("/reports/04daftar_pns_urut_kepangkatan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sKriteria = listKriteria.getSelectedItemApi().getValue().toString();
		String sSubkriteria = listSubkriteria.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
	
		String title = "DAFTAR URUT KEPANGKATAN PEGAWAI NEGERI SIPIL BERDASARKAN ";
		if(!sKriteria.equals("")){
			title += sKriteria+" ";
		}
		if(!sSubkriteria.equals("")){
			if(sKriteria.equals(ConstantsText.GOLONGAN)){
				sSubkriteria = PegawaiUtil.getRoman(Integer.parseInt(sSubkriteria));
			}
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

}
