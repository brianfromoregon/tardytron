package com.tardytron.client.datejs;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper for the DateJs javascript library, a useful library for
 * parsing, generating, and manipulating dates.
 *
 * For docs, see:
 *
 * http://code.google.com/p/datejs/wiki/APIDocumentation
 *
 * I didn't include the core Date methods - use toDate() and access that
 * object for the "basics".
 *
 */
public class DateJs {
	JavaScriptObject date;

	public class DateParseFailedException extends Exception {
		private static final long serialVersionUID = 1L;
		public DateParseFailedException() {
		}
	}
	public DateJs(Date fromDate) {
		date = nativeInit();
		setTime(fromDate.getTime());
	}

	public Date toDate() {
		return new Date((long)getTime());
	}

	public native DateJs setTime(double millis) /*-{
		this.@com.tardytron.client.datejs.DateJs::date.setTime(millis);
		return this;
	}-*/;

	public native double getTime() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getTime();
    }-*/;

	public DateJs() {
		this.date = nativeInit();
	}

	private native JavaScriptObject nativeInit() /*-{
    	return new $wnd.Date();
    }-*/;

	public DateJs(DateJs other) {
		assignTo(other);
	}

	public native DateJs assignTo(DateJs other) /*-{
		this.@com.tardytron.client.datejs.DateJs::date = other.@com.tardytron.client.datejs.DateJs::date.clone();
		return this;
	}-*/;

	public DateJs(String dateString) throws DateParseFailedException {
		assignToParsedDate(dateString);
		if(this.date == null) throw new DateParseFailedException();
	}
	public native DateJs assignToParsedDate(String dateString) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date = $wnd.Date.parse(dateString);
    	return this;
    }-*/;

	public DateJs(String dateString, String format) throws DateParseFailedException {
		assignToParsedDateExact(dateString, format);
		if(this.date == null) throw new DateParseFailedException();
	}
	public native DateJs assignToParsedDateExact(String dateString, String format) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date = $wnd.Date.parseExact(dateString, format);
    	return this;
    }-*/;

	public static DateJs now() {
		return new DateJs();
	}
	public static DateJs today() {
		return new DateJs().clearTime();
	}

	public static native int getDayNumberFromName(String name) /*-{
		return $wnd.Date.getDayNumberFromName(name);
	}-*/;

	public static native int getDaysInMonth(int year, int month) /*-{
    	return $wnd.Date.getDaysInMonth(year, month);
    }-*/;

	public static native int getMonthNumberFromName(String month) /*-{
    	return $wnd.Date.getMonthNumberFromName(month);
    }-*/;

	public static native int getTimeZoneAbbreviation(int tzOffset, boolean daylightSavings) /*-{
    	return $wnd.Date.getTimeZoneAbbreviation(tzOffset, daylightSavings);
    }-*/;

	public static native int getTimeZoneOffset(String tzName, boolean daylightSavings) /*-{
    	return $wnd.Date.getTimeZoneOffset(tzName, daylightSavings);
    }-*/;

	public static native int isLeapYear(int year) /*-{
    	return $wnd.Date.isLeapYear(year);
    }-*/;

	public native DateJs addMilliseconds(int millis) /*-{
		this.@com.tardytron.client.datejs.DateJs::date.addMilliseconds(millis);
		return this;
	}-*/;

	public native DateJs addSeconds(int seconds) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addSeconds(seconds);
    	return this;
    }-*/;

	public native DateJs addMinutes(int minutes) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addMinutes(minutes);
    	return this;
    }-*/;

	public native DateJs addHours(int hours) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addHours(hours);
    	return this;
    }-*/;

	public native DateJs addDays(int days) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addDays(days);
    	return this;
    }-*/;

	public native DateJs addWeeks(int weeks) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addWeeks(weeks);
    	return this;
    }-*/;

	public native DateJs addMonths(int months) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addMonths(months);
    	return this;
    }-*/;


	public native DateJs addYears(int years) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.addYears(years);
    	return this;
    }-*/;

	public native boolean between(DateJs startDate, DateJs endDate) /*-{
		return this.@com.tardytron.client.datejs.DateJs::date.between(startDate.date, endDate.date);
	}-*/;

	public native DateJs clearTime() /*-{
		this.@com.tardytron.client.datejs.DateJs::date.clearTime();
		return this;
	}-*/;

	public native int compareTo(DateJs other) /*-{
		return this.@com.tardytron.client.datejs.DateJs::date.compareTo(other.date);
	}-*/;

	public native boolean equals(DateJs other) /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.equals(other.date);
    }-*/;

	public native String getDayName(boolean useAbbreviation) /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getDayName(useAbbreviation);
    }-*/;

	public native int getDayOfYear() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getDayOfYear();
    }-*/;

	public native int getDaysInMonth() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getDaysInMonth();
    }-*/;

	public native String getMonthName() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getMonthName();
    }-*/;

	public static class DateParser {
		JavaScriptObject func;
		DateParser(String format) {
			func = nativeInit(format);
		}

		private native JavaScriptObject nativeInit(String formatString) /*-{
			return $wnd.Date.getParseFunction(formatString);
		}-*/;

		public native double parseDateToMillis(String dateString) /*-{
			var f = this.@com.tardytron.client.datejs.DateJs.DateParser::func;
			return f(dateString).getTime()
		}-*/;

		public Date parse(String dateString) {
			return new Date((long)parseDateToMillis(dateString));
		}
	}

	// TODO Support an array of formats
	public static DateParser getParseFunction(String format) {
		return new DateParser(format);
	}

	public native String getTimezone() /*-{
		return this.@com.tardytron.client.datejs.DateJs::date.getTimezone();
	}-*/;

	public native int getUTCOffset() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getUTCOffset();
    }-*/;
	public native String getWeekOfYear(int firstDayOfWeek) /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.getWeekOfYear(firstDayOfWeek);
    }-*/;
	public native boolean isDST() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.isDST();
    }-*/;
	public native boolean isLeapYear() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.isLeapYear();
    }-*/;
	public native boolean isWeekday() /*-{
    	return this.@com.tardytron.client.datejs.DateJs::date.isWeekday();
    }-*/;

	public native DateJs moveToDayOfWeek(int dayOfWeek, int pastOrFuture) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.moveToDayOfWeek(dayOfWeek,pastOrFuture);
    	return this;
    }-*/;

	public native DateJs moveToFirstDayOfMonth() /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.moveToFirstDayOfMonth();
    	return this;
    }-*/;

	public native DateJs moveToLastDayOfMonth() /*-{
	this.@com.tardytron.client.datejs.DateJs::date.moveToLastDayOfMonth();
	return this;
}-*/;

	public native DateJs moveToMonth(int month, int pastOrFuture) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.moveToDayOfWeek(month,pastOrFuture);
    	return this;
    }-*/;

	public static DateJs parse(String dateString) throws DateParseFailedException {
		return new DateJs(dateString);
	}

	public static DateJs parseExact(String dateString, String format) throws DateParseFailedException {
		return new DateJs(dateString, format);
	}

	public native DateJs setTimezone(String timezoneAbbreviation) /*-{
		this.@com.tardytron.client.datejs.DateJs::date.setTimezone(timezoneAbbreviation);
		return this;
	}-*/;

	public native DateJs setTimezoneOffset(int timezoneOffset) /*-{
    	this.@com.tardytron.client.datejs.DateJs::date.setTimezoneOffset(timezoneOffset);
    	return this;
    }-*/;

	public native String toString(String format) /*-{
		return this.@com.tardytron.client.datejs.DateJs::date.toString(format);
	}-*/;

	public static native boolean validateDay(int year, int month, int day) /*-{
    	$wnd.Date.validateDay(year, month, day);
    }-*/;
	public static native boolean validateHour(int hour) /*-{
    	$wnd.Date.validateHour(hour);
    }-*/;
	public static native boolean validateMillisecond(int millisecond) /*-{
    	$wnd.Date.validateMillisecond(millisecond);
    }-*/;
	public static native boolean validateMinute(int minutes) /*-{
    	$wnd.Date.validateMinute(minutes);
    }-*/;
	public static native boolean validateMonth(int month) /*-{
    	$wnd.Date.validateMonth(month);
    }-*/;
	public static native boolean validateSecond(int seconds) /*-{
    	$wnd.Date.validateSecond(seconds);
    }-*/;
	public static native boolean validateYear(int year) /*-{
    	$wnd.Date.validateYear(year);
    }-*/;
}
