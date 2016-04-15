package codemetropolis.toolchain.gui.metricgenerators;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Class for SourceMeter metric generation. It encapsulates the GUI to set the objects and also the execution.
 * @author szkabel
 *
 */
public class SourceMeterGenerator extends MetricGenerator {
	
	private CMTextField projectRootPath;	
	private CMTextField sourceMeterPath;	
	
	/**
	 * Constructor and register the type of the corresponding ConverterType.
	 */
	public SourceMeterGenerator() {
		super(ConverterType.SOURCEMETER,"SourceMeter");
	}

	@Override
	public CMMetricPanel getGUIpanel(CodeMetropolisGUI gui) {
		CMMetricPanel result = new CMMetricPanel(this, gui);
		result.setLayout(null);

		// Label for the root path
	    CMLabel projectRootLabel = new CMLabel("Project root:");
	    projectRootLabel.setBounds(5, 5, 120, 30);
	    result.add(projectRootLabel);
	    
	    //text field
	    projectRootPath = new CMTextField();
	    projectRootPath.setBounds(130, 5, 225, 30);
	    result.add(projectRootPath);	    
	    //Button
	    CMButton projectRootBrowse = new CMButton("Browse");	    
	    //projectRootBrowse.addActionListener(new DirBrowseListener(this.getParent(),projectRootPath));
	    projectRootBrowse.addActionListener(new BrowseListener(gui,projectRootPath,JFileChooser.DIRECTORIES_ONLY));
	    projectRootBrowse.setBounds(360, 5, 100, 30);
	    result.add(projectRootBrowse);
	    
	    //label for source meter location
	    CMLabel sourceMeterLabel = new CMLabel("SourceMeter exe:");
	    sourceMeterLabel.setBounds(5, 40, 120, 30);
	    result.add(sourceMeterLabel);
	    
	    //text field
	    sourceMeterPath = new CMTextField();
	    sourceMeterPath.setBounds(130, 40, 225, 30);
	    result.add(sourceMeterPath);	    	   
	    //button
	    CMButton sourceMeterBrowse = new CMButton("Browse");
	    sourceMeterBrowse.addActionListener(new BrowseListener(gui, sourceMeterPath,JFileChooser.FILES_ONLY));
	    sourceMeterBrowse.setBounds(360, 40, 100, 30);	    	    	    	    	   
	    result.add(sourceMeterBrowse);
	    
	    return result;
	}
	
	@Override
	public String execute(String dst, ExecutionOptions execOpt) {
		System.out.println("Generate");
		String smPath = sourceMeterPath.getText();
		String projectPath = projectRootPath.getText();
		String command = smPath + " -projectName=" + execOpt.getProjectName() + " -projectBaseDir=" + projectPath + " -resultsDir=" + dst;
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
			Process p = pb.start();
			InputStream inputStream = p.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            inputStream.close();
            bufferedReader.close();
	        
            String result = dst + File.separator + execOpt.getProjectName() + File.separator + goIntoFirstDirectoryNTimes(dst + File.separator + execOpt.getProjectName(), 2) + execOpt.getProjectName() +".graph";
            System.out.println(result);
            return result;
                       
            //System.out.println(command);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return "";
	    }		
		
		
		
	}
	
	/**
	 * Goes down to the file hierarchy in n deepness always to the first folder
	 * @param baseFolder
	 * @param n deepness of the recursion
	 * @return a String with the relative path
	 */
	private String goIntoFirstDirectoryNTimes(String baseFolder, int n) {
		if (n>0) {
			File base = new File(baseFolder);
			File[] filesInFolder = base.listFiles();
			int j = 0;
			while (j<filesInFolder.length) {
				if (filesInFolder[j].isDirectory()) {
					return filesInFolder[j].getName() + File.separator + goIntoFirstDirectoryNTimes(filesInFolder[j].getAbsolutePath(), n-1);
				}
				j++;
			}	
			return "";
		} else {
			return "";
		}
	}

}
