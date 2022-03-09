///////////////////SPINNERS/////////////////////////

//1//
function spinnerFunction() {
    var spinner = document.getElementById("mySpinner");
    var textToHide = document.getElementById("myTextToHide");
    var textToShow = document.getElementById("myTextToShow");

    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    textToHide.style.display = 'none';
    textToShow.style.display = '';
  }
  //2//
  function spinnerFunction2() {
    var spinner = document.getElementById("mySpinner2");
    var textToHide = document.getElementById("myTextToHide2");
    var textToShow = document.getElementById("myTextToShow2");

    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    textToHide.style.display = 'none';
    textToShow.style.display = '';
  }

   //3//
   function spinnerFunction3() {
    var spinner = document.getElementById("mySpinner3");
    var textToHide = document.getElementById("myTextToHide3");
    var textToShow = document.getElementById("myTextToShow3");

    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    textToHide.style.display = 'none';
    textToShow.style.display = '';
  }

   //4//
   function spinnerFunction4() {
    var spinner = document.getElementById("mySpinner4");
    var textToHide = document.getElementById("myTextToHide4");
    var textToShow = document.getElementById("myTextToShow4");

    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    textToHide.style.display = 'none';
    textToShow.style.display = '';
  }

  //5//
  function spinnerFunction5() {
    var spinner = document.getElementById("mySpinner5");
    var textToHide = document.getElementById("myTextToHide5");
    var textToShow = document.getElementById("myTextToShow5");

    spinner.classList.add("spinner-border");
    spinner.classList.add("spinner-border-sm");
    textToHide.style.display = 'none';
    textToShow.style.display = '';
  }

  //CONFIRM PASSWORD//

  var password = document.getElementById("password")
  , confirm_password = document.getElementById("confirm_password");

function validatePassword(){
  if(password.value != confirm_password.value) {
    confirm_password.setCustomValidity("Passwords Don't Match");
  } else {
    confirm_password.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;