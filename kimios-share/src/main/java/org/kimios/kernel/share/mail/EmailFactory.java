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

package org.kimios.kernel.share.mail;

import org.apache.commons.mail.*;
import org.kimios.kernel.dms.Document;
import org.kimios.kernel.dms.DocumentVersion;
import org.kimios.kernel.repositories.RepositoryManager;

import javax.activation.FileDataSource;

public class EmailFactory {

    private String mailServer = "smtp.googlemail.com";

    private String mailAccount = "my.email";

    private String mailAccountPassword = "password";

    private int mailServerPort = 465;

    private boolean mailServerSsl = true;

    public MultiPartEmail getMultipartEmailObject() throws EmailException {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(mailServer);
        email.setSmtpPort(mailServerPort);
        email.setAuthenticator(new DefaultAuthenticator(mailAccount, mailAccountPassword));
        email.setSSLOnConnect(mailServerSsl);
        return email;
    }


    public void addDocumentVersionAttachment(MultiPartEmail email, Document document, DocumentVersion documentVersion)
        throws  Exception {
        FileDataSource fileDataSource = new FileDataSource(RepositoryManager.directFileAccess(documentVersion));
        email.attach(fileDataSource, document.getPath().substring(document.getPath().lastIndexOf("/") + 1),
                "", EmailAttachment.ATTACHMENT);
    }


}
