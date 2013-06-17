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
import java.util.ArrayList;
import java.util.List;
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
import de.forsthaus.backend.dao.TrHukumanDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.TrHukuman;
import de.forsthaus.backend.util.ConstantsText;
import de.forsthaus.webui.pegawai.model.GabunganListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/hukumanDialog.zul file.<br>
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
public class PegawaiDetailCtrl_RiwayatPengangkatan_HukumanDialog extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_HukumanDialog.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window hukumanDialogWindow; // autowired
	protected Listbox cb_tingkat;
	protected Listbox cb_jenis;
	protected Listbox cb_pejabat;
	protected Textbox tb_nosk;
	protected Textbox tb_tglsk;
	protected Textbox tb_tglakhir;
	protected Textbox tb_tglmulai;
	
	private final List<String> TINGKAT_HUKUMAN = new ArrayList<String>() {{add("RINGAN");add("SEDANG");add("BERAT");}};

	// overhanded vars per params
	private transient Listbox listBoxHukuman; // overhanded
	private TrHukuman hukuman;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_tingkat;
	private transient String oldVar_jenis;
	private transient String oldVar_pejabat;
	private transient String oldVar_nosk;
	private transient String oldVar_tglsk;
	private transient String oldVar_tglakhir;
	private transient String oldVar_tglmulai;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_HukumanDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient TrHukumanDAO trHukumanDAO;
	private transient GabunganDAO gabunganDAO;

	/**
	 * default constructor.<br>
	 */
	public PegawaiDetailCtrl_RiwayatPengangkatan_HukumanDialog() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$hukumanDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("hukuman")) {
			setTrHukuman((TrHukuman) args.get("hukuman"));
		} else {
			setTrHukuman(null);
		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listbox")) {
			listBoxHukuman = (Listbox) args.get("listbox");
		} else {
			listBoxHukuman = null;
		}

		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getTrHukuman());

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
	public void onClose$hukumanDialogWindow(Event event) throws Exception {
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
			hukumanDialogWindow.onClose();
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

		hukumanDialogWindow.onClose();
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
	 *            hukuman
	 */
	public void doWriteBeanToComponents(TrHukuman hukuman) {
		cb_tingkat.setSelectedIndex(((ListModelList) cb_tingkat.getModel()).indexOf(hukuman.getJnsHukum()));
		cb_jenis.setSelectedIndex(((ListModelList) cb_jenis.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISHUK, hukuman.getJnsHukum())));
		cb_pejabat.setSelectedIndex(((ListModelList) cb_pejabat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, hukuman.getKodePej())));
		tb_nosk.setValue(hukuman.getNumSkHukum());
		tb_tglsk.setValue(hukuman.getTglSkHukum());
		tb_tglmulai.setValue(hukuman.getTglMulai());
		tb_tglakhir.setValue(hukuman.getTglAkhir());
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(TrHukuman hukuman) {
		hukuman.setTkHukum(((ListModelList) cb_tingkat.getModel()).getElementAt(cb_tingkat.getSelectedIndex()).toString());
		hukuman.setJnsHukum(((Gabungan) ((ListModelList) cb_jenis.getModel()).getElementAt(cb_jenis.getSelectedIndex())).getKode());
		hukuman.setKodePej(((Gabungan) ((ListModelList) cb_pejabat.getModel()).getElementAt(cb_pejabat.getSelectedIndex())).getKode());
		hukuman.setNumSkHukum(tb_nosk.getValue());
		hukuman.setTglSkHukum(tb_tglsk.getValue());
		hukuman.setTglMulai(tb_tglmulai.getValue());
		hukuman.setTglAkhir(tb_tglakhir.getValue());
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
	public void doShowDialog(TrHukuman hukuman) throws InterruptedException {
		ListModelList tingkat = new ListModelList(TINGKAT_HUKUMAN);
		cb_tingkat.setModel(tingkat);
		
		ListModelList jenis = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISHUK));
		cb_jenis.setModel(jenis);
		cb_jenis.setItemRenderer(new GabunganListModelItemRenderer());
		
		ListModelList pejabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_PEJABAT));
		cb_pejabat.setModel(pejabat);
		cb_pejabat.setItemRenderer(new GabunganListModelItemRenderer());
		
		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (hukuman == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			hukuman = getTrHukumanDAO().getNew();
			setTrHukuman(hukuman);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (hukuman.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(hukuman);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			hukumanDialogWindow.doModal(); // open the dialog in modal
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
		tb_nosk.setMaxlength(50);
	}

	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {
		oldVar_tingkat = ((ListModelList) cb_tingkat.getModel()).getElementAt(cb_tingkat.getSelectedIndex()).toString();
		oldVar_jenis = ((Gabungan) ((ListModelList) cb_jenis.getModel()).getElementAt(cb_jenis.getSelectedIndex())).getKode();
		oldVar_pejabat = ((Gabungan) ((ListModelList) cb_pejabat.getModel()).getElementAt(cb_pejabat.getSelectedIndex())).getKode();
		oldVar_nosk = tb_nosk.getValue();
		oldVar_tglsk = tb_tglsk.getValue();
		oldVar_tglmulai = tb_tglmulai.getValue();
		oldVar_tglakhir = tb_tglakhir.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		cb_tingkat.setSelectedIndex(((ListModelList) cb_tingkat.getModel()).indexOf(oldVar_tingkat));
		cb_tingkat.setSelectedIndex(((ListModelList) cb_tingkat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISHUK, oldVar_jenis)));
		cb_pejabat.setSelectedIndex(((ListModelList) cb_pejabat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, oldVar_pejabat)));
		tb_nosk.setValue(oldVar_nosk);
		tb_tglsk.setValue(oldVar_tglsk);
		tb_tglmulai.setValue(oldVar_tglmulai);
		tb_tglakhir.setValue(oldVar_tglakhir);
	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

		if (oldVar_tingkat != ((ListModelList) cb_tingkat.getModel()).getElementAt(cb_tingkat.getSelectedIndex()).toString()) {
			changed = true;
		}
		if (oldVar_jenis != ((Gabungan) ((ListModelList) cb_jenis.getModel()).getElementAt(cb_jenis.getSelectedIndex())).getKode()) {
			changed = true;
		}
		if (oldVar_pejabat != ((Gabungan) ((ListModelList) cb_pejabat.getModel()).getElementAt(cb_pejabat.getSelectedIndex())).getKode()) {
			changed = true;
		}
		if (oldVar_nosk != tb_nosk.getValue()) {
			changed = true;
		}
		if (oldVar_tglsk != tb_tglsk.getValue()) {
			changed = true;
		}
		if (oldVar_tglmulai != tb_tglmulai.getValue()) {
			changed = true;
		}
		if (oldVar_tglakhir != tb_tglakhir.getValue()) {
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
	 * Deletes a hukuman object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final TrHukuman huk = getTrHukuman();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + huk.getNumSkHukum();
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
					getTrHukumanDAO().delete(huk);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
				final ListModelList lml = (ListModelList) listBoxHukuman.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
				if (lml.indexOf(huk) == -1) {
				} else {
					lml.remove(lml.indexOf(huk));
				}

				hukumanDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new hukuman object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setTrHukuman(getTrHukumanDAO().getNew());

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		btnCtrl.setBtnStatus_Edit();
		cb_tingkat.setDisabled(false);
		cb_jenis.setDisabled(false);
		cb_pejabat.setDisabled(false);
		tb_nosk.setReadonly(false);
		tb_tglsk.setReadonly(false);
		tb_tglmulai.setReadonly(false);
		tb_tglakhir.setReadonly(false);

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		cb_tingkat.setDisabled(true);
		cb_jenis.setDisabled(true);
		cb_pejabat.setDisabled(true);
		tb_nosk.setReadonly(true);
		tb_tglsk.setReadonly(true);
		tb_tglmulai.setReadonly(true);
		tb_tglakhir.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		cb_tingkat.setSelectedIndex(0);
		cb_jenis.setSelectedIndex(0);
		cb_pejabat.setSelectedIndex(0);
		tb_nosk.setValue("");
		tb_tglsk.setValue("");
		tb_tglmulai.setValue("");
		tb_tglakhir.setValue("");
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final TrHukuman gol = getTrHukuman();
		
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
			getTrHukumanDAO().saveOrUpdate(gol);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxHukuman.getListModel();

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

	public TrHukumanDAO getTrHukumanDAO() {
		return this.trHukumanDAO;
	}

	public void setTrHukumanDAO(TrHukumanDAO trHukumanDAO) {
		this.trHukumanDAO = trHukumanDAO;
	}

	public TrHukuman getTrHukuman() {
		return hukuman;
	}

	public void setTrHukuman(TrHukuman trHukuman) {
		this.hukuman = trHukuman;
	}

	public GabunganDAO getGabunganDAO() {
		return gabunganDAO;
	}

	public void setGabunganDAO(GabunganDAO gabunganDAO) {
		this.gabunganDAO = gabunganDAO;
	}

}
