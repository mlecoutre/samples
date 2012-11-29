(function ($) {
    /** DOCUMENT READY - INITIALIZATION **/
    $(document).ready(function () {

        chart = initChart();

    });



    $('#display').click(function () {
        //TODO: clean the chart when click on the display button

        var as = $('#selAS').val();
        var server = $('#selServer').val();
        var applicationName = $('#selApplicationName').val();
        addSerie(chart, 'threads',applicationName, server, as);

    });

    $('#clear').click(function () {
        while (chart.series.length > 0)
        chart.series[0].remove(true);
    });

    function initChart() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'memoryContainer'
            },
            title: 'Threads report',
            yAxis: {
                title: {
                    text: 'nb threads'
                }
            },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 0,
                        lineWidth: 0
                    }
                }
            },
            xAxis: {
                type: 'datetime',
            },
            tooltip: {
                formatter: function () {

                    return $.datepicker.formatDate('yy-mm-dd', (new Date(this.x))) + ' : ' + this.y;
                    //return this.y + "Mb";
                }
            },
            series: []
        });
        return chart;
    }

    function addSerie(chart, type, applicationName, server, as) {
        var reqA = $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'http://localhost:9090/monitor?action=' + type + '&applicationName='+applicationName+'&server=' + server + '&as=' + as
        });
        reqA.done(function (mem) {
            chart.addSeries({
                type: 'spline',
                name: as,
                data: mem
            });
        })
    }

})(jQuery);