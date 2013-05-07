package de.forsthaus.webui.pegawai;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.util.ZksampleBeanUtils;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class PegawaiDetailCtrl_DataPokok_Identitas extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005265307103199688L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_DataPokok_Identitas.class);
	
	protected Window windowPegawaiDetail_DataPokok_Identitas;
	private Borderlayout borderLayout_PegawaiDataPokok_Identitas;
	private Textbox textBox_nip;
	private Textbox textBox_nama;
	private Textbox textBox_tempatlahir;
	private Combobox comboBox_kotalahir;
	private Datebox dateBox_tanggallahir;
	private Combobox comboBox_jeniskelamin;
	private Combobox comboBox_agama;
	private Combobox comboBox_statuskepeg;
	private Combobox comboBox_jeniskepeg;
	private Combobox comboBox_dudukkepeg;
	private Combobox comboBox_statuskawin;
	private Combobox comboBox_goldar;
	private Textbox textBox_alamat;
	private Textbox textBox_rt;
	private Textbox textBox_rw;
	private Textbox textBox_telp;
	private Textbox textBox_kodepos;
	private Combobox comboBox_prov;
	private Combobox comboBox_kota;
	private Combobox comboBox_kec;
	private Combobox comboBox_desa;
	private Textbox textBox_karpeg;
	private Textbox textBox_askes;
	private Textbox textBox_taspen;
	private Textbox textBox_karissu;
	private Textbox textBox_npwp;
	private Textbox textBox_ktp;
	private Textbox textBox_niplama;
		
	private final String btnController_classPrefix = "btn_Pegawai_DataPokok_Identitas_";
	private ButtonStatusCtrl buttonCtrl_Pegawai_DataPokok_Identitas;
	private Button edit;
	private Button save;
	private Button cancel;
	
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	
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
		
		doFitSize(event);
		this.buttonCtrl_Pegawai_DataPokok_Identitas.setInitEdit();
	}
	
	public void onClick$edit(Event event) throws Exception {
		doEdit();
	}
	
	public void doEdit() {
		doStoreInitValue();
		this.buttonCtrl_Pegawai_DataPokok_Identitas.setBtnStatus_Edit();
		doReadOnlyMode(false);
		getBinder().loadAll();
		textBox_nip.setFocus(true);
	}
	
	public void doCancel() {
		doResetToInitValue();
		if(getBinder() != null) {
			getBinder().loadAll();
			doReadOnlyMode(true);
			this.buttonCtrl_Pegawai_DataPokok_Identitas.setInitEdit();
		}
	}
	
	public void doReadOnlyMode(boolean b) {
		textBox_nip.setReadonly(b);
		textBox_nama.setReadonly(b);
		textBox_tempatlahir.setReadonly(b);
		comboBox_kotalahir.setReadonly(b);
		dateBox_tanggallahir.setReadonly(b);
		comboBox_jeniskelamin.setReadonly(b);
		comboBox_agama.setReadonly(b);
		comboBox_statuskepeg.setReadonly(b);
		comboBox_jeniskepeg.setReadonly(b);
		comboBox_dudukkepeg.setReadonly(b);
		comboBox_statuskawin.setReadonly(b);
		comboBox_goldar.setReadonly(b);
		textBox_alamat.setReadonly(b);
		textBox_rt.setReadonly(b);
		textBox_rw.setReadonly(b);
		textBox_telp.setReadonly(b);
		textBox_kodepos.setReadonly(b);
		comboBox_prov.setReadonly(b);
		comboBox_kota.setReadonly(b);
		comboBox_kec.setReadonly(b);
		comboBox_desa.setReadonly(b);
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

}
