//package turfinfos2.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import turfinfos2.model.CheckoutForm;
//
//@Controller
//public class WebController {
//
//    @Value("${stripe.public.key}")
//    private String stripePublicKey;
//
//    @GetMapping("/payment")
//    public String home(Model model){
//        model.addAttribute("checkoutForm",new CheckoutForm());
//        return "payment";
//    }
//
//    @PostMapping("/")
//    public String checkout(@ModelAttribute
//                                       @Valid CheckoutForm checkoutForm,
//                           BindingResult bindingResult,
//                           Model model){
//        if (bindingResult.hasErrors()){
//            return "index";
//        }
//
//        model.addAttribute("stripePublicKey",stripePublicKey);
//        model.addAttribute("amount",checkoutForm.getAmount());
//        model.addAttribute("email", checkoutForm.getEmail());
//        model.addAttribute("featureRequest", checkoutForm.getFeatureRequest());
//        return "checkout";
//    }
//}