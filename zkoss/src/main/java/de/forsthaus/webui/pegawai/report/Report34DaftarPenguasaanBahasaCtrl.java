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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.dao.TrBahasaDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report34DaftarPenguasaanBahasaCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJenisBahasa;
	private Listbox listNamaBahasa;
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;
	private TrBahasaDAO trBahasaDAO;

	public Report34DaftarPenguasaanBahasaCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init nama bahasa
		List<String> listBahasa = new ArrayList<String>();
		
		listBahasa = trBahasaDAO.getBahasaByJenis("A");
		String trBahasa = null;
		for (Iterator<String> iterator = listBahasa.iterator(); iterator.hasNext();) {
			trBahasa = (String) iterator.next();
			listNamaBahasa.appendItemApi(trBahasa, trBahasa);
		}
		
		listNamaBahasa.setSelectedIndex(0);
	}
	
	public void onSelect$listJenisBahasa(Event event)  throws InterruptedException {
		String sJenisBahasa = listJenisBahasa.getSelectedItemApi().getValue().toString();
	
		if(!sJenisBahasa.equals("")){
			ListModelList aList = new ListModelList();
			List<String> listBahasa = new ArrayList<String>();
			
			listBahasa = trBahasaDAO.getBahasaByJenis(sJenisBahasa);
			String trBahasa = null;
			for (Iterator<String> iterator = listBahasa.iterator(); iterator.hasNext();) {
				trBahasa = (String) iterator.next();
				aList.add(trBahasa);
			}
			listNamaBahasa.setModel(aList);
		}

		listNamaBahasa.setSelectedIndex(0);
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJenisBahasa = listJenisBahasa.getSelectedItemApi().getValue().toString();
		String sNamaBahasa = listNamaBahasa.getSelectedItemApi().getValue().toString();
		
		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JENIS_BAHASA, sJenisBahasa);
		criterias.put(ConstantsText.NAMA_BAHASA, sNamaBahasa);
		listTbMaster = TbMasterDAO.getDaftar34PenguasaanBahasa(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/34daftar_penguasaan_bahasa.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sNamaBahasa = listNamaBahasa.getSelectedItemApi().getValue().toString();
		
		String title = "DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL YANG AKTIF BERBAHASA "+sNamaBahasa;
		title += "<br/>DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Listbox getListJenisBahasa() {
		return listJenisBahasa;
	}

	public void setListJenisBahasa(Listbox listJenisBahasa) {
		this.listJenisBahasa = listJenisBahasa;
	}

	public Listbox getListNamaBahasa() {
		return listNamaBahasa;
	}

	public void setListNamaBahasa(Listbox listNamaBahasa) {
		this.listNamaBahasa = listNamaBahasa;
	}

	public TrBahasaDAO getTrBahasaDAO() {
		return trBahasaDAO;
	}

	public void setTrBahasaDAO(TrBahasaDAO trBahasaDAO) {
		this.trBahasaDAO = trBahasaDAO;
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
