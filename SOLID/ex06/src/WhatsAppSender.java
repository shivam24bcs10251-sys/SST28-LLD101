public class WhatsAppSender extends NotificationSender {

    protected SendResult doSend(Notification n) {

        if (!n.to.startsWith("+")) {
            String msg =
                "WA ERROR: phone must start with + and country code";
            System.out.println(msg);
            return SendResult.error(msg);
        }

        String output =
            "WA -> to=" + n.to +
            " body=" + n.body;

        System.out.println(output);

        return SendResult.ok(output);
    }
}