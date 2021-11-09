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