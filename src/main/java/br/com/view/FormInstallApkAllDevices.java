package br.com.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.util.Utils;

public class FormInstallApkAllDevices {

	private JFrame frmInstaleUmaApk;
	private JTextField textFieldCaminhoAPK;
	private JButton btnSelecionarApp;
	private JButton btnInstalar;
	private JLabel lblSelecioneAApk;
	private JTextField textFieldPackage;
	private JLabel lblPacoteDaAplicao;
	private Utils utils = new Utils();
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormInstallApkAllDevices window = new FormInstallApkAllDevices();
					window.frmInstaleUmaApk.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormInstallApkAllDevices() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		criarObjetosFormulario();
		frmInstaleUmaApk.setTitle("Instalação Apks");
		frmInstaleUmaApk.setBounds(100, 100, 346, 210);
		frmInstaleUmaApk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInstaleUmaApk.getContentPane().setLayout(null);
		frmInstaleUmaApk.setLocationRelativeTo(null);
		definiPosicoesComponentesFormulario();
		
		btnSelecionarApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				escolhaArquivoAPK();
			}
		});
		
		btnInstalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				utils.criaArquivoBatParaExecucao();
				utils.escreverNoArquivoBatMonkey(textFieldCaminhoAPK.getText().toString(), textFieldPackage.getText().toString());
				String bat = utils.getNomeBatExecucao();
				try {
					JOptionPane.showMessageDialog(null,"Aguarde a instalação da apk no(s) dispositivo(s)....... Isso pode demorar um pouco.");
					Runtime.getRuntime().exec("cmd /c start " + bat);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		addComponentesFormulario();
		configuraEstadoInicialComponentes();
	}
	
	public void escolhaArquivoAPK(){
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileFilter(new FileNameExtensionFilter(".apk", "apk"));
		int retorno = fileChooser.showOpenDialog(null);
		if (retorno == JFileChooser.APPROVE_OPTION){
			textFieldCaminhoAPK.setText(fileChooser.getSelectedFile().toString());
			try {
				textFieldPackage.setText(utils.getPackageApk(fileChooser.getSelectedFile().toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void criarObjetosFormulario(){
		frmInstaleUmaApk = new JFrame();
		btnSelecionarApp = new JButton("SelecioneAPP");
		btnInstalar = new JButton("Instalar");
		lblSelecioneAApk = new JLabel("Selecione a apk:");
		textFieldCaminhoAPK = new JTextField();
		lblPacoteDaAplicao = new JLabel("Pacote da aplicação:");
		textFieldPackage = new JTextField();
	}
	
	public void definiPosicoesComponentesFormulario(){
		btnSelecionarApp.setBounds(163, 113, 114, 23);
		textFieldCaminhoAPK.setBounds(10, 32, 308, 20);
		btnInstalar.setBounds(64, 113, 89, 23);
		lblSelecioneAApk.setBounds(12, 11, 100, 14);
		lblPacoteDaAplicao.setBounds(12, 61, 177, 19);
		textFieldCaminhoAPK.setColumns(10);
		textFieldPackage.setBounds(10, 82, 308, 20);
		textFieldPackage.setColumns(10);
	}
	
	public void addComponentesFormulario(){
		frmInstaleUmaApk.getContentPane().add(btnSelecionarApp);
		frmInstaleUmaApk.getContentPane().add(textFieldCaminhoAPK);
		frmInstaleUmaApk.getContentPane().add(btnInstalar);
		frmInstaleUmaApk.getContentPane().add(lblSelecioneAApk);
		frmInstaleUmaApk.getContentPane().add(textFieldPackage);
		frmInstaleUmaApk.getContentPane().add(lblPacoteDaAplicao);
	}
	
	public void configuraEstadoInicialComponentes(){
		textFieldCaminhoAPK.setEditable(false);
		textFieldPackage.setEditable(false);
	}
}
