package br.com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public List<String> getDevice() {
		try {
			ProcessBuilder pb = new ProcessBuilder("adb", "devices");
			pb.redirectErrorStream(true);
			Process p = pb.start();
			p.waitFor();
			BufferedReader out = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			List<String> listadevices = new ArrayList<String>();
			out.readLine();

			while (out.ready()) {
				String devices = (out.readLine()).replace("device", "");
				if (!devices.equals("")) {
					listadevices.add(devices);
				}
			}
			out.close();
			return listadevices;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPackageApk(String caminhoAPK) throws IOException {
		String comando = "aapt dump badging " + caminhoAPK;
		BufferedReader in = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(comando).getInputStream()));
		String str = in.readLine();
		String pacote = str.substring(15, str.indexOf('\'', 15));
		return pacote;
	}
	
	public void criaDiretorioScripts(){
		String caminhoScripts = "c:/temp/";

		if (!new File(caminhoScripts).exists()) {
             (new File(caminhoScripts)).mkdir();
             System.out.println("Diretório criado com sucesso.");   
         }else{
        	 System.out.println("Diretório já existe.");
         }
	}

	public void criaArquivoBatParaExecucao() {
		try {
			criaDiretorioScripts();
			File file = new File("c:/temp/script.bat");
			if (file.exists()) {
				System.out.println("Arquivo já existente.");
			} else {
				file.createNewFile();
				System.out.println("Arquivo criado com sucesso.");
			}
		} catch (IOException e) {
		}
	}

	public void escreverNoArquivoBatMonkey(String caminhoAPK, String packageAPK) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("c:/temp/script.bat"));
			List<String> listaDevices = getDevice();

			for (String devices : listaDevices) {
				out.write("adb -s " + devices + " uninstall " + packageAPK + "\n");
				out.write("adb -s " + devices + " install " + caminhoAPK + "\n");
			}
			out.write("\nexit");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getNomeBatExecucao(){
		String nomeArquivo = "c:\\temp\\script.bat";
		return nomeArquivo;
	}
}
