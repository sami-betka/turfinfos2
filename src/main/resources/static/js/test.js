function add() {
  document.getElementById("field2").value = document.getElementById("field1").value;
}
////////////////////////////////////////////////////////////////////////////////////

//       bouton up 

	//Get the button
		var mybutton = document.getElementById("myBtn");

		// When the user scrolls down 20px from the top of the document, show the button
		window.onscroll = function() {
			scrollFunction()
		};

		function scrollFunction() {
			if (document.body.scrollTop > 20
					|| document.documentElement.scrollTop > 20) {
				mybutton.style.display = "block";
			} else {
				mybutton.style.display = "none";
			}
		}

		// When the user clicks on the button, scroll to the top of the document
		function topFunction() {
			document.body.scrollTop = 0;
			document.documentElement.scrollTop = 0;

		}
///////////////////////////////////////////////////////////////////////:
		
		
//        icone
		feather.replace()
		
////////////////////////////////////////////////////////////////
		
//	   	  popup 
	$(function () {
  $('[data-toggle="popover"]').popover()
})


$('.popover-dismiss').popover({
  trigger: 'focus'
})
$('.popover').css("background-color", "red");


//////////////////////////////////////////////////////////////////////////
      
                //Payment page

$(function($) {
$('[data-numeric]').payment('restrictNumeric');
$('.cc-number').payment('formatCardNumber');
$('.cc-exp').payment('formatCardExpiry');
$('.cc-cvc').payment('formatCardCVC');
$.fn.toggleInputError = function(erred) {
this.parent('.form-group').toggleClass('has-error', erred);
return this;
};
$('form').submit(function(e) {
e.preventDefault();
var cardType = $.payment.cardType($('.cc-number').val());
$('.cc-number').toggleInputError(!$.payment.validateCardNumber($('.cc-number').val()));
$('.cc-exp').toggleInputError(!$.payment.validateCardExpiry($('.cc-exp').payment('cardExpiryVal')));
$('.cc-cvc').toggleInputError(!$.payment.validateCardCVC($('.cc-cvc').val(), cardType));
$('.cc-brand').text(cardType);
$('.validation').removeClass('text-danger text-success');
$('.validation').addClass($('.has-error').length ? 'text-danger' : 'text-success');
});
});
		
		//////////////////////////////////////////////////////


//////////////////////////spinner


$(document).ready(function() {
    $("#btnSpinner").click(function() {
      // disable button
      $(this).prop("disabled", true);
      // add spinner to button
      $(this).html(
        `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Paiement en cours...`
      );
    });
});

$(document).ready(function() {
      $('.spinner-border').hide();
});


//////////////////////////////////////////////////////////

               ///////Active element

$('ul li').on('click', function (e) {
    $('li').each(function () {
        $(this).addClass('active');
    })

});
