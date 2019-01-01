package wat.semestr7.ai.services.paypal;

public class CreatedPaymentResponse
{
    private String status;
    private String redirect_url;

    public CreatedPaymentResponse(String status, String redirect_url) {
        this.status = status;
        this.redirect_url = redirect_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}
