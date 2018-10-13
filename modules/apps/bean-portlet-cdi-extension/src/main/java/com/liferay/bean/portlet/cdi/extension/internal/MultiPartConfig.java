/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.bean.portlet.cdi.extension.internal;

/**
 * @author Shuyang Zhou
 */
public class MultiPartConfig {

	public static final MultiPartConfig UNSUPPORTED = new MultiPartConfig(
		false, 0, null, -1, -1);

	public MultiPartConfig(
		int fileSizeThreshold, String location, long maxFileSize,
		long maxRequestSize) {

		this(true, fileSizeThreshold, location, maxFileSize, maxRequestSize);
	}

	public int getFileSizeThreshold() {
		return _fileSizeThreshold;
	}

	public String getLocation() {
		return _location;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public long getMaxRequestSize() {
		return _maxRequestSize;
	}

	public boolean isSupported() {
		return _supported;
	}

	public MultiPartConfig merge(MultiPartConfig multiPartConfig) {
		if (!_supported && !multiPartConfig._supported) {
			return this;
		}

		int fileSizeThreshold = _fileSizeThreshold;

		if (multiPartConfig._fileSizeThreshold > 0) {
			fileSizeThreshold = multiPartConfig._fileSizeThreshold;
		}

		String location = _location;

		if (multiPartConfig._location != null) {
			location = multiPartConfig._location;
		}

		long maxFileSize = _maxFileSize;

		if (multiPartConfig._maxFileSize > 0) {
			maxFileSize = multiPartConfig._maxFileSize;
		}

		long maxRequestSize = _maxRequestSize;

		if (multiPartConfig._maxRequestSize > 0) {
			maxRequestSize = multiPartConfig._maxRequestSize;
		}

		return new MultiPartConfig(
			fileSizeThreshold, location, maxFileSize, maxRequestSize);
	}

	private MultiPartConfig(
		boolean supported, int fileSizeThreshold, String location,
		long maxFileSize, long maxRequestSize) {

		_supported = supported;
		_fileSizeThreshold = fileSizeThreshold;
		_location = location;
		_maxFileSize = maxFileSize;
		_maxRequestSize = maxRequestSize;
	}

	private final int _fileSizeThreshold;
	private final String _location;
	private final long _maxFileSize;
	private final long _maxRequestSize;
	private final boolean _supported;

}