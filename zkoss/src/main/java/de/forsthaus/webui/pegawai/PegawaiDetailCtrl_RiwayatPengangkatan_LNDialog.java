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
import de.forsthaus.backend.dao.TrKgbPangkatDAO;
import de.forsthaus.backend.model.Gabungan;
import de.forsthaus.backend.model.GolonganRuang;
import de.forsthaus.backend.model.TrKgbPangkat;
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
 * /WEB-INF/pages/sec_right/jabatanDialog.zul file.<br>
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
public class PegawaiDetailCtrl_RiwayatPengangkatan_LNDialog extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(PegawaiDetailCtrl_RiwayatPengangkatan_LNDialog.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window jabatanDialogWindow; // autowired
	protected Listbox cb_golru;
	protected Listbox cb_jenispangkat;
	protected Textbox tb_tmt;
	protected Listbox cb_pejabat;
	protected Textbox tb_nosk;
	protected Textbox tb_tglsk;

	// overhanded vars per params
	private transient Listbox listBoxJabatan; // overhanded
	private TrKgbPangkat pangkat;

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_golru;
	private transient String oldVar_jenispangkat;
	private transient String oldVar_tmt;
	private transient String oldVar_pejabat;
	private transient String oldVar_nosk;
	private transient String oldVar_tglsk;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_JabatanDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient TrKgbPangkatDAO trKgbPangkatDAO;
	private transient GabunganDAO gabunganDAO;
	private transient GolonganRuangDAO golonganRuangDAO;

	/**
	 * default constructor.<br>
	 */
	public PegawaiDetailCtrl_RiwayatPengangkatan_LNDialog() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$jabatanDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, null, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("pangkat")) {
			setTrKgbPangkat((TrKgbPangkat) args.get("pangkat"));
		} else {
			setTrKgbPangkat(null);
		}

		// we get the listBox Object for the users list. So we have access
		// to it and can synchronize the shown data when we do insert, edit or
		// delete users here.
		if (args.containsKey("listbox")) {
			listBoxJabatan = (Listbox) args.get("listbox");
		} else {
			listBoxJabatan = null;
		}

		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getTrKgbPangkat());

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
	public void onClose$jabatanDialogWindow(Event event) throws Exception {
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
			jabatanDialogWindow.onClose();
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

		jabatanDialogWindow.onClose();
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
	 *            jabatan
	 */
	public void doWriteBeanToComponents(TrKgbPangkat pangkat) {
		cb_golru.setSelectedIndex(((ListModelList) cb_golru.getModel()).indexOf(golonganRuangDAO.getGolonganRuangByKode(pangkat.getKodeGolRu()).get(0)));
		cb_jenispangkat.setSelectedIndex(((ListModelList) cb_jenispangkat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISNAIK, pangkat.getKodeKenpa())));
		tb_tmt.setValue(pangkat.getTmt());
		cb_pejabat.setSelectedIndex(((ListModelList) cb_pejabat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, pangkat.getKodePej())));
		tb_nosk.setValue(pangkat.getNumSkPang());
		tb_tglsk.setValue(pangkat.getTglSkPang());
	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(TrKgbPangkat pangkat) {
		pangkat.setKodeGolRu(((GolonganRuang) ((ListModelList) cb_golru.getModel()).getElementAt(cb_golru.getSelectedIndex())).getKodeGolonganRuang());
		pangkat.setKodeKenpa(((Gabungan) ((ListModelList) cb_jenispangkat.getModel()).getElementAt(cb_jenispangkat.getSelectedIndex())).getKode());
		pangkat.setTmt(tb_tmt.getValue());
		pangkat.setKodePej(((Gabungan) ((ListModelList) cb_pejabat.getModel()).getElementAt(cb_pejabat.getSelectedIndex())).getKode());
		pangkat.setNumSkPang(tb_nosk.getValue());
		pangkat.setTglSkPang(tb_tglsk.getValue());
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
	public void doShowDialog(TrKgbPangkat pangkat) throws InterruptedException {
		ListModelList golru = new ListModelList(golonganRuangDAO.getAllGolonganRuang());
		cb_golru.setModel(golru);
		cb_golru.setItemRenderer(new GolonganRuangListModelItemRenderer());
		
		ListModelList jenisnaik = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_JENISNAIK));
		cb_jenispangkat.setModel(jenisnaik);
		cb_jenispangkat.setItemRenderer(new GabunganListModelItemRenderer());
		
		ListModelList pejabat = new ListModelList(gabunganDAO.getGabunganByKodeTabel(ConstantsText.KODETABEL_PEJABAT));
		cb_pejabat.setModel(pejabat);
		cb_pejabat.setItemRenderer(new GabunganListModelItemRenderer());
		
		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (pangkat == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			pangkat = getTrKgbPangkatDAO().getNew();
			setTrKgbPangkat(pangkat);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (pangkat.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(pangkat);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			jabatanDialogWindow.doModal(); // open the dialog in modal
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
		oldVar_golru = ((GolonganRuang) ((ListModelList) cb_golru.getModel()).getElementAt(cb_golru.getSelectedIndex())).getKodeGolonganRuang();
		oldVar_jenispangkat = ((Gabungan) ((ListModelList) cb_jenispangkat.getModel()).getElementAt(cb_jenispangkat.getSelectedIndex())).getKode();
		oldVar_tmt = tb_tmt.getValue();
		oldVar_pejabat = ((Gabungan) ((ListModelList) cb_pejabat.getModel()).getElementAt(cb_pejabat.getSelectedIndex())).getKode();
		oldVar_nosk = tb_nosk.getValue();
		oldVar_tglsk = tb_tglsk.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		cb_golru.setSelectedIndex(((ListModelList) cb_golru.getModel()).indexOf(golonganRuangDAO.getGolonganRuangByKode(oldVar_golru).get(0)));
		cb_jenispangkat.setSelectedIndex(((ListModelList) cb_jenispangkat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_JENISNAIK, oldVar_jenispangkat)));
		tb_tmt.setValue(oldVar_tmt);
		cb_pejabat.setSelectedIndex(((ListModelList) cb_pejabat.getModel()).indexOf(gabunganDAO.getGabunganByKodeTabelAndKode(ConstantsText.KODETABEL_PEJABAT, oldVar_pejabat)));
		tb_nosk.setValue(oldVar_nosk);
		tb_tglsk.setValue(oldVar_tglsk);
	}

	/**
	 * Checks, if data are changed since the last call of <br>
	 * doStoreInitData() . <br>
	 * 
	 * @return true, if data are changed, otherwise false
	 */
	private boolean isDataChanged() {
		boolean changed = false;

		if (oldVar_golru != ((GolonganRuang) ((ListModelList) cb_golru.getModel()).getElementAt(cb_golru.getSelectedIndex())).getKodeGolonganRuang()) {
			changed = true;
		}
		if (oldVar_jenispangkat != ((Gabungan) ((ListModelList) cb_jenispangkat.getModel()).getElementAt(cb_jenispangkat.getSelectedIndex())).getKode()) {
			changed = true;
		}
		if (oldVar_tmt != tb_tmt.getValue()) {
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
	 * Deletes a jabatan object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final TrKgbPangkat gol = getTrKgbPangkat();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + gol.getKodeGolRu();
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
					getTrKgbPangkatDAO().delete(gol);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
				final ListModelList lml = (ListModelList) listBoxJabatan.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
				if (lml.indexOf(gol) == -1) {
				} else {
					lml.remove(lml.indexOf(gol));
				}

				jabatanDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new jabatan object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setTrKgbPangkat(getTrKgbPangkatDAO().getNew());

		doClear(); // clear all commponents
		doEdit(); // edit mode

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		btnCtrl.setBtnStatus_Edit();
		cb_golru.setDisabled(false);
		cb_jenispangkat.setDisabled(false);
		tb_tmt.setReadonly(false);
		cb_pejabat.setDisabled(false);
		tb_nosk.setReadonly(false);
		tb_tglsk.setReadonly(false);

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {
		cb_golru.setDisabled(true);
		cb_jenispangkat.setDisabled(true);
		tb_tmt.setReadonly(true);
		cb_pejabat.setDisabled(true);
		tb_nosk.setReadonly(true);
		tb_tglsk.setReadonly(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		cb_golru.setSelectedIndex(0);
		cb_jenispangkat.setSelectedIndex(0);
		tb_tmt.setValue("");
		cb_pejabat.setSelectedIndex(0);
		tb_nosk.setValue("");
		tb_tglsk.setValue("");
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		final TrKgbPangkat gol = getTrKgbPangkat();
		
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
			getTrKgbPangkatDAO().saveOrUpdate(gol);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listBoxJabatan.getListModel();

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

	public TrKgbPangkatDAO getTrKgbPangkatDAO() {
		return this.trKgbPangkatDAO;
	}

	public void setTrKgbPangkatDAO(TrKgbPangkatDAO trKgbPangkatDAO) {
		this.trKgbPangkatDAO = trKgbPangkatDAO;
	}

	public TrKgbPangkat getTrKgbPangkat() {
		return pangkat;
	}

	public void setTrKgbPangkat(TrKgbPangkat trKgbPangkat) {
		this.pangkat = trKgbPangkat;
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

}
