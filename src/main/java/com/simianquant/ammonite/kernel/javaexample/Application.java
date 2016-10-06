package com.simianquant.ammonite.kernel.javaexample;

import ammonite.kernel.compat.ReplKernelCompat;

final class Application{

	// ---------------------------- Initializing the Variables ---------------------------------------------------------

	private static final ReplKernelCompat kernel = new ReplKernelCompat(); // to carry out the operations
	private static final ReplKernelCompat kernelBkp = new ReplKernelCompat(); // to illustrate the absence of static leakage

	private static ProcessProcessor processor = new ProcessProcessor();

	// ---------------------------- Helper Functions -------------------------------------------------------------------

	private static void println(String text){
		System.out.println(text);
	}

	private static void printRowMarker(){
		println("--------------------------------------------------------------------------------");
	}

	private static void process(String text){
		printRowMarker();
		println("Processing: " + text);
		kernel.process(text, null, processor);
	}

	private static void processBkp(String text){
		printRowMarker();
		println("Processing in backup kernel: " + text);
		kernelBkp.process(text, null, processor);
	}

	public static void main(String[] args){
		// Process empty string
		process("");

		// Generate a name error
		process("ogachaka");

		// Generate a syntax error
		process("def foo(i: Int){ ");

		// declare a function
		process("def greet(name: String) = s\"Hello, $name\" ");

		final String greetString = "greet(\"Harshad\")";

		// use the function
		process(greetString);

		// no static leakage
		processBkp(greetString);

		// using a function from the classpath
		process("import com.simianquant.ammonite.kernel.javaexample.Newton");
		process("Newton.sqrt(2)");

		// read mutable state from the classpath
		process("import com.simianquant.ammonite.kernel.javaexample.Mutable");
		process("Mutable.mutableInt");
		process("Mutable.mutableInt = 42");
		process("Mutable.mutableInt");
	}

}