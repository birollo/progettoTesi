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
    let thisDate = new Date(from.valueOf()).toISOString();
    button.style.display = "none";
    document.getElementById('checkLoading').style.display = "block";

    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/controllaTabella',
        contentType: 'text'
    }).done(function (response) {

        document.getElementById('checkLoading').style.display = "none";
        button.style.display = "block";

        let userResponse = response[0];
        let dayResponse = response[1];


        //SVUOTO
        document.getElementById("errorTables").innerHTML = "";

        //CREO LE DUE TABELLE
        document.getElementById("errorTables").innerHTML += "<table class='table-wrapper-scroll-y my-custom-scrollbar' style='margin-left: 30px'>"+
                                                            "<thead><tr><th scope='col'>Controlli sulla ordinanza sul lavoro (OLDL)</th></tr></thead><tbody id='errorTableUsers'></tbody></table>" +
                                                            "<table class='table-wrapper-scroll-y my-custom-scrollbar' style='min-width: 250px; margin-left: 30px'>"+
                                                            "<thead><tr><th scope='col'>Contorlli sull'esistenza dei turni</th></tr></thead><tbody id='errorTableDays'></tbody></table>";


        let errorTableUsers = document.getElementById("errorTableUsers");
        let errorTableDays = document.getElementById("errorTableDays");

        // errorTableUsers.innerHTML ="";
        // errorTableDays.innerHTML ="";


        // document.getElementById("errorTableUsersTitle").innerHTML += "<h1>Errori sugli autisti</h1>";
        // errorTableUsers.innerHTML += "<tbody>";
        let count = 0;
        for (let i = 0; i < userResponse.length; i ++){
            let thisResponse = userResponse[i];


            for (let k = 0; k < thisResponse.length; k++){
                if (thisResponse[k]=== "OK"){

                } else {
                    count++;
                    errorTableUsers.innerHTML += "<tr><td scope='row' style='background:#FF9797'>"+ thisResponse[k] + "</td></tr>";
                }
            }
        }
        if(count ===0 ){
            errorTableUsers.innerHTML +="<tr><td scope='row' style='background:#6FD35B'>"+ "Tutto OK" + "</td></tr>";
        }



        // errorTableUsers.innerHTML += "</tbody>";
        count = 0;
    let dDay = new Date(thisDate);
        // document.getElementById("errorTableDaysTitle").innerHTML += "<h1>Errori sui giorni</h1>";
        // errorTableDays.innerHTML += "<tbody>";
        for (let i = 0; i < dayResponse.length; i ++) {
            let thisResponse = dayResponse[i];
            for (let k = 0; k < thisResponse.length; k++) {
                if (thisResponse[k] === "OK") {

                } else {
                    count++;
                    errorTableDays.innerHTML += "<tr><td scope='row' style='background:#FF9797'>" + "Il " + formatDate(dDay) + " " + thisResponse[k] + "</td></tr>";
                }

            }
            dDay.setDate(dDay.getDate() + 1);

            // errorTableDays.innerHTML += "</tbody>";
        }

        if (count === 0) {
            errorTableDays.innerHTML += "<tr><td scope='row' style='background:#6FD35B'>" + "Tutto OK" + "</td></tr>";
        }


    }).fail(function (err) {
    console.log(err);
    console.log('error');
})
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('-');
}