package org.siemac.metamac.statistical.operations.web.external;

import static java.util.ResourceBundle.Control.getNoFallbackControl;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.LocaleUtils;



// https://stackoverflow.com/questions/7469223/jsp-and-resourcebundles
public class MessagesResourceBundle extends ResourceBundle {

    private static final String MESSAGES_ATTRIBUTE_NAME = "msg";
    private static final String MESSAGES_ES = "i18n.messages_es"; // Comes from folders and filename: i18n/messages-swagger.properties
    private static final String MESSAGES_CA = "i18n.messages_ca";
    public static final String LANG_ES = "es";
    public static final String LANG_CA = "ca";
    
    public MessagesResourceBundle(Locale locale) {
        setLocale(locale);
    }

    public MessagesResourceBundle(String locale) {
        setLocale(locale);
    }

    public static void setFor(HttpServletRequest request) {
        if (request.getSession().getAttribute(MESSAGES_ATTRIBUTE_NAME) == null) {
            request.getSession().setAttribute(MESSAGES_ATTRIBUTE_NAME, new MessagesResourceBundle(request.getLocale()));
        }
    }

    public static MessagesResourceBundle getCurrentInstance(HttpServletRequest request) {
        return (MessagesResourceBundle) request.getSession().getAttribute(MESSAGES_ATTRIBUTE_NAME);
    }

    public void setLocale(Locale locale) {
       if (LANG_ES.equals(String.valueOf(locale))) {
          setParent(getBundle(MESSAGES_ES, locale, getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT)));
       } else if (LANG_CA.equals(String.valueOf(locale))) {
          setParent(getBundle(MESSAGES_CA, locale, getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT)));
       }
    }

    public void setLocale(String locale) {
        setLocale(LocaleUtils.toLocale(locale));
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }

    @Override
    protected Object handleGetObject(String key) {
        return parent.getObject(key);
    }

}
