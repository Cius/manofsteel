package de.forsthaus.webui.pegawai;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.dao.TpKgbPangkatDAO;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TpIdentitas;
import de.forsthaus.backend.model.TpKgbPangkat;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.backend.util.ZksampleBeanUtils;
import de.forsthaus.webui.pegawai.model.GabunganListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.GolonganRuangListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

public class PegawaiDetailCtrl_DataPokok_Pangkat extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005265307103199688L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_DataPokok_Pangkat.class);
	
	protected Window windowPegawaiDetail_DataPokok_Pangkat;
	private Borderlayout borderLayout_PegawaiDataPokok_Pangkat;
	private Textbox textBox_nip;	
	private Listbox comboBox_pejabat;
	private Textbox textBox_nosk;
	private Datebox dateBox_tglsk;
	private Listbox comboBox_golru;	
	private Datebox dateBox_tmtpangkat;
	private Textbox textBox_thgolongan;
	private Textbox textBox_blgolongan;
	private Textbox textBox_gapok;
	private Listbox comboBox_jenisnaik;
	private Textbox textBox_kredit;
		
	private final String btnController_classPrefix = "btn_Pegawai_DataPokok_Pangkat_";
	private ButtonStatusCtrl buttonCtrl_Pegawai_DataPokok_Pangkat;
	private Button edit;
	private Button save;
	private Button cancel;
	
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	private TpCpnsDAO tpCpnsDAO;
	private GabunganDAO gabunganDAO;
	private GolonganRuangDAO golonganRuangDAO;
	private TpKgbPangkatDAO tpKgbPangkatDAO;
	
	private AnnotateDataBinder binder;
	
	public PegawaiDetailCtrl_DataPokok_Pangkat() {
		super();
	}
	
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if (this.arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_DataPokok((PegawaiDetailCtrl_DataPokok) this.arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_DataPokok().setPegawaiDetailCtrl_DataPokok_Pangkat(this);

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
	
	public void onCreate$windowPegawaiDetail_DataPokok_Pangkat(Event event) throws Exception {
		this.buttonCtrl_Pegawai_DataPokok_Pangkat = new ButtonStatusCtrl(getUserWorkspace(), btnController_classPrefix, true, null, null, null, null, null, null, null, edit, null, save, cancel, null);
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		this.binder.loadAll();
		
		ListModelList pejabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_PEJABAT));
		comboBox_pejabat.setModel(pejabat);
		comboBox_pejabat.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_pejabat.setSelectedIndex(pejabat.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, getSelected().getPangkat().getKodePejPkt())));
		
		ListModelList golru = new ListModelList(golonganRuangDAO.getAllGolonganRuang());
		comboBox_golru.setModel(golru);
		comboBox_golru.setItemRenderer(new GolonganRuangListModelItemRenderer());
		comboBox_golru.setSelectedIndex(golru.indexOf(golonganRuangDAO.getGolonganRuangByKode(getSelected().getPangkat().getKodeGolRuPkt())));
		
		ListModelList jenisnaik = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISNAIK));
		comboBox_jenisnaik.setModel(jenisnaik);
		comboBox_jenisnaik.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_jenisnaik.setSelectedIndex(jenisnaik.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISNAIK, getSelected().getPangkat().getKodeNPang())));
				
		doFitSize(event);
		this.buttonCtrl_Pegawai_DataPokok_Pangkat.setInitEdit();
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
	
	private void doEdit() {
		doStoreInitValue();
		this.buttonCtrl_Pegawai_DataPokok_Pangkat.setBtnStatus_Edit();
		doReadOnlyMode(false);
		getBinder().loadAll();
		textBox_nip.setFocus(true);
	}
	
	private void doCancel() {
		doResetToInitValue();
		if(getBinder() != null) {
			getBinder().loadAll();
			doReadOnlyMode(true);
			this.buttonCtrl_Pegawai_DataPokok_Pangkat.setInitEdit();
		}
	}
	
	private void doSave() throws InterruptedException {
		try {
			TpKgbPangkat pangkat = getSelected().getPangkat();
			tpKgbPangkatDAO.saveOrUpdate(pangkat);
			doStoreInitValue();
			// refresh the list
//			getPegawaiDetailCtrl_DataPokok().getPegawaiMainCtrl().getPegawaiListCtrl().doFillList();

		} catch (final DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetToInitValue();

			return;

		} finally {
			this.buttonCtrl_Pegawai_DataPokok_Pangkat.setInitEdit();
			doReadOnlyMode(true);
		}
	}
	
	public void doReadOnlyMode(boolean b) {
//		textBox_nip.setReadonly(b);
		comboBox_pejabat.setDisabled(b);
		textBox_nosk.setReadonly(b);
		dateBox_tglsk.setReadonly(b);
		comboBox_golru.setDisabled(b);
		dateBox_tmtpangkat.setDisabled(b);
		textBox_thgolongan.setReadonly(b);
		textBox_blgolongan.setReadonly(b);
		textBox_gapok.setReadonly(b);
		comboBox_jenisnaik.setDisabled(b);
		textBox_kredit.setReadonly(b);
	}
	
	public void doFitSize(Event event) {
		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - 152;
		this.borderLayout_PegawaiDataPokok_Pangkat.setHeight(String.valueOf(maxListBoxHeight) + "px");

		this.windowPegawaiDetail_DataPokok_Pangkat.invalidate();
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

	public GolonganRuangDAO getGolonganRuangDAO() {
		return golonganRuangDAO;
	}

	public void setGolonganRuangDAO(GolonganRuangDAO golonganRuangDAO) {
		this.golonganRuangDAO = golonganRuangDAO;
	}

	public TpKgbPangkatDAO getTpKgbPangkatDAO() {
		return tpKgbPangkatDAO;
	}

	public void setTpKgbPangkatDAO(TpKgbPangkatDAO tpKgbPangkatDAO) {
		this.tpKgbPangkatDAO = tpKgbPangkatDAO;
	}

}
