public class SmsSender extends NotificationSender {

    protected SendResult doSend(Notification n) {

        String output =
            "SMS -> to=" + n.to +
            " body=" + n.body;

        System.out.println(output);

        return SendResult.ok(output);
    }
}