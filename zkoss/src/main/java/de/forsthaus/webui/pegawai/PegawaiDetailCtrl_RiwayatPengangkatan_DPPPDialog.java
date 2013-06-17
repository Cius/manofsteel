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
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.GabunganDAO;
import de.forsthaus.backend.dao.GolonganRuangDAO;
import de.forsthaus.backend.dao.TrDPPPDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.TrDPPP;
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
 * /WEB-INF/pages/sec_right/dpppDialog.zul file.<br>
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
public class PegawaiDetailCtrl_RiwayatPengangkatan_DPPPDialog extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_DPPPDialog.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window dpppDialogWindow; // autowired
	protected Textbox tb_thnilai;
	protected Textbox tb_penilai;
	protected Textbox tb_atasan;
	protected Textbox tb_kesetiaan;
	protected Textbox tb_prestasi;
	protected Textbox tb_tanggungjawab;
	protected Textbox tb_ketaatan;
	protected Textbox tb_kejujuran;
	protected Textbox tb_kerjasama;
	protected Textbox tb_prakarsa;
	protected Textbox tb_kepemimpinan;
	protected Textbox tb_total;
	protected Textbox tb_rata;
	protected Textbox tb_kesetiaannilai;
	protected Textbox tb_prestasinilai;
	protected Textbox tb_tanggungjawabnilai;
	protected Textbox tb_ketaatannilai;
	protected Textbox tb_kejujurannilai;
	protected Textbox tb_kerjasamanilai;
	protected Textbox tb_prakarsanilai;
	protected Textbox tb_kepemimpinannilai;
	protected Textbox tb_ratanilai;

	// overhanded vars per params
	private transient Listbox listBoxDPPP; // overhanded
	private TrDPPP dppp;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_thnilai;
	private transient String oldVar_penilai;
	private transient String oldVar_atasan;
	private transient String oldVar_kesetiaan;
	private transient String oldVar_prestasi;
	private transient String oldVar_tanggungjawab;
	private transient String oldVar_ketaatan;
	private transient String oldVar_kejujuran;
	private transient String oldVar_kerjasama;
	private transient String oldVar_prakarsa;
	private transient String oldVar_kepemimpinan;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_DPPPDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient TrDPPPDAO trDPPPDAO;

	/**
	 * default constructor.<br>
	 */
	public PegawaiDetailCtrl_RiwayatPengangkatan_DPPPDialog() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$dpppDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("dppp")) {
			setTrDPPP((TrDPPP) args.get("dppp"));
		} else {
			setTrDPPP(null);
		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listbox")) {
			listBoxDPPP = (Listbox) args.get("listbox");
		} else {
			listBoxDPPP = null;
		}

		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getTrDPPP());

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
	public void onClose$dpppDialogWindow(Event event) throws Exception {
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
			dpppDialogWindow.onClose();
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

		dpppDialogWindow.onClose();
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
	 *            dppp
	 */
	public void doWriteBeanToComponents(TrDPPP dppp) {
		tb_thnilai.setValue(dppp.getThNilai());
		tb_penilai.setValue(dppp.getPenilai());
		tb_atasan.setValue(dppp.getPenilai());
		tb_kesetiaan.setValue(dppp.getPenilai());
		tb_prestasi.setValue(dppp.getPenilai());
		tb_tanggungjawab.setValue(dppp.getPenilai());
		tb_ketaatan.setValue(dppp.getPenilai());
		tb_kejujuran.setValue(dppp.getPenilai());
		tb_kerjasama.setValue(dppp.getPenilai());
		tb_prakarsa.setValue(dppp.getPenilai());
		tb_kepemimpinan.setValue(dppp.getPenilai());
		tb_total.setValue(dppp.getPenilai());
		tb_rata.setValue(dppp.getPenilai());
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(TrDPPP dppp) {
		dppp.setThNilai(tb_thnilai.getValue());
		dppp.setPenilai(tb_penilai.getValue());
		dppp.setAtasan(tb_atasan.getValue());
		dppp.setNumSetia(Integer.valueOf(tb_kesetiaan.getValue()));
		dppp.setNumPres(Integer.valueOf(tb_prestasi.getValue()));
		dppp.setNumTangJawab(Integer.valueOf(tb_tanggungjawab.getValue()));
		dppp.setNumTaat(Integer.valueOf(tb_ketaatan.getValue()));
		dppp.setNumJujur(Integer.valueOf(tb_kejujuran.getValue()));
		dppp.setNumKerja(Integer.valueOf(tb_kerjasama.getValue()));
		dppp.setNumPkarsa(Integer.valueOf(tb_prakarsa.getValue()));
		dppp.setNumPimpin(Integer.valueOf(tb_kepemimpinan.getValue()));
		dppp.setNumTotal(Integer.valueOf(tb_total.getValue()));
		dppp.setNumRata(Integer.valueOf(tb_rata.getValue()));
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
	public void doShowDialog(TrDPPP dppp) throws InterruptedException {
		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (dppp == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			dppp = getTrDPPPDAO().getNew();
			setTrDPPP(dppp);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (dppp.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(dppp);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			dpppDialogWindow.doModal(); // open the dialog in modal
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
		oldVar_thnilai = tb_thnilai.getValue();
		oldVar_penilai = tb_penilai.getValue();
		oldVar_atasan = tb_atasan.getValue();
		oldVar_kesetiaan = tb_kesetiaan.getValue();
		oldVar_prestasi = tb_prestasi.getValue();
		oldVar_tanggungjawab = tb_tanggungjawab.getValue();
		oldVar_ketaatan = tb_ketaatan.getValue();
		oldVar_kejujuran = tb_kejujuran.getValue();
		oldVar_kerjasama = tb_kerjasama.getValue();
		oldVar_prakarsa = tb_prakarsa.getValue();
		oldVar_kepemimpinan = tb_kepemimpinan.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		dppp.setThNilai(oldVar_thnilai);
		dppp.setPenilai(oldVar_penilai);
		dppp.setAtasan(oldVar_atasan);
		dppp.setNumSetia(Integer.valueOf(oldVar_kesetiaan));
		dppp.setNumPres(Integer.valueOf(oldVar_prestasi));
		dppp.setNumTangJawab(Integer.valueOf(oldVar_tanggungjawab));
		dppp.setNumTaat(Integer.valueOf(oldVar_ketaatan));
		dppp.setNumJujur(Integer.valueOf(oldVar_kejujuran));
		dppp.setNumKerja(Integer.valueOf(oldVar_kerjasama));
		dppp.setNumPkarsa(Integer.valueOf(oldVar_prakarsa));
		dppp.setNumPimpin(Integer.valueOf(oldVar_kepemimpinan));
		
		dppp.setNumTotal(getTotal());
		dppp.setNumRata(getRata());
	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

		if (oldVar_thnilai != tb_thnilai.getValue()) {
			changed = true;
		}
		if (oldVar_penilai != tb_penilai.getValue()) {
			changed = true;
		}
		if (oldVar_atasan != tb_atasan.getValue()) {
			changed = true;
		}
		if (oldVar_kesetiaan != tb_kesetiaan.getValue()) {
			changed = true;
		}
		if (oldVar_prestasi != tb_prestasi.getValue()) {
			changed = true;
		}
		if (oldVar_tanggungjawab != tb_tanggungjawab.getValue()) {
			changed = true;
		}
		if (oldVar_ketaatan != tb_ketaatan.getValue()) {
			changed = true;
		}
		if (oldVar_kejujuran != tb_kejujuran.getValue()) {
			changed = true;
		}
		if (oldVar_kerjasama != tb_kerjasama.getValue()) {
			changed = true;
		}
		if (oldVar_prakarsa != tb_prakarsa.getValue()) {
			changed = true;
		}
		if (oldVar_kepemimpinan != tb_kepemimpinan.getValue()) {
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
	 * Deletes a dppp object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final TrDPPP dppp = getTrDPPP();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + dppp.getThNilai();
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
					getTrDPPPDAO().delete(dppp);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
				final ListModelList lml = (ListModelList) listBoxDPPP.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
				if (lml.indexOf(dppp) == -1) {
				} else {
					lml.remove(lml.indexOf(dppp));
				}

				dpppDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new dppp object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setTrDPPP(getTrDPPPDAO().getNew());

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		btnCtrl.setBtnStatus_Edit();
		tb_thnilai.setReadonly(false);
		tb_penilai.setReadonly(false);
		tb_atasan.setReadonly(false);
		tb_kesetiaan.setReadonly(false);
		tb_prestasi.setReadonly(false);
		tb_tanggungjawab.setReadonly(false);
		tb_ketaatan.setReadonly(false);
		tb_kejujuran.setReadonly(false);
		tb_kerjasama.setReadonly(false);
		tb_prakarsa.setReadonly(false);
		tb_kepemimpinan.setReadonly(false);

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		tb_thnilai.setReadonly(true);
		tb_penilai.setReadonly(true);
		tb_atasan.setReadonly(true);
		tb_kesetiaan.setReadonly(true);
		tb_prestasi.setReadonly(true);
		tb_tanggungjawab.setReadonly(true);
		tb_ketaatan.setReadonly(true);
		tb_kejujuran.setReadonly(true);
		tb_kerjasama.setReadonly(true);
		tb_prakarsa.setReadonly(true);
		tb_kepemimpinan.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		tb_thnilai.setValue("");
		tb_penilai.setValue("");
		tb_atasan.setValue("");
		tb_kesetiaan.setValue("");
		tb_prestasi.setValue("");
		tb_tanggungjawab.setValue("");
		tb_ketaatan.setValue("");
		tb_kejujuran.setValue("");
		tb_kerjasama.setValue("");
		tb_prakarsa.setValue("");
		tb_kepemimpinan.setValue("");
		tb_total.setValue("");
		tb_rata.setValue("");
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final TrDPPP gol = getTrDPPP();
		
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
			getTrDPPPDAO().saveOrUpdate(gol);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxDPPP.getListModel();

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
	
	private int getTotal() {
		return dppp.getNumSetia() + dppp.getNumPres() + dppp.getNumTangJawab() + dppp.getNumTaat() + dppp.getNumJujur() + dppp.getNumKerja()
				+ dppp.getNumPkarsa() + dppp.getNumPimpin();
	}
	
	private int getRata() {
		return dppp.getNumTotal()/8;
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

	public TrDPPPDAO getTrDPPPDAO() {
		return this.trDPPPDAO;
	}

	public void setTrDPPPDAO(TrDPPPDAO trDPPPDAO) {
		this.trDPPPDAO = trDPPPDAO;
	}

	public TrDPPP getTrDPPP() {
		return dppp;
	}

	public void setTrDPPP(TrDPPP trDPPP) {
		this.dppp = trDPPP;
	}
}
