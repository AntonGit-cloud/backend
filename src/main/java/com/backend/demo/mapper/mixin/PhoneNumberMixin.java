package com.backend.demo.mapper.mixin;

import com.backend.demo.model.User;
import com.backend.demo.dto.PhoneNumberInfo;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.mapstruct.Named;

public interface PhoneNumberMixin {

    @Named("mapPhoneNumber")
    default String mapPhoneNumber(PhoneNumberInfo phoneNumberInfo) {
        if (phoneNumberInfo == null) {
            return null;
        }
        return String.format("%s %s", phoneNumberInfo.getDialCode(), phoneNumberInfo.getNumber());
    }

    @Named("mapUserPhoneNumber")
    default String mapUserPhoneNumber(User user) {
        if (user.getDialCode() == null || user.getPhoneNumber() == null) {
            return null;
        }
        return user.getDialCode() + user.getPhoneNumber();
    }

    @Named("getDialCodeFromString")
    default String getDialCodeFromString(String phoneNumber) {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phone = phoneUtil.parse(phoneNumber, "");
            return String.format("+%s", phone.getCountryCode());
        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Wrong formatted phone number");
        }
    }


    @Named("getPhoneNumberFromString")
    default String getPhoneNumberFromString(String phoneNumber) {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phone = phoneUtil.parse(phoneNumber, "");
            return String.valueOf(phone.getNationalNumber());
        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Wrong formatted phone number");
        }
    }

}
