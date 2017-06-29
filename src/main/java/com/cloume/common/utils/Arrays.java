package com.cloume.common.utils;

public final class Arrays {

	@SafeVarargs
	static public <T> T[] from(T ...elements) {
		return elements;
	}
}
