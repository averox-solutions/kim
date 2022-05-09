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
package org.kimios.webservices.exceptions;

import javax.xml.ws.WebFault;

@WebFault(name = "DMServiceException", targetNamespace = "http://webservices.kimios.org")
public class DMServiceException extends Exception
{
    public DMServiceException()
    {
        super();
    }

    public DMServiceException(String message)
    {
        super(message);
        code = 0;
    }

    public DMServiceException(String message, Throwable cause)
    {
        super(message, cause);
        code = 0;
        dataTransferId = -1;
    }

    public DMServiceException(int code, String message, Throwable cause)
    {
        super(message, cause);
        this.code = code;
        dataTransferId = -1;
    }

    public DMServiceException(String s, Throwable throwable, int code, long dataTransferId) {
        super(s, throwable);
        this.code = code;
        this.dataTransferId = dataTransferId;
    }

    private int code = 0;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    private long dataTransferId;

    public long getDataTransferId() {
        return dataTransferId;
    }

    public void setDataTransferId(long dataTransferId) {
        this.dataTransferId = dataTransferId;
    }
}
