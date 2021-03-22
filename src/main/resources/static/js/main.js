$('document').ready(function () {

    $('#logoutButton').on('click', function () {
        sessionStorage.clear();
    });

    if ($('#loginOrRegister').val() == "register") {
        $('#registerCollapse').collapse('show');
        $('#registerCollapseBtn').attr('class', 'btn btn-dark float-right btn-sm disabled');
    } else {
        $('#loginCollapse').collapse('show');
        $('#loginCollapseBtn').attr('class', 'btn btn-sm btn-dark disabled');
    }

    $('#loginCollapseBtn').on('click', function () {
        $('#loginCollapseBtn').attr('class', 'btn btn-dark btn-sm disabled')
        $('#registerCollapseBtn').attr('class', 'btn btn-dark btn-sm float-right')
        $('#loginCollapse').collapse('show');
        $('#registerCollapse').collapse('hide');
    });

    $('#registerCollapseBtn').on('click', function () {
        $('#loginCollapseBtn').attr('class', 'btn btn-dark btn-sm')
        $('#registerCollapseBtn').attr('class', 'btn btn-dark btn-sm float-right disabled')
        $('#loginCollapse').collapse('hide');
        $('#registerCollapse').collapse('show');
    });

    $('#showVehicleModal').click(function (event) {
        event.preventDefault();
        $('#newVehicleModal').modal('show');
    });

    $('.table #showOrderVehicleModal').click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        localStorage.setItem("vehicleOrderHref", href);
        $('#vehicleOrderModal').modal('show');
    });

    $('.table #showOrderHistoryModal').click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        localStorage.setItem("vehicleOrderHref", href);
        $('#vehicleOrderHistoryModal').modal('show');
        $.get(href, function (orders){
            if (orders.length > 0) {
                $('#orderHistoryHead')
                    .append($('<tr>')
                        .append($('<th>')
                            .text('Kto wypożyczał'))
                        .append($('<th>')
                            .text('Od kiedy'))
                        .append($('<th>')
                            .text('Do kiedy'))
                    );
                $.each(orders, function (i, order){
                    $('#orderHistoryBody')
                        .append($('<tr>')
                            .append($('<td>')
                                .text(order.user.username))
                            .append($('<td>')
                                .text(order.dateStart))
                            .append($('<td>')
                                .text(order.dateFinish))
                        )
                });
            } else {
                $('#orderHistoryBody')
                    .append($('<tr>')
                        .append($('<td>')
                            .text('Aktualnie nie ma żadnych wypożyczeń'))
                    );
            }
        });
    });

    $('#vehicleOrderModal').on('hidden.bs.modal', function () {
        localStorage.clear();
        $('#dateStart').val('');
        $('#dateFinish').val('');
    });

    $('#vehicleOrderHistoryModal').on('hidden.bs.modal', function () {
        localStorage.clear();
        $('#orderHistoryHead tr').remove();
        $('#orderHistoryBody tr').remove();
    });

    $('#orderVehicleButton').on('click', function (event){
        event.preventDefault();
        var href = localStorage.getItem("vehicleOrderHref");
        $.post(href, {
            dateStart: $('#dateStart').val(),
            dateFinish: $('#dateFinish').val()
        }).done(function (response){
            $('#vehicleOrderModal').modal('hide');
            localStorage.clear();
            if (response == true){
                window.location.reload();
            } else {
                document.getElementById("vehicleOrderAlreadyExistAlert").style.display = "block";
            }
        });
    });

    $('#closeVehicleOrderAlreadyExistAlert').click(function (event) {
        event.preventDefault();
        document.getElementById("vehicleOrderAlreadyExistAlert").style.display = "none";
    });

    $('#showInformationModal').click(function (event) {
        event.preventDefault();
        $('#informationModal').modal('show');
    });

    $('.table #deleteVehicle').click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function () {
            window.location.reload();
        });
    });

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });

});