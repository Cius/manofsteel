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
package de.forsthaus.webui.wilayah;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.forsthaus.backend.dao.WilayahDAO;
import de.forsthaus.backend.model.SecTyp;
import de.forsthaus.backend.model.Wilayah;
import de.forsthaus.webui.security.right.model.SecRightSecTypListModelItemRenderer;
import de.forsthaus.webui.util.ButtonStatusCtrl;
import de.forsthaus.webui.util.GFCBaseCtrl;
import de.forsthaus.webui.util.MultiLineMessageBox;
import de.forsthaus.webui.util.ZksampleMessageUtils;
import de.forsthaus.webui.wilayah.model.WilayahListModelItemRenderer;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the
 * /WEB-INF/pages/sec_right/propinsiDialog.zul file.<br>
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
public class PropinsiDialogCtrl extends GFCBaseCtrl implements Serializable {

	private static final long serialVersionUID = -546886879998950467L;
	private static final Logger logger = Logger.getLogger(PropinsiDialogCtrl.class);

	/*
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * All the components that are defined here and have a corresponding
	 * component with the same 'id' in the zul-file are getting autowired by our
	 * 'extends GFCBaseCtrl' GenericForwardComposer.
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	protected Window propinsiDialogWindow; // autowired
	protected Label label_Kode; //autowired
	protected Textbox tb_Kode; // autowired
	protected Textbox tb_Nama; // autowired
	protected Textbox tb_Ibukota; // autowired

	// overhanded vars per params
	private transient Listbox listbox; // overhanded
	private transient Wilayah wilayah; // overhanded

	// old value vars for edit mode. that we can check if something
	// on the values are edited since the last init.
	private transient String oldVar_Kode;
	private transient String oldVar_Nama;
	private transient String oldVar_Ibukota;
//	private transient Listitem oldVar_rigType;

	private transient boolean validationOn;

	// Button controller for the CRUD buttons
	private transient final String btnCtroller_ClassPrefix = "button_PropinsiDialog_";
	private transient ButtonStatusCtrl btnCtrl;
	protected Button btnNew; // autowired
	protected Button btnEdit; // autowired
	protected Button btnDelete; // autowired
	protected Button btnSave; // autowired
	protected Button btnCancel; // autowired
	protected Button btnClose; // autowired

	// ServiceDAOs / Domain Classes
	private transient WilayahDAO wilayahDAO;
	
	private int selectedTab = 0;
	private Listbox lb_Propinsi;
	private Listbox lb_Kota;
	private Listbox lb_Kecamatan;
	private Row row_Propinsi;
	private Row row_Kota;
	private Row row_Kecamatan;
	

	/**
	 * default constructor.<br>
	 */
	public PropinsiDialogCtrl() {
		super();
	}

	/**
	 * Before binding the data and calling the dialog window we check, if the
	 * zul-file is called with a parameter for a selected user object in a Map.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$propinsiDialogWindow(Event event) throws Exception {
		// create the Button Controller. Disable not used buttons during working
		btnCtrl = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, true, btnNew, btnEdit, btnDelete, btnSave, btnCancel, btnClose);

		// get the params map that are overhanded by creation.
		Map<String, Object> args = getCreationArgsMap(event);

		if (args.containsKey("wilayah")) {
			wilayah = (Wilayah) args.get("wilayah");
			setWilayah(wilayah);
		} else {
			setWilayah(null);
		}
		
		if (args.containsKey("tabSelected"))
			selectedTab = (Integer)args.get("tabSelected");

		if (args.containsKey("listbox"))
			listbox= (Listbox)args.get("listbox");
		
		row_Kecamatan.setVisible(false);
		row_Kota.setVisible(false);
		row_Propinsi.setVisible(false);
		
		// if available, select the object
		//61 00 00 0000
		switch (selectedTab) {
		case 3:
			row_Kecamatan.setVisible(true);
			lb_Kecamatan.setModel(new ListModelList(getWilayahDAO().getWilayahByType("3")));
			lb_Kecamatan.setItemRenderer(new WilayahListModelItemRenderer());
			if (!wilayah.isNew()) {
				ListModelList lml = (ListModelList) lb_Kecamatan.getModel();
				lb_Kecamatan.setSelectedIndex(lml.indexOf(getWilayahDAO().getWilayahByKode(replaceWithZero(wilayah.getKodeWilayah(), 6))));
			}
		case 2:
			row_Kota.setVisible(true);
			lb_Kota.setModel(new ListModelList(getWilayahDAO().getWilayahByType("2")));
			lb_Kota.setItemRenderer(new WilayahListModelItemRenderer());
			if (!wilayah.isNew()) {
				ListModelList lml = (ListModelList) lb_Kota.getModel();
				lb_Kota.setSelectedIndex(lml.indexOf(getWilayahDAO().getWilayahByKode(replaceWithZero(wilayah.getKodeWilayah(), 4))));
			}
		case 1:
			row_Propinsi.setVisible(true);
			lb_Propinsi.setModel(new ListModelList(getWilayahDAO().getWilayahByType("1")));
			lb_Propinsi.setItemRenderer(new WilayahListModelItemRenderer());
			if (!wilayah.isNew()) {
				ListModelList lml = (ListModelList) lb_Propinsi.getModel();
				lb_Propinsi.setSelectedIndex(lml.indexOf(getWilayahDAO().getWilayahByKode(replaceWithZero(wilayah.getKodeWilayah(), 2))));
			}
		}
		
		// set Field Properties
		doSetFieldProperties();

		doShowDialog(getWilayah());

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
	public void onClose$propinsiDialogWindow(Event event) throws Exception {
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

	/**
	 * when the "new" button is clicked. <br>
	 * 
	 * @param event
	 */
	public void onClick$btnNew(Event event) {
		// logger.debug(event.toString());

		doNew();
	}

	/**
	 * when the "delete" button is clicked. <br>
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
	public void onClick$btnDelete(Event event) throws InterruptedException {
		// logger.debug(event.toString());

		doDelete();
	}

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
			propinsiDialogWindow.onClose();
		}
	}
	
	public void onSelect$lb_Propinsi(Event event) throws InterruptedException {
		Listitem listItem = lb_Propinsi.getSelectedItem();
		ListModelList model = (ListModelList)lb_Propinsi.getModel();
		Wilayah wil = (Wilayah)model.get(listItem.getIndex());
		tb_Kode.setValue(wil.getKodeWilayah().substring(0, 2));
		if (row_Kota.isVisible()) {
			lb_Kota.setModel(new ListModelList(getWilayahDAO().getKotaByPropinsi(wil))); 
		}
	}
	
	public void onSelect$lb_Kota(Event event) throws InterruptedException {
		Listitem listItem = lb_Kota.getSelectedItem();
		ListModelList model = (ListModelList)lb_Kota.getModel();
		Wilayah wil = (Wilayah)model.get(listItem.getIndex());
		tb_Kode.setValue(wil.getKodeWilayah().substring(0, 4));
		if (row_Kecamatan.isVisible()) {
			lb_Kecamatan.setModel(new ListModelList(getWilayahDAO().getKecamatanByKota(wil))); 
		}
	}
	
	public void onSelect$lb_Kecamatan(Event event) throws InterruptedException {
		Listitem listItem = lb_Kecamatan.getSelectedItem();
		ListModelList model = (ListModelList)lb_Kecamatan.getModel();
		Wilayah wil = (Wilayah)model.get(listItem.getIndex());
		tb_Kode.setValue(wil.getKodeWilayah().substring(0, 6));
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

		propinsiDialogWindow.onClose();
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
	 * @param wilayah
	 *            Propinsi
	 */
	public void doWriteBeanToComponents(Wilayah wilayah) {

		tb_Kode.setValue(wilayah.getKodeWilayah());
		tb_Nama.setValue(wilayah.getNamaWilayah());
		tb_Ibukota.setValue(wilayah.getIbukota());

	}

	/**
	 * Writes the components values to the bean.<br>
	 * 
	 * @param aRight
	 */
	public void doWriteComponentsToBean(Wilayah wilayah) {

		wilayah.setKodeWilayah(tb_Kode.getValue());
		wilayah.setNamaWilayah(tb_Nama.getValue());
		wilayah.setIbukota(tb_Ibukota.getValue());
	}

	/**
	 * Opens the Dialog window modal.
	 * 
	 * It checks if the dialog opens with a new or existing object, if so they
	 * set the readOnly mode accordingly.
	 * 
	 * @param wilayah
	 * @throws InterruptedException
	 */
	public void doShowDialog(Wilayah wilayah) throws InterruptedException {

		// if aRight == null then we opened the Dialog without
		// args for a given entity, so we get a new Obj().
		if (wilayah == null) {
			/** !!! DO NOT BREAK THE TIERS !!! */
			// We don't create a new DomainObject() in the frontend.
			// We GET it from the backend.
			wilayah = getWilayahDAO().getNewWilayah();
			setWilayah(wilayah);
		} else {
			setWilayah(wilayah);
		}

		// set Readonly mode accordingly if the object is new or not.
		if (wilayah.isNew()) {
			doEdit();
			btnCtrl.setInitNew();
		} else {
			btnCtrl.setInitEdit();
			doReadOnly();
		}

		try {
			// fill the components with the data
			doWriteBeanToComponents(wilayah);

			// stores the inital data for comparing if they are changed
			// during user action.
			doStoreInitValues();

			propinsiDialogWindow.doModal(); // open the dialog in modal
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
		tb_Nama.setMaxlength(50);
		tb_Ibukota.setMaxlength(50);
	}

	/**
	 * Stores the init values in mem vars. <br>
	 */
	private void doStoreInitValues() {
		oldVar_Kode = tb_Kode.getValue();
		oldVar_Nama = tb_Nama.getValue();
		oldVar_Ibukota = tb_Ibukota.getValue();
	}

	/**
	 * Resets the init values from mem vars. <br>
	 */
	private void doResetInitValues() {
		tb_Kode.setValue(oldVar_Kode);
		tb_Nama.setValue(oldVar_Nama);
		tb_Ibukota.setValue(oldVar_Ibukota);
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
		if (oldVar_Nama != tb_Nama.getValue()) {
			changed = true;
		}
		if (oldVar_Ibukota != tb_Ibukota.getValue()) {
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
		tb_Nama.setConstraint("NO EMPTY");
	}

	/**
	 * Disables the Validation by setting empty constraints.
	 */
	private void doRemoveValidation() {

		setValidationOn(false);

		tb_Kode.setConstraint("");
		// TODO helper textbox for selectedItem ?????
		// rigType.getSelectedItem()) {
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Deletes a propinsi object from database.<br>
	 * 
	 * @throws InterruptedException
	 */
	private void doDelete() throws InterruptedException {

		final Wilayah wil = getWilayah();

		// Show a confirm box
		String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + wil.getNamaWilayah();
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
					getWilayahDAO().delete(wil);
				} catch (DataAccessException e) {
					ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
				}

				// now synchronize the listBox
//				final ListModelList lml = (ListModelList) listBoxPropinsis.getListModel();

				// Check if the object is new or updated
				// -1 means that the obj is not in the list, so it's
				// new..
//				if (lml.indexOf(wil) == -1) {
//				} else {
//					lml.remove(lml.indexOf(wil));
//				}

				propinsiDialogWindow.onClose(); // close
			}
		}

		) == MultiLineMessageBox.YES) {
		}

	}

	/**
	 * Create a new propinsi object. <br>
	 */
	private void doNew() {

		// remember the old vars
		doStoreInitValues();

		/** !!! DO NOT BREAK THE TIERS !!! */
		// We don't create a new DomainObject() in the frontend.
		// We GET it from the backend.
		setWilayah(getWilayahDAO().getNewWilayah());

		doClear(); // clear all commponents
		doEdit(); // edit mode

//		rigType.setSelectedIndex(-1);

		btnCtrl.setBtnStatus_New();

	}

	/**
	 * Set the components for edit mode. <br>
	 */
	private void doEdit() {

		tb_Kode.setReadonly(false);
		tb_Nama.setReadonly(false);
		tb_Ibukota.setReadonly(false);

		btnCtrl.setBtnStatus_Edit();

		// remember the old vars
		doStoreInitValues();
	}

	/**
	 * Set the components to ReadOnly. <br>
	 */
	public void doReadOnly() {

		tb_Kode.setReadonly(true);
		tb_Nama.setReadonly(true);
		tb_Ibukota.setReadonly(true);
		lb_Propinsi.setDisabled(true);
		lb_Kota.setDisabled(true);
		lb_Kecamatan.setDisabled(true);
	}

	/**
	 * Clears the components values. <br>
	 */
	public void doClear() {

		// temporarely disable the validation to allow the field's clearing
		doRemoveValidation();

		tb_Kode.setValue("");
		tb_Nama.setValue("");
		tb_Ibukota.setValue("");
		// unselect the last customers branch
//		rigType.setSelectedIndex(-1);
	}

	/**
	 * Saves the components to table. <br>
	 * 
	 * @throws InterruptedException
	 */
	public void doSave() throws InterruptedException {

		Wilayah wil = getWilayah();
		
		// save tipe wilayah 
		wil.setTipeWilayah(String.valueOf(selectedTab + 1));

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// force validation, if on, than execute by component.getValue()
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (!isValidationOn()) {
			doSetValidation();
		}

		// fill the object with the components data
		doWriteComponentsToBean(wil);

		// save it to database
		try {
			getWilayahDAO().saveOrUpdate(wil);
		} catch (DataAccessException e) {
			ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

			// Reset to init values
			doResetInitValues();

			doReadOnly();
			btnCtrl.setBtnStatus_Save();
			return;
		}

		// now synchronize the listBox
		ListModelList lml = (ListModelList) this.listbox.getListModel();

//		 Check if the object is new or updated
		// -1 means that the obj is not in the list, so it's new.
		if (lml.indexOf(wil) == -1) {
			lml.add(wil);
		} else {
			lml.set(lml.indexOf(wil), wil);
		}

		doReadOnly();
		btnCtrl.setBtnStatus_Save();
		// init the old values vars new
		doStoreInitValues();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// ++++++++++++++++++ getter / setter +++++++++++++++++++//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	public Wilayah getWilayah() {
		return this.wilayah;
	}

	public void setWilayah(Wilayah right) {
		this.wilayah = right;
	}

	public void setValidationOn(boolean validationOn) {
		this.validationOn = validationOn;
	}

	public boolean isValidationOn() {
		return this.validationOn;
	}

	public WilayahDAO getWilayahDAO() {
		return this.wilayahDAO;
	}

	public void setWilayahDAO(WilayahDAO wilayahDAO) {
		this.wilayahDAO = wilayahDAO;
	}
	
	
	private String replaceWithZero(String string, int startIndex) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < startIndex; i++)
			buffer.append(string.charAt(i));
		for (int i = startIndex; i < 10; i++)
			buffer.append('0');
		
		return buffer.toString();
	}

}
