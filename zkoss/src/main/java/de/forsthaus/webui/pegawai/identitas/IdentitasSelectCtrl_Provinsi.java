package de.forsthaus.webui.pegawai.identitas;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.webui.pegawai.PegawaiDetailCtrl_DataPokok_Identitas;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class IdentitasSelectCtrl_Provinsi extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5567108179155333971L;
	private static final Logger logger = Logger.getLogger(IdentitasSelectCtrl_Provinsi.class);
	
	protected Window propinsiSelectWindow;
	private Radiogroup radio_propinsi;
	private Listbox listbox_propinsi;
	
	private WilayahDAO wilayahDAO;
	private PegawaiDetailCtrl_DataPokok_Identitas identitasCtrl;
	
	private AnnotateDataBinder binder;
	private BindingListModelList wilayahList;
	
	public IdentitasSelectCtrl_Provinsi() {
		super();
	}
	
	public void onCreate$propinsiSelectWindow(Event event) throws Exception {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		
		final BindingListModelList lml = (BindingListModelList) getListbox_propinsi().getModel();
		setWilayahList(lml);
		
		binder.loadAll();
	}
	
	public WilayahDAO getWilayahDAO() {
		return wilayahDAO;
	}
	public void setWilayahDAO(WilayahDAO wilayahDAO) {
		this.wilayahDAO = wilayahDAO;
	}
	public PegawaiDetailCtrl_DataPokok_Identitas getIdentitasCtrl() {
		return identitasCtrl;
	}
	public void setIdentitasCtrl(PegawaiDetailCtrl_DataPokok_Identitas identitasCtrl) {
		this.identitasCtrl = identitasCtrl;
	}
	public TpCpns getSelected() {
		return getIdentitasCtrl().getSelected();
	}
	public void setSelected(TpCpns selected) {
		this.getIdentitasCtrl().setSelected(selected);
	}

	public BindingListModelList getWilayahList() {
		return wilayahList;
	}

	public void setWilayahList(BindingListModelList wilayahList) {
		this.wilayahList = wilayahList;
	}

	public Listbox getListbox_propinsi() {
		return listbox_propinsi;
	}

	public void setListbox_propinsi(Listbox listbox_propinsi) {
		this.listbox_propinsi = listbox_propinsi;
	}
	
}
