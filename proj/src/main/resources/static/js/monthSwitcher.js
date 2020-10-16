
//dattepicker con dipendenze aggiuntive
// http://jsfiddle.net/brunodd/k5zookLt/20/
var katon = new Date();
var fechaFin = new Date();
var FromEndDate = new Date();
var ToEndDate = new Date();




$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', function(selected){
    katon = new Date(selected.date.valueOf());
    katon.setDate(katon.getDate(new Date(selected.date.valueOf())));
    $('.to').datepicker('setStartDate', katon);
});

// $('.to').datepicker({
//     autoclose: true,
//     minViewMode: 1,
//     format: 'mm/yyyy'
// }).on('changeDate', function(selected){
//     FromEndDate = new Date(selected.date.valueOf());
//     FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
//     $('.from').datepicker('setEndDate', FromEndDate);
// });