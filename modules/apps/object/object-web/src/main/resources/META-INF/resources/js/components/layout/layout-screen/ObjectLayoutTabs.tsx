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

import {useModal} from '@clayui/modal';
import React, {useContext, useState} from 'react';

import LayoutContext from '../context';
import ModalAddObjectLayoutBox from './ModalAddObjectLayoutBox';
import ObjectLayoutTab from './ObjectLayoutTab';

const ObjectLayoutTabs: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [{objectLayout}] = useContext(LayoutContext);
	const [visibleModal, setVisibleModal] = useState(false);
	const [selectedTabIndex, setSelectedTabIndex] = useState(0);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			{objectLayout?.objectLayoutTabs?.map(
				({name, objectLayoutBoxes, objectRelationshipId}, tabIndex) => {
					return (
						<ObjectLayoutTab
							key={tabIndex}
							name={name}
							objectLayoutBoxes={objectLayoutBoxes}
							objectRelationshipId={objectRelationshipId}
							onClickAddBlock={() => {
								setVisibleModal(true);
								setSelectedTabIndex(tabIndex);
							}}
							tabIndex={tabIndex}
						/>
					);
				}
			)}

			{visibleModal && (
				<ModalAddObjectLayoutBox
					observer={observer}
					onClose={onClose}
					tabIndex={selectedTabIndex}
				/>
			)}
		</>
	);
};

export default ObjectLayoutTabs;
