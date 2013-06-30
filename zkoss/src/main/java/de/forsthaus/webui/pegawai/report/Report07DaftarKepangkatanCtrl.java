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

public class Report07DaftarKepangkatanCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -6484788269534812786L;
	private Listbox listGolAwal;
	private Listbox listGolAkhir;
	private Listbox listJenisKelamin;
	private Listbox listUnitOrganisasi;
	private Listbox listUnitKerja;
	private Label lblSd;
	private Button btnCari;
	private Jasperreport report;
	
	private TbMasterDAO TbMasterDAO;
	private MasterUnitKerjaDAO masterUnitKerjaDAO;

	public Report07DaftarKepangkatanCtrl() {
		super();
	}
	
	public void onCreate$window_Report(Event event) throws InterruptedException {
		//init list golongan
		String [] gols ={"I/a","I/b","I/c","I/d","I/e","II/a","II/b","II/c","II/d","II/e","III/a","III/b","III/c","III/d","III/e","IV/a","IV/b","IV/c","IV/d","IV/e"};
		String [] kodeGols ={"11","12","13","14","15","21","22","23","24","25","31","32","33","34","35","41","42","43","44","45"};
		for (int i = 0; i < kodeGols.length; i++) {
			listGolAwal.appendItemApi(gols[i], kodeGols[i]);
			if(i!=0){
				listGolAkhir.appendItemApi(gols[i], kodeGols[i]);
			}
		}
		listGolAwal.appendItemApi(ConstantsText.SELURUH, ConstantsText.SELURUH);
		listGolAwal.setSelectedIndex(listGolAwal.getItemCount()-1);
		listGolAkhir.setSelectedIndex(0);
		listGolAkhir.setVisible(false);
		lblSd.setVisible(false);
		
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
		String sGolAwal = listGolAwal.getSelectedItemApi().getValue().toString();
		String sGolAkhir = "";
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sGolAwal.equals(ConstantsText.SELURUH)){
			sGolAkhir = listGolAkhir.getSelectedItemApi().getValue().toString();
			if(!checkGolAkhir()){
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
         
		Map<String,Object> criterias = new HashMap<String, Object>();
		criterias.put(ConstantsText.GOLONGAN_AWAL, sGolAwal);
		criterias.put(ConstantsText.GOLONGAN_AKHIR, sGolAkhir);
		criterias.put(ConstantsText.JENIS_KELAMIN, sJenisKelamin);
		criterias.put(ConstantsText.UNIT_KERJA,sUnitKerja);
		criterias.put(ConstantsText.UNIT_ORGANISASI,sUnitOrganisasi);
		listTbMaster = TbMasterDAO.getDaftar07Kepangkatan(criterias);
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("PARAM_TITLE", composeTitle());
		parameters.put("PARAM_PATH_LOGO", ConstantsText.PARAM_PATH_LOGO);
		
		JRBeanCollectionDataSource jbs = new JRBeanCollectionDataSource(listTbMaster);
		report.setDatasource(jbs);
		report.setSrc("/reports/07daftar_kepangkatan.jasper");
		report.setParameters(parameters);
		report.setType("pdf");
	}
	
	private String composeTitle(){
		String sGolAwal = listGolAwal.getSelectedItemApi().getValue().toString();
		String sGolAkhir = listGolAkhir.getSelectedItemApi().getValue().toString();
		String sJenisKelamin = listJenisKelamin.getSelectedItemApi().getValue().toString();
		String sUnitOrganisasi = listUnitOrganisasi.getSelectedItemApi().getValue().toString();
		String sUnitKerja = listUnitKerja.getSelectedItemApi().getValue().toString();
		
		if(!sUnitKerja.equals("")){
			String [] arrUnitKerja = sUnitKerja.split(ConstantsText.SEPARATOR);
			sUnitKerja = arrUnitKerja[1];
		}else if(!sUnitOrganisasi.equals("")){
			String [] arrUnitOrganisasi = sUnitOrganisasi.split(ConstantsText.SEPARATOR);
			sUnitOrganisasi = arrUnitOrganisasi[1];
		}
		
		String title = "DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL ";
		if(!sJenisKelamin.equals(ConstantsText.JENIS_KELAMIN_SELURUH)){
			title +=sJenisKelamin+" ";
		}
		
		if(!sGolAwal.equals(ConstantsText.SELURUH)){
			int iGolAwal = Integer.parseInt(listGolAwal.getSelectedItemApi().getValue().toString());
    		int iGolAkhir = Integer.parseInt(listGolAkhir.getSelectedItemApi().getValue().toString());
    		title +="BERDASARKAN GOLONGAN ";
    		title += PegawaiUtil.convertGolongan(sGolAwal)+" ";
    		if(iGolAwal<iGolAkhir){
    			title += "- "+PegawaiUtil.convertGolongan(sGolAkhir);
    		}
		}
		if(!sGolAkhir.equals("")){
			
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
		//"DAFTAR NOMINATIF PEGAWAI NEGERI SIPIL "+ (!$P{PARAM_GENDER}.equals("")?$P{PARAM_GENDER}+" ":"") +"BERDASARKAN GOLONGAN " + (!$P{PARAM_GOLONGAN_AWAL}.equals("")?$P{PARAM_GOLONGAN_AWAL}+" - "+$P{PARAM_GOLONGAN_AKHIR}:"") + "<br/>" + (!$P{PARAM_LINGKUNGAN}.equals("")?"DI LINGKUNGAN : "+$P{PARAM_LINGKUNGAN}:"DI LINGKUNGAN PEMERINTAH KABUPATEN SANGGAU")
	}

    public boolean checkGolAkhir(){
    	try{
    		int iGolAwal = Integer.parseInt(listGolAwal.getSelectedItemApi().getValue().toString());
    		int iGolAkhir = Integer.parseInt(listGolAkhir.getSelectedItemApi().getValue().toString());
    		if(iGolAwal>iGolAkhir){
    			alert("Golongan Awal harus lebih kecil daripada Golongan Akhir");
    			listGolAwal.setSelectedIndex(0);
    			listGolAkhir.setSelectedIndex(0);
    			return false;
    		}
    	}catch(Exception e){
    		e.printStackTrace();	
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

	public Listbox getListJenisKelamin() {
		return listJenisKelamin;
	}

	public Listbox getListGolAwal() {
		return listGolAwal;
	}

	public void setListGolAwal(Listbox listGolAwal) {
		this.listGolAwal = listGolAwal;
	}

	public Listbox getListGolAkhir() {
		return listGolAkhir;
	}

	public void setListGolAkhir(Listbox listGolAkhir) {
		this.listGolAkhir = listGolAkhir;
	}

	public MasterUnitKerjaDAO getMasterUnitKerjaDAO() {
		return masterUnitKerjaDAO;
	}

	public void setMasterUnitKerjaDAO(MasterUnitKerjaDAO masterUnitKerjaDAO) {
		this.masterUnitKerjaDAO = masterUnitKerjaDAO;
	}

	public void setListJenisKelamin(Listbox listJenisKelamin) {
		this.listJenisKelamin = listJenisKelamin;
	}

	public Label getLblSd() {
		return lblSd;
	}

	public void setLblSd(Label lblSd) {
		this.lblSd = lblSd;
	}

}
