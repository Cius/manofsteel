package de.forsthaus.webui.pegawai.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.api.Listbox;

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report35DaftarUcapanPurnaBhaktiCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listBulan;
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report35DaftarUcapanPurnaBhaktiCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init bulan
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		
		for (int i = 0; i < 12; i++) {
			listBulan.appendItemApi(ConstantsText.namaBulan[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+ConstantsText.SEPARATOR+cal.get(Calendar.YEAR));
			cal.add(Calendar.MONTH, 1);
		}
		listBulan.setSelectedIndex(0);
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		try{
			List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
			String sBulan = listBulan.getSelectedItemApi().getValue().toString();
			
			String [] tanggal = sBulan.split(ConstantsText.SEPARATOR);
			int bulan =Integer.parseInt(tanggal[0]);
	
			Map<String, Object> criterias = new HashMap<String, Object>();
			criterias.put(ConstantsText.BULAN, bulan+1);
			listTbMaster = TbMasterDAO.getDaftar35PurnaBhakti(criterias);
	
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("PARAM_TITLE", composeTitle());
			parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);
	
			JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
			report.setDatasource(jbs);
			report.setSrc("/reports/35daftar_purna_bhakti.jasper");
			report.setParameters(parameters);
			report.setType("pdf");
		}catch(Exception e){
			alert("Terjadi kesalahan dalam pencarian data");
		}
	}

	private String composeTitle() {
		String sBulan = listBulan.getSelectedItemApi().getValue().toString();
		
		String title = "DAFTAR NOMINATIF PNS YANG PURNA BHAKTI PADA BULAN ";
		
		String [] tanggal = sBulan.split(ConstantsText.SEPARATOR);
		String bulan = ConstantsText.namaBulan[Integer.parseInt(tanggal[0])];
		String tahun = tanggal[1];
		title += bulan + " " + tahun + "<br/>";
		title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}
	
	public Listbox getListBulan() {
		return listBulan;
	}

	public void setListBulan(Listbox listBulan) {
		this.listBulan = listBulan;
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
