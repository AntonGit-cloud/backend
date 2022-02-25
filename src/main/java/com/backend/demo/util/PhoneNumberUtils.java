package com.backend.demo.util;

import com.backend.demo.dto.PhoneNumberInfo;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneNumberUtils {

    public static boolean isValidPhoneNumber(PhoneNumberInfo phoneNumberInfo) {
        return isValidPhoneNumber(phoneNumberInfo.getDialCode() + phoneNumberInfo.getNumber());
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.isBlank()) {
            return false;
        }
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phone = phoneUtil.parse(phoneNumber, "");
            return phoneUtil.isValidNumber(phone);
        } catch (NumberParseException e) {
            throw new IllegalArgumentException(String.format("'%s' phone number format is invalid", phoneNumber));
        }
    }

}

