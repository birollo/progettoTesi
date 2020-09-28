let name = document.getElementById("name");
let duration = document.getElementById("duration");
let valueServizio = document.getElementById("valueServizio");
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
};

duration.onkeyup = function() {
    // Validate password
    if((/[a-zA-Z0-9]$/g).test(duration.value)){
        duration.classList.add("is-valid");
        duration.style.borderColor="green";
    } else {
        duration.classList.remove("is-valid");
        duration.classList.add("is-invalid");
        duration.style.borderColor="red";
    }
};

valueServizio.onkeyup = function() {
    // Validate password
    if((/[a-zA-Z0-9]$/g).test(valueServizio.value)){
        valueServizio.classList.remove("is-invalid");
        valueServizio.classList.add("is-valid");
        valueServizio.style.borderColor="green";
    } else {
        valueServizio.classList.remove("is-valid");
        valueServizio.classList.add("is-invalid");
        valueServizio.style.borderColor="red";
    }
};

name.onchange = button;
duration.onchange = button;
valueServizio.onchange = button;

function button(){
    if(name.classList.contains("is-valid")&& duration.classList.contains("is-valid")&&
        valueServizio.classList.contains("is-valid")){
        valid.style.display="block"
    }else{
        valid.style.display="none"
    }

}