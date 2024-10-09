package api.payload;

public class Order {

    int id;
    int petId = 33497253; // hardcoded pet id
    int quantity;
    String shipDate;
    String status = "placed"; // hardcoded
    boolean complete = true; // hardcoded

    public Order() {}

    public Order(Order another) {
        this.id = another.id;
        this.quantity = another.quantity;
        this.shipDate = another.shipDate;
        this.petId = another.petId;
        this.status = another.status;
        this.complete = another.complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}