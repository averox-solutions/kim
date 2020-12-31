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
import org.kimios.kernel.ws.pojo.*;
import org.kimios.kernel.ws.pojo.web.AuthenticationSourceParam;
import org.kimios.webservices.exceptions.DMServiceException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: farf Date: 4/1/12 Time: 5:00 PM
 */
@Path("/administration")
@WebService(targetNamespace = "http://kimios.org", serviceName = "AdministrationService")
@CrossOriginResourceSharing(allowAllOrigins = true)
@Api(value="/administration", description = "Administration Operations")
public interface AdministrationService
{
    @GET @ApiOperation(value ="")
    @Path("/getRoles")
    @Produces("application/json")
    public Role[] getRoles(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "role") @WebParam(name = "role") int role) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getUserRoles")
    @Produces("application/json")
    public Role[] getUserRoles(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/createRole")
    @Produces("application/json")
    public void createRole(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "role") @WebParam(name = "role") int role,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/deleteRole")
    @Produces("application/json")
    public void deleteRole(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "role") @WebParam(name = "role") int role,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAuthenticationSource")
    @Produces("application/json")
    public AuthenticationSource getAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "name") @WebParam(name = "name") String name) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAuthenticationSourceParamsXml")
    @Produces("application/json")
    public String getAuthenticationSourceParamsXml(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "className") @WebParam(name = "className") String className) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAuthenticationSourceParams")
    @Produces("application/json")
    public Map<String, String> getAuthenticationSourceParams(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "className") @WebParam(name = "className") String className) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/createAuthenticationSourceFromXml")
    @Produces("application/json")
    public void createAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "className") @WebParam(name = "className") String className,
            @QueryParam(value = "enableSso") @WebParam(name = "enableSso") boolean enableSso,
            @QueryParam(value = "enableMailCheck") @WebParam(name = "enableMailCheck") boolean enableMailCheck,
            @QueryParam(value = "xmlParameters") @WebParam(name = "xmlParameters") String xmlParameters)
            throws DMServiceException;


    @POST @ApiOperation(value ="")
    @Path("/createAuthenticationSource")
    @Produces("application/json")
    @Consumes("application/json")
    @WebMethod(operationName = "create-domain")
    public void createAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "className") @WebParam(name = "className") String className,
            @QueryParam(value = "enableSso") @WebParam(name = "enableSso") boolean enableSso,
            @QueryParam(value = "enableMailCheck") @WebParam(name = "enableMailCheck") boolean enableMailCheck,
            @WebParam(name = "parameters") Map<String, String> params)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/updateAuthenticationSourceFromXml")
    @Produces("application/json")
    public void updateAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "currentName") @WebParam(name = "currentName") String authenticationSourceName,
            @QueryParam(value = "className") @WebParam(name = "className") String className,
            @QueryParam(value = "enableSso") @WebParam(name = "enableSso") boolean enableSso,
            @QueryParam(value = "enableMailCheck") @WebParam(name = "enableMailCheck") boolean enableMailCheck,
            @QueryParam(value = "xmlParameters") @WebParam(name = "xmlParameters") String xmlParameters)
            throws DMServiceException;


    @POST @ApiOperation(value ="")
    @Path("/updateAuthenticationSource")
    @Produces("application/json")
    @Consumes("application/json")
    @WebMethod(operationName = "update-domain")
    public void updateAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "currentName") @WebParam(name = "currentName") String authenticationSourceName,
            @QueryParam(value = "className") @WebParam(name = "className") String className,
            @QueryParam(value = "enableSso") @WebParam(name = "enableSso") boolean enableSso,
            @QueryParam(value = "enableMailCheck") @WebParam(name = "enableMailCheck") boolean enableMailCheck,
            @WebParam(name = "params") Map<String, String>parameters)
            throws DMServiceException;

    @POST @ApiOperation(value ="")
    @Path("/updateAuthenticationSourceFromObj")
    @Produces("application/json")
    @Consumes("application/json")
    @WebMethod(operationName = "update-domain-obj-param")
    public void updateAuthenticationSource(@ApiParam() AuthenticationSourceParam authenticationSourceParam)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/deleteAuthenticationSource")
    @Produces("application/json")
    public void deleteAuthenticationSource(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "className") @WebParam(name = "className") String name) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAvailableAuthenticationSource")
    @Produces("application/json")
    public String getAvailableAuthenticationSourceXml(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/available-domain-types")
    @Produces("application/json")
    public List<String> getAvailableAuthenticationSources(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAvailableAuthenticationSourceParamsXml")
    @Produces("application/json")
    public String getAvailableAuthenticationSourceParamsXml(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "className") @WebParam(name = "className") String className) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAvailableAuthenticationSourceParams")
    @Produces("application/json")
    public List<String> getAvailableAuthenticationSourceParams(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "className") @WebParam(name = "className") String className) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/createUser")
    @Produces("application/json")
    public void createUser(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
            @QueryParam(value = "firstName") @WebParam(name = "firstName") String firstName,
            @QueryParam(value = "lastName") @WebParam(name = "lastName") String lastName,
            @QueryParam(value = "phoneNumber") @WebParam(name = "phoneNumber") String phoneNumber,
            @QueryParam(value = "mail") @WebParam(name = "mail") String mail,
            @QueryParam(value = "password") @WebParam(name = "password") String password,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName,
            @QueryParam(value = "enabled") @WebParam(name = "enabled") boolean enabled)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/updateUser")
    @Produces("application/json")
    public void updateUser(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
            @QueryParam(value = "firstName") @WebParam(name = "firstName") String firstName,
            @QueryParam(value = "lastName") @WebParam(name = "lastName") String lastName,
            @QueryParam(value = "phoneNumber") @WebParam(name = "phoneNumber") String phoneNumber,
            @QueryParam(value = "mail") @WebParam(name = "mail") String mail,
            @QueryParam(value = "password") @WebParam(name = "password") String password,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName,
            @QueryParam(value = "enabled") @WebParam(name = "enabled") boolean enabled)
            throws DMServiceException;

    @POST
    @Path("/updateUserEmails")
    @Consumes("application/json")
    @Produces("application/json")
    public void updateUserEmails(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
                           @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
                           @QueryParam(value = "authenticationSource") @WebParam(name = "authenticationSource") String authenticationSourceName,
                                 @WebParam(name = "emails") List<String> emails)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/deleteUser")
    @Produces("application/json")
    public void deleteUser(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/createGroup")
    @Produces("application/json")
    public void createGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/updateGroup")
    @Produces("application/json")
    public void updateGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "name") @WebParam(name = "name") String name,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/deleteGroup")
    @Produces("application/json")
    public void deleteGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/addUserToGroup")
    @Produces("application/json")
    public void addUserToGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/removeUserFromGroup")
    @Produces("application/json")
    public void removeUserFromGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "uid") @WebParam(name = "uid") String uid,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getManageableUser")
    @Produces("application/json")
    public User getManageableUser(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            String uid,
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getManageableUsers")
    @Produces("application/json")
    public User[] getManageableUsers(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getManageableGroup")
    @Produces("application/json")
    public Group getManageableGroup(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "gid") @WebParam(name = "gid") String gid,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getManageableGroups")
    @Produces("application/json")
    public Group[] getManageableGroups(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userId") @WebParam(name = "userId") String userId,
            @QueryParam(value = "authenticationSourceName") @WebParam(name = "authenticationSourceName")
            String authenticationSourceName) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/reindex")
    @Produces("application/json")
    public void reindex(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "path") @WebParam(name = "path") String path) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getReindexProgress")
    @Produces("application/json")
    public int getReindexProgress(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getCheckedOutDocuments")
    @Produces("application/json")
    public Document[] getCheckedOutDocuments(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/clearLock")
    @Produces("application/json")
    public void clearLock(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "documentId") @WebParam(name = "documentId") long documentId) throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/changeOwnership")
    @Produces("application/json")
    public void changeOwnership(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "dmEntityId") @WebParam(name = "dmEntityId") long dmEntityId,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getConnectedUsers")
    @Produces("application/json")
    public User[] getConnectedUsers(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getAllEnabledSessions")
    @Produces("application/json")
    public Session[] getAllEnabledSessions(
            @QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getEnabledSessions")
    @Produces("application/json")
    public Session[] getEnabledSessions(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/removeEnabledSession")
    @Produces("application/json")
    public void removeEnabledSession(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "sessionIdToRemove") @WebParam(name = "sessionIdToRemove") String sessionIdToRemove)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/removeEnabledSessions")
    @Produces("application/json")
    public void removeEnabledSessions(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userName") @WebParam(name = "userName") String userName,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/setUserAttribute")
    @Produces("application/json")
    public void setUserAttribute(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userId") @WebParam(name = "userId") String userId,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource,
            @QueryParam(value = "attributeName") @WebParam(name = "attributeName") String attributeName,
            @QueryParam(value = "attributeValue") @WebParam(name = "attributeValue") String attributeValue)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getUserAttribute")
    @Produces("application/json")
    public String getUserAttribute(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userId") @WebParam(name = "userId") String userId,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource,
            @QueryParam(value = "attributeName") @WebParam(name = "attributeName") String attributeName)
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/getUserByAttribute")
    @Produces("application/json")
    public User getUserByAttribute(@QueryParam(value = "sessionId") @WebParam(name = "sessionId") String sessionId,
            @QueryParam(value = "userSource") @WebParam(name = "userSource") String userSource,
            @QueryParam(value = "attributeName") @WebParam(name = "attributeName") String attributeName,
            @QueryParam(value = "attributeValue") @WebParam(name = "attributeValue") String attributeValue)
            throws DMServiceException;


    @GET @ApiOperation(value ="")
    @Path("/disableServiceLogging")
    public void disableServiceLogging()
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/enableServiceLogging")
    public void enableServiceLogging()
            throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/listLoggers")
    @Produces("application/json")
    public HashMap<String,String> listLoggers() throws DMServiceException;

    @GET @ApiOperation(value ="")
    @Path("/setLoggerLevel")
    @Produces("application/json")
    public void setLoggerLevel(@QueryParam(value = "loggerName") @WebParam(name = "loggerName") String loggerName,
                                   @QueryParam(value = "loggerLevel") @WebParam(name = "loggerLevel") String loggerLevel)
        throws DMServiceException;


}
