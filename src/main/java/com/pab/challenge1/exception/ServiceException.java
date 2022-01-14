/*
 * ServiceException
 * @author paul
 */
package com.pab.challenge1.exception;

import com.pab.challenge1.database.exception.SystemException;

public class ServiceException extends Exception implements SystemException {

    private static final long serialVersionUID = 1L;

    private String message;
    private String className;
    private String methodName;
    private String dataStream;

    public ServiceException() {
        super();
    }

    public ServiceException(String s) {
        super(s);
        setMessage(s);
    }

    public ServiceException(String s, Throwable cause) {
        super(s, cause);
        setMessage(s);
    }

    public ServiceException(String message, String classname, String methodName, String dataStream) {
        super(message);
        setClassName(classname);
        setDataStream(dataStream);
        setMessage(message);
        setMethodName(methodName);
    }

    public ServiceException(String message, String classname, String methodName, String dataStream, Throwable cause) {
        super(message, cause);
        setClassName(classname);
        setDataStream(dataStream);
        setMessage(message);
        setMethodName(methodName);
    }

    public String getClassName() {
        return className;
    }

    public String getDataStream() {
        return dataStream;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setClassName(String newClassName) {
        className = newClassName;
    }

    public void setDataStream(String newDataStream) {
        dataStream = newDataStream;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public void setMethodName(String newMethodName) {
        methodName = newMethodName;
    }

    @Override
    public String toString() {
        return "Service Exception: \nClass Name:"
                + getClassName()
                + "\nMethodName:"
                + getMethodName()
                + "\nMessage:"
                + getMessage()
                + "\nDataStream:"
                + getDataStream();
    }
}
