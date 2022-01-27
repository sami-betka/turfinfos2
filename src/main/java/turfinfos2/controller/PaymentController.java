package turfinfos2.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import turfinfos2.model.CreatePayment;

@RestController
public class PaymentController {
	
	 static class CreatePaymentResponse {
		    private String clientSecret;
		    
		    public CreatePaymentResponse(String clientSecret) {
		      this.clientSecret = clientSecret;
		    }
			public String getClientSecret() {
				return clientSecret;
			}
			public void setClientSecret(String clientSecret) {
				this.clientSecret = clientSecret;
			}
		    
		  }
	 
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody @Valid  CreatePayment createPayment)throws StripeException {
    	
        PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setCurrency("eur")
                .putMetadata("featureRequest", createPayment.getFeatureRequest())
                .setAmount(createPayment.getAmount() * 100L)
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponse(intent.getClientSecret());
    }
}