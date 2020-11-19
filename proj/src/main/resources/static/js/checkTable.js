let choser = document.getElementById('monthChose');
// choser.addEventListener('change', toggleCheck);

let button = document.getElementById('buttonCheck');
button.addEventListener("click", check);
button.style.display = "none";

$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', toggleCheck);


function toggleCheck() {
    let isChose = choser.value;
    if (isChose === "Scegli mese"){
        button.style.display = "none";
    }else {
        button.style.display = "block";
    }


}

function check(){
    let thisDate = new Date("2021/01/01").toISOString();
    let errorTable = document.getElementById("errorTable");

    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/controllaTabella',
        contentType: 'text'
    }).done(function (response) {
        errorTable.innerHTML ="";
        let risposte = response;
        for (let i = 0; i < risposte.length; i ++){
            let thisResponse = risposte[i];
            let count = 0;
            for (let k = 0; k < thisResponse.length; k++){
                if (thisResponse[k]=== "OK"){

                } else {
                    count++;
                    errorTable.innerHTML += "<a class='list-group-item list-group-item-danger'>" + thisResponse[k] + "</a>";
                }
            }
            if (count===0){
                errorTable.innerHTML +="<a class='list-group-item list-group-item-succes'>" + "Tutto OK" + "</a>";
            }

        }

    }).fail(function (err) {
    console.log(err);
    console.log('error');
})
}