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
package org.kimios.webservices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.kimios.kernel.ws.pojo.Log;
import org.kimios.kernel.ws.pojo.UpdateNoticeMessage;
import org.kimios.webservices.exceptions.DMServiceException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 */
@Path("/log")
@WebService(targetNamespace = "http://kimios.org", serviceName = "LogService")
@CrossOriginResourceSharing(allowAllOrigins = true)
@Api(value="/log", description = "Log Operations")
public interface LogService
{
    @GET @ApiOperation(value ="")
    @Path("/getDocumentLogs")
    @Produces("application/json")
    public Log[] getDocumentLogs(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "documentId") @WebParam(name = "documentId") long documentId) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getFolderLogs")
    @Produces("application/json")
    public Log[] getFolderLogs(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "folderId") @WebParam(name = "folderId") long folderId) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getWorkspaceLogs")
    @Produces("application/json")
    public Log[] getWorkspaceLogs(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "workspaceId") @WebParam(name = "workspaceId") long workspaceId)
            throws DMServiceException;

    @POST @ApiOperation(value ="")
    @Path("/sendUpdateNotice")
    @Produces("application/json")
    @Consumes("application/json")
    @WebMethod(operationName = "send-update-notice")
    public UpdateNoticeMessage sendUpdateNotice(@ApiParam() UpdateNoticeMessage updateNoticeMessage)
            throws DMServiceException;
}
