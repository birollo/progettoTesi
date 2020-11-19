let name = document.getElementById("name");
let duration = document.getElementById("duration");
let value = document.getElementById("value");
let valid = document.getElementById("validate");


name.onkeyup = function() {
    // Validate password
    if((/[a-zA-Z0-9]$/g).test(name.value)){
        name.classList.remove("is-invalid");
        name.classList.add("is-valid");
        name.style.borderColor="green";
    } else {
        name.classList.remove("is-valid");
        name.classList.add("is-invalid");
        name.style.borderColor="red";
    }
    button();
};

duration.onkeyup = function() {
    // Validate password
    if((/[0-9]$/g).test(duration.value)){
        duration.classList.remove("is-invalid");
        duration.classList.add("is-valid");
        duration.style.borderColor="green";
    } else {
        duration.classList.remove("is-valid");
        duration.classList.add("is-invalid");
        duration.style.borderColor="red";
    }
    button();
};

value.onkeyup = function() {
    // Validate password
    if((/[0-9]$/g).test(value.value)){
        value.classList.remove("is-invalid");
        value.classList.add("is-valid");
        value.style.borderColor="green";
    } else {
        value.classList.remove("is-valid");
        value.classList.add("is-invalid");
        value.style.borderColor="red";
    }
    button();
};



name.onchange = button;
duration.onchange = button;
value.onchange = button;

function button(){
    if(name.classList.contains("is-valid")&& duration.classList.contains("is-valid") && value.classList.contains("is-valid")){
        valid.style.display="block"
    }else{
        valid.style.display="none"
    }

}