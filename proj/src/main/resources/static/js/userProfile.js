var edit= document.getElementById("edit");
edit.onclick = function () {
    var details = document.getElementById("details");
    var change = document.getElementById("change");
    var submit= document.getElementById("submit");
    details.style.display="none";
    change.style.display="block";

    submit.onclick = function(){
        details.style.display="block";
        change.style.display="none";
    };
};
