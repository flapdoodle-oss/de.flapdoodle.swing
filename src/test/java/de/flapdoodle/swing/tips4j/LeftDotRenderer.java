package de.flapdoodle.swing.tips4j;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/*
 *  Simple text renderer that will display the text right justified with
 *  leading dots when the column width is not large enough to display the
 *  entire text. 
 */
class LeftDotRenderer extends DefaultTableCellRenderer
{
	public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		//  Determine the width available to render the text

		int availableWidth = table.getColumnModel().getColumn(column).getWidth();
		availableWidth -= table.getIntercellSpacing().getWidth();
		Insets borderInsets = getBorder().getBorderInsets((Component)this);
		availableWidth -= (borderInsets.left + borderInsets.right);
		String cellText = getText();
		FontMetrics fm = getFontMetrics( getFont() );

		//  Not enough space so start rendering from the end of the string
		//  until all the space is used up

		if (fm.stringWidth(cellText) > availableWidth)
		{
			String dots = "...";
			int textWidth = fm.stringWidth( dots );
			int i = cellText.length() - 1;

			for (; i > 0; i--)
			{
				textWidth += fm.charWidth(cellText.charAt(i));

				if (textWidth > availableWidth)
				{
					break;
				}
			}

			setText( dots + cellText.substring(i + 1) );
		}

		return this;
	}
}
