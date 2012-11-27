(function ($) {
    /** DOCUMENT READY - INITIALIZATION **/
    $(document).ready(function () {
        chart = initChart();
        addMemoryChart(chart, 'free-memory', 'appcfm51');
        addMemoryChart(chart 'available-memory', 'appcfm51');
        addMemoryChart(chart 'total-memory', 'appcfm51');
        addMemoryChart(chart 'max-memory', 'appcfm51');
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
            xAxis: {
                type: 'datetime',
            },
            tooltip: {
                formatter: function () {
                    var decTime = this.y;
                    var hour = Math.floor(decTime);
                    var min = Math.round(60 * (decTime - hour));
                    return $.datepicker.formatDate('yy-mm-dd', (new Date(this.x))) + '=> ' + hour + ':' + min;
                    //return this.y + "Mb";
                }
            },
            series: []
        });
        return chart;
    }

    function addMemoryChart(chart,type, server) {
        var reqA = $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'http://localhost:9090/monitor?action='+type+'&server='+server

        });
        reqA.done(function (mem) {
            chart.addSeries({
                type: 'spline',
                name: type,
                data: mem
            });
        })
    }

})(jQuery);