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
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.kimios.kernel.ws.pojo.DataTransaction;
import org.kimios.webservices.exceptions.DMServiceException;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: farf Date: 4/1/12 Time: 4:58 PM
 */
@Path("/filetransfer")
@WebService(targetNamespace = "http://kimios.org", serviceName = "FileTransferService")
@CrossOriginResourceSharing(allowAllOrigins = true)
@Api(value="/filetransfer", description = "Upload / Download Operations")
public interface FileTransferService
{
     @GET @ApiOperation(value ="")
    @Path("/startUploadTransaction")
    @Produces(MediaType.APPLICATION_JSON)
    public DataTransaction startUploadTransaction(
        @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
        @QueryParam(value = "documentId") @WebParam(name = "documentId") long documentId,
        @QueryParam(value = "isCompressed") @WebParam(name = "isCompressed") boolean isCompressed )
        throws DMServiceException;

     @GET @ApiOperation(value ="")
    @Path("/sendChunk")
    @Produces(MediaType.APPLICATION_JSON)
    public void sendChunk( @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
                           @QueryParam(value = "transactionId") @WebParam(
                               name = "transactionId") long transactionId,
                           @QueryParam(value = "data") @WebParam(name = "data") byte[] data )
        throws DMServiceException;

     @GET @ApiOperation(value ="")
    @Path("/endUploadTransaction")
    @Produces(MediaType.APPLICATION_JSON)
    public void endUploadTransaction( @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
                                      @QueryParam(value = "transactionId") @WebParam(
                                          name = "transactionId") long transactionId,
                                      @QueryParam(value = "md5") @WebParam(name = "md5") String md5,
                                      @QueryParam(value = "sha1") @WebParam(name = "sha1") String sha1 )
        throws DMServiceException;

     @GET @ApiOperation(value ="")
     @Path("/startDownloadTransaction")
     @Produces(MediaType.APPLICATION_JSON)
     public DataTransaction startDownloadTransaction(
             @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
             @QueryParam(value = "documentVersionId") @WebParam(name = "documentVersionId") long documentVersionId,
             @QueryParam(value = "isCompressed") @WebParam(name = "isCompressed") boolean isCompressed,
             @QueryParam(value = "convertToPdf") @WebParam(name = "convertToPdf") boolean convertToPdf
     ) throws DMServiceException;


    public byte[] getChunck( @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
                             @QueryParam(value = "transactionId") @WebParam(
                                 name = "transactionId") long transactionId,
                             @QueryParam(value = "offset") @WebParam(name = "offset") long offset,
                             @QueryParam(value = "chunkSize") @WebParam(name = "chunkSize") int chunkSize )
        throws DMServiceException;


    @POST @ApiOperation(value ="")
    @Path("/uploadDocument")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public void uploadDocument( @QueryParam("sessionId") String sessionId,
                                @QueryParam("transactionId") long transactionId,
                                @Multipart(value = "document") InputStream documentStream,
                                @Multipart(value = "md5", required = false) String hashMd5,
                                @Multipart(value = "sha1", required = false) String hashSha1 )
        throws DMServiceException;


    @GET @ApiOperation(value ="")
    @Path( "/downloadDocumentVersion" )
    @Produces( value = {MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON} )
    public Response downloadDocumentVersion( @QueryParam("sessionId") String sessionId,
                                                @QueryParam("transactionId") long transactionId,
                                                @DefaultValue("true") @QueryParam("inline") Boolean inline)
            throws DMServiceException;


     @GET @ApiOperation(value ="")
    @Path( "/downloadDocument" )
    @Produces( MediaType.APPLICATION_OCTET_STREAM )
    public Response downloadDocument( @QueryParam("sessionId") String sessionId,
                                      @QueryParam("transactionId") long transactionId,
                                      @DefaultValue("true") @QueryParam("inline") Boolean inline)
            throws DMServiceException;

     @GET @ApiOperation(value ="")
    @Path( "/downloadDocumentWithCustomFileName" )
    @Produces( MediaType.APPLICATION_OCTET_STREAM )
    public Response downloadDocument( @QueryParam("sessionId") String sessionId,
                                                @QueryParam("transactionId") long transactionId,
                                                @DefaultValue("true") @QueryParam("inline") Boolean inline,
                                                @DefaultValue("") @QueryParam("metaIds") List<Long> metaIds)
            throws DMServiceException;



     @GET @ApiOperation(value ="")
    @Path("/createTokenDownload")
    @Produces(MediaType.APPLICATION_JSON)
    public DataTransaction createTokenDownloadTransaction(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "documentVersionId") @WebParam(name = "documentVersionId") long documentVersionId)
            throws DMServiceException;


     @GET @ApiOperation(value ="")
    @Path( "/downloadDocumentByToken" )
    @Produces( { MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_FORM_URLENCODED } )
    public Response downloadDocumentByToken(
            @Context UriInfo uriInfo,
            @QueryParam(value = "token") String token,
            @QueryParam(value = "password") String password
    ) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path( "/downloadDocumentVersionPreview" )
    @Produces( value = {MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON} )
    public Response downloadDocumentVersionPreview( @QueryParam("sessionId") String sessionId,
                                             @QueryParam("transactionId") long transactionId,
                                             @DefaultValue("true") @QueryParam("inline") Boolean inline)
            throws DMServiceException;
}
