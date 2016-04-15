package codemetropolis.toolchain.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.converter.ConverterExecutor;
import codemetropolis.toolchain.converter.ConverterExecutorArgs;
import codemetropolis.toolchain.gui.metricgenerators.MetricGenerator;
import codemetropolis.toolchain.gui.metricgenerators.SonarQubeGenerator;
import codemetropolis.toolchain.gui.metricgenerators.SourceMeterGenerator;
import codemetropolis.toolchain.mapping.MappingExecutor;
import codemetropolis.toolchain.mapping.MappingExecutorArgs;
import codemetropolis.toolchain.placing.PlacingExecutor;
import codemetropolis.toolchain.placing.PlacingExecutorArgs;
import codemetropolis.toolchain.rendering.RenderingExecutor;
import codemetropolis.toolchain.rendering.RenderingExecutorArgs;
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
	private final String converterPrefix = "converter_out_";
	private final String mappingPrefix = "mapping_out_";
	private final String placingPrefix = "placing_out_";
	
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
		String metricResult = execOpt.getMetricGenerator().execute(tmpfolder, execOpt);
		String lastInterModuleXML;
		String newInterModeuleXML;
		
		//do converter execution
		FileLogger.load(Settings.get("converter_log_file"));
		ConverterExecutor convExecutor = new ConverterExecutor();
		convExecutor.setPrefix(Resources.get("converter_prefix"));
	    convExecutor.setErrorPrefix(Resources.get("error_prefix"));
	    Map<String, String> params = new HashMap<String,String>();
	    lastInterModuleXML = metricResult;
	    newInterModeuleXML = tmpfolder + File.separator + converterPrefix + execOpt.getProjectName()+ ".xml";
	    convExecutor.execute(
	    		new ConverterExecutorArgs(
		    			execOpt.getMetricGenerator().getType(),
			    		lastInterModuleXML,
			    		newInterModeuleXML,
			    		params			    		
		    	));
	    
	    //do mapping
	    lastInterModuleXML = newInterModeuleXML;
	    newInterModeuleXML = tmpfolder + File.separator + mappingPrefix + execOpt.getProjectName()+ ".xml";
	    FileLogger.load(Settings.get("mapping_log_file"));
	    
	    MappingExecutor mapExecutor = new MappingExecutor();
	    mapExecutor.setPrefix(Resources.get("mapping_prefix"));
	    mapExecutor.setErrorPrefix(Resources.get("error_prefix"));
	    //TODO: include to GUI the missing last burnt in parameter.
	    mapExecutor.execute(
	    		new MappingExecutorArgs(
		    		lastInterModuleXML,
		    		newInterModeuleXML,
		    		execOpt.getMappingXml(),
		    		1.0,
		    		false)
	    		);

	    //do placing		
	    lastInterModuleXML = newInterModeuleXML;
	    newInterModeuleXML = tmpfolder + File.separator + placingPrefix + execOpt.getProjectName()+ ".xml";	    
	    FileLogger.load(Settings.get("placing_log_file"));
	    
	    PlacingExecutor placeExecutor = new PlacingExecutor();
	    placeExecutor.setPrefix(Resources.get("placing_prefix"));
	    placeExecutor.setErrorPrefix(Resources.get("error_prefix"));
		placeExecutor.execute(
				new PlacingExecutorArgs(
						lastInterModuleXML,
						newInterModeuleXML,
						"pack",
						execOpt.isShowMap())
				);
		
		//do rendering the MC world.		
		lastInterModuleXML = newInterModeuleXML;
		String worldPath = execOpt.getMinecraftRoot()+File.separator+"saves"+File.separator+execOpt.getProjectName(); 
		FileLogger.load(Settings.get("rendering_log_file"));
		
		RenderingExecutor renderExecutor = new RenderingExecutor();
		renderExecutor.setPrefix(Resources.get("rendering_prefix"));
	    renderExecutor.setErrorPrefix(Resources.get("error_prefix"));
	    renderExecutor.execute(
	    		new RenderingExecutorArgs(
	    				lastInterModuleXML,
	    				worldPath,
	    				true)
	    		);
	    
	    System.out.println("Rendering finished.");
				
	}

	public List<MetricGenerator> getGeneratorList() {
		return generatorList;
	}

	public void setGeneratorList(List<MetricGenerator> generatorList) {
		this.generatorList = generatorList;
	}
	
	
}
