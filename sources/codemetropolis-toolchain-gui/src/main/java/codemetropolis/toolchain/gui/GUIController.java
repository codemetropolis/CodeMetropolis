package codemetropolis.toolchain.gui;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.gui.metricgenerators.MetricGenerator;
import codemetropolis.toolchain.gui.metricgenerators.SonarQubeGenerator;
import codemetropolis.toolchain.gui.metricgenerators.SourceMeterGenerator;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;


/**
 * Controller class to handle the connection between backend and GUI.
 * 
 * @author szkabel
 *
 */
public class GUIController {
	
	private ExecutionOptions execOpt;
	private List<MetricGenerator> generatorList;
	
	/**
	 * Constructor. Register here the possible metricGenerators.
	 */
	public GUIController() {
		generatorList = new ArrayList<MetricGenerator>();
		generatorList.add(new SourceMeterGenerator());
		generatorList.add(new SonarQubeGenerator());
	}
	
	public void setOptions(String projectName, MetricGenerator conOpt, String mapPath, String mcRoot, boolean showMap) {
		execOpt = new ExecutionOptions(projectName, conOpt, mapPath, mcRoot, showMap);
	}
	
	/**
	 * Check for the correctness of parameters.
	 */
	public void checkOptions() {
		//TODO: implement checks like: mcRoot is a folder etc		
	}
	/**
	 * Run the 4 project in sequence saving the intermediate xml-s into the .codemetropolis folder
	 */
	public void runWorldGenearation() {		
		//make temp folder for inter-module xmls.
		String tmpfolder = execOpt.getMinecraftRoot()+File.separator+"saves"+File.separator+".codeMetropolis";
		try {
	    	Runtime r = Runtime.getRuntime();	    	
	    	r.exec("cmd /c mkdir " + tmpfolder);	    		    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		//evaluate (run) metric generator so that the result file (folder) will be in the temp. 
		execOpt.getMetricGenerator().execute(tmpfolder, execOpt);
		
	}

	public List<MetricGenerator> getGeneratorList() {
		return generatorList;
	}

	public void setGeneratorList(List<MetricGenerator> generatorList) {
		this.generatorList = generatorList;
	}
	
	
}
