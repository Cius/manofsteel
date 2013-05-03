package de.forsthaus.webui.pegawai;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import de.forsthaus.UserWorkspace;
import de.forsthaus.backend.dao.TpIdentitasDAO;
import de.forsthaus.backend.model.TpCpns;
import de.forsthaus.webui.util.GFCBaseCtrl;

public class PegawaiDetailCtrl_DataPokok extends GFCBaseCtrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8152100945425893228L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_DataPokok.class); 
	
	protected Window windowPegawaiDetail_DataPokok;
	private Borderlayout borderLayout_PegawaiDataPokok;
	
	private Tabbox tabbox_PegawaiDataPokok;
	private Tab tab_PegawaiDataPokok_Identitas;
	
	private Tabpanel tabPanel_PegawaiDataPokok_Identitas;
	
	protected transient AnnotateDataBinder binder;
	private PegawaiMainCtrl pegawaiMainCtrl;
	
	private TpIdentitasDAO identitasDAO;
	
	public PegawaiDetailCtrl_DataPokok() {
		super();
	}
	
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		this.self.setAttribute("controller", this, false);
		
		if (this.arg.containsKey("ModuleMainController")) {
			setPegawaiMainCtrl((PegawaiMainCtrl) this.arg.get("ModuleMainController"));

			getPegawaiMainCtrl().setPegawaiDetailCtrl_DataPokok(this);

			if (getPegawaiMainCtrl().getSelected() != null) {
				setSelected(getPegawaiMainCtrl().getSelected());
			} else
				setSelected(null);
		} else {
			setSelected(null);
		}
	}
	
	public void onCreate$windowPegawaiDetail_DataPokok(Event event) throws Exception {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		this.binder.loadAll();
		
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int menuOffset = UserWorkspace.getInstance().getMenuOffset();
		int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
		height = height - menuOffset;
		final int maxListBoxHeight = height - 152;
		this.borderLayout_PegawaiDataPokok.setHeight(String.valueOf(maxListBoxHeight) + "px");

		this.windowPegawaiDetail_DataPokok.invalidate();
	}
	
	public void setSelected(TpCpns cpns) {
		getPegawaiMainCtrl().setSelected(cpns);
	}
	
	public TpCpns getSelected() {
		return getPegawaiMainCtrl().getSelected();
	}

	public PegawaiMainCtrl getPegawaiMainCtrl() {
		return pegawaiMainCtrl;
	}

	public void setPegawaiMainCtrl(PegawaiMainCtrl pegawaiMainCtrl) {
		this.pegawaiMainCtrl = pegawaiMainCtrl;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}
}
