package wat.semestr7.ai.services.paypal;

import com.paypal.api.payments.Payment;

public class ExecutedPaymentResponse
{
    private String status;
    private Payment payment;

    public ExecutedPaymentResponse(String status, Payment payment) {
        this.status = status;
        this.payment = payment;
    }

    public ExecutedPaymentResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "ExecutedPaymentResponse{" +
                "status='" + status + '\'' +
                ", payment=" + payment +
                '}';
    }
}
