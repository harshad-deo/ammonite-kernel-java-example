package com.simianquant.ammonite.kernel.javaexample;

import ammonite.kernel.compat.KernelProcessProcessor;
import java.util.List;

final class ProcessProcessor implements KernelProcessProcessor<Void, Void>{

	private void println(String text){
		System.out.println(text);
	}

	private String mkString(List<String> inp){
		final StringBuilder builder = new StringBuilder();
		for(String s: inp){
			builder.append(s);
		}
		return builder.toString();
	}

	@Override public Void processEmpty(Void data) {
		println("Empty Text");
		return null;
	}

	@Override public Void processError(String firstError, List<String> otherErrors, Void data){
		final String netErrors = firstError + "; " + mkString(otherErrors);

		println("Errors: " + netErrors);
		
		return null;
	}

	@Override public Void processSuccess(Object value, List<String> infos, List<String> warnings, Void data){
		final String netInfos = mkString(infos);
		final String netWarnings = mkString(warnings);

		println("Result: " + value);
		println("Info: " + netInfos);
		println("Warnings: " + netWarnings);

		return null;
	}

}