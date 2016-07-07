package installer;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONWriter;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//TODO: DAMAGE INDICATORS, TNT BREADCRUMBS
public class Installer {
	public static File workingDirectory = null;
	static String VERSION = "1.8";
	static String FG_INSTALLER = "forge-1.8-11.14.4.1563-installer.jar";
	static String LL_INSTALLER = "liteloader-installer-1.8.0-00-SNAPSHOT.jar";
	
	public static void doInstall(List<String> toInstall) throws Exception {
		File dirMc = Installer.workingDirectory;
		Utils.dbg("Dir minecraft: " + dirMc);
		File dirMcLib = new File(dirMc, "libraries");
		Utils.dbg("Dir libraries: " + dirMcLib);
		File dirMcVers = new File(dirMc, "versions");
		Utils.dbg("Dir versions: " + dirMcVers);
		File dirMcMods = new File(dirMc, "mods/" + VERSION);
		dirMcMods.mkdirs();
		
		if(!(new File(dirMcVers, VERSION).exists())) {
			Utils.showErrorMessage(
					  "Minecraft version not found: " + VERSION + "\n"
					+ "You need to start the version " + VERSION + " manually once.");
			throw new RuntimeException("QUIET");
		}
		
		JSONArray array = new JSONArray(toInstall);
		
		for(int i = 0; i < array.size(); i++) {
			JSONArray mods = (JSONArray)new JSONParser().parse(array.get(i).toString());
			
			for(int o = 0; o < mods.size(); o++) {
				Utils.dbg(" - " + exportResource(dirMcMods, "/mods/" + mods.get(o).toString(), mods.get(o).toString()));
			}
		}
		
		Utils.dbg("Minecraft Version: " + VERSION);
		
		copyInstallers(dirMc);
		
		try {
			Process process = Runtime.getRuntime().exec("java -jar " + dirMc.getPath().replace('\\', '/') + "/installers/" + FG_INSTALLER);
			Utils.showMessage("Please Install Forge if you don't have it already.");
			process.waitFor();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		try {
			Process process = Runtime.getRuntime().exec("java -jar " + dirMc.getPath().replace('\\', '/') + "/installers/" + LL_INSTALLER);
			Utils.showMessage(
					  "Please Install Liteloader if you don't have it already running with forge.\n"
					+ "Make sure liteloader is being extended to the forge version you're using.\n"
					+ "For Example: 'Extend from: 1.8-forge1.8-11.14.4.1563' and name the profile whatever you want.\n"
					+ "The profile name you use will be used to launch Minecraft from now on.");
			process.waitFor();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private static String exportResource(File dir, String resourceLoc, String exportName) throws Exception {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		
		if(!dir.exists()) dir.mkdirs();
		
		if(new File(dir.getPath().replace('\\', '/') + "/" + exportName).exists())
			return "Already Exists: " + exportName;
		
		try {
			stream = Installer.class.getResourceAsStream(resourceLoc);
			
			if (stream == null) {
				throw new Exception("Cannot get resource \"" + resourceLoc + "\" from Jar file.");
			}
			
			int readBytes;
			byte[] buffer = new byte[4096];
			resStreamOut = new FileOutputStream(dir.getPath().replace('\\', '/') + "/" + exportName);
			
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			stream.close();
			resStreamOut.close();
		}
		
		return dir.getPath().replace('\\', '/') + "/" + exportName;
	}
	
	private static void copyInstallers(File dirMcLib) throws Exception {
		File install = new File(dirMcLib, "installers");
		if(!install.exists()) install.mkdirs();

		Utils.dbg(" - " + exportResource(install, "/lib/" + FG_INSTALLER, FG_INSTALLER));
		Utils.dbg(" - " + exportResource(install, "/lib/" + LL_INSTALLER, LL_INSTALLER));
	}

	public static JSONObject getMods() throws IOException, ParseException {
		InputStream in = Installer.class.getResourceAsStream("/mods.txt");
		return (JSONObject)new JSONParser().parse(new String(Utils.readAll(in)));
	}
}