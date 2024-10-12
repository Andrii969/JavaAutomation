package api.payload;


import lombok.Data;

@Data
public class Order {
    int id;
    int petId;
    int quantity;
    String shipDate;
    String status;
    boolean complete;
}