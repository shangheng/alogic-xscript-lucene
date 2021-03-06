import java.util.HashMap;
import java.util.Map;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.Script;
import com.alogic.xscript.doc.XsObject;
import com.alogic.xscript.doc.json.JsonObject;
import com.anysoft.util.CommandLine;
import com.anysoft.util.Properties;
import com.anysoft.util.Settings;
import com.jayway.jsonpath.spi.JsonProvider;
import com.jayway.jsonpath.spi.JsonProviderFactory;

public class Demo {

	public static void run(String src, Properties p) {
		Script script = Script.create(src, p);
		if (script == null) {
			System.out.println("Fail to compile the script");
			return;
		}
		long start = System.currentTimeMillis();
		Map<String, Object> root = new HashMap<String, Object>();
		XsObject doc = new JsonObject("root",root);
		LogicletContext ctx = new LogicletContext(p);
		script.execute(doc, doc, ctx, new ExecuteWatcher.Quiet());

		System.out.println("Script:" + src);
		System.out.println("Duration:" + (System.currentTimeMillis() - start) + "ms");

		JsonProvider provider = JsonProviderFactory.createProvider();
		System.out.println("#########################################################");
		System.out.println(provider.toJson(root));
		System.out.println("#########################################################");
}
	
	public static void main(String[] args) {
		Settings settings = Settings.get();		
		settings.addSettings(new CommandLine(args));		
		settings.addSettings("java:///conf/settings.xml#Demo", null, Settings.getResourceFactory());
		
        run("java:///xscript/indexNew.xml#Demo", settings);
        run("java:///xscript/indexQuery.xml#Demo", settings);
        run("java:///xscript/indexDelete.xml#Demo", settings);
        run("java:///xscript/indexQuery.xml#Demo", settings);
        run("java:///xscript/indexUpdate.xml#Demo", settings);
        run("java:///xscript/indexQuery.xml#Demo", settings);
	}
}
