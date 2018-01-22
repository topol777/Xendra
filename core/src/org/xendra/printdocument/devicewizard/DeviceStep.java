//The contents of this file are subject to the Mozilla Public License Version 1.1
//(the "License"); you may not use this file except in compliance with the 
//License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
//Software distributed under the License is distributed on an "AS IS" basis,
//WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
//for the specific language governing rights and
//limitations under the License.
//
//The Original Code is "The Columba Project"
//
//The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003. 
//
//All Rights Reserved.
package org.xendra.printdocument.devicewizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.javaprog.ui.wizard.AbstractStep;
import net.javaprog.ui.wizard.DataModel;
import net.javaprog.ui.wizard.DefaultDataLookup;
import net.miginfocom.swing.MigLayout;

import org.columba.core.gui.base.LabelWithMnemonic;
import org.columba.core.gui.base.MultiLineLabel;
import org.columba.core.gui.base.WizardTextField;
import org.xendra.printdocument.ResourceLoader;


class DeviceStep extends AbstractStep implements ActionListener {
    protected DataModel data;
    protected JTextField deviceNameField;    
    protected JButton devicetest;
    protected JLabel message;
   
    public DeviceStep(DataModel data) {
        super(ResourceLoader.getString("dialog", "devicewizard", "device"),
        	  ResourceLoader.getString("dialog", "devicewizard", "device_description"));
        this.data = data;        
        setCanGoNext(false);
    }

    protected JComponent createComponent() {    	
        JComponent component = new JPanel();
        component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
        component.add(new MultiLineLabel(ResourceLoader.getString("dialog", "devicewizard", "device_text")));
        component.add(Box.createVerticalStrut(40));

        WizardTextField middlePanel = new WizardTextField();        

        DocumentListener fieldListener = new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                checkdevice();
            }

            public void insertUpdate(DocumentEvent e) {
                checkdevice();
            }

            protected void checkdevice() {
            	message.setText("");
                String s = deviceNameField.getText();               
				File f = new File(s);				
				if (f.exists())
				{
					try {
						FileWriter os = new FileWriter(s);		
						os.close();
						os = null;
		                setCanGoNext(true);						
					}
					catch (Exception e) {
						message.setText(e.getMessage());
					}
				}				
            }

            public void changedUpdate(DocumentEvent e) {
            }
        };
               
        LabelWithMnemonic deviceNameLabel = new LabelWithMnemonic(ResourceLoader.getString("dialog", "devicewizard", "devicename"));        
        middlePanel.addLabel(deviceNameLabel);
        deviceNameField = new JTextField();
        message = new JLabel();        
        Method method = null;
        try {
            method = deviceNameField.getClass().getMethod("getText", null);
        } catch (NoSuchMethodException nsme) {}
        data.registerDataLookup("devicename", new DefaultDataLookup(deviceNameField, method, null));
        deviceNameField.getDocument().addDocumentListener(fieldListener);        
        deviceNameLabel.setLabelFor(deviceNameField);
        devicetest = new JButton(ResourceLoader.getString("dialog","devicewizard","devicetest"));
        devicetest.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.add(deviceNameField,"width 80%");
        panel.add(devicetest);
        //middlePanel.addTextField(deviceNameField);
        middlePanel.addTextField(panel);        
        //middlePanel.add(devicetest);
        middlePanel.addEmptyExample();
        
        component.add(middlePanel);
        component.add(message);
        return component;
    }

    public void prepareRendering() {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	deviceNameField.requestFocus();
                }
            });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        String s = deviceNameField.getText();               
		File f = new File(s);				
		try {
			FileWriter os = new FileWriter(s);		
			os.close();
			os = null;
            setCanGoNext(true);						
		}
		catch (Exception ex) {
			message.setText(ex.getMessage());
		}
	}
}