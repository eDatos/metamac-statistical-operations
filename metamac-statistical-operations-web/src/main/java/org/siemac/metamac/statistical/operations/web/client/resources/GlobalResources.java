package org.siemac.metamac.statistical.operations.web.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface GlobalResources extends ClientBundle {

    public static final GlobalResources RESOURCE = GWT.create(GlobalResources.class);

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/new_listgrid.png")
    ImageResource newListGrid();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/edit_listgrid.png")
    ImageResource editListGrid();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/save_listgrid.png")
    ImageResource saveListGrid();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/delete_listgrid.png")
    ImageResource deleteListGrid();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/cancel_listgrid.png")
    ImageResource cancelListGrid();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/info.png")
    ImageResource info();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/error.png")
    ImageResource error();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/ask.png")
    ImageResource ask();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/success.png")
    ImageResource success();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/publish_internally.png")
    ImageResource publishInternally();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/publish_externally.png")
    ImageResource publishExternally();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/unpublish_externally.png")
    ImageResource unpublishExternally();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/reload.png")
    ImageResource reload();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/link.png")
    ImageResource link();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/add_link.png")
    ImageResource addLink();

}
