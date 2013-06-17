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
package de.forsthaus.webui.pegawai;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.dao.TrOrganisasiDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.TrOrganisasi;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.pegawai.model.GabunganListModelItemRenderer;
import de.forsthaus.webui.pegawai.model.GolonganRuangListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/organisasiDialog.zul file.<br>
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
public class PegawaiDetailCtrl_RiwayatPengangkatan_OrganisasiDialog extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_OrganisasiDialog.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window organisasiDialogWindow; // autowired
	protected Listbox cb_jenisorg;
	protected Textbox tb_nama;
	protected Textbox tb_jabatan;
	protected Textbox tb_tglmulai;
	protected Textbox tb_tglakhir;
	protected Textbox tb_pimpinan;
	protected Textbox tb_tempat;

	// overhanded vars per params
	private transient Listbox listBoxOrganisasi; // overhanded
	private TrOrganisasi organisasi;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_jenisorg;
	private transient String oldVar_nama;
	private transient String oldVar_jabatan;
	private transient String oldVar_tglmulai;
	private transient String oldVar_tglakhir;
	private transient String oldVar_pimpinan;
	private transient String oldVar_tempat;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_OrganisasiDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient TrOrganisasiDAO trOrganisasiDAO;
	private transient GabunganDAO gabunganDAO;

	/**
	 * default constructor.<br>
	 */
	public PegawaiDetailCtrl_RiwayatPengangkatan_OrganisasiDialog() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$organisasiDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("organisasi")) {
			setTrOrganisasi((TrOrganisasi) args.get("organisasi"));
		} else {
			setTrOrganisasi(null);
		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listbox")) {
			listBoxOrganisasi = (Listbox) args.get("listbox");
		} else {
			listBoxOrganisasi = null;
		}

		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getTrOrganisasi());

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
	public void onClose$organisasiDialogWindow(Event event) throws Exception {
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
			organisasiDialogWindow.onClose();
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

		organisasiDialogWindow.onClose();
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
	 *            organisasi
	 */
	public void doWriteBeanToComponents(TrOrganisasi organisasi) {
		cb_jenisorg.setSelectedIndex(((ListModelList) cb_jenisorg.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISORG, organisasi.getKodeJnsOrg())));
		tb_nama.setValue(organisasi.getNumOrganisasi());
		tb_jabatan.setValue(organisasi.getJabOrg());
		tb_tglmulai.setValue(organisasi.getTglMulai());
		tb_tglakhir.setValue(organisasi.getTglAkhir());
		tb_pimpinan.setValue(organisasi.getPimpinan());
		tb_tempat.setValue(organisasi.getTempat());
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(TrOrganisasi organisasi) {
		organisasi.setKodeJnsOrg(((Gabungan) ((ListModelList) cb_jenisorg.getModel()).getElementAt(cb_jenisorg.getSelectedIndex())).getKode());
		organisasi.setNumOrganisasi(tb_nama.getValue());
		organisasi.setJabOrg(tb_jabatan.getValue());
		organisasi.setTglMulai(tb_tglmulai.getValue());
		organisasi.setTglAkhir(tb_tglakhir.getValue());
		organisasi.setPimpinan(tb_pimpinan.getValue());
		organisasi.setTempat(tb_tempat.getValue());
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
	public void doShowDialog(TrOrganisasi organisasi) throws InterruptedException {
		ListModelList jenisorg = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISORG));
		cb_jenisorg.setModel(jenisorg);
		cb_jenisorg.setItemRenderer(new GabunganListModelItemRenderer());
		
		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (organisasi == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			organisasi = getTrOrganisasiDAO().getNew();
			setTrOrganisasi(organisasi);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (organisasi.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(organisasi);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			organisasiDialogWindow.doModal(); // open the dialog in modal
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
	}

	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {
		oldVar_jenisorg = ((Gabungan) ((ListModelList) cb_jenisorg.getModel()).getElementAt(cb_jenisorg.getSelectedIndex())).getKode();
		oldVar_nama = tb_nama.getValue();
		oldVar_jabatan = tb_jabatan.getValue();
		oldVar_tglmulai = tb_tglmulai.getValue();
		oldVar_tglakhir = tb_tglakhir.getValue();
		oldVar_pimpinan = tb_pimpinan.getValue();
		oldVar_tempat = tb_tempat.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		cb_jenisorg.setSelectedIndex(((ListModelList) cb_jenisorg.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISORG, oldVar_jenisorg)));
		tb_nama.setValue(oldVar_nama);
		tb_jabatan.setValue(oldVar_jabatan);
		tb_tglmulai.setValue(oldVar_tglmulai);
		tb_tglakhir.setValue(oldVar_tglakhir);
		tb_pimpinan.setValue(oldVar_pimpinan);
		tb_tempat.setValue(oldVar_tempat);
	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

		if (oldVar_jenisorg != ((Gabungan) ((ListModelList) cb_jenisorg.getModel()).getElementAt(cb_jenisorg.getSelectedIndex())).getKode()) {
			changed = true;
		}
		if (oldVar_nama != tb_nama.getValue()) {
			changed = true;
		}
		if (oldVar_jabatan != tb_jabatan.getValue()) {
			changed = true;
		}
		if (oldVar_tglmulai != tb_tglmulai.getValue()) {
			changed = true;
		}
		if (oldVar_tglakhir != tb_tglakhir.getValue()) {
			changed = true;
		}
		if (oldVar_pimpinan != tb_pimpinan.getValue()) {
			changed = true;
		}
		if (oldVar_tempat != tb_tempat.getValue()) {
			changed = true;
		}

		return changed;
	}

	/**
	 * Sets the Validation by setting the accordingly constraints to the fields.
	 */
	private void doSetValidation() {
	}

	/**
	 * Disables the Validation by setting empty constraints.
	 */
	private void doRemoveValidation() {
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Deletes a organisasi object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final TrOrganisasi org = getTrOrganisasi();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + org.getJabOrg();
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
					getTrOrganisasiDAO().delete(org);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
				final ListModelList lml = (ListModelList) listBoxOrganisasi.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
				if (lml.indexOf(org) == -1) {
				} else {
					lml.remove(lml.indexOf(org));
				}

				organisasiDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new organisasi object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setTrOrganisasi(getTrOrganisasiDAO().getNew());

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		btnCtrl.setBtnStatus_Edit();
		cb_jenisorg.setDisabled(false);
		tb_nama.setReadonly(false);
		tb_jabatan.setReadonly(false);
		tb_tglmulai.setReadonly(false);
		tb_tglakhir.setReadonly(false);
		tb_pimpinan.setReadonly(false);
		tb_tempat.setReadonly(false);

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		cb_jenisorg.setDisabled(true);
		tb_nama.setReadonly(true);
		tb_jabatan.setReadonly(true);
		tb_tglmulai.setReadonly(true);
		tb_tglakhir.setReadonly(true);
		tb_pimpinan.setReadonly(true);
		tb_tempat.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		cb_jenisorg.setSelectedIndex(0);
		tb_nama.setValue("");
		tb_jabatan.setValue("");
		tb_tglmulai.setValue("");
		tb_tglakhir.setValue("");
		tb_pimpinan.setValue("");
		tb_tempat.setValue("");
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final TrOrganisasi gol = getTrOrganisasi();
		
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
			getTrOrganisasiDAO().saveOrUpdate(gol);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxOrganisasi.getListModel();

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

	public TrOrganisasiDAO getTrOrganisasiDAO() {
		return this.trOrganisasiDAO;
	}

	public void setTrOrganisasiDAO(TrOrganisasiDAO trOrganisasiDAO) {
		this.trOrganisasiDAO = trOrganisasiDAO;
	}

	public TrOrganisasi getTrOrganisasi() {
		return organisasi;
	}

	public void setTrOrganisasi(TrOrganisasi trOrganisasi) {
		this.organisasi = trOrganisasi;
	}

	public GabunganDAO getGabunganDAO() {
		return gabunganDAO;
	}

	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.gabunganDAO = gabunganDAO;
	}

}
