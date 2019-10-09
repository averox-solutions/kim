/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2015  DevLib'
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * aong with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.kimios.kernel.index.query.model;

import org.kimios.kernel.ws.pojo.DMEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fabien Alin <a href="mailto:fabien.alin@gmail.com">fabien.alin@gmail.com</a>
 */
public class SearchResponse
{

    public SearchResponse()
    {
    }

    public SearchResponse( int results, List<DMEntity> rows )
    {
        this.results = results;
        this.rows = rows;
    }

    public SearchResponse( List<Long> documentIds )
    {
        this.documentIds = documentIds;
    }

    private int results;

    private SearchRequest temporaryRequest;

    private boolean facetResponse;

    private List<DMEntity> rows;

    private List<Long> documentIds;

    private String virtualPath = "";

    private HashMap facetsData;

    private HashMap tagsfacetsData;

    public Map getFacetsData()
    {
        return facetsData;
    }

    public void setFacetsData( HashMap facetsData )
    {
        this.facetsData = facetsData;
    }

    public HashMap getTagsfacetsData() {
        return tagsfacetsData;
    }

    public void setTagsfacetsData(HashMap tagsfacetsData) {
        this.tagsfacetsData = tagsfacetsData;
    }

    public boolean isFacetResponse()
    {
        return facetResponse;
    }

    public void setFacetResponse( boolean facetResponse )
    {
        this.facetResponse = facetResponse;
    }

    public List<Long> getDocumentIds()
    {
        return documentIds;
    }

    public void setDocumentIds( List<Long> documentIds )
    {
        this.documentIds = documentIds;
    }

    public int getResults()
    {
        return results;
    }

    public void setResults( int results )
    {
        this.results = results;
    }

    public List<DMEntity> getRows()
    {
        return rows;
    }

    public void setRows( List<DMEntity> rows )
    {
        this.rows = rows;
    }

    public String getVirtualPath()
    {
        return virtualPath;
    }

    public void setVirtualPath( String virtualPath )
    {
        this.virtualPath = virtualPath;
    }

    public SearchRequest getTemporaryRequest() {
        return temporaryRequest;
    }

    public void setTemporaryRequest(SearchRequest temporaryRequest) {
        this.temporaryRequest = temporaryRequest;
    }
}
