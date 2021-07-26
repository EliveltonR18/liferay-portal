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

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import React from 'react';

import Modal from '../../../src/main/resources/META-INF/resources/js/modals/ImportStructureModal';

const ImportStructureModal = () => (
	<Modal namespace="test" onClose={jest.fn()} />
);

describe('Import Structure Modal', () => {
	afterEach(() => {
		cleanup();
	});

	it('render the title modal', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const title = await waitForElement(() => getByText('import-structure'));

		expect(title).toBeInTheDocument(true);
	});

	it('render the info alert', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const infoAlert = await waitForElement(() =>
			getByText(
				'once-you-click-import-the-process-will-run-in-the-background-this-may-take-a-while'
			)
		);

		expect(infoAlert).toBeInTheDocument(true);
	});

	it('render name input', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const inputName = await waitForElement(() => getByText('name'));

		expect(inputName).toBeInTheDocument(true);
	});

	it('input name has correct name prop', async () => {
		const {getByLabelText} = render(<ImportStructureModal />);

		const inputName = await waitForElement(() => getByLabelText('name'));

		expect(inputName).toHaveAttribute('name', 'test_name');
	});

	it('render json input', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const jsonInput = await waitForElement(() => getByText('json-file'));

		expect(jsonInput).toBeInTheDocument(true);
	});

	it('input json file has correct name prop', async () => {
		render(<ImportStructureModal />);

		const inputs = await waitForElement(() =>
			document.getElementsByName('test_jsonFile')
		);

		expect(!!inputs).toBe(true);
	});

	it('render cancel button in footer', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const buttonCancel = await waitForElement(() => getByText('cancel'));

		expect(buttonCancel).toBeInTheDocument(true);
	});

	it('the cancel button close modal', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const buttonCancel = await waitForElement(() => getByText('cancel'));

		fireEvent.click(buttonCancel);

		const body = document.getElementsByTagName('body');
		const hasModalOpenClass =
			body.item(0).getAttribute('class') === 'modal-open';

		expect(hasModalOpenClass).toBe(false);
	});

	it('render import button in footer', async () => {
		const {getByText} = render(<ImportStructureModal />);

		const buttonImport = await waitForElement(() => getByText('import'));

		expect(buttonImport).toBeInTheDocument(true);
	});
});
