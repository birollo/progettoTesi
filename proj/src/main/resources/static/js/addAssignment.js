
//todo: gif durante caricamento
// These will be automatically called when Ajax requests begin or end.
// $.ajaxStart(function () {
//     $('#loading').show();  // show loading indicator
// });
//
// $.ajaxStop(function()
// {
//     $('#loading').hide();  // hide loading indicator
// });


const selectElement = document.getElementById('thisTurno');
const difference = document.getElementById('difference');

// selectElement.addEventListener('online', getStartTime);
// selectElement.addEventListener('change',computeDifference);

selectElement.addEventListener('change',ciao);
async function ciao() {
    await getStartTime();
    await computeDifference();
}

function getStartTime() {
    let value = document.getElementById('thisTurno').value;
    $.ajax({
        type: 'GET',
        // async: true,
        url: '/assegnazione/{id}/{date}/getStartTime',
        data: {
          turno: value,
        },
        contentType: 'text'
    }).done(function (response) {
        let turno = response;
        let startTime = turno.startTime.split(":");
        startTime = startTime[0] +":"+ startTime[1];
        document.getElementById("thisStart").innerHTML = startTime;

        let endTime = document.getElementById("thisEnd").innerText.toString();


    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}



function computeDifference() {
    console.log(diff_hours());
    difference.innerHTML = diff_hours();
}


function diff_hours() {
    const start = document.getElementById("thisStart").innerText.toString();
    const end = document.getElementById("thisEnd").innerText.toString();
    let dt1 = new Date("October 14, 2014 " + start + ":00");
    let dt2 = new Date("October 13 2014 " + end + ":00");
    //todo: legge startTime precedente
    var diff =(dt2.getTime() - dt1.getTime()) / 1000;
    diff /= (60 * 60);
    var ore = Math.abs(Math.trunc(diff));
    var minuti = Math.round(Math.abs((diff % 1) *60));
    return ore + ":" + minuti;
}
