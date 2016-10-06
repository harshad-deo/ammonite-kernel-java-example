package com.simianquant.ammonite.kernel.javaexample;

import ammonite.kernel.compat.KernelProcessProcessor;
import java.util.List;

final class ProcessProcessor implements KernelProcessProcessor<Void, Void>{

	private void println(String text){
		System.out.println(text);
	}

	@Override public Void processEmpty(Void data) {
		println("Empty Text");
		return null;
	}

	@Override public Void processError(String firstError, List<String> otherErrors, Void data){
		otherErrors.add(0, firstError);
		final String netErrors = String.join("; ", otherErrors);

		println("Errors: " + netErrors);
		
		return null;
	}

	@Override public Void processSuccess(Object value, List<String> infos, List<String> warnings, Void data){
		final String netInfos = String.join("; ", infos);
		final String netWarnings = String.join("; ", warnings);

		println("Result: " + value);
		println("Info: " + netInfos);
		println("Warnings: " + netWarnings);

		return null;
	}

}