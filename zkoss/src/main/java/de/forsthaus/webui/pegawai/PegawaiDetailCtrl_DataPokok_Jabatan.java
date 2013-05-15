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
import de.forsthaus.backend.dao.EselonDAO;
import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.dao.JabFungDAO;
import de.forsthaus.backend.dao.MasterUnitKerjaDAO;
import de.forsthaus.backend.dao.TpCpnsDAO;
import de.forsthaus.backend.dao.TpJabatanDAO;
import de.forsthaus.backend.dao.UnitKerjaDAO;
import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.backend.model.TpIdentitas;
import de.forsthaus.backend.model.TpJabatan;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.backend.util.ZksampleBeanUtils;
import de.forsthaus.webui.pegawai.model.EselonListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.GabunganListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.JabFungListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.UnitKerjaListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

public class PegawaiDetailCtrl_DataPokok_Jabatan extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005265307103199688L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_DataPokok_Jabatan.class);
	
	protected Window windowPegawaiDetail_DataPokok_Jabatan;
	private Borderlayout borderLayout_PegawaiDataPokok_Jabatan;
	private Textbox textBox_nip;
	private Listbox comboBox_pejabat;
	private Textbox textBox_nosk;
	private Datebox dateBox_tglsk;
	private Listbox comboBox_unker;
	private Listbox comboBox_jenisjabat;
	private Listbox comboBox_eselon;
	private Listbox comboBox_rumjabat;
	private Listbox comboBox_pokjabat;
	private Textbox textBox_nmjabat;
	private Datebox dateBox_tmtjabatan;
	private Listbox comboBox_sumpahjabat;
	private Textbox textBox_rangkap;
	private Datebox dateBox_tmtrangkap;
		
	private final String btnController_classPrefix = "btn_Pegawai_DataPokok_Jabatan_";
	private ButtonStatusCtrl buttonCtrl_Pegawai_DataPokok_Jabatan;
	private Button edit;
	private Button save;
	private Button cancel;
	
	private ListModelList sumpahjabat;
	private ListModelList rumjabat;
	private ListModelList pokjabat;
	
	private PegawaiDetailCtrl_DataPokok pegawaiDetailCtrl_DataPokok;
	private TpCpnsDAO tpCpnsDAO;
	private GabunganDAO gabunganDAO;
	private UnitKerjaDAO unitKerjaDAO;
	private EselonDAO eselonDAO;
	private JabFungDAO jabFungDAO;
	private TpJabatanDAO tpJabatanDAO;
		
	private AnnotateDataBinder binder;
	
	public PegawaiDetailCtrl_DataPokok_Jabatan() {
		super();
	}
	
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if (this.arg.containsKey("ModuleMainController")) {
			setPegawaiDetailCtrl_DataPokok((PegawaiDetailCtrl_DataPokok) this.arg.get("ModuleMainController"));

			getPegawaiDetailCtrl_DataPokok().setPegawaiDetailCtrl_DataPokok_Jabatan(this);

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
	
	public void onCreate$windowPegawaiDetail_DataPokok_Jabatan(Event event) throws Exception {
		this.buttonCtrl_Pegawai_DataPokok_Jabatan = new ButtonStatusCtrl(getUserWorkspace(), btnController_classPrefix, true, null, null, null, null, null, null, null, edit, null, save, cancel, null);
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		this.binder.loadAll();
		
		ListModelList pejabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_PEJABAT));
		comboBox_pejabat.setModel(pejabat);
		comboBox_pejabat.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_pejabat.setSelectedIndex(pejabat.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, getSelected().getJabatan().getKodePejJab())));
		
		ListModelList unker = new ListModelList(unitKerjaDAO.getAllUnitKerja());
		comboBox_unker.setModel(unker);
		comboBox_unker.setItemRenderer(new UnitKerjaListModelItemRenderer());
		comboBox_unker.setSelectedIndex(unker.indexOf(unitKerjaDAO.getUnitKerjaByKode(getSelected().getJabatan().getKodeUnKer())));
		
		ListModelList jenisjabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISJABATAN));
		comboBox_jenisjabat.setModel(jenisjabat);
		comboBox_jenisjabat.setItemRenderer(new GabunganListModelItemRenderer());
		comboBox_jenisjabat.setSelectedIndex(jenisjabat.indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISJABATAN, getSelected().getJabatan().getJnsJab())));
		
		ListModelList eselon = new ListModelList(eselonDAO.getAllEselon());
		comboBox_eselon.setModel(eselon);
		comboBox_eselon.setItemRenderer(new EselonListModelItemRenderer());
		comboBox_eselon.setSelectedIndex(eselon.indexOf(eselonDAO.getEselonByKode(getSelected().getJabatan().getKodeEselon())));
		
		rumjabat = new ListModelList(jabFungDAO.getAll());
		comboBox_rumjabat.setModel(rumjabat);
		comboBox_rumjabat.setItemRenderer(new JabFungListModelItemRenderer());
		
		pokjabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_POKJABATAN));
		comboBox_pokjabat.setModel(pokjabat);
		comboBox_pokjabat.setItemRenderer(new GabunganListModelItemRenderer());
		
		//TODO
		//sumpah
				
		doFitSize(event);
		this.buttonCtrl_Pegawai_DataPokok_Jabatan.setInitEdit();
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
		this.buttonCtrl_Pegawai_DataPokok_Jabatan.setBtnStatus_Edit();
		doReadOnlyMode(false);
		getBinder().loadAll();
		textBox_nip.setFocus(true);
	}
	
	private void doCancel() {
		doResetToInitValue();
		if(getBinder() != null) {
			getBinder().loadAll();
			doReadOnlyMode(true);
			this.buttonCtrl_Pegawai_DataPokok_Jabatan.setInitEdit();
		}
	}
	
	private void doSave() throws InterruptedException {
		try {
			TpJabatan jabatan = getSelected().getJabatan();
			tpJabatanDAO.saveOrUpdate(jabatan);
			doStoreInitValue();
			// refresh the list
			getPegawaiDetailCtrl_DataPokok().getPegawaiMainCtrl().getPegawaiListCtrl().doFillList();

		} catch (final DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetToInitValue();

			return;

		} finally {
			this.buttonCtrl_Pegawai_DataPokok_Jabatan.setInitEdit();
			doReadOnlyMode(true);
		}
	}
	
	public void doReadOnlyMode(boolean b) {
//		textBox_nip.setReadonly(b);
		comboBox_pejabat.setDisabled(b);
		textBox_nosk.setReadonly(b);
		dateBox_tglsk.setReadonly(b);
		comboBox_unker.setDisabled(b);
		comboBox_jenisjabat.setDisabled(b);
		comboBox_eselon.setDisabled(b);
		comboBox_rumjabat.setDisabled(b);
		textBox_nmjabat.setReadonly(b);
		dateBox_tmtjabatan.setReadonly(b);
		comboBox_sumpahjabat.setDisabled(b);
	}
	
	public void goJabatan(String jabatan) {
		switch (Integer.valueOf(jabatan)) {
		case 1:
			goStruktural();
			break;
		case 2:
			goFungKhusus();
			break;
		case 3:
			goPejNegara();
			break;
		case 4:
			goFungUmum();
			break;
		case 5:
			goJabKorpri();
			break;
		case 6:
			goJabRangkap();
			break;
		default:
			break;
		}
	}
	
	public void goStruktural() {
		comboBox_rumjabat.setVisible(false);
		comboBox_pokjabat.setVisible(false);
		textBox_rangkap.setVisible(false);
		dateBox_tmtrangkap.setVisible(false);
		comboBox_sumpahjabat.setVisible(true);
		
//		if(getSelected().getJabatan().getsJab() != null)
//			comboBox_sumpahjabat.setSelectedIndex(sumpahjabat.indexOf())
//		else
//			comboBox_sumpahjabat.setSelectedIndex(0);
	}
	
	public void goFungKhusus() {
		comboBox_rumjabat.setVisible(true);
		comboBox_pokjabat.setVisible(false);
		textBox_rangkap.setVisible(false);
		dateBox_tmtrangkap.setVisible(false);
		comboBox_sumpahjabat.setVisible(true);
		
		if(getSelected().getJabatan().getKodeJab() != null)
			comboBox_rumjabat.setSelectedIndex(rumjabat.indexOf(jabFungDAO.getByKode(getSelected().getJabatan().getKodeJab())));
//		if(getSelected().getJabatan().getsJab() != null)
//		comboBox_sumpahjabat.setSelectedIndex(sumpahjabat.indexOf())
//	else
//		comboBox_sumpahjabat.setSelectedIndex(0);
	}
	
	public void goPejNegara() {
		comboBox_rumjabat.setVisible(false);
		comboBox_pokjabat.setVisible(false);
		textBox_rangkap.setVisible(true);
		dateBox_tmtrangkap.setVisible(true);
		comboBox_sumpahjabat.setVisible(true);
		
//		if(getSelected().getJabatan().getsJab() != null)
//		comboBox_sumpahjabat.setSelectedIndex(sumpahjabat.indexOf())
//	else
//		comboBox_sumpahjabat.setSelectedIndex(0);
	}
	
	public void goFungUmum() {
		comboBox_rumjabat.setVisible(false);
		comboBox_pokjabat.setVisible(true);
		textBox_rangkap.setVisible(false);
		dateBox_tmtrangkap.setVisible(false);
		comboBox_sumpahjabat.setVisible(false);
		
		if(getSelected().getJabatan().getKodeJab() != null)
			comboBox_pokjabat.setSelectedIndex(pokjabat.indexOf(jabFungDAO.getByKode(getSelected().getJabatan().getKodeJab())));
	}
	
	public void goJabKorpri() {
		comboBox_rumjabat.setVisible(false);
		comboBox_pokjabat.setVisible(false);
		textBox_rangkap.setVisible(false);
		dateBox_tmtrangkap.setVisible(false);
		comboBox_sumpahjabat.setVisible(true);
		
//		if(getSelected().getJabatan().getsJab() != null)
//		comboBox_sumpahjabat.setSelectedIndex(sumpahjabat.indexOf())
//	else
//		comboBox_sumpahjabat.setSelectedIndex(0);
	}
	
	public void goJabRangkap() {
		comboBox_rumjabat.setVisible(false);
		comboBox_pokjabat.setVisible(false);
		textBox_rangkap.setVisible(true);
		dateBox_tmtrangkap.setVisible(true);
		comboBox_sumpahjabat.setVisible(true);
		
//		if(getSelected().getJabatan().getsJab() != null)
//		comboBox_sumpahjabat.setSelectedIndex(sumpahjabat.indexOf())
//	else
//		comboBox_sumpahjabat.setSelectedIndex(0);
	}
	
	public void doFitSize(Event event) {
		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - 152;
		this.borderLayout_PegawaiDataPokok_Jabatan.setHeight(String.valueOf(maxListBoxHeight) + "px");

		this.windowPegawaiDetail_DataPokok_Jabatan.invalidate();
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

	public UnitKerjaDAO getUnitKerjaDAO() {
		return unitKerjaDAO;
	}

	public void setUnitKerjaDAO(UnitKerjaDAO unitKerjaDAO) {
		this.unitKerjaDAO = unitKerjaDAO;
	}

	public EselonDAO getEselonDAO() {
		return eselonDAO;
	}

	public void setEselonDAO(EselonDAO eselonDAO) {
		this.eselonDAO = eselonDAO;
	}

	public JabFungDAO getJabFungDAO() {
		return jabFungDAO;
	}

	public void setJabFungDAO(JabFungDAO jabFungDAO) {
		this.jabFungDAO = jabFungDAO;
	}

	public TpJabatanDAO getTpJabatanDAO() {
		return tpJabatanDAO;
	}

	public void setTpJabatanDAO(TpJabatanDAO tpJabatanDAO) {
		this.tpJabatanDAO = tpJabatanDAO;
	}
}
