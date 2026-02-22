public class OrderLine {
    public MenuItem item;
    public int quantity;

    public OrderLine(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}