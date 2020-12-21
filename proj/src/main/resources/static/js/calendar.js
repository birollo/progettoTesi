var lista_persone = [];
// let config = {};
let GSTCState = {};


let meseCorrente = {};
let mesePrecedente = {};

var katon = new Date();
let from = new Date("2021/01/01").valueOf();
$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', function(selected){

    document.getElementById("errorTables").innerHTML = "";


    katon = new Date(selected.date.valueOf());
    katon.setDate(katon.getDate(new Date(selected.date.valueOf())));
    $('.to').datepicker('setStartDate', katon);
    from = new Date(selected.date.valueOf()).valueOf();
    caricamento();
});


// async function aggiornamento(url = '', data = {}) {
//     // Default options are marked with *
//     const response = await fetch(url, {
//         method: 'POST', // *GET, POST, PUT, DELETE, etc.
//         mode: 'cors', // no-cors, *cors, same-origin
//         cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
//         credentials: 'same-origin', // include, *same-origin, omit
//         headers: {
//             'Content-Type': 'application/json'
//             // 'Content-Type': 'application/x-www-form-urlencoded',
//         },
//         redirect: 'follow', // manual, *follow, error
//         referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
//         body: JSON.stringify(data), // body data type must match "Content-Type" header
//     });
//     console.log("ho fatto");
//     return response.json(); // parses JSON response into native JavaScript objects
// }


let fistLoad = 0;
function caricamento() {
    const queryString = window.location.pathname.replace('/', '').replace('%20', ' ').replace('%20', ' ');
    // queryString.replace('%20', ' ');
    let thisDate = new Date(from.valueOf()).toISOString();
    if (queryString === ''){
        console.log("niente data");
        // thisDate = new Date(from.valueOf()).toISOString();
    }else {
        thisDate = new Date(queryString).toISOString();
        // thisDate = new Date(from.valueOf()).toISOString();
        from = new Date(queryString).toISOString();
        console.log("data=" + from);
        from = new Date(queryString).valueOf();
    }
    

    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            // dataType: 'text',
            data: {date: thisDate},
            // async: true,
            url: '/listaPersone',
            contentType: 'text'
        }).done(function (response) {

            items = {};
            lista_persone = response;

            for (var x = 0; x < lista_persone.length; x++) {
                // console.log("x" + lista_persone.length);
                if (lista_persone[x].u.turni.length === 0) {

                } else {
                    for (var y = 0; y < lista_persone[x].u.turni.length; y++) {
                        let thisId = lista_persone[x].u.turni[y].turno.name;
                        let thisName = lista_persone[x].u.name;
                        let thisSurname = lista_persone[x].u.surname;
                        let wrongDate = new Date(lista_persone[x].u.turni[y].date);
                        let thisDate = new Date();
                        thisDate.setTime(wrongDate.getTime() + 8.64e+7);
                        // let thisDate = new Date(lista_persone[x].turni[y].date).toString();

                        let color = choseItemColor(lista_persone[x].u.turni[y].turno.tipo);
                        createItem(thisId, thisSurname + " " + thisName, thisDate, color);

                    }
                }
                createRow(lista_persone[x].u.surname + " " + lista_persone[x].u.name , lista_persone[x].totThisMonth , lista_persone[x].totPreviousMonth);
            }

            createColumns();

            //todo: monthChoser
            let monthChose = document.getElementById('monthChose').value;
            let month;
            let year;
            let monthPlusYear;
            if (monthChose === "") {
                let date = new Date();
                month = date.getMonth();
                year = date.getFullYear();
                monthPlusYear = month.toString() + "/" + year.toString();
            } else {
                monthPlusYear = monthChose;
            }
            //todo:
            //ora monthPlusYear ha forma mm/YYYY

            let myConfig = createConfig();


            // let GSTCState = GSTC.api.stateFromConfig(myConfig);
            GSTCState = (window.state = GSTC.api.stateFromConfig(myConfig));
            // GSTCState.subscribe('config.plugin.ItemMovement', itemMovement => {
            //     if (!itemMovement || !itemMovement.item) return;
            //     state.update(`config.chart.items.${itemMovement.item.id}.isResizing`, itemMovement.item.resizing);
            // });


            const element = document.getElementById('app');

            const gstc = GSTC({
                element,
                state: GSTCState
            });
            window.gstc = gstc; // debug

            // const app = GSTC({
            //     element: document.getElementById("app"),
            //     state
            // });



            // weekEndColor();


        }).done(function () {
            if (queryString === ''){

            }else {
                check();
            }
            setTimeout(() => {  weekEndColor() }, 750);

        }).fail(function (err) {
            console.log(err);
            console.log('error');
        })
    });
}

function choseItemColor(type) {
    let color;
    switch (type) {
        case "Mattina":
            color = "SeaGreen";
            break;
        case "Meridiano":
            color = "Gold";
            break;
        case "Pomeriggio":
            color = "DarkGray";
            break;
        case "Sera":
            color = "DarkGray";
            break;
        case "Riposo":
            color = "LightSkyBlue";
            break;
        default:
            color = "DarkGray";

    }
    return color;
}

const rows = {};
function createRow(i, current,previos) {
    const id = i.toString();
    const prec = previos.toString();
    const corr = current.toString();
    let differencePrev = previos -10000;
    let differenceCurr = current -10000;
    let colorCurr = "white";
    let colorPrev = "white";


    if (differencePrev > 0 && differencePrev < 500 || differencePrev < 0 && differencePrev > -500){

    }else if (differencePrev<1000 && differencePrev > 500 || differencePrev < -500 && differencePrev> -1000){
        colorPrev = "rgba(247, 247, 73,.8)";
    }else if (differencePrev > 1000 || differencePrev < -1000){
        colorPrev = "rgba(255,153,0,.8)";
    }

    if (differenceCurr > 0 && differenceCurr < 500 || differenceCurr < 0 && differenceCurr > -500){

    }else if (differenceCurr<1000 && differenceCurr > 500 || differenceCurr < -500 && differenceCurr> -1000){
        colorCurr = "rgba(247, 247, 73,.8)";
    }else if (differenceCurr > 1000 || differenceCurr < -1000){
        colorCurr = "rgba(255,153,0,.8)";
    }

    rows[id] = {
        id,
        nome: `${id}`,

        // precedente: `${prec}`,
        precedente: '<div style="background:'+ `${colorPrev}` + '">'+`${prec}`+ " (" +`${differencePrev}`+ ")" +'</div>',
        // precedente:{
        //     data: `${prec}`,
        //     background: 'red',
        // },
        corrente: '<div style="background:'+ `${colorCurr}` + '">'+`${corr}`+ " (" +`${differenceCurr}`+ ")" +'</div>',
    };
}

const startDate = GSTC.api
    .date()
    .subtract(1, 'month')
    .valueOf();


let columns = {};

function createColumns() {
    columns = {
        percent: 100,
        resizer: {
            inRealTime: true
        },
        data: {
            nome: {
                id: 'nome',
                data: 'nome',
                width: 125,
                // expander: true,
                header: {
                    content: 'Cognome'
                }
            },
            precedente: {
                id: 'precedente',
                data:'precedente',
                width: 125,
                header: {
                    content: 'Mese precedente'
                },
                isHTML: true,
            },
            corrente: {
                id: 'corrente',
                data: 'corrente',
                width: 125,
                header: {
                    content: 'Mese corrente'
                },
                isHTML: true,
            }
        },
    }
}

let items = {};
let count = 0;

function createItem(id, name, date, color) {
    let endDay = new Date(date);
    let startDate = new Date(date);
    let thisId = id.toString();
    endDay.setDate(endDay.getDate() + 1);

    items[count] = {
        id: count,
        label: id,
        rowId: name,
        // moveable: false,
        time: {
            start: startDate.valueOf(),
            end: endDay.valueOf()
        },
        style: {
            background: color,
            color: 'Black'
        }
    };
    // items[items.length + 1] = {
    //     thisId,
    //     rowId: name,
    //     moveable: true,
    //     label: id,
    //     time: {
    //         start: date.valueOf(),
    //         end: endDay.valueOf(),
    //     },
    //     style: {background: 'green'}
    // };
    count++;
}


class ItemClickAction {
    constructor(element, data) {
        this.data = data;
        this.onClick = this.onClick.bind(this);
        element.addEventListener('click', this.onClick);
    }

    update(element, data) {
        this.data = data;
    }

    destroy(element, data) {
        element.removeEventListener('click', this.onClick);
    }

    onClick(event) {

        let rowId = this.data.item.rowId;
        console.log(this.data.toString());
        let time = this.data.item.time.start;
        // let date = new Date(time - 8.64e+7);
        let date = new Date(time);
        location.href = "assegnazione/" + rowId + "/" + date;

        // let message = prompt("Inserisci il numero del nuovo turno", "");
        // if (message != null) {
        //     let itemId = message;
        //     let rowId = this.data.item.rowId;
        //     let date = new Date(this.data.item.time.start);
        //     //console.log("message" + message);
        //     createItem(itemId, rowId, date);
        //     let jsonAssegnazione = {
        //         "turno": itemId,
        //         "persona": rowId,
        //         "data": date
        //     };
        //     aggiornamento('http://localhost:8080/aggiornamento', jsonAssegnazione);
        //
        //     GSTCState.update('config', config => {
        //         config.list.rows = rows;
        //         config.chart.items = items;
        //         config.chart.time.from = new Date('2020-01-01').getTime();
        //         config.chart.time.to = new Date('2020-01-14').getTime();
        //         return config;
        //     });
        //
        // }
    }
}


class RowClickAction {
    constructor(element, data) {
        this.data = data;
        this.onClick = this.onClick.bind(this);
        element.addEventListener('click', this.onClick);
    }

    update(element, data) {
        this.data = data;
    }

    destroy(element, data) {
        element.removeEventListener('click', this.onClick);
    }

    onClick(event) {
        let rowId = this.data.id.split(":")[0];
        let date = new Date(this.data.id.split(":")[1] + ":00");
        location.href = "assegnazione/" + rowId + "/" + date;

        // console.log("this.data" + this.data.id);
        // modal();
        // let message = prompt("Inserisci il turno", "1");
        // if (message != null) {
        //     let itemId = message;
        //     let rowId = this.data.id.split(":")[0];
        //     let date = new Date(this.data.id.split(":")[1] + ":00");
        //     // console.log("message" + message);
        //     createItem(itemId, rowId, date);
        //     let jsonAssegnazione = {
        //         "turno": itemId,
        //         "persona": rowId,
        //         "data": date
        //     };
        //     aggiornamento('http://localhost:8080/aggiornamento', jsonAssegnazione);
        //     GSTCState.update('config', config => {
        //         config.list.rows = rows;
        //         config.chart.items = items;
        //         config.chart.time.from = new Date('2020-01-01').getTime();
        //         config.chart.time.to = new Date('2020-01-14').getTime();
        //         return config;
        //     });
        //
        // }
    }
}

function createConfig() {


    const to = from + 1.21e+9;

    // const from = GSTC.api
    //     .date()
    //     .startOf('month')
    //     .valueOf();
    // const to = GSTC.api
    //     .date()
    //     .startOf('month')
    //     .valueOf()+1209600000 ;


    const config = {
        plugins: [
            // ItemMovement({
            //     moveable: false,
            //     resizable: false,
            //     collisionDetection: true
            // }),
            // Selection({
            //     selected(data, type) {
            //         console.log(data, type);
            //     }
            // }),
            // CalendarScroll(),
            WeekendHighlight(),
            CalendarScroll({
                speed: 0.2,
                hideScroll: true,
                onChange(time) {
                    // console.log(GSTC.api.date(time.from).format('YYYY-MM-DD'), GSTC.api.date(time.to).format('YYYY-MM-DD'));
                    setTimeout(() => {  weekEndColor() }, 550);
                }
            })
        ],
        height: 600,
        list: {
            rowHeight: 30,
            rows,
            columns,
        },
        chart: {
            items,
            time: {
                //inizio calendario
                // from: new Date('2020-01-01').getTime(),
                //fine calendario
                // to: new Date('2020-02-01').getTime(),
                // period: 'day'

                calculatedZoomMode: true,
                from,
                to
            }
        },
        actions: {
            'chart-timeline-items-row-item': [ItemClickAction],
            'chart-timeline-grid-row-block': [RowClickAction],
        },
    };
    return config;
}




function weekEndColor( ) {
    let giorni = document.getElementsByClassName("gantt-schedule-timeline-calendar__chart-calendar-date--level-1");
    for (var i=0; i < giorni.length; i++){
        let ciao = giorni[i].childNodes[2].childNodes[2].childNodes[1].data.valueOf();
        if (ciao === "Sat" || ciao === "Sun"){
            giorni[i].style.backgroundColor = "rgba(227, 129, 230,.8)";
        }else {
            giorni[i].style.backgroundColor = "rgb(253, 253, 253)";
        }
    }



}
// //creazione calendario
// let GSTCState = (window.state = GSTC.api.stateFromConfig(config));
//
// const element = document.getElementById('app');

// const gstc = GSTC({
//     element,
//     state: GSTCState
// });

