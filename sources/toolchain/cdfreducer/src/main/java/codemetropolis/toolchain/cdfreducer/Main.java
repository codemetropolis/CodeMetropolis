package codemetropolis.toolchain.cdfreducer;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		FileLogger.load(Settings.get("cdfreducer_log_file"));

		CommandLineOptions options = new CommandLineOptions();
		CmdLineParser parser = new CmdLineParser(options);

		try {
			parser.parseArgument(args);
			if (options.showHelp()) {
				System.out.println(Resources.get("cdfreducer_introduction"));
				System.out.println(Resources.get("cdfreducer_usage") + '\n' + Resources.get("cdfreducer_usage_aliases"));
				return;
			}
			if (options.getSource() == null || options.getOutputFile() == null) {
				throw new IllegalArgumentException();
			}
		} catch (CmdLineException | IllegalArgumentException e) {
			String message = Resources.get("command_line_error");
			FileLogger.logError(message, e);
			System.err.println(message);
			return;
		}


		Map<String, String> params = new HashMap<>();
		if (options.getParams() != null) {
			try {
				String[] paramsArray = options.getParams();
				for (String str : paramsArray) {
					String[] strParts = str.split("=");
					// using "pnr" and "pvr" as aliases
					if (strParts[0].toLowerCase().equals("pnr"))
						params.put("property-name-regex", strParts[1]);
					else if (strParts[0].toLowerCase().equals("pvr"))
						params.put("property-value-regex", strParts[1]);
					else
						params.put(strParts[0].toLowerCase(), strParts[1].toLowerCase());
				}
			} catch (Exception e) {
				String message = Resources.get("invalid_params");
				System.err.println(message);
				FileLogger.logError(message, e);
			}
		}

		CdfReducerExecutor executor = new CdfReducerExecutor();
		executor.setPrefix(Resources.get("cdfreducer_prefix"));
		executor.setErrorPrefix(Resources.get("error_prefix"));
		executor.execute(
				new CdfReducerExecutorArgs(
						options.getSource(),
						options.getOutputFile(),
						params
				));
		}
	}
