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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import React, {useContext, useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import {ItemTypes} from '../../../utils/itemTypes';
import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import {DragBlockItem, TName, TObjectLayoutBox} from '../types';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import ObjectLayoutBox from './ObjectLayoutBox';
import ObjectLayoutRelationship from './ObjectLayoutRelationship';

interface IObjectLayoutTabProps extends React.HTMLAttributes<HTMLElement> {
	name: TName;
	objectLayoutBoxes: TObjectLayoutBox[];
	objectRelationshipId: number;
	onClickAddBlock: () => void;
	tabIndex: number;
}

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

const ObjectLayoutTab: React.FC<IObjectLayoutTabProps> = ({
	name,
	objectLayoutBoxes,
	objectRelationshipId,
	onClickAddBlock,
	tabIndex,
}) => {
	const ref = useRef<HTMLDivElement>(null);
	const [
		{
			isViewOnly,
			objectLayout: {objectLayoutTabs},
		},
		dispatch,
	] = useContext(LayoutContext);

	const [_, drop] = useDrop({
		accept: [ItemTypes.BLOCK, ItemTypes.TAB],
		collect: (monitor) => ({
			handlerId: monitor.getHandlerId(),
		}),
		drop: (
			{
				boxIndex: itemBoxIndex,
				collapsable,
				name: itemName,
				objectLayoutRows,
				tabIndex: itemTabIndex,
			}: DragBlockItem,
			monitor
		) => {
			if (monitor.getItemType() === ItemTypes.BLOCK) {
				dispatch({
					payload: {
						boxIndex: itemBoxIndex,
						collapsable,
						itemTabIndex,
						name: itemName,
						objectLayoutRows,
						tabIndex,
					},
					type: TYPES.CHANGE_OBJECT_LAYOUT_BOX_TAB_INDEX,
				});
			}
		},
		hover: (item: any, monitor) => {
			if (!ref.current) {
				return;
			}

			const dragIndex = item.tabIndex;
			const hoverIndex = tabIndex;

			if (dragIndex === hoverIndex) {
				return;
			}

			if (
				!objectLayoutTabs.some(
					(tab) =>
						tab.name === item.name &&
						tab.objectRelationshipId === objectRelationshipId
				)
			) {
				return;
			}

			const hoverBoundingRect = ref.current?.getBoundingClientRect();

			const hoverMiddleY =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			const clientOffset = monitor.getClientOffset();

			const hoverClientY = clientOffset!.y - hoverBoundingRect.top;

			if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
				return;
			}

			if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
				return;
			}

			dispatch({
				payload: {
					dragIndex,
					hoverIndex,
				},
				type: TYPES.CHANGE_OBJECT_LAYOUT_TAB_INDEX,
			});

			item.boxIndex = hoverIndex;
		},
	});

	const [{opacity}, drag] = useDrag({
		collect: (monitor) => ({
			opacity: monitor.isDragging() ? 0.4 : 1,
		}),
		item: {
			name,
			objectRelationshipId,
			tabIndex,
			type: ItemTypes.TAB,
		},
	});

	drag(drop(ref));

	const isRelationshipType = objectRelationshipId !== 0;
	const labelDisplayType = isRelationshipType ? 'warning' : 'info';

	return (
		<Panel
			className="layout-tab__tab"
			key={`layout_${tabIndex}`}
			ref={ref}
			style={{opacity}}
		>
			<Panel.Header
				contentLeft={
					<ClayLabel displayType={labelDisplayType}>
						{isRelationshipType
							? Liferay.Language.get('relationships')
							: Liferay.Language.get('fields')}
					</ClayLabel>
				}
				contentRight={
					<>
						{!isRelationshipType && (
							<ClayButton
								disabled={isViewOnly}
								displayType="secondary"
								onClick={onClickAddBlock}
								small
							>
								<ClayIcon symbol="plus" />

								<span className="ml-2">
									{Liferay.Language.get('add-block')}
								</span>
							</ClayButton>
						)}

						<DropdownWithDeleteButton
							onClick={() => {
								dispatch({
									payload: {
										tabIndex,
									},
									type: TYPES.DELETE_OBJECT_LAYOUT_TAB,
								});
							}}
						/>
					</>
				}
				title={name[defaultLanguageId]}
			/>

			{!!objectLayoutBoxes?.length && !isRelationshipType && (
				<Panel.Body>
					{objectLayoutBoxes.map(
						({collapsable, name, objectLayoutRows}, boxIndex) => (
							<ObjectLayoutBox
								boxIndex={boxIndex}
								collapsable={collapsable}
								key={`box_${boxIndex}`}
								name={name}
								objectLayoutRows={objectLayoutRows}
								tabIndex={tabIndex}
							/>
						)
					)}
				</Panel.Body>
			)}

			{isRelationshipType && (
				<Panel.Body>
					<ObjectLayoutRelationship
						objectRelationshipId={objectRelationshipId}
					/>
				</Panel.Body>
			)}
		</Panel>
	);
};

export default ObjectLayoutTab;
