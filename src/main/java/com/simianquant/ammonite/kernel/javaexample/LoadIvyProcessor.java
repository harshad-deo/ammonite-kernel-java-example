package com.simianquant.ammonite.kernel.javaexample;

import ammonite.kernel.compat.KernelLoadIvyProcessor;
import java.util.List;

final class LoadIvyProcessor implements KernelLoadIvyProcessor<Void, Void>{

	private static void println(String text){
		System.out.println(text);
	}

	@Override public Void processError(String firstError, List<String> otherErrors, Void data){
		final StringBuilder builder = new StringBuilder();
		builder.append(firstError + "; ");
		for(String s: otherErrors){
			builder.append(s);
		}
		println("Errors: " + builder.toString());
		return null;
	}

	@Override public Void processSuccess(Void data){
		println("Success");
		return null;
	}

}