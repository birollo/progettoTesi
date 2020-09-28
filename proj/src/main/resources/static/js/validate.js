var register_form = document.getElementById("register-form");
var password = document.getElementById("password1");
var name1 = document.getElementById("name");
var surname = document.getElementById("surname");
var username = document.getElementById("username1");
var confirm=document.getElementById("confirm-password");
var valid=document.getElementById("validate");
var role=document.getElementById("role");
var nascita=document.getElementById("nascita");
var option=document.getElementById("option");

/*// When the user clicks on the password field, show the message box
password.onfocus = function() {
    document.getElementById("validate").style.display = "block";
};

// When the user clicks outside of the password field, hide the message box
password.onblur = function() {
    document.getElementById("validate").style.display = "none";
};*/

password.onkeyup = function() {
    // Validate password
    if((/^[a-zA-Z0-9_]{8,15}$/g).test(password.value)){
        password.classList.remove("is-invalid");
        password.classList.add("is-valid");
        password.style.borderColor="green";
    } else {
        password.classList.remove("is-valid");
        password.classList.add("is-invalid");
        password.style.borderColor="red";
    }
};

confirm.onkeyup= function(){
    // Validate confirm password
    if((/^[a-zA-Z0-9_]{8,15}$/g).test(confirm.value) && confirm.value === password.value) {
        confirm.classList.remove("is-invalid");
        confirm.classList.add("is-valid");
        confirm.style.borderColor="green";
    } else {
        confirm.classList.remove("is-valid");
        confirm.classList.add("is-invalid");
        confirm.style.borderColor="red";
    }
};

name1.onkeyup= function(){
    //Validate name
    console.log("Qui");
    if((/^[a-zA-Z]+$/g).test(name1.value)){
        name1.classList.remove("is-invalid");
        name1.classList.add("is-valid");
        name1.style.borderColor="green";
    } else {
        name1.classList.remove("is-valid");
        name1.classList.add("is-invalid");
        name1.style.borderColor="red";
    }
};

surname.onkeyup= function(){
    //Validate surname
    if((/^[a-zA-Z]+$/g).test(surname.value)){
        surname.classList.remove("is-invalid");
        surname.classList.add("is-valid");
        surname.style.borderColor="green";
    } else {
        surname.classList.remove("is-valid");
        surname.classList.add("is-invalid");
        surname.style.borderColor="red";
    }
};

username.onkeyup= function(){
    //Validate username
    if((/^[a-zA-Z0-9_]+$/g).test(username.value)){
        username.classList.remove("is-invalid");
        username.classList.add("is-valid");
        username.style.borderColor="green";
    } else {
        username.classList.remove("is-valid");
        username.classList.add("is-invalid");
        username.style.borderColor="red";
    }
};

role.onclick = function () {
    role.classList.remove("is-invalid");
    role.classList.add("is-valid");
    role.style.borderColor="green";
    option.setAttribute("disabled", "disabled");
};

nascita.onclick = function () {
    nascita.classList.remove("is-invalid");
    nascita.classList.add("is-valid");
    nascita.style.borderColor="green";
    option.setAttribute("disabled", "disabled");
};

function button(){
    if(surname.classList.contains("is-valid")&& username.classList.contains("is-valid")&&
        password.classList.contains("is-valid")&& confirm.classList.contains("is-valid")&&
        role.classList.contains("is-valid")&& nascita.classList.contains("is-valid")){
        valid.style.display="block"
    }else{
        valid.style.display="none"
    }

}

name1.onchange = button;
confirm.onchange = button;
username.onchange = button;
password.onchange = button;
surname.onchange = button;
role.onchange = button;
nascita.onchange = button;

$(function() {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

});