/**
 * Copyright 2010 the original author or authors.
 * 
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package de.forsthaus.webui.eselon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.EselonDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.model.Eselon;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.webui.eselon.model.EselonGolonganRuangListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/eselonDialog.zul file.<br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * 
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 *          07/24/2009: sge changes for clustering.<br>
 *          10/12/2009: sge changings in the saving routine.<br>
 *          11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 *          (GenericForwardComposer) for spring-managed creation.<br>
 * 
 * @author bbruhns
 * @author sgerth
 */
public class EselonDialogCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(EselonDialogCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window eselonDialogWindow; // autowired
	protected Textbox tb_Kode;
	protected Textbox tb_Eselon;
	protected Listbox dd_GolRuangAwal;
	protected Listbox dd_GolRuangAkhir;
	protected Decimalbox tb_TunjanganJabatan;
	protected Intbox tb_BUP;
	

	// overhanded vars per params
	private transient Listbox listBoxEselon; // overhanded
	private Eselon eselon;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_Kode;
	private transient String oldVar_Eselon;
	private transient String oldVar_GolRuangAwal;
	private transient String oldVar_GolRuangAkhir;
	private transient double oldVar_tunjanganJabatan;
	private transient int oldVar_BUP;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_EselonDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnNew; // autowired
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient EselonDAO eselonDAO;
	private transient GolonganRuangDAO golonganRuangDAO;

	/**
	 * default constructor.<br>
	 */
	public EselonDialogCtrl() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$eselonDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, btnNew, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("eselon")) {
			setEselon((Eselon) args.get("eselon"));
		} else {
			setEselon(null);
		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listBoxEselon")) {
			listBoxEselon = (Listbox) args.get("listBoxEselon");
		} else {
			listBoxEselon = null;
		}

		// +++++++++ DropDown ListBox
		// set listModel and itemRenderer for the dropdown listbox
		dd_GolRuangAwal.setModel(new ListModelList(getGolonganRuangDAO().getAllGolonganRuang()));
		dd_GolRuangAwal.setItemRenderer(new EselonGolonganRuangListModelItemRenderer());
		dd_GolRuangAkhir.setModel(new ListModelList(getGolonganRuangDAO().getAllGolonganRuang()));
		dd_GolRuangAkhir.setItemRenderer(new EselonGolonganRuangListModelItemRenderer());

		// if available, select the object
		ListModelList lml = (ListModelList) dd_GolRuangAwal.getModel();
		GolonganRuang typ = eselon.getgAwal();
		ListModelList lml2 = (ListModelList) dd_GolRuangAkhir.getModel();
		GolonganRuang typ2 = eselon.getgAkhir();

		if (eselon.isNew()) {
			dd_GolRuangAwal.setSelectedIndex(-1);
			dd_GolRuangAkhir.setSelectedIndex(-1);
		} else {
			dd_GolRuangAwal.setSelectedIndex(lml.indexOf(typ));
			dd_GolRuangAkhir.setSelectedIndex(lml2.indexOf(typ2));
		}
				
		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getEselon());

	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++ Components events +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * If we close the dialog window. <br>
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onClose$eselonDialogWindow(Event event) throws Exception {
		// logger.debug(event.toString());

		doClose();
	}

	/**
	 * when the "save" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnSave(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		doSave();
	}

	/**
	 * when the "edit" button is clicked. <br>
	 * 
	 * @param event
	 */
	public void onClick$btnEdit(Event event) {
		// logger.debug(event.toString());

		doEdit();
	}

	/**
	 * when the "help" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnHelp(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		ZksampleMessageUtils.doShowNotImplementedMessage();
	}

//	/**
//	 * when the "new" button is clicked. <br>
//	 * 
//	 * @param event
//	 */
//	public void onClick$btnNew(Event event) {
//		// logger.debug(event.toString());
//
//		doNew();
//	}

//	/**
//	 * when the "delete" button is clicked. <br>
//	 * 
//	 * @param event
//	 * @throws InterruptedException
//	 */
//	public void onClick$btnDelete(Event event) throws InterruptedException {
//		// logger.debug(event.toString());
//
//		doDelete();
//	}

	/**
	 * when the "cancel" button is clicked. <br>
	 * 
	 * @param event
	 */
	public void onClick$btnCancel(Event event) {
		// logger.debug(event.toString());

		doCancel();
	}

	/**
	 * when the "close" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnClose(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		try {
			doClose();
		} catch (final Exception e) {
			// close anyway
			eselonDialogWindow.onClose();
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++ GUI operations +++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Closes the dialog window. <br>
	 * <br>
	 * Before closing we check if there are unsaved changes in <br>
	 * the components and ask the user if saving the modifications. <br>
	 * 
	 */
	private void doClose() throws Exception {

		if (isDataChanged()) {

			// Show a confirm box
			String msg = Labels.getLabel("message_Data_Modified_Save_Data_YesNo");
			String title = Labels.getLabel("message.Information");

			MultiLineMessageBox.doSetTemplate();
			if (MultiLineMessageBox.show(msg, title, MultiLineMessageBox.YES | MultiLineMessageBox.NO, MultiLineMessageBox.QUESTION, true, new EventListener() {
				@Override
				public void onEvent(Event evt) {
					switch (((Integer) evt.getData()).intValue()) {
					case MultiLineMessageBox.YES:
						try {
							doSave();
						} catch (final InterruptedException e) {
							throw new RuntimeException(e);
						}
					case MultiLineMessageBox.NO:
						break; //
					}
				}
			}

			) == MultiLineMessageBox.YES) {
			}
		}

		eselonDialogWindow.onClose();
	}

	/**
	 * Cancel the actual operation. <br>
	 * <br>
	 * Resets to the original status.<br>
	 * 
	 */
	private void doCancel() {
		doResetInitValues();
		doReadOnly();
		btnCtrl.setInitEdit();
	}

	/**
	 * Writes the bean data to the components.<br>
	 * 
	 * @param aRight
	 *            Eselon
	 */
	public void doWriteBeanToComponents(Eselon eselon) {
		tb_Kode.setValue(eselon.getkEselon());
		tb_Eselon.setValue(eselon.getnEselon());
		String tunJab = eselon.getTunJab() == null ? "" : String.valueOf(eselon.getTunJab());
		tb_TunjanganJabatan.setValue(new BigDecimal(String.valueOf(tunJab)));
		tb_BUP.setValue(eselon.getBup());

		ListModelList lml = (ListModelList) dd_GolRuangAwal.getModel();
		GolonganRuang typ = eselon.getgAwal();
		ListModelList lml2 = (ListModelList) dd_GolRuangAkhir.getModel();
		GolonganRuang typ2 = eselon.getgAkhir();

		if (eselon.getgAwal() == null) {
			dd_GolRuangAwal.setSelectedIndex(-1);
		} else {
			dd_GolRuangAwal.setSelectedIndex(lml.indexOf(typ));
		}
		if (eselon.getgAkhir() == null) {
			dd_GolRuangAkhir.setSelectedIndex(-1);
		} else {
			dd_GolRuangAkhir.setSelectedIndex(lml2.indexOf(typ2));
		}
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(Eselon eselon) {
		eselon.setkEselon(tb_Kode.getValue());
		eselon.setnEselon(tb_Eselon.getValue());
//		eselon.setgAwal(tb_GolRuangAwal.getValue());
//		eselon.setgAkhir(tb_GolRuangAkhir.getValue());
		eselon.setTunJab(tb_TunjanganJabatan.doubleValue());
		eselon.setBup(tb_BUP.intValue());
	}

	/**
	 * Opens the Dialog window modal.
	 * 
	 * It checks if the dialog opens with a new or existing object, if so they
	 * set the readOnly mode accordingly.
	 * 
	 * @param golongan
	 * @throws InterruptedException
	 */
	public void doShowDialog(Eselon eselon) throws InterruptedException {

		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (eselon == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			eselon = getEselonDAO().getNewEselon();
			setEselon(eselon);
		} else {
			setEselon(null);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (eselon.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(eselon);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			eselonDialogWindow.doModal(); // open the dialog in modal
			// mode
		} catch (final Exception e) {
			Messagebox.show(e.toString());
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++ helpers ++++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Set the properties of the fields, like maxLength.<br>
	 */
	private void doSetFieldProperties() {
		tb_Kode.setMaxlength(50);
		tb_Eselon.setMaxlength(50);
	}

	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {
		oldVar_Kode = tb_Kode.getValue();
		oldVar_Eselon = tb_Eselon.getValue();
//		oldVar_GolRuangAwal = dd_GolRuangAwal.get
//		oldVar_GolRuangAkhir = tb_GolRuangAkhir.getValue();
		oldVar_tunjanganJabatan = tb_TunjanganJabatan.doubleValue();
		oldVar_BUP = tb_BUP.intValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		tb_Kode.setValue(oldVar_Kode);
		tb_Eselon.setValue(oldVar_Eselon);
//		tb_GolRuangAwal.setValue(oldVar_GolRuangAwal);
//		tb_GolRuangAkhir.setValue(oldVar_GolRuangAkhir);
		tb_TunjanganJabatan.setValue(String.valueOf(oldVar_tunjanganJabatan));
		tb_BUP.setValue(oldVar_BUP);
	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

		if (oldVar_Kode != tb_Kode.getValue()) {
			changed = true;
		}
		if (oldVar_Eselon != tb_Eselon.getValue()) {
			changed = true;
		}
//		if (oldVar_GolRuangAwal != tb_GolRuangAwal.getValue()) {
//			changed = true;
//		}
//		if (oldVar_GolRuangAkhir != tb_GolRuangAkhir.getValue()) {
//			changed = true;
//		}
		if (oldVar_tunjanganJabatan != tb_TunjanganJabatan.doubleValue()) {
			changed = true;
		}
		if (oldVar_BUP != tb_BUP.intValue()) {
			changed = true;
		}

		return changed;
	}

	/**
	 * Sets the Validation by setting the accordingly constraints to the fields.
	 */
	private void doSetValidation() {

		setValidationOn(true);

		tb_Kode.setConstraint("NO EMPTY");
		tb_Eselon.setConstraint("NO EMPTY");
		tb_TunjanganJabatan.setConstraint("NO EMPTY");
		tb_BUP.setConstraint("NO EMPTY");
	}

	/**
	 * Disables the Validation by setting empty constraints.
	 */
	private void doRemoveValidation() {

		setValidationOn(false);

		tb_Kode.setConstraint("");
		tb_Eselon.setConstraint("");
		tb_TunjanganJabatan.setConstraint("");
		tb_BUP.setConstraint("");
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	
	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		tb_Kode.setReadonly(false);
		tb_Eselon.setReadonly(false);
		tb_TunjanganJabatan.setReadonly(false);
		tb_BUP.setReadonly(false);
		dd_GolRuangAwal.setDisabled(false);
		dd_GolRuangAkhir.setDisabled(false);

		btnCtrl.setBtnStatus_Edit();

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		tb_Kode.setReadonly(true);
		tb_Eselon.setReadonly(true);
		dd_GolRuangAwal.setDisabled(true);
		dd_GolRuangAkhir.setDisabled(true);
		tb_TunjanganJabatan.setReadonly(true);
		tb_BUP.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		tb_Kode.setValue("");
		tb_Eselon.setValue("");
		dd_GolRuangAwal.setSelectedIndex(-1);
		dd_GolRuangAkhir.setSelectedIndex(-1);
		tb_TunjanganJabatan.setValue("0.0");
		tb_BUP.setValue(0);
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final Eselon es = getEselon();

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// force validation, if on, than execute by component.getValue()
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (!isValidationOn()) {
			doSetValidation();
		}

		// fill the object with the components data
		doWriteComponentsToBean(es);

		// save it to database
		try {
			getEselonDAO().saveOrUpdate(es);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxEselon.getListModel();

		// Check if the object is new or updated
		// -1 means that the obj is not in the list, so it's new.
		if (lml.indexOf(es) == -1) {
			lml.add(es);
		} else {
			lml.set(lml.indexOf(es), es);
		}

		doReadOnly();
		btnCtrl.setBtnStatus_Save();
		// init the old values vars new
		doStoreInitValues();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	public void setValidationOn(boolean validationOn) {
		this.validationOn = validationOn;
	}

	public boolean isValidationOn() {
		return this.validationOn;
	}

	public EselonDAO getEselonDAO() {
		return this.eselonDAO;
	}

	public void setEselonDAO(EselonDAO eselonDAO) {
		this.eselonDAO = eselonDAO;
	}

	public GolonganRuangDAO getGolonganRuangDAO() {
		return golonganRuangDAO;
	}

	public void setGolonganRuangDAO(GolonganRuangDAO golonganRuangDAO) {
		this.golonganRuangDAO = golonganRuangDAO;
	}

	public Eselon getEselon() {
		return eselon;
	}

	public void setEselon(Eselon eselon) {
		this.eselon = eselon;
	}

}
