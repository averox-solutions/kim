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
package org.kimios.kernel.controller.impl;

import org.apache.commons.lang.StringUtils;
import org.kimios.exceptions.ConfigException;
import org.kimios.kernel.controller.AKimiosController;
import org.kimios.kernel.controller.IAdministrationController;
import org.kimios.kernel.dms.model.*;
import org.kimios.api.events.annotations.DmsEvent;
import org.kimios.api.events.annotations.DmsEventName;
import org.kimios.api.events.annotations.DmsEventOccur;
import org.kimios.exceptions.AccessDeniedException;
import org.kimios.exceptions.DataSourceException;
import org.kimios.kernel.events.model.EventContext;
import org.kimios.kernel.hibernate.HFactory;
import org.kimios.kernel.security.model.Role;
import org.kimios.kernel.security.model.SecurityEntity;
import org.kimios.kernel.security.model.SecurityEntityType;
import org.kimios.kernel.security.model.Session;
import org.kimios.kernel.security.SessionManager;
import org.kimios.kernel.user.model.*;
import org.springframework.transaction.annotation.Transactional;

;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/*
 * 
 * 
 *  Administration Controller  :
 *  
 *    - Here are all of the administration functionnalities (domain management, user and group, roles)
 * 
 */
@Transactional
public class AdministrationController extends AKimiosController implements IAdministrationController
{
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getRoles(org.kimios.kernel.security.Session, int)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getRoles(org.kimios.kernel.security.Session, int)
    */
    public Vector<Role> getRoles(Session session, int role) throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        } else {
            return securityFactoryInstantiator.getRoleFactory().getRoles(role);
        }
    }

    /**
     * Return users from a role
     */
    public Vector<Role> getRoles(Session session, String userName, String userSource)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        } else {
            return securityFactoryInstantiator.getRoleFactory().getRoles(userName, userSource);
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#createRole(org.kimios.kernel.security.Session, int, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#createRole(org.kimios.kernel.security.Session, int, java.lang.String, java.lang.String)
    */
    public void createRole(Session session, int role, String userName, String userSource)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        } else {
            securityFactoryInstantiator.getRoleFactory().saveRole(new Role(role, userName, userSource));
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#deleteRole(org.kimios.kernel.security.Session, int, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#deleteRole(org.kimios.kernel.security.Session, int, java.lang.String, java.lang.String)
    */
    public void deleteRole(Session session, int role, String userName, String userSource)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        } else {
            if (session.getUserName().equals(userName) && session.getUserSource().equals(userSource)) {
                throw new AccessDeniedException();
            }
            securityFactoryInstantiator.getRoleFactory().deleteRole(new Role(role, userName, userSource));
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String)
    */
    public AuthenticationSource getAuthenticationSource(Session session, String name) throws AccessDeniedException,
            ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(name);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAuthenticationSourceParams(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    @Deprecated
    public String getAuthenticationSourceParamsXml(Session session, String name, String className) throws ConfigException,
            DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            return authFactoryInstantiator.getAuthenticationSourceParamsFactory()
                    .getParamsXml(name);
        } catch (Exception e) {
            throw new ConfigException(e.getMessage());
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAuthenticationSourceParams(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public Map<String, String> getAuthenticationSourceParams(Session session, String name, String className) throws ConfigException,
            DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            return authFactoryInstantiator.getAuthenticationSourceParamsFactory()
                    .getParams(name);
        } catch (Exception e) {
            throw new ConfigException(e.getMessage());
        }
    }


    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#createAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String)
    */
    public void createAuthenticationSource(Session session, String name, String className, boolean enableSso, boolean enableMailCheck, String xmlParameters)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            AuthenticationSource authenticationSource = (AuthenticationSource) Class.forName(className).newInstance();
            authenticationSource.setName(name);
            authenticationSource.setEnableSSOCheck(enableSso);
            authenticationSource.setEnableAuthByEmail(enableMailCheck);
            authFactoryInstantiator.getAuthenticationSourceFactory()
                    .saveAuthenticationSource(authenticationSource, className);
            authFactoryInstantiator.getAuthenticationSourceParamsFactory().createParams(name, xmlParameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigException(e.getMessage());
        }
    }

    public void createAuthenticationSource(Session session, String name, String className, boolean enableSso, boolean enableMailCheck,
                                           Map<String, String> parameters)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            AuthenticationSource authenticationSource = (AuthenticationSource) Class.forName(className).newInstance();
            authenticationSource.setName(name);
            authenticationSource.setEnableSSOCheck(enableSso);
            authenticationSource.setEnableAuthByEmail(enableMailCheck);
            authFactoryInstantiator.getAuthenticationSourceFactory()
                    .saveAuthenticationSource(authenticationSource, className);
            authFactoryInstantiator.getAuthenticationSourceParamsFactory().createParams(name, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigException(e.getMessage());
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#updateAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    */
    public void updateAuthenticationSource(Session session, String name, String className, boolean enableSso,
                                           boolean enableMailCheck, String xmlParameters)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            authFactoryInstantiator.getAuthenticationSourceParamsFactory().updateParams(name, xmlParameters, enableSso, enableMailCheck);
        } catch (Exception e) {
            throw new ConfigException(e.getMessage());
        }
    }

    /* (non-Javadoc)
   * @see org.kimios.kernel.controller.impl.IAdministrationController#updateAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
    public void updateAuthenticationSource(Session session, String name, String className, boolean enableSso,
                                           boolean enableMailCheck, Map<String, String> params)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        try {
            authFactoryInstantiator.getAuthenticationSourceParamsFactory().updateParams(name, params, className, enableSso, enableMailCheck);
        } catch (Exception e) {
            throw new ConfigException(e.getMessage());
        }
    }


    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#deleteAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String)
    */
    public void deleteAuthenticationSource(Session session, String name) throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(),
                session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        AuthenticationSource source =
                authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSource(name);
        authFactoryInstantiator.getAuthenticationSourceFactory().deleteAuthenticationSource(source);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAvailableAuthenticationSource(org.kimios.kernel.security.Session)
    */
    public String getAvailableAuthenticationSourceXml(Session session) throws ConfigException, DataSourceException,
            AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(),
                session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAvailableAuthenticationSourceXml();
    }

    public List<String> getAvailableAuthenticationSource(Session session) throws ConfigException, DataSourceException,
            AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(),
                session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAvailableAuthenticationSource();
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAvailableAuthenticationSourceParams(org.kimios.kernel.security.Session, java.lang.String)
    */
    @Deprecated
    public String getAvailableAuthenticationSourceParamsXml(Session session, String className) throws ConfigException,
            DataSourceException, AccessDeniedException, ClassNotFoundException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(),
                session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAvailableAuthenticationSourceParamsXml(className);
    }


    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getAvailableAuthenticationSourceParams(org.kimios.kernel.security.Session, java.lang.String)
    */
    public List<String> getAvailableAuthenticationSourceParams(Session session, String className) throws ConfigException,
            DataSourceException, AccessDeniedException, ClassNotFoundException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(),
                session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAvailableAuthenticationSourceParams(className);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#updateAuthenticationSource(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public void updateAuthenticationSource(Session session, String name, String newName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        AuthenticationSource source =
                authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSource(name);
        authFactoryInstantiator.getAuthenticationSourceFactory().updateAuthenticationSource(source);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#createUser(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.USER_CREATE })
    public void createUser(Session session, String uid, String firstName, String lastName, String phoneNumber, String mail, String password,
            String authenticationSourceName, boolean enabled)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory();
        f.saveUser(new User(uid, firstName, lastName, phoneNumber, new Date(), mail, authenticationSourceName, enabled),
                password);

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("user", uid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#updateUser(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.USER_UPDATE })
    public void updateUser(Session session, String uid, String firstName, String lastName, String phoneNumber, String mail, String password,
            String authenticationSourceName, boolean enabled)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        boolean isHimself =
                uid.equals(session.getUserName()) && authenticationSourceName.equals(session.getUserSource());
        boolean isAdmin = securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) != null;
        if (!isHimself && !isAdmin) {
            throw new AccessDeniedException();
        }
        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory();
        User u = f.getUser(uid);
        u.setMail(mail);
        u.setUid(uid);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setName(firstName + " " + lastName);
        u.setPhoneNumber(phoneNumber);
        u.setEnabled(enabled);
        f.updateUser(u, password);

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("user", uid);
    }

    @DmsEvent(eventName = { DmsEventName.USER_UPDATE })
    public void updateUserEmails(Session session, String uid, String authenticationSource, List<String> emails)
            throws AccessDeniedException, ConfigException, DataSourceException
    {

        boolean isHimself =
                uid.equals(session.getUserName()) && authenticationSource.equals(session.getUserSource());
        boolean isAdmin = securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) != null;
        if (!isHimself && !isAdmin) {
            throw new AccessDeniedException();
        }
        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSource)
                .getUserFactory();


        User u = f.getUser(uid);
        f.addUserEmails(uid, emails);

        EventContext.addParameter("source", authenticationSource);
        EventContext.addParameter("user", uid);
    }


    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#deleteUser(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.USER_DELETE })
    public void deleteUser(Session session, String uid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        // clean user permissions
        this.deleteUserPermissions(session, uid, authenticationSourceName);

        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory();
        f.deleteUser(new User(uid, authenticationSourceName));

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("user", uid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#createGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.GROUP_CREATE })
    public void createGroup(Session session, String gid, String name, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        GroupFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName).getGroupFactory();
        f.saveGroup(new Group(gid, name, authenticationSourceName));

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("group", gid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#updateGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.GROUP_UPDATE })
    public void updateGroup(Session session, String gid, String name, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        GroupFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName).getGroupFactory();
        f.updateGroup(new Group(gid, name, authenticationSourceName));

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("group", gid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#deleteGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.GROUP_DELETE })
    public void deleteGroup(Session session, String gid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        GroupFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName).getGroupFactory();
        f.deleteGroup(new Group(gid, "", authenticationSourceName));

        EventContext.addParameter("source", authenticationSourceName);
        EventContext.addParameter("group", gid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#addUserToGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.USER_GROUP_ADD })
    public void addUserToGroup(Session session, String uid, String gid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory();
        f.addUserToGroup(new User(uid, authenticationSourceName),
                new Group(gid, "", authenticationSourceName));
        EventContext.addParameter("group", gid);
        EventContext.addParameter("user", uid);
        EventContext.addParameter("source", authenticationSourceName);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#removeUserFromGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String, java.lang.String)
    */
    @DmsEvent(eventName = { DmsEventName.USER_GROUP_REMOVE })
    public void removeUserFromGroup(Session session, String uid, String gid, String authenticationSourceName)
            throws AccessDeniedException,
            ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        UserFactory f = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory();
        f.removeUserFromGroup(new User(uid, authenticationSourceName),
                new Group(gid, "", authenticationSourceName));

        EventContext.addParameter("group", gid);
        EventContext.addParameter("user", uid);
        EventContext.addParameter("source", authenticationSourceName);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getUser(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getUser(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public User getUser(Session session, String uid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        User user = authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getUserFactory().getUser(uid);
        if (user != null) {
            HFactory.initializeAndUnproxy(user.getEmails());
        }
        return user;
    }

    /**
     * Get users from a gid and authentication source
     */
    public Vector<User> getUsers(Session session, String gid,
            String authenticationSourceName) throws ConfigException,
            DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN,
                session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        Group group = authFactoryInstantiator
                .getAuthenticationSourceFactory().getAuthenticationSource(
                        authenticationSourceName).getGroupFactory().getGroup(
                        gid);
        return authFactoryInstantiator
                .getAuthenticationSourceFactory().getAuthenticationSource(
                        authenticationSourceName).getUserFactory().getUsers(
                        group);
    }

    /**
     * Get users from an authentication source
     */
    public Vector<User> getUsers(Session session,
                                 String authenticationSourceName) throws ConfigException,
            DataSourceException, AccessDeniedException {
        if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN,
                session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator
                .getAuthenticationSourceFactory().getAuthenticationSource(
                        authenticationSourceName).getUserFactory().getUsers();
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getGroup(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public Group getGroup(Session session, String gid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getGroupFactory().getGroup(gid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getGroups(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getGroups(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public Vector<Group> getGroups(Session session, String userUid, String authenticationSourceName)
            throws AccessDeniedException, ConfigException,
            DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        return authFactoryInstantiator.getAuthenticationSourceFactory()
                .getAuthenticationSource(authenticationSourceName)
                .getGroupFactory().getGroups(userUid);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getCheckedOutDocuments(org.kimios.kernel.security.Session)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getCheckedOutDocuments(org.kimios.kernel.security.Session)
    */
    public List<Document> getCheckedOutDocuments(Session session)
            throws ConfigException, DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) != null)
        {
            List<Document> docs = dmsFactoryInstantiator.getDocumentFactory().getDocuments();
            docs = getSecurityAgent()
                    .areReadable(docs, session.getUserName(), session.getUserSource(), session.getGroups());
            List<Document> checkedOutDocs = new ArrayList<Document>();
            for (Document doc : docs) {
                if (doc.isCheckedOut()) {
                    checkedOutDocs.add(doc);
                }
            }
            return checkedOutDocs;
        } else {
            throw new AccessDeniedException();
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#clearLock(org.kimios.kernel.security.Session, long)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#clearLock(org.kimios.kernel.security.Session, long)
    */
    @DmsEvent(eventName = DmsEventName.DOCUMENT_CHECKIN, when = DmsEventOccur.AFTER)
    public void clearLock(Session session, long documentUid)
            throws AccessDeniedException, ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        Document doc = dmsFactoryInstantiator.getDocumentFactory().getDocument(documentUid);
        if (doc != null) {
            Lock lock = dmsFactoryInstantiator.getLockFactory().getDocumentLock(
                    dmsFactoryInstantiator.getDocumentFactory().getDocument(documentUid));
            if (lock != null) {
                User
                        user =
                        new User(lock.getUser(), lock.getUserSource());
                dmsFactoryInstantiator.getLockFactory().checkin(doc, user);
            }
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#changeOwnership(org.kimios.kernel.security.Session, long, int, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#changeOwnership(org.kimios.kernel.security.Session, long, int, java.lang.String, java.lang.String)
    */
    public void changeOwnership(Session session, long dmEntityUid, String userName, String userSource)
            throws AccessDeniedException,
            ConfigException, DataSourceException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        DMEntity entity = dmsFactoryInstantiator.getDmEntityFactory().getEntity(dmEntityUid);
        switch (entity.getType()) {
            case DMEntityType.WORKSPACE:
                Workspace w = dmsFactoryInstantiator.getWorkspaceFactory().getWorkspace(dmEntityUid);
                if (w != null) {
                    w.setOwner(userName);
                    w.setOwnerSource(userSource);
                    dmsFactoryInstantiator.getWorkspaceFactory().updateWorkspace(w);
                }
                break;
            case DMEntityType.FOLDER:
                Folder f = dmsFactoryInstantiator.getFolderFactory().getFolder(dmEntityUid);
                if (f != null) {
                    f.setOwner(userName);
                    f.setOwnerSource(userSource);
                    dmsFactoryInstantiator.getFolderFactory().updateFolder(f);
                }
                break;
            case DMEntityType.DOCUMENT:
                Document d = dmsFactoryInstantiator.getDocumentFactory().getDocument(dmEntityUid);
                if (d != null) {
                    d.setOwner(userName);
                    d.setOwnerSource(userSource);
                    dmsFactoryInstantiator.getDocumentFactory().updateDocument(d);
                }
                break;
        }
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getConnectedUsers(org.kimios.kernel.security.Session)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getConnectedUsers(org.kimios.kernel.security.Session)
    */
    public org.kimios.kernel.ws.pojo.User[] getConnectedUsers(Session session)
            throws ConfigException, DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        Collection<User> users = SessionManager.getInstance().getConnectedUsers();
        List<org.kimios.kernel.ws.pojo.User> connectedUsers = users.stream().map( userConnected -> {
            User userWithData = this.getUser(session, userConnected.getUid(), userConnected.getAuthenticationSourceName());
            return userWithData.toPojo();
        }).collect(Collectors.toList());
        return connectedUsers.toArray(new org.kimios.kernel.ws.pojo.User[connectedUsers.size()]);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getEnabledSessions(org.kimios.kernel.security.Session)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getEnabledSessions(org.kimios.kernel.security.Session)
    */
    public org.kimios.kernel.ws.pojo.Session[] getEnabledSessions(Session session)
            throws DataSourceException, ConfigException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        List<Session> sessions = SessionManager.getInstance().getSessions();
        org.kimios.kernel.ws.pojo.Session[] enabledSessions = new org.kimios.kernel.ws.pojo.Session[sessions.size()];
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            enabledSessions[i] = new org.kimios.kernel.ws.pojo.Session(s.getUid(), s.getUserName(), s.getUserSource(),
                    s.getLastUse(), s.getMetaDatas());
        }
        return enabledSessions;
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getEnabledSessions(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#getEnabledSessions(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public org.kimios.kernel.ws.pojo.Session[] getEnabledSessions(Session session, String userName, String userSource)
            throws DataSourceException,
            ConfigException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        List<Session> sessions = SessionManager.getInstance().getSessions(userName, userSource);
        org.kimios.kernel.ws.pojo.Session[] enabledSessions = new org.kimios.kernel.ws.pojo.Session[sessions.size()];
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            enabledSessions[i] = new org.kimios.kernel.ws.pojo.Session(s.getUid(), s.getUserName(), s.getUserSource(),
                    s.getLastUse(), s.getMetaDatas());
        }
        return enabledSessions;
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#removeEnabledSession(org.kimios.kernel.security.Session, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#removeEnabledSession(org.kimios.kernel.security.Session, java.lang.String)
    */
    public void removeEnabledSession(Session session, String sessionUidToRemove)
            throws DataSourceException, ConfigException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        SessionManager.getInstance().removeSession(sessionUidToRemove);
    }

    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#removeEnabledSessions(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    /* (non-Javadoc)
    * @see org.kimios.kernel.controller.impl.IAdministrationController#removeEnabledSessions(org.kimios.kernel.security.Session, java.lang.String, java.lang.String)
    */
    public void removeEnabledSessions(Session session, String userName, String userSource)
            throws ConfigException, DataSourceException, AccessDeniedException
    {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        SessionManager.getInstance().removeSessions(userName, userSource);
    }

    @DmsEvent(eventName = { DmsEventName.USER_ATTRIBUTE_SET })
    public void setUserAttribute(Session session, String userId,
            String userSource, String attributeName, String attributeValue)
            throws ConfigException, DataSourceException, AccessDeniedException
    {
//    if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
//      throw new AccessDeniedException();
        User user =
                authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSource(userSource)
                        .getUserFactory()
                        .getUser(userId);
        if (user == null) {
            throw new AccessDeniedException();
        }
        try {
            authFactoryInstantiator.getAuthenticationSourceFactory()
                    .getAuthenticationSource(userSource)
                    .getUserFactory()
                    .setAttribute(user, attributeName, attributeValue);
        } catch (NullPointerException ex) {
            throw new AccessDeniedException();
        }
    }

    public String getUserAttribute(Session session, String userId,
            String userSource, String attributeName) throws ConfigException,
            DataSourceException, AccessDeniedException
    {
//    if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
//      throw new AccessDeniedException();
        User user =
                authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSource(userSource)
                        .getUserFactory()
                        .getUser(userId);
        if (user == null) {
            throw new AccessDeniedException();
        }
        try {
            return authFactoryInstantiator.getAuthenticationSourceFactory()
                    .getAuthenticationSource(userSource)
                    .getUserFactory()
                    .getAttribute(user, attributeName)
                    .toString();
        } catch (NullPointerException ex) {
            throw new AccessDeniedException();
        }
    }

    public User getUserByAttributeValue(Session session, String userSource, String attributeName,
            String attributeValue)
            throws ConfigException, DataSourceException, AccessDeniedException
    {
//    if (securityFactoryInstantiator.getRoleFactory().getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
//      throw new AccessDeniedException();
        User u =
                authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSource(userSource)
                        .getUserFactory()
                        .getUserByAttributeValue(attributeName, attributeValue);
        if (u == null) {
            throw new AccessDeniedException();
        }
        return u;
    }

    public void deleteUserPermissions(Session session, String uid, String authenticationSourceName) throws AccessDeniedException, ConfigException, DataSourceException {
        if (securityFactoryInstantiator.getRoleFactory()
                .getRole(Role.ADMIN, session.getUserName(), session.getUserSource()) == null)
        {
            throw new AccessDeniedException();
        }
        User user = new User(uid, authenticationSourceName);
        int entityType = user.getType();
        securityFactoryInstantiator.getDMEntitySecurityFactory().deleteSecurityEntityRules(uid, authenticationSourceName, entityType);
    }

    public List<SecurityEntity> searchSecurityEntities(String searchText, String sourceName, int securityEntityType) throws AccessDeniedException, ConfigException, DataSourceException {
        List<SecurityEntity> securityEntities = new ArrayList<SecurityEntity>();
        // users
        if (StringUtils.isNotBlank(sourceName)) {
            AuthenticationSource authSource = authFactoryInstantiator.getAuthenticationSourceFactory()
                    .getAuthenticationSource(sourceName);
            securityEntities.addAll(this.searchSecurityEntitiesFromAuthenticationSource(searchText, authSource, securityEntityType));
        } else {
            Vector<AuthenticationSource> authSources = authFactoryInstantiator.getAuthenticationSourceFactory().getAuthenticationSources();
            for (AuthenticationSource authSource : authSources) {
                securityEntities.addAll(this.searchSecurityEntitiesFromAuthenticationSource(searchText, authSource, securityEntityType));
            }
        }
        return securityEntities;
    }

    private List<SecurityEntity> searchSecurityEntitiesFromAuthenticationSource(String searchText, AuthenticationSource authSource, int securityEntityType) {
        List<SecurityEntity> securityEntities = new ArrayList<SecurityEntity>();
        switch (securityEntityType) {
            case SecurityEntityType.USER:
                securityEntities.addAll(authSource.getUserFactory().searchUsers(searchText, authSource.getName()));
                break;
            case SecurityEntityType.GROUP:
                securityEntities.addAll(authSource.getGroupFactory().searchGroups(searchText, authSource.getName()));
                break;
            default:
                securityEntities.addAll(authSource.getUserFactory().searchUsers(searchText, authSource.getName()));
                securityEntities.addAll(authSource.getGroupFactory().searchGroups(searchText, authSource.getName()));
        }
        return securityEntities;
    }
}

