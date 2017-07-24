package io.hmheng.grading.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.Marker;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GradingLogger implements Logger{

	Logger parentLogger;
	
	public GradingLogger(Logger parentLogger2) {
		this.parentLogger = parentLogger;
	}
	void GradingLogger() {
	}
	
	@Override
	public String getName() {
		return parentLogger.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return parentLogger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		parentLogger.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		parentLogger.trace(format,arg);
		
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		parentLogger.trace(format,arg1, arg2);
		
	}

	@Override
	public void trace(String format, Object... arguments) {
		parentLogger.trace(format,arguments);
		
	}

	@Override
	public void trace(String msg, Throwable t) {
		parentLogger.trace(msg,t);
		
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return parentLogger.isTraceEnabled(marker);
	}

	@Override
	public void trace(Marker marker, String msg) {
		parentLogger.trace(marker,msg);
		
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		parentLogger.trace(marker,format,arg);
		
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		parentLogger.trace(marker,format,arg1);
		
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		parentLogger.trace(marker,format,argArray);
		
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		parentLogger.trace(marker,msg,t);
		
	}

	@Override
	public boolean isDebugEnabled() {
		return parentLogger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		parentLogger.debug(msg);
		
	}

	@Override
	public void debug(String format, Object arg) {
		parentLogger.debug(format,arg);
		
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		parentLogger.debug(format,arg1,arg2);
		
	}

	@Override
	public void debug(String format, Object... arguments) {
		parentLogger.debug(format,arguments);
		
	}

	@Override
	public void debug(String msg, Throwable t) {
		parentLogger.debug(msg,t);
		
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return parentLogger.isDebugEnabled();
	}

	@Override
	public void debug(Marker marker, String msg) {
		parentLogger.debug(marker, msg);;
		
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		parentLogger.debug(marker, format, arg);
		
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		parentLogger.debug(marker, format, arg1, arg2);
		
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		parentLogger.debug(marker, format, arguments);
		
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		parentLogger.debug(marker, msg, t);
		
	}

	@Override
	public boolean isInfoEnabled() {
		return parentLogger.isInfoEnabled();
		
	}

	@Override
	public void info(String msg) {
		parentLogger.info(msg);
		
	}
	
	//Newely Implemented
	public void info(Object value) {

		String valueStr = null;
		
        if(value== null || value.equals("")){
            return ;
        }
        
        if(value instanceof String) {
        	valueStr = (String) value;
        }

        try {
            valueStr = new ObjectMapper().writeValueAsString(value);
        } catch (IOException e) {
           valueStr = "";
        }
        
        parentLogger.info(valueStr);
    
	}
	

	@Override
	public void info(String format, Object arg) {
		parentLogger.info(format, arg);
		
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		parentLogger.info(format, arg1, arg2);
		
	}

	@Override
	public void info(String format, Object... arguments) {
		parentLogger.info(format, arguments);
		
	}

	@Override
	public void info(String msg, Throwable t) {
		parentLogger.info(msg, t);
		
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return parentLogger.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		parentLogger.info(marker, msg);
		
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		parentLogger.info(marker, format, arg);
		
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		parentLogger.info(marker, format, arg1, arg2);
		
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		parentLogger.info(marker, format, arguments);
		
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		parentLogger.info(marker, msg, t);
		
	}

	@Override
	public boolean isWarnEnabled() {
		return parentLogger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		parentLogger.warn(msg);
		
	}

	@Override
	public void warn(String format, Object arg) {
		parentLogger.warn(format, arg);
		
	}

	@Override
	public void warn(String format, Object... arguments) {
		parentLogger.warn(format, arguments);
		
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		parentLogger.warn(format, arg1, arg2);
		
	}

	@Override
	public void warn(String msg, Throwable t) {
		parentLogger.warn(msg, t);
		
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return parentLogger.isWarnEnabled();
	}

	@Override
	public void warn(Marker marker, String msg) {
		parentLogger.warn(marker, msg);
		
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		parentLogger.warn(marker, format, arg);
		
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		parentLogger.warn(marker, format, arg1, arg2);
		
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		parentLogger.warn(marker, format, arguments);
		
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		parentLogger.warn(marker, msg, t);
		
	}

	@Override
	public boolean isErrorEnabled() {
		return parentLogger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		parentLogger.error(msg);
		
	}

	@Override
	public void error(String format, Object arg) {
		parentLogger.error(format, arg);
		
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		parentLogger.error(format, arg1, arg2);
		
	}

	@Override
	public void error(String format, Object... arguments) {
		parentLogger.error(format, arguments);
		
	}

	@Override
	public void error(String msg, Throwable t) {
		parentLogger.error(msg, t);
		
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return parentLogger.isErrorEnabled();
	}

	@Override
	public void error(Marker marker, String msg) {
		parentLogger.error(marker, msg);
		
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		parentLogger.error(marker, format, arg);
		
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		parentLogger.error(marker, format, arg1, arg2);
		
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		parentLogger.error(marker, format, arguments);
		
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		parentLogger.error(marker, msg, t);
		
	}

}
