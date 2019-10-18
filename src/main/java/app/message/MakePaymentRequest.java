package app.message;

import javax.validation.constraints.NotEmpty;

public class MakePaymentRequest {
    @NotEmpty
    String deliveryAddress;

    public MakePaymentRequest(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public MakePaymentRequest() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
