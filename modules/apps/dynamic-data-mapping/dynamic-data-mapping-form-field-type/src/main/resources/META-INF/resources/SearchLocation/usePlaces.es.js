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

import {useEffect, useRef, useState} from 'react';

const loadScript = (readOnly, elementId, googlePlacesAPIKey, callback) => {
	const script = document.createElement('script');
	script.setAttribute('type', 'text/javascript');
	const scriptReadyState = script.getAttribute('readyState');

	if (scriptReadyState) {
		script.setAttribute('onreadystatechange', () => {
			if (
				scriptReadyState === 'loaded' ||
				scriptReadyState === 'complete'
			) {
				script.setAttribute('onreadystatechange', null);
				callback();
			}
		});
	}
	else {
		script.onload = () => callback();
	}

	let url = 'https://maps.googleapis.com/maps/api/js?libraries=places';

	if (googlePlacesAPIKey) {
		url += '&key=' + googlePlacesAPIKey;
	}

	script.setAttribute('src', url);
	const element = document.getElementById(elementId);
	/* eslint-disable-next-line no-unused-expressions */
	element && !readOnly ? element.appendChild(script) : null;
};

function handleScriptLoad(autoComplete, elementId, setListener) {
	const element = document.getElementById(elementId);
	autoComplete.current = new window.google.maps.places.Autocomplete(element);
	autoComplete.current.setFields(['address_component', 'formatted_address']);
	const listener = autoComplete.current.addListener(
		'place_changed',
		() => {}
	);
	setListener(listener);
}

const usePlaces = ({elementId, googlePlacesAPIKey, isReadOnly}) => {
	const autoComplete = useRef();
	const [listener, setListener] = useState();

	useEffect(() => {
		loadScript(isReadOnly, elementId, googlePlacesAPIKey, () =>
			handleScriptLoad(autoComplete, elementId, setListener)
		);

		return () => {
			window.google.maps.event.removeListener(listener);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export default usePlaces;
