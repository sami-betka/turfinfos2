package turfinfos2.twilio;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}