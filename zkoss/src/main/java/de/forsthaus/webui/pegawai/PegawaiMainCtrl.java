package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class PegawaiMainCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572901298975748252L;
	private static final Logger logger = Logger.getLogger(PegawaiMainCtrl.class);
	
	protected Window windowPegawaiMain;
	
	protected Tabbox tabbox_PegawaiMain;
	protected Tab tab_IdentitasPegawai;
	protected Tab tab_RiwayatPengangkatan;
	protected Tab tab_RiwayatPendidikan;
	protected Tab tab_RiwayatKeluarga;
	
	protected Textbox txt_Nip;
	
	protected Button button_Search;
	protected Button button_New;
	protected Button button_Save;
	protected Button button_Delete;
	protected Button button_Cancel;
	
	private PegawaiListCtrl pegawaiListCtrl;
	private PegawaiDetailCtrl pegawaiDetailCtrl;
	
	private TpCpnsDAO tpCpnsDAO;
	
	public PegawaiMainCtrl() {
		super();
	}
}
