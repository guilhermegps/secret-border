package com.secretborder.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import lombok.Getter;

public class DiceDialog extends JDialog {

	private final JPanel dicePanel = new JPanel();
	private JLabel lblLoad;
	private JProgressBar progressBar;
	private JButton btnOk;
	private byte[] cache = new byte[128];
	@Getter
	private byte[] entropy;
	@Getter
	private int count = 0;
	
	public static byte[] showDiceDialog(Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, "You can use the dice buttons or your own keyboard to input your entropy");
		DiceDialog dialog = new DiceDialog();
		try {
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			if(parentComponent != null)
				dialog.setLocationRelativeTo(parentComponent);
			dialog.setVisible(true);
			
			return dialog.getEntropy();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dialog.dispose();
			dialog = null;
		}
	}

	/**
	 * Create the dialog.
	 */
	public DiceDialog() {		
		setBounds(100, 100, 390, 380);
		setResizable(false);
		setModalityType(DEFAULT_MODALITY_TYPE);
		getContentPane().setLayout(new BorderLayout());
		dicePanel.setToolTipText("");
		dicePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(dicePanel, BorderLayout.CENTER);
		GridBagLayout gbl_dicePanel = new GridBagLayout();
		gbl_dicePanel.columnWidths = new int[]{0, 100, 40, 100, 40, 65, 0};
		gbl_dicePanel.rowHeights = new int[]{0, 0, 40, 0, 40, 0, 0};
		gbl_dicePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_dicePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		
		KeyboardFocusManager
	    .getCurrentKeyboardFocusManager()
	    .addKeyEventDispatcher(new KeyEventDispatcher() {
	    public boolean dispatchKeyEvent(KeyEvent e) {
	        boolean keyHandled = false;
	        if (e.getID() == KeyEvent.KEY_PRESSED) {
	            setValue(e.getKeyChar());
	        }
	        return keyHandled;
	    }
	});
		
		dicePanel.setLayout(gbl_dicePanel);
		{
			JButton btn1 = new JButton("1");
			btn1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('1');
				}
			});
			btn1.setFont(new Font("Dialog", Font.BOLD, 30));
			btn1.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn1 = new GridBagConstraints();
			gbc_btn1.insets = new Insets(0, 0, 5, 5);
			gbc_btn1.gridx = 1;
			gbc_btn1.gridy = 1;
			dicePanel.add(btn1, gbc_btn1);
		}
		{
			JButton btn2 = new JButton("2");
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('2');
				}
			});
			btn2.setFont(new Font("Dialog", Font.BOLD, 30));
			btn2.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn2 = new GridBagConstraints();
			gbc_btn2.insets = new Insets(0, 0, 5, 5);
			gbc_btn2.gridx = 3;
			gbc_btn2.gridy = 1;
			dicePanel.add(btn2, gbc_btn2);
		}
		{
			JButton btn3 = new JButton("3");
			btn3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('3');
				}
			});
			btn3.setFont(new Font("Dialog", Font.BOLD, 30));
			btn3.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn3 = new GridBagConstraints();
			gbc_btn3.insets = new Insets(0, 0, 5, 0);
			gbc_btn3.gridx = 5;
			gbc_btn3.gridy = 1;
			dicePanel.add(btn3, gbc_btn3);
		}
		{
			JButton btn4 = new JButton("4");
			btn4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('4');
				}
			});
			btn4.setFont(new Font("Dialog", Font.BOLD, 30));
			btn4.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn4 = new GridBagConstraints();
			gbc_btn4.insets = new Insets(0, 0, 5, 5);
			gbc_btn4.gridx = 1;
			gbc_btn4.gridy = 3;
			dicePanel.add(btn4, gbc_btn4);
		}
		{
			JButton btn5 = new JButton("5");
			btn5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('5');
				}
			});
			btn5.setFont(new Font("Dialog", Font.BOLD, 30));
			btn5.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn5 = new GridBagConstraints();
			gbc_btn5.insets = new Insets(0, 0, 5, 5);
			gbc_btn5.gridx = 3;
			gbc_btn5.gridy = 3;
			dicePanel.add(btn5, gbc_btn5);
		}
		{
			JButton btn6 = new JButton("6");
			btn6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setValue('6');
				}
			});
			btn6.setFont(new Font("Dialog", Font.BOLD, 30));
			btn6.setPreferredSize(new Dimension(90, 90));
			GridBagConstraints gbc_btn6 = new GridBagConstraints();
			gbc_btn6.insets = new Insets(0, 0, 5, 0);
			gbc_btn6.gridx = 5;
			gbc_btn6.gridy = 3;
			dicePanel.add(btn6, gbc_btn6);
		}
		{
			lblLoad = new JLabel("0/128");
			lblLoad.setFont(new Font("Dialog", Font.BOLD, 10));
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.SOUTHEAST;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 5;
			gbc_label.gridy = 4;
			dicePanel.add(lblLoad, gbc_label);
		}
		{
			progressBar = new JProgressBar();
			progressBar.setString("0/128");
			progressBar.setToolTipText("");
			progressBar.setMaximum(128);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.gridwidth = 5;
			gbc_progressBar.gridx = 1;
			gbc_progressBar.gridy = 5;
			dicePanel.add(progressBar, gbc_progressBar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						entropy = cache;
						dispose();
					}
				});
				btnOk.setEnabled(false);
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
	private void setValue(char value) {
		if(count<cache.length) {			
			cache[count] = (byte) value;
			count++;
			
			var loadString = String.format("%d/%d", count, cache.length);
			lblLoad.setText(loadString);
			progressBar.setValue(count);
			
			btnOk.setEnabled(count==cache.length);
		}
	}

}
