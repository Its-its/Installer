package installer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.json.simple.JSONObject;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InstallerFrame extends JFrame {
	private JLabel labelOfVersion = null;
	private JLabel labelMcVersion = null;
	private JPanel panelCenter = null;
	private JButton buttonInstall = null;
	private JButton buttonClose = null;
	private JPanel panelBottom = null;
	private JPanel panelContentPane = null;
	EventHandler eventHandler = new EventHandler();
	private JTextArea textArea = null;
	private JTextField fileField = null;
	private JButton button = null;
	
	class EventHandler implements ActionListener {
		EventHandler() {}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == InstallerFrame.this.getButtonClose()) {
				InstallerFrame.this.connEtoC2(e);
			}
			
			if (e.getSource() == InstallerFrame.this.getButtonInstall()) {
				InstallerFrame.this.connEtoC1(e);
			}
		}
	}
	
	public InstallerFrame() {
		initialize();
	}
	
	private void customInit() {
		try {
			setDefaultCloseOperation(3);
			
			getButtonInstall().setEnabled(true);
			
			getButtonInstall().requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			InstallerFrame frm = new InstallerFrame();
			
			Utils.centerWindow(frm, null);
			
			frm.setVisible(true);
		} catch (Exception e) {
			String msg = e.getMessage();
			if ((msg != null) && (msg.equals("QUIET"))) {
				return;
			}
			e.printStackTrace();
			String str = Utils.getExceptionStackTrace(e);
			str = str.replace("\t", "  ");
			JTextArea textArea = new JTextArea(str);
			textArea.setEditable(false);
			Font f = textArea.getFont();
			Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
			textArea.setFont(f2);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(600, 400));
			JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
		}
	}

	private void handleException(Throwable e) {
		String msg = e.getMessage();
		if ((msg != null) && (msg.equals("QUIET"))) {
			return;
		}
		e.printStackTrace();
		String str = Utils.getExceptionStackTrace(e);
		str = str.replace("\t", "  ");
		JTextArea textArea = new JTextArea(str);
		textArea.setEditable(false);
		Font f = textArea.getFont();
		Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
		textArea.setFont(f2);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
	}

	private JLabel getLabelOfVersion() {
		if (this.labelOfVersion == null) {
			try {
				this.labelOfVersion = new JLabel();
				this.labelOfVersion.setName("LabelOfVersion");
				this.labelOfVersion.setBounds(2, 5, 385, 42);
				this.labelOfVersion.setFont(new Font("Dialog", 1, 18));
				this.labelOfVersion.setHorizontalAlignment(0);
				this.labelOfVersion.setPreferredSize(new Dimension(385, 42));
				this.labelOfVersion.setText("Cosmic Mod Pack 1.0");
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.labelOfVersion;
	}

	private JLabel getLabelMcVersion() {
		if (this.labelMcVersion == null) {
			try {
				this.labelMcVersion = new JLabel();
				this.labelMcVersion.setName("LabelMcVersion");
				this.labelMcVersion.setBounds(2, 38, 385, 25);
				this.labelMcVersion.setFont(new Font("Dialog", 1, 14));
				this.labelMcVersion.setHorizontalAlignment(0);
				this.labelMcVersion.setPreferredSize(new Dimension(385, 25));
				this.labelMcVersion.setText("for Minecraft 1.8.0");
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.labelMcVersion;
	}

	private JPanel getPanelCenter() {
		if (this.panelCenter == null) {
			try {
				this.panelCenter = new JPanel();
				this.panelCenter.setName("PanelCenter");
				this.panelCenter.setLayout(null);
				this.panelCenter.add(getLabelOfVersion(), getLabelOfVersion().getName());
				this.panelCenter.add(getLabelMcVersion(), getLabelMcVersion().getName());
				this.panelCenter.add(getTextArea(), getTextArea().getName());
				
				json = Installer.getMods();
				Object[] ats = json.keySet().toArray();
				for(int i = 0; i < json.keySet().size(); i++) {
					this.panelCenter.add(createCheckBox("" + ats[i]));
				}
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.panelCenter;
	}
	
	JSONObject json;
	int y = 0;
	List<JCheckBox> boxes = new ArrayList<JCheckBox>();
	
	public JCheckBox createCheckBox(String name) {
		JCheckBox chckbx = new JCheckBox(name);
		chckbx.setSelected(true);
		chckbx.setBounds(15, 130 + (y++ * 23), 200, 23);
		boxes.add(chckbx);
		return chckbx;
	}

	private JButton getButtonInstall() {
		if (this.buttonInstall == null) {
			try {
				this.buttonInstall = new JButton();
				this.buttonInstall.setName("ButtonInstall");
				this.buttonInstall.setPreferredSize(new Dimension(100, 26));
				this.buttonInstall.setText("Install");
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.buttonInstall;
	}

	private JButton getButtonClose() {
		if (this.buttonClose == null) {
			try {
				this.buttonClose = new JButton();
				this.buttonClose.setName("ButtonClose");
				this.buttonClose.setPreferredSize(new Dimension(100, 26));
				this.buttonClose.setText("Cancel");
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.buttonClose;
	}

	private JPanel getPanelBottom() {
		if (this.panelBottom == null) {
			try {
				this.panelBottom = new JPanel();
				this.panelBottom.setName("PanelBottom");
				this.panelBottom.setLayout(new FlowLayout(1, 15, 10));
				this.panelBottom.setPreferredSize(new Dimension(390, 85));
				this.panelBottom.add(getButtonInstall(), getButtonInstall().getName());
				this.panelBottom.add(getButtonClose(), getButtonClose().getName());
				this.panelBottom.add(getFileField());
				panelBottom.add(getFileButton());
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.panelBottom;
	}

	private JPanel getPanelContentPane() {
		if (this.panelContentPane == null) {
			try {
				this.panelContentPane = new JPanel();
				this.panelContentPane.setName("PanelContentPane");
				this.panelContentPane.setLayout(new BorderLayout(5, 5));
				this.panelContentPane.add(getPanelCenter(), "Center");
				this.panelContentPane.add(getPanelBottom(), "South");
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.panelContentPane;
	}

	private void initialize() {
		try {
			setName("Trump");
			setSize(400, 550);
			setDefaultCloseOperation(0);
			//setResizable(false);
			setTitle("Cosmic ModPack Installer");
			setContentPane(getPanelContentPane());
			initConnections();
		} catch (Throwable ivjExc) {
			handleException(ivjExc);
		}
		customInit();
	}

	public void onInstall() {
		try {
			List<String> names = new ArrayList<String>();
			for (JCheckBox box : this.boxes) {
				if (box.isSelected()) {
					names.add(json.get(box.getText()).toString());
				}
			}
			
			Installer.doInstall(names);
			
			Utils.showMessage(
					  "The Cosmic Mod Pack was successfully installed.\n"
					+ "Please use the profile name for liteloader to launch Minecraft from now on.");
			
			dispose();
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void onClose() {
		dispose();
	}

	private void connEtoC1(ActionEvent arg1) {
		try {
			onInstall();
		} catch (Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	private void connEtoC2(ActionEvent arg1) {
		try {
			onClose();
		} catch (Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	private void initConnections() throws Exception {
		getButtonInstall().addActionListener(this.eventHandler);
		getButtonClose().addActionListener(this.eventHandler);
	}

	private JTextArea getTextArea() {
		if (this.textArea == null) {
			try {
				this.textArea = new JTextArea();
				this.textArea.setName("TextArea");
				this.textArea.setBounds(15, 66, 365, 53);
				this.textArea.setEditable(false);
				this.textArea.setEnabled(true);
				this.textArea.setFont(new Font("Dialog", 0, 12));
				this.textArea.setLineWrap(true);
				this.textArea.setOpaque(false);
				this.textArea.setPreferredSize(new Dimension(365, 44));
				this.textArea.setText("This installer will install whatever mods you want and install Forge/Liteloader for you.");
				this.textArea.setWrapStyleWord(true);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return this.textArea;
	}
	
	private JTextField getFileField() {
		if (this.fileField == null) {
			Installer.workingDirectory = Utils.getWorkingDirectory();
			this.fileField = new JTextField();
			this.fileField.setHorizontalAlignment(SwingConstants.CENTER);
			this.fileField.setColumns(35);
			this.fileField.setEditable(false);
			this.fileField.setText(Installer.workingDirectory.getPath());
		}
		return this.fileField;
	}
	private JButton getFileButton() {
		if (this.button == null) {
			this.button = new JButton("...");
			this.button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser(InstallerFrame.this.fileField.getText());
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.showOpenDialog(InstallerFrame.this);
					
					InstallerFrame.this.fileField.setText(fileChooser.getSelectedFile().getPath());
					Installer.workingDirectory = fileChooser.getSelectedFile();
				}
			});
			this.button.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return this.button;
	}
}