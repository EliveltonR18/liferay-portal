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

import {ClayInput} from '@clayui/form';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import Component from './SearchLocationField.es';

function transformValues({
	addressValue,
	cityValue,
	countryValue,
	postalCodeValue,
	searchLocationValue,
	stateValue,
	value,
}) {
	if (value && typeof value === 'string') {
		try {
			const values = JSON.parse(value);
			addressValue = values.addressValue;
			cityValue = values.cityValue;
			countryValue = values.countryValue;
			postalCodeValue = values.postalCodeValue;
			searchLocationValue = values.searchLocationValue;
			stateValue = values.stateValue;
		} catch (e) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return value
		? [
				addressValue,
				cityValue,
				countryValue,
				postalCodeValue,
				searchLocationValue,
				stateValue,
		  ]
		: [];
}

let autoComplete;

const loadScript = (url, callback, readOnly) => {
	const script = document.createElement('script');
	script.type = 'text/javascript';

	if (script.readyState) {
		script.onreadystatechange = function () {
			if (
				script.readyState === 'loaded' ||
				script.readyState === 'complete'
			) {
				script.onreadystatechange = null;
				callback();
			}
		};
	} else {
		script.onload = () => callback();
	}
	script.src = url;
	const element = document.getElementById(
		'search_location-input_fieldDetails'
	);
	const hasChild = element.getElementsByTagName('script').length > 0;
	/* eslint-disable-next-line no-unused-expressions */
	element && !readOnly && !hasChild ? element.appendChild(script) : null;
};

function handleScriptLoad(updateValuesByPlace) {
	const element = document.getElementById(
		'search_location-input_fieldDetails'
	);
	autoComplete = new window.google.maps.places.Autocomplete(element);
	autoComplete.setFields(['address_component', 'formatted_address']);
	autoComplete.addListener('place_changed', () =>
		handlePlaceSelect(updateValuesByPlace)
	);
}

async function handlePlaceSelect(updateValuesByPlace) {
	const place = autoComplete.getPlace();
	const addressComponents = place?.address_components;
	const addressTypes = {
		administrative_area_level_1: 'short_name',
		administrative_area_level_2: 'long_name',
		country: 'long_name',
		postal_code: 'short_name',
		route: 'long_name',
	};
	const address = {
		country: '',
		administrative_area_level_1: '',
		administrative_area_level_2: '',
		formatted_address: place?.formatted_address,
		postal_code: '',
		route: '',
	};

	for (let i = 0; i < addressComponents.length; i++) {
		const addressType = addressComponents[i].types[0];

		address[addressType] = addressComponents[i][addressTypes[addressType]];
	}

	updateValuesByPlace({
		addressValue: address.route,
		cityValue: address.administrative_area_level_2,
		countryValue: address.country,
		postalCodeValue: address.postal_code,
		searchLocationValue: address.formatted_address,
		stateValue: address.administrative_area_level_1,
	});
}

const Main = ({
	addressValue = '',
	cityValue = '',
	countryValue = '',
	name,
	onChange,
	postalCodeValue = '',
	readOnly,
	searchLocationValue = '',
	stateValue = '',
	value,
	...otherProps
}) => {
	delete otherProps.label;
	const [
		transformedAddressValue,
		transformedCityValue,
		transformedCountryValue,
		transformedPostalCodeValue,
		transformedSearchLocationValue,
		transformedStateValue,
	] = useMemo(() => {
		return transformValues({
			addressValue,
			cityValue,
			countryValue,
			postalCodeValue,
			searchLocationValue,
			stateValue,
			value,
		});
	}, [
		addressValue,
		cityValue,
		countryValue,
		postalCodeValue,
		searchLocationValue,
		stateValue,
		value,
	]);

	const [values, setValues] = useState({});
	const autoCompleteRef = useRef(null);

	// remover api key

	const GOOGLE_API_KEY = 'INSERT_YOUR_API_KEY';
	const url = `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_API_KEY}&libraries=places`;

	const updateValuesByPlace = (values) => {
		setValues(values);
		onChange({target: {value: JSON.stringify(values)}});
	};

	const updateValues = useCallback(
		(key, valueProp) => {
			const value = {
				...values,
				[key]: valueProp,
			};
			setValues(value);
			onChange({target: {value: JSON.stringify(value)}});
		},
		[values, setValues, onChange]
	);

	useEffect(() => {
		loadScript(url, () => handleScriptLoad(updateValuesByPlace), readOnly);
	}, [url, updateValues, readOnly]);

	return (
		<div>
			<div>
				<div>
					<Component
						label="Search Location"
						name="search_location-input"
						onChange={(event) =>
							updateValues(
								'searchLocationValue',
								event.target.value
							)
						}
						readOnly={readOnly}
						ref={autoCompleteRef}
						value={transformedSearchLocationValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Address"
						name="address-input"
						onChange={(event) =>
							updateValues('addressValue', event.target.value)
						}
						readOnly={readOnly}
						value={transformedAddressValue || ''}
						{...otherProps}
					/>
				</div>
			</div>
			<div>
				<div>
					<Component
						label="City"
						name="city-input"
						onChange={(event) =>
							updateValues('cityValue', event.target.value)
						}
						readOnly={readOnly}
						value={transformedCityValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="State"
						name="state-input"
						onChange={(event) =>
							updateValues('stateValue', event.target.value)
						}
						readOnly={readOnly}
						value={transformedStateValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Postal Code"
						name="postal_code-input"
						onChange={(event) =>
							updateValues('postalCodeValue', event.target.value)
						}
						readOnly={readOnly}
						value={transformedPostalCodeValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Country"
						name="country-input"
						onChange={(event) =>
							updateValues('countryValue', event.target.value)
						}
						readOnly={readOnly}
						value={transformedCountryValue || ''}
						{...otherProps}
					/>
				</div>
			</div>
			<ClayInput name={name} type="hidden" value={value} />
		</div>
	);
};

Main.displayName = 'SearchLocation';

export default Main;
