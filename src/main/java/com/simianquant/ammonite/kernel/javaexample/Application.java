package com.simianquant.ammonite.kernel.javaexample;

import ammonite.kernel.compat.ReplKernelCompat;
import ammonite.kernel.compat.AutocompleteOutputCompat;
import coursier.maven.MavenRepository;
import java.util.List;

final class Application{

	// ---------------------------- Initializing the Variables ---------------------------------------------------------

	private static final ReplKernelCompat kernel = new ReplKernelCompat(); // to carry out the operations
	private static final ReplKernelCompat kernelBkp = new ReplKernelCompat(); // to illustrate the absence of static leakage

	private static ProcessProcessor processor = new ProcessProcessor();
	private static LoadIvyProcessor ivyProcessor = new LoadIvyProcessor();

	// ---------------------------- Helper Functions -------------------------------------------------------------------

	private static void println(String text){
		System.out.println(text);
	}

	private static void printRowMarker(){
		println("--------------------------------------------------------------------------------");
	}

	private static String mkString(List<String> inp){
		final StringBuilder builder = new StringBuilder();
		for(String s: inp){
			builder.append(s + ", ");
		}
		return builder.toString();
	}

	private static void process(String text){
		printRowMarker();
		println("Processing: " + text);
		kernel.process(text, null, processor);
	}

	private static void processComplete(String text){
		printRowMarker();
		println("Autocomplete for: " + text);
		final AutocompleteOutputCompat output = kernel.complete(text, text.length());
		println("Names: " + mkString(output.names()));
		println("Signatures: " + mkString(output.signatures()));
	}

	private static void processBkp(String text){
		printRowMarker();
		println("Processing in backup kernel: " + text);
		kernelBkp.process(text, null, processor);
	}

	private static void processLoadIvy(String groupId, String artifactId, String version){
		printRowMarker();
		println("Processing: " + groupId + "::" + artifactId + "::" + version);
		kernel.loadIvy(groupId, artifactId, version, null, ivyProcessor);
	}

	public static void main(String[] args){
		// Process empty string
		process("");

		// Generate a name error
		process("oogachaka");

		// Generate a syntax error
		process("def foo(i: Int){ ");

		// declare a function
		process("def greet(name: String) = s\"Hello, $name\" ");

		final String greetString = "greet(\"Harshad\")";

		// use the function
		process(greetString);

		// Autocomplete
		processComplete("import collection.immutable.Li");
		processComplete("gre");

		// no static leakage
		processBkp(greetString + " // should fail, because in different instance");

		// using a function from the classpath
		process("import com.simianquant.ammonite.kernel.javaexample.Newton");
		process("Newton.sqrt(2)");

		// read mutable state from the classpath
		process("import com.simianquant.ammonite.kernel.javaexample.Mutable");
		process("Mutable.mutableInt");
		process("Mutable.mutableInt = 42");
		process("Mutable.mutableInt");

		// the change in mutable state is visible to others
		processBkp("com.simianquant.ammonite.kernel.javaexample.Mutable.mutableInt");

		// load ivy dependencies
		processLoadIvy("com.simianquant", "typequux_2.11", "0.2.0");
		process("import typequux._");
		process("import typequux._");
		process("val p = 3 :+: true :+: \"asdf\" :+: false :+: \'k\' :+: () :+: 13 :+: 9.3 :+: HNil");
		process("val idxd = p.t[String] // type indexing"); 
		process("idxd.before");
		process("idxd.at");
		process("idxd.updated(19)");

		// no static leakage of added files

		processBkp("import typequux._ // should fail, because in different instance");

	}

}