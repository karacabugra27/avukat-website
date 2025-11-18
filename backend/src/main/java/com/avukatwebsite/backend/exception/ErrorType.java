package com.avukatwebsite.backend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    LAWYER_NOT_FOUND("LAW_404_001", HttpStatus.NOT_FOUND, "Avukat bulunamadı"),
    LAWYER_EMAIL_IN_USE("LAW_409_001", HttpStatus.CONFLICT, "Bu e-posta adresi zaten kullanımda"),

    MEMBERSHIP_NOT_FOUND("MEM_404_001", HttpStatus.NOT_FOUND, "Üyelik kaydı bulunamadı"),
    MEMBERSHIP_INVALID_DATE_RANGE("MEM_400_001", HttpStatus.BAD_REQUEST, "Üyelik bitiş tarihi başlangıç tarihinden önce olamaz"),

    FAQ_NOT_FOUND("FAQ_404_001", HttpStatus.NOT_FOUND, "SSS kaydı bulunamadı"),

    EXPERIENCE_NOT_FOUND("EXP_404_001", HttpStatus.NOT_FOUND, "Deneyim kaydı bulunamadı"),

    LAWYER_SCHEDULE_NOT_FOUND("SCH_404_001", HttpStatus.NOT_FOUND, "Çalışma takvimi kaydı bulunamadı"),
    LAWYER_SCHEDULE_INVALID_TIME_RANGE("SCH_400_001", HttpStatus.BAD_REQUEST, "Başlangıç saati bitiş saatinden önce olmalıdır"),

    APPOINTMENT_NOT_FOUND("APP_404_001", HttpStatus.NOT_FOUND, "Randevu kaydı bulunamadı"),
    APPOINTMENT_SCHEDULE_NOT_FOUND("APP_404_002", HttpStatus.NOT_FOUND, "İlgili çalışma takvimi bulunamadı"),
    APPOINTMENT_TIME_INVALID("APP_400_001", HttpStatus.BAD_REQUEST, "Randevu bitiş saati başlangıç saatinden sonra olmalıdır"),
    APPOINTMENT_DAY_OFF("APP_400_002", HttpStatus.BAD_REQUEST, "Tatil gününde randevu oluşturulamaz"),

    CONTACT_MESSAGE_NOT_FOUND("CON_404_001", HttpStatus.NOT_FOUND, "İletişim mesajı bulunamadı"),

    VALIDATION_FAILED("GEN_400_001", HttpStatus.BAD_REQUEST, "Geçersiz istek"),
    GENERIC_BUSINESS_ERROR("GEN_400_002", HttpStatus.BAD_REQUEST, "İş kuralı ihlali"),
    INTERNAL_SERVER_ERROR("GEN_500_001", HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen bir hata oluştu"),

    AUTH_NOT_FOUND("AUTH_404_001", HttpStatus.NOT_FOUND, "Kişi kaydı bulunamadı"),
    AUTH_BAD_CREDENTIALS("AUTH_401_001", HttpStatus.UNAUTHORIZED, "Geçersiz kullanıcı adı veya şifre"),
    AUTH_FORBIDDEN("AUTH_403_001", HttpStatus.FORBIDDEN, "Bu işlem için yetkiniz bulunmuyor");

    private final String code;
    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorType(String code, HttpStatus httpStatus, String defaultMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
