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
import React, {useMemo, useState} from 'react';

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
		}
		catch (e) {
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

const Main = ({
	addressValue = '',
	cityValue = '',
	countryValue = '',
	name,
	onChange,
	postalCodeValue = '',
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

	return (
		<div>
			<div>
				<div>
					<Component
						label="Search Location"
						onChange={(event) => {
							const value = {
								...values,
								searchLocationValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
						value={transformedSearchLocationValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Address"
						onChange={(event) => {
							const value = {
								...values,
								addressValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
						value={transformedAddressValue || ''}
						{...otherProps}
					/>
				</div>
			</div>
			<div>
				<div>
					<Component
						label="City"
						onChange={(event) => {
							const value = {
								...values,
								cityValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
						value={transformedCityValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="State"
						onChange={(event) => {
							const value = {
								...values,
								stateValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
						value={transformedStateValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Postal Code"
						onChange={(event) => {
							const value = {
								...values,
								postalCodeValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
						value={transformedPostalCodeValue || ''}
						{...otherProps}
					/>
				</div>
				<div>
					<Component
						label="Country"
						onChange={(event) => {
							const value = {
								...values,
								countryValue: event.target.value,
							};
							setValues(value);
							onChange(event, JSON.stringify(value));
						}}
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
