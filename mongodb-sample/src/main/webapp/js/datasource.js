(function ($) {
    /** DOCUMENT READY - INITIALIZATION **/
    $(document).ready(function () {

        chart = initChart();

    });

    function initDataSourceList() {
        var as = $('#selAS').val();
        var server = $('#selServer').val();
        var applicationName = $('#selApplicationName').val();
        var reqDS = $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: '/services/MonitorConfig/' + applicationName +'/' + server + '/' + as
        });
        reqDS.done(function (dataSources) {
            $('#dataSources').html(Mustache.to_html($('#dataSources-template').html(), dataSources));
            $('#dataSourceBox').show();
            $('#showDS').toggleClass('btn-primary');
            $('#display').toggleClass('btn-primary');
        });
    }

    $('#showDS').click(function () {
        initDataSourceList();
    });

    $('#display').click(function () {
        //TODO: clean the chart when click on the display button
        var as = $('#selAS').val();
        var server = $('#selServer').val();
        var dataSource = $('#selDataSource').val();
        var applicationName = $('#selapplicationName').val();
        addDSChart(chart, 'ds-used-connections', applicationName, server, as, dataSource);

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
            title: 'Monitoring reports',
            yAxis: {
                title: {
                    text: 'Megabytes'
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

    function addDSChart(chart, type, applicationName, server, as, ds) {
        var reqA = $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'http://localhost:9090/monitor?action=' + type +'&applicationName='+applicationName+ '&server=' + server + '&as=' + as + '&dataSource=' + ds

        });
        reqA.done(function (mem) {
            chart.addSeries({
                type: 'spline',
                name: ds,
                data: mem
            });
        })
    }

})(jQuery);