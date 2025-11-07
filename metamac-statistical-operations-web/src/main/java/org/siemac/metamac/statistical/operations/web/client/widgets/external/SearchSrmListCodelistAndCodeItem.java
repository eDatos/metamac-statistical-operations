package org.siemac.metamac.statistical.operations.web.client.widgets.external;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.operations.web.client.instance.view.handlers.InstanceUiHandlers;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchMultipleSrmItemWithSchemeFilterPaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

import java.util.List;

public abstract class SearchSrmListCodelistAndCodeItem extends ExternalItemListItem {

    private SearchMultipleSrmItemWithSchemeFilterPaginatedWindow window;
    protected InstanceUiHandlers                                 uiHandlers;

    public SearchSrmListCodelistAndCodeItem(String name, String title, int maxResults) {
        super(name, title, true);
        appendWindow(maxResults);
    }

    private void appendWindow(final int maxResults) {

        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                SearchPaginatedAction<SrmExternalResourceRestCriteria> filterSearchAction = new SearchPaginatedAction<SrmExternalResourceRestCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                        webCriteria.setOnlyLastVersion(window.getFilter().getSearchCriteria().isItemSchemeLastVersion());
                        retrieveItemSchemes(firstResult, maxResults, webCriteria);
                    }
                };

                window = new SearchMultipleSrmItemWithSchemeFilterPaginatedWindow(MetamacWebCommon.getConstants().resourceSelection(), maxResults, filterSearchAction,
                        new SearchPaginatedAction<SrmItemRestCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                                retrieveItems(firstResult, maxResults, webCriteria);
                            }
                        });

                window.retrieveItems();

                window.setSelectedResources(getSelectedRelatedResources());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setExternalItems(window.getSelectedResources());
                        window.markForDestroy();
                    }
                });
            }
        });
    }

    protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
        getUiHandlers().retrieveCodelists(getName(), webCriteria, firstResult, maxResults);
    }

    protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
        getUiHandlers().retrieveCodes(getName(), webCriteria, firstResult, maxResults);
    }

    public void setUiHandlers(InstanceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public InstanceUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setItems(ExternalItemsResult result) {
        setResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }

    private void setResources(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        if (window != null) {
            window.setResources(externalItemsDtos);
            window.refreshSourcePaginationInfo(firstResult, externalItemsDtos.size(), totalResults);
        }
    }

    public void setItemSchemes(ExternalItemsResult result) {
        setFilterResources(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }

    private void setFilterResources(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        if (window != null) {
            window.setFilterResources(externalItemsDtos);
            window.refreshFilterSourcePaginationInfo(firstResult, externalItemsDtos.size(), totalResults);
        }
    }
}
