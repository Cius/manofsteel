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

import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TbMasterDAO;
import de.forsthaus.backend.model.TbMaster;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class Report31DaftarAlamatKantorRumahCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listJabatan;
	private Listbox listEselon;
	private Button btnCari;
	private Jasperreport report;

	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report31DaftarAlamatKantorRumahCtrl() {
		super();
	}

	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init kelompok
		String [] jabatan ={"PNS STRUKTURAL", "PNS FUNGSIONAL KHUSUS","PNS FUNGSIONAL UMUM"};
		String [] kodeJabatan ={"1","2","3"};
		for (int i = 0; i < jabatan.length; i++) {
			listJabatan.appendItemApi(jabatan[i], kodeJabatan[i]);
		}
		listJabatan.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listJabatan.setSelectedIndex(listJabatan.getItemCount()-1);
		
		//Init eselon
		String [] eselon ={"II", "III","IV","V"};
		String [] kodeEselon={"2","3","4","5"};
		for (int i = 0; i < eselon.length; i++) {
			listEselon.appendItemApi(eselon[i], kodeEselon[i]);
		}
		listEselon.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listEselon.setSelectedIndex(listEselon.getItemCount()-1);
	}

	public void onClick$btnCari(Event event) throws InterruptedException {
		List<TbMaster> listTbMaster = new ArrayList<TbMaster>();
		String sJabatan = listJabatan.getSelectedItemApi().getValue().toString();
		String sEselon = listEselon.getSelectedItemApi().getValue().toString();

		Map<String, Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.JABATAN, sJabatan);
		criterias.put(ConstantsText.ESELON, sEselon);
		listTbMaster = TbMasterDAO.getDaftar31AlamatKantorRumah(criterias);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);

		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/31daftar_alamat_kantor_rumah.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}

	private String composeTitle() {
		String sJabatan = listJabatan.getSelectedItemApi().getValue().toString();
		String sEselon = listEselon.getSelectedItemApi().getValue().toString();
		String [] kelJabatan = {"PEJABAT STRUKTURAL","PEJABAT FUNGSIONAL KHUSUS","PEJABAT FUNGSIONAL UMUM"};
		String [] eselon = {"II","III","IV","V"};
		
		String title = "DAFTAR ALAMAT KANTOR DAN RUMAH ";
		if(!sJabatan.equals(ConstantsText.SELURUH)){
			if(sJabatan.equals("1")){
				title +=kelJabatan[0]+" ";
			}else if(sJabatan.equals("2")){
				title +=kelJabatan[1]+" ";
			}else if(sJabatan.equals("3")){
				title +=kelJabatan[2]+" ";
			}
		}
		
		if(!sEselon.equals(ConstantsText.SELURUH)){
			sEselon = eselon[Integer.parseInt(sEselon)-2];
			title += "ESELON "+sEselon;
		}
		
		title += "<br/>";
		title += "DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU";
		return title;
		//"DAFTAR NOMINATIF PNS FUNGSIONAL UMUM "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") + (!$P{PARAM_KELOMPOK}.equals("")?"SEBAGAI "+$P{PARAM_KELOMPOK}+" ":"") + (!$P{PARAM_GOLONGAN}.equals("")?"DENGAN GOL. RUANG "+$P{PARAM_GOLONGAN}:"")+ "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

	public Listbox getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(Listbox listJabatan) {
		this.listJabatan = listJabatan;
	}

	public Listbox getListEselon() {
		return listEselon;
	}

	public void setListEselon(Listbox listEselon) {
		this.listEselon = listEselon;
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
