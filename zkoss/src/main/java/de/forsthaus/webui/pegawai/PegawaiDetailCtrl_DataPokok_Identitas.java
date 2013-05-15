package de.forsthaus.webui.pegawai;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.dao.TpIdentitasDAO;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TpIdentitas;
import de.forsthaus.backend.model.Wilayah;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.backend.util.HibernateSearchObject;
import de.forsthaus.backend.util.ZksampleBeanUtils;
import de.forsthaus.webui.pegawai.model.GabunganListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.WilayahListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.util.pagging.PagedListWrapper;

public class PegawaiDetailCtrl_DataPokok_Identitas extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005265307103199688L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_DataPokok_Identitas.class);
	
	protected Window windowPegawaiDetail_DataPokok_Identitas;
	protected Borderlayout borderLayout_PegawaiDataPokok_Identitas;
	protected Textbox textBox_nip;
	protected Textbox textBox_nama;
	protected Textbox textBox_tempatlahir;
	protected Listbox comboBox_kotalahir;
	protected Datebox dateBox_tanggallahir;
	protected Listbox comboBox_jeniskelamin;
	protected Listbox comboBox_agama;
	protected Listbox comboBox_statuspeg;
	protected Listbox comboBox_jenispeg;
	protected Listbox comboBox_dudukpeg;
	protected Listbox comboBox_statuskawin;
	protected Listbox comboBox_goldarah;
	protected Textbox textBox_alamat;
	protected Textbox textBox_rt;
	protected Textbox textBox_rw;
	protected Textbox textBox_telp;
	protected Textbox textBox_kodepos;
	protected Listbox comboBox_provinsi;
	protected Listbox comboBox_kota;
	protected Listbox comboBox_kec;
	protected Listbox comboBox_des;
	protected Textbox textBox_karpeg;
	protected Textbox textBox_askes;
	protected Textbox textBox_taspen;
	protected Textbox textBox_karissu;
	protected Textbox textBox_npwp;
	protected Textbox textBox_ktp;
	private Textbox textBox_niplama;
		
	private final String btnController_classPrefix = "btn_Pegawai_DataPokok_Identitas_";
	private ButtonStatusCtrl buttonCtrl_Pegawai_DataPokok_Identitas;
	private Button edit;
	private Button save;
	private Button cancel;
	
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	private TpCpnsDAO tpCpnsDAO;
	private GabunganDAO gabunganDAO;
	private WilayahDAO wilayahDAO;
	private TpIdentitasDAO tpIdentitasDAO;
	
	private AnnotateDataBinder binder;
	
	public PegawaiDetailCtrl_DataPokok_Identitas() {
		super();
	}
	
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if (this.arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_DataPokok((PegawaiDetailCtrl_DataPokok) this.arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_DataPokok().setPegawaiDetailCtrl_DataPokok_Identitas(this);

			if (getPegawaiDetailCtrl_DataPokok().getSelected() != null) {
				TpCpns entry = getSelected();
				
				setSelected(getPegawaiDetailCtrl_DataPokok().getSelected());
				textBox_nip.setValue(entry.getNip());
			} else
				setSelected(null);
		} else {
			setSelected(null);
		}
	}
	
	public void onCreate$windowPegawaiDetail_DataPokok_Identitas(Event event) throws Exception {
		this.buttonCtrl_Pegawai_DataPokok_Identitas = new ButtonStatusCtrl(getUserWorkspace(), btnController_classPrefix, true, null, null, null, null, null, null, null, edit, null, save, cancel, null);
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		this.binder.loadAll();
		
		ListModelList kotalahir = new ListModelList(wilayahDAO.getWilayahByType(ConstantsText.TIPEWILAYAH_KOTA));
		comboBox_kotalahir.setModel(kotalahir);
		comboBox_kotalahir.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kotalahir.setSelectedIndex(kotalahir.indexOf(wilayahDAO.getWilayahByKode(getSelected().getIdentitas().getKabLahir() + ConstantsText.ZEROADDITION_KOTA)));
		
//		ListModelList jeniskelamin = new ListModelList();
//		comboBox_kotalahir.setModel(jeniskelamin);
//		comboBox_kotalahir.setItemRenderer();
//		comboBox_kotalahir.setSelectedIndex(jeniskelamin.indexOf();
		
		ListModelList agama = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_AGAMA));
		comboBox_agama.setModel(agama);
		comboBox_agama.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_agama.setSelectedIndex(agama.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_AGAMA, getSelected().getIdentitas().getKodeAgama())));
		
		ListModelList statuspegawai = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_STATUSPEG));
		comboBox_statuspeg.setModel(statuspegawai);
		comboBox_statuspeg.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_statuspeg.setSelectedIndex(statuspegawai.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_STATUSPEG, getSelected().getIdentitas().getKodeStaPeg())));
		
		ListModelList jenispegawai = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISPEG));
		comboBox_jenispeg.setModel(jenispegawai);
		comboBox_jenispeg.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_jenispeg.setSelectedIndex(jenispegawai.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISPEG, getSelected().getIdentitas().getKodeJnsPeg())));
		
		ListModelList dudukpegawai = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_DUDUKPEG));
		comboBox_dudukpeg.setModel(dudukpegawai);
		comboBox_dudukpeg.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_dudukpeg.setSelectedIndex(dudukpegawai.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_DUDUKPEG, getSelected().getIdentitas().getKodeDukPns())));
		
//		ListModelList statuskawin = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.));
//		comboBox_kotalahir.setModel(statuskawin);
//		comboBox_kotalahir.setItemRenderer(new GabunganListModelItemRenderer());
//		comboBox_kotalahir.setSelectedIndex(statuskawin.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText., getSelected().getIdentitas().getKodeStaWin()())));
		
//		ListModelList goldarah = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_STATUSPEG));
//		comboBox_kotalahir.setModel(statuspegawai);
//		comboBox_kotalahir.setItemRenderer(new GabunganListModelItemRenderer());
//		comboBox_kotalahir.setSelectedIndex(statuspegawai.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_STATUSPEG, getSelected().getIdentitas().getKodeStaPeg())));
		
		Wilayah prov = wilayahDAO.getWilayahByKode(getSelected().getIdentitas().getAlKProp() + ConstantsText.ZEROADDITION_PROVINSI);
		ListModelList provinsi = new ListModelList(wilayahDAO.getWilayahByType(ConstantsText.TIPEWILAYAH_PROVINSI));
		comboBox_provinsi.setModel(provinsi);
		comboBox_provinsi.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_provinsi.setSelectedIndex(provinsi.indexOf(prov));
		
		Wilayah kot = wilayahDAO.getWilayahByKode(getSelected().getIdentitas().getAlKProp() + getSelected().getIdentitas().getAlKKab() + ConstantsText.ZEROADDITION_KOTA);
		ListModelList kota = new ListModelList(wilayahDAO.getKotaByPropinsi(prov));
		comboBox_kota.setModel(kota);
		comboBox_kota.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kota.setSelectedIndex(kota.indexOf(kot));
		
		Wilayah kc = wilayahDAO.getWilayahByKode(getSelected().getIdentitas().getAlKProp() + getSelected().getIdentitas().getAlKKab() + getSelected().getIdentitas().getAlKKec() + ConstantsText.ZEROADDITION_KEC);
		ListModelList kec = new ListModelList(wilayahDAO.getKecamatanByKota(kot));
		comboBox_kec.setModel(kec);
		comboBox_kec.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kec.setSelectedIndex(kec.indexOf(kc));
		
		Wilayah ds = wilayahDAO.getWilayahByKode(getSelected().getIdentitas().getAlKProp() + getSelected().getIdentitas().getAlKKab() + getSelected().getIdentitas().getAlKKec() + getSelected().getIdentitas().getAlKDes());
		ListModelList des = new ListModelList(wilayahDAO.getKelurahanByKecamatan(kc));
		comboBox_des.setModel(des);
		comboBox_des.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_des.setSelectedIndex(des.indexOf(ds));
		
		doFitSize(event);
		this.buttonCtrl_Pegawai_DataPokok_Identitas.setInitEdit();
	}
	
	public void onClick$edit(Event event) throws Exception {
		doEdit();
	}
	
	public void onClick$cancel(Event event) throws Exception {
		doCancel();
	}
	
	public void onClick$save(Event event) throws Exception {
		doSave();
	}
	
	public void onSelect$comboBox_provinsi(SelectEvent event) throws Exception {
		ListModel provList = comboBox_provinsi.getModel();
		Wilayah prov = (Wilayah) provList.getElementAt(comboBox_provinsi.getSelectedItem().getIndex());
		
		ListModelList kota = new ListModelList(wilayahDAO.getKotaByPropinsi(prov));
		comboBox_kota.setModel(kota);
		comboBox_kota.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kota.setSelectedIndex(0);
	}
	
	public void onSelect$comboBox_kota(SelectEvent event) throws Exception {
		ListModel kotaList = comboBox_kota.getModel();
		Wilayah kota = (Wilayah) kotaList.getElementAt(comboBox_kota.getSelectedItem().getIndex());
		
		ListModelList kec = new ListModelList(wilayahDAO.getKotaByPropinsi(kota));
		comboBox_kota.setModel(kec);
		comboBox_kota.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kota.setSelectedIndex(0);
	}
	
	public void onSelect$comboBox_des(Event event) throws Exception {
		ListModel kecList = comboBox_kec.getModel();
		Wilayah kec = (Wilayah) kecList.getElementAt(comboBox_kec.getSelectedItem().getIndex());
		
		ListModelList des = new ListModelList(wilayahDAO.getKotaByPropinsi(kec));
		comboBox_kota.setModel(des);
		comboBox_kota.setItemRenderer(new WilayahListModelItemRenderer());
		comboBox_kota.setSelectedIndex(0);
	}
	
	private void doEdit() {
		doStoreInitValue();
		this.buttonCtrl_Pegawai_DataPokok_Identitas.setBtnStatus_Edit();
		doReadOnlyMode(false);
		getBinder().loadAll();
		textBox_nip.setFocus(true);
	}
	
	private void doCancel() {
		doResetToInitValue();
		if(getBinder() != null) {
			getBinder().loadAll();
			doReadOnlyMode(true);
			this.buttonCtrl_Pegawai_DataPokok_Identitas.setInitEdit();
		}
	}
	
	private void doSave() throws InterruptedException {
		try {
			TpIdentitas identitas = getSelected().getIdentitas();
			tpIdentitasDAO.saveOrUpdate(identitas);
			doStoreInitValue();
			// refresh the list
			getPegawaiDetailCtrl_DataPokok().getPegawaiMainCtrl().getPegawaiListCtrl().doFillList();

		} catch (final DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetToInitValue();

			return;

		} finally {
			this.buttonCtrl_Pegawai_DataPokok_Identitas.setInitEdit();
			doReadOnlyMode(true);
		}
	}
	
	public void doReadOnlyMode(boolean b) {
//		textBox_nip.setReadonly(b);
		textBox_nama.setReadonly(b);
		textBox_tempatlahir.setReadonly(b);
		comboBox_kotalahir.setDisabled(b);
		dateBox_tanggallahir.setReadonly(b);
		comboBox_jeniskelamin.setDisabled(b);
		comboBox_agama.setDisabled(b);
		comboBox_statuspeg.setDisabled(b);
		comboBox_jenispeg.setDisabled(b);
		comboBox_dudukpeg.setDisabled(b);
		comboBox_statuskawin.setDisabled(b);
		comboBox_goldarah.setDisabled(b);
		textBox_alamat.setReadonly(b);
		textBox_rt.setReadonly(b);
		textBox_rw.setReadonly(b);
		textBox_telp.setReadonly(b);
		textBox_kodepos.setReadonly(b);
		comboBox_provinsi.setDisabled(b);
		comboBox_kota.setDisabled(b);
		comboBox_kec.setDisabled(b);
		comboBox_des.setDisabled(b);
		textBox_karpeg.setReadonly(b);
		textBox_askes.setReadonly(b);
		textBox_taspen.setReadonly(b);
		textBox_karissu.setReadonly(b);
		textBox_npwp.setReadonly(b);
		textBox_ktp.setReadonly(b);
		textBox_niplama.setReadonly(b);
	}
	
	public void doFitSize(Event event) {
		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - 152;
		this.borderLayout_PegawaiDataPokok_Identitas.setHeight(String.valueOf(maxListBoxHeight) + "px");

		this.windowPegawaiDetail_DataPokok_Identitas.invalidate();
	}
	
	public void doStoreInitValue() {
		if(getSelected() != null) {
			try {
				setOriginal((TpCpns) ZksampleBeanUtils.cloneBean(getSelected()));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doResetToInitValue() {
		if(getOriginal() != null) {
			try {
				setSelected((TpCpns) ZksampleBeanUtils.cloneBean(getOriginal()));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public TpCpns getOriginal() {
		return getPegawaiDetailCtrl_DataPokok().getOriginal();
	}
	
	public void setOriginal(TpCpns cpns) {
		getPegawaiDetailCtrl_DataPokok().setOriginal(cpns);
	}

	public TpCpns getSelected() {
		return getPegawaiDetailCtrl_DataPokok().getSelected();
	}

	public void setSelected(TpCpns selected) {
		getPegawaiDetailCtrl_DataPokok().setSelected(selected);
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public PegawaiDetailCtrl_DataPokok getPegawaiDetailCtrl_DataPokok() {
		return pegawaiDetailCtrl_DataPokok;
	}

	public void setPegawaiDetailCtrl_DataPokok(
			PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok) {
		this.pegawaiDetailCtrl_DataPokok = pegawaiDetailCtrl_DataPokok;
	}

	public TpCpnsDAO getTpCpnsDAO() {
		return tpCpnsDAO;
	}

	public void setTpCpnsDAO(TpCpnsDAO tpCpnsDAO) {
		this.tpCpnsDAO = tpCpnsDAO;
	}

	public GabunganDAO getGabunganDAO() {
		return gabunganDAO;
	}

	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.gabunganDAO = gabunganDAO;
	}

	public WilayahDAO getWilayahDAO() {
		return wilayahDAO;
	}

	public void setWilayahDAO(WilayahDAO wilayahDAO) {
		this.wilayahDAO = wilayahDAO;
	}

	public TpIdentitasDAO getTpIdentitasDAO() {
		return tpIdentitasDAO;
	}

	public void setTpIdentitasDAO(TpIdentitasDAO tpIdentitasDAO) {
		this.tpIdentitasDAO = tpIdentitasDAO;
	}
}
