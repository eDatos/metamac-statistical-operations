package org.siemac.metamac.statistical.operations.web.client.widgets;

import org.siemac.metamac.statistical.operations.web.client.OperationsWeb;
import org.siemac.metamac.statistical.operations.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ListGridToolStrip extends ToolStrip {

    private ToolStripButton          newButton;
    private ToolStripButton          deleteButton;
    private ToolStripButton          exportTsvButton;

    private DeleteConfirmationWindow deleteConfirmationWindow;

    public ListGridToolStrip(final String messageDeleteWindow) {
        super();
        setWidth100();

        deleteConfirmationWindow = new DeleteConfirmationWindow(new String(), messageDeleteWindow);
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);

        newButton = new ToolStripButton(OperationsWeb.getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        exportTsvButton = new CustomToolStripButton(OperationsWeb.getConstants().actionExportTsv(), org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.exportResource().getURL());

        deleteButton = new ToolStripButton(OperationsWeb.getConstants().actionDelete(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        deleteButton.setVisibility(Visibility.HIDDEN);
        deleteButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });

        exportTsvButton.setVisibility(Visibility.HIDDEN);

        addButton(newButton);
        addButton(exportTsvButton);
        addButton(deleteButton);
    }

    public ToolStripButton getNewButton() {
        return newButton;
    }

    public ToolStripButton getExportTsvButton() {
        return exportTsvButton;
    }

    public ToolStripButton getDeleteButton() {
        return deleteButton;
    }

    public DeleteConfirmationWindow getDeleteConfirmationWindow() {
        return deleteConfirmationWindow;
    }

}
