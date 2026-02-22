public abstract class NotificationSender {

    public final SendResult send(Notification notification) {

        if (notification == null ||
            notification.to == null ||
            notification.body == null) {

            return SendResult.error("Invalid notification");
        }

        return doSend(notification);
    }

    protected abstract SendResult doSend(Notification notification);
}