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
package de.forsthaus.webui.dikum;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.DikumDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.model.Dikum;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.webui.dikum.model.DikumGolonganRuangListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/programStudiDialog.zul file.<br>
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
public class ProgramStudiDialogCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -3989381318982978024L;

	private static final Logger logger = Logger.getLogger(ProgramStudiDialogCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window programStudiDialogWindow; // autowired
	protected Textbox tb_Kode;
	protected Textbox tb_ProgramStudi;
	protected Listbox dd_Gawal;
	protected Listbox dd_Gakhir;
	protected Textbox tb_Rumpun;

	// overhanded vars per params
	private transient Listbox listBoxProgramStudi; // overhanded
	private Dikum dikum;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_Kode;
	private transient String oldVar_ProgramStudi;
	private transient Listitem oldVar_Gawal;
	private transient Listitem oldVar_Gakhir;
	private transient String oldVar_Rumpun;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_ProgramStudiDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnNew; // autowired
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient DikumDAO dikumDAO;
	private transient GolonganRuangDAO golonganRuangDAO;

	/**
	 * default constructor.<br>
	 */
	public ProgramStudiDialogCtrl() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$programStudiDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, btnNew, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("programStudi")) {
			setDikum((Dikum) args.get("programStudi"));
		} else {
			setDikum(null);
		}
		

		dd_Gawal.setModel(new ListModelList(getGolonganRuangDAO().getAllGolonganRuang()));
		dd_Gawal.setItemRenderer(new DikumGolonganRuangListModelItemRenderer());
		dd_Gakhir.setModel(new ListModelList(getGolonganRuangDAO().getAllGolonganRuang()));
		dd_Gakhir.setItemRenderer(new DikumGolonganRuangListModelItemRenderer());

		// if available, select the object
		ListModelList lml = (ListModelList) dd_Gawal.getModel();
		GolonganRuang typ = dikum.getGawal();
		ListModelList lml2 = (ListModelList) dd_Gakhir.getModel();
		GolonganRuang typ2 = dikum.getGakhir();

		if (dikum.isNew()) {
			dd_Gawal.setSelectedIndex(-1);
			dd_Gakhir.setSelectedIndex(-1);
		} else {
			dd_Gawal.setSelectedIndex(lml.indexOf(typ));
			dd_Gakhir.setSelectedIndex(lml2.indexOf(typ2));
		}
		
		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listBoxProgramStudi")) {
			listBoxProgramStudi = (Listbox) args.get("listBoxProgramStudi");
		} else {
			listBoxProgramStudi = null;
		}

		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getDikum());

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
	public void onClose$programStudiDialogWindow(Event event) throws Exception {
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
			programStudiDialogWindow.onClose();
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

		programStudiDialogWindow.onClose();
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
	 *            ProgramStudi
	 */
	public void doWriteBeanToComponents(Dikum programStudi) {
		tb_Kode.setValue(programStudi.getKjur());
		tb_ProgramStudi.setValue(programStudi.getNjur());
		tb_Rumpun.setValue(programStudi.getKrumpun());
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(Dikum programStudi) {
		programStudi.setKjur(tb_Kode.getValue());
		programStudi.setNjur(tb_ProgramStudi.getValue());
		programStudi.setKrumpun(tb_Rumpun.getValue());
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
	public void doShowDialog(Dikum programStudi) throws InterruptedException {

		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (programStudi == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			programStudi = getDikumDAO().getNewDikum();
			setDikum(programStudi);
		} else {
			setDikum(null);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (programStudi.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(programStudi);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			programStudiDialogWindow.doModal(); // open the dialog in modal
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
		tb_ProgramStudi.setMaxlength(100);
		tb_Rumpun.setMaxlength(50);
	}

	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {
		oldVar_Kode = tb_Kode.getValue();
		oldVar_ProgramStudi = tb_ProgramStudi.getValue();
		oldVar_Gawal = dd_Gawal.getSelectedItem();
		oldVar_Gakhir = dd_Gakhir.getSelectedItem();
		oldVar_Rumpun = tb_Rumpun.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		tb_Kode.setValue(oldVar_Kode);
		tb_ProgramStudi.setValue(oldVar_ProgramStudi);
		dd_Gawal.setSelectedItem(oldVar_Gawal);
		dd_Gakhir.setSelectedItem(oldVar_Gakhir);
		tb_Rumpun.setValue(oldVar_Rumpun);
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
		if (oldVar_ProgramStudi != tb_ProgramStudi.getValue()) {
			changed = true;
		}
		if (oldVar_Gawal != dd_Gawal.getSelectedItem()) {
			changed = true;
		}
		if (oldVar_Gakhir != dd_Gakhir.getSelectedItem()) {
			changed = true;
		}
		if (oldVar_Rumpun != tb_Rumpun.getValue()) {
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
		tb_ProgramStudi.setConstraint("NO EMPTY");
		tb_Rumpun.setConstraint("NO EMPTY");
	}

	/**
	 * Disables the Validation by setting empty constraints.
	 */
	private void doRemoveValidation() {

		setValidationOn(false);

		tb_Kode.setConstraint("");
		tb_ProgramStudi.setConstraint("");
		tb_Rumpun.setConstraint("");
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Deletes a programStudi object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final Dikum gol = getDikum();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + gol.getNjur();
		String title = Labels.getLabel("message.Deleting.Record");

		MultiLineMessageBox.doSetTemplate();
		if (MultiLineMessageBox.show(msg, title, MultiLineMessageBox.YES | MultiLineMessageBox.NO, MultiLineMessageBox.QUESTION, true, new EventListener() {
			@Override
			public void onEvent(Event evt) {
				switch (((Integer) evt.getData()).intValue()) {
				case MultiLineMessageBox.YES:
					try {
						delete();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case MultiLineMessageBox.NO:
					break; //
				}
			}

			// delete from database
			private void delete() throws InterruptedException {

				try {
					getDikumDAO().delete(gol);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
				final ListModelList lml = (ListModelList) listBoxProgramStudi.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
				if (lml.indexOf(gol) == -1) {
				} else {
					lml.remove(lml.indexOf(gol));
				}

				programStudiDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new programStudi object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setDikum(getDikumDAO().getNewDikum());

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		tb_Kode.setReadonly(false);
		tb_ProgramStudi.setReadonly(false);
		dd_Gawal.setDisabled(false);
		dd_Gakhir.setDisabled(false);
		tb_Rumpun.setReadonly(false);

		btnCtrl.setBtnStatus_Edit();

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		tb_Kode.setReadonly(true);
		tb_ProgramStudi.setReadonly(true);
		dd_Gawal.setDisabled(true);
		dd_Gakhir.setDisabled(true);
		tb_Rumpun.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		tb_Kode.setValue("");
		tb_ProgramStudi.setValue("");
		dd_Gawal.setSelectedIndex(-1);
		dd_Gakhir.setSelectedIndex(-1);
		tb_Rumpun.setValue("");
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final Dikum gol = getDikum();

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// force validation, if on, than execute by component.getValue()
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (!isValidationOn()) {
			doSetValidation();
		}

		// fill the object with the components data
		doWriteComponentsToBean(gol);

		// save it to database
		try {
			getDikumDAO().saveOrUpdate(gol);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxProgramStudi.getListModel();

		// Check if the object is new or updated
		// -1 means that the obj is not in the list, so it's new.
		if (lml.indexOf(gol) == -1) {
			lml.add(gol);
		} else {
			lml.set(lml.indexOf(gol), gol);
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

	public DikumDAO getDikumDAO() {
		return this.dikumDAO;
	}

	public void setDikumDAO(DikumDAO dikumDAO) {
		this.dikumDAO = dikumDAO;
	}

	public Dikum getDikum() {
		return dikum;
	}

	public void setDikum(Dikum dikum) {
		this.dikum = dikum;
	}

	public GolonganRuangDAO getGolonganRuangDAO() {
		return golonganRuangDAO;
	}

	public void setGolonganRuangDAO(GolonganRuangDAO golonganRuangDAO) {
		this.golonganRuangDAO = golonganRuangDAO;
	}
}

