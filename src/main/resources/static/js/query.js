/**
 * Created by ang on 2017/8/8.
 */

$(document).ready(function(){

    $('#dd').calendar({
        trigger: '#dt',
        zIndex: 999,
        format: 'yyyy-mm-dd',
        onSelected: function (view, date, data) {
            console.log('event: onSelected')
        },
        onClose: function (view, date, data) {
            console.log('event: onClose')
            console.log('view:' + view)
            console.log('date:' + date)
            console.log('data:' + (data || 'None'));
        }
    });


    $('#asubmit').click(function () {


        var request = {
            day: $('#dt').val(),
            tenantid: $('#tenantid').val()
        };

        var requestJson = JSON.stringify(request);
        queryShow(encodeURI(requestJson));

    });
});


function queryShow(data){
    setQueryCountByHour("containerLine", "/queryCountByHour",data);
    setQueryByTenantIDByDay("containerBar", "/queryByTenantIDByDay",data);
    setQueryStateCount("containerPie", "/queryStateCount",data);
    setQueryCacheState("containerPie2", "/queryCacheState",data);
}


function getData(data,url){
    var result;
    $.ajax({
        type: "get",
        url: url+"?data="+data,
        data: "",
        async:false,
        contentType: "application/x-www-form-urlencoded;charset:utf-8",
        //contentType: "application/json;charset:utf-8",
        success: function (data) {
            result = data;
        }
    });
    return result;
}


function setQueryCacheState(container, path,request) {

    var pieData=getData(request,path);

    var colors = Highcharts.getOptions().colors,
        categories = ['不刷新', '强行刷新'],
        data = [{
            y: pieData.data[0],
            color: colors[0],
            drilldown: {
                name: 'IE 版本',
                categories: ['使用缓存', '未使用缓存'],
                data: [pieData.data[1], pieData.data[2]],
                color: colors[0]
            }
        }, {
            y: pieData.data[3],
            color: colors[1],
            drilldown: {
                name: 'Firefox 版本',
                categories: ['不适用缓存'],
                data: [pieData.data[3]],
                color: colors[1]
            }
        }],
        browserData = [],
        versionsData = [],
        i,
        j,
        dataLen = data.length,
        drillDataLen,
        brightness;
// Build the data arrays
    for (i = 0; i < dataLen; i += 1) {
        // add browser data
        browserData.push({
            name: categories[i],
            y: data[i].y,
            color: data[i].color
        });
        // add version data
        drillDataLen = data[i].drilldown.data.length;
        for (j = 0; j < drillDataLen; j += 1) {
            brightness = 0.2 - (j / drillDataLen) / 5;
            versionsData.push({
                name: data[i].drilldown.categories[j],
                y: data[i].drilldown.data[j],
                color: Highcharts.Color(data[i].color).brighten(brightness).get()
            });
        }
    }
// Create the chart
    Highcharts.chart(container, {
        chart: {
            type: 'pie'
        },
        title: {
            text: pieData.day+'查询缓存使用情况'
        },
        subtitle: {
            text: '内环为是否刷新，外环为具体缓存的使用情况'
        },
        yAxis: {
            title: {
                text: '查询缓存使用比率'
            }
        },
        exporting:{
            enabled:false
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            pie: {
                shadow: false,
                center: ['50%', '50%']
            }
        },
        tooltip: {
            valueSuffix: '%'
        },
        series: [{
            name: '刷新情况',
            data: browserData,
            size: '60%',
            dataLabels: {
                formatter: function () {
                    return this.y > 5 ? this.point.name : null;
                },
                color: '#ffffff',
                distance: -30
            }
        }, {
            name: '缓存使用',
            data: versionsData,
            size: '80%',
            innerSize: '60%',
            dataLabels: {
                formatter: function () {
                    // display only if larger than 1
                    return this.y > 1 ? '<b>' + this.point.name + ':</b> ' +
                        this.y + '%' : null;
                }
            },
            id: 'versions'
        }],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 400
                },
                chartOptions: {
                    series: [{
                        id: 'versions',
                        dataLabels: {
                            enabled: false
                        }
                    }]
                }
            }]
        }
    });
}



function setQueryByTenantIDByDay(container, path,request){
    var pieData=getData(request,path);
    Highcharts.chart(container, {
        chart: {
            type: 'column'
        },
        title: {
            text: pieData.day+'租户查询情况'
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -270,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '查询量'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '查询量: <b>{point.y} 次</b>'
        },
        exporting:{
            enabled:false
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '查询次数',
            data:pieData.data,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
}


function setQueryCountByHour(container, path, request){
   
    var pieData=getData(request,path);
    Highcharts.chart(container, {
        title: {
            text: pieData.day+'查询统计',
            x: -20
        },
        xAxis: {
            categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11','12', '13', '14', '15','16', '17', '18', '19', '20', '21', '22', '23']
        },
        yAxis: {
            title: {
                text: '查询量'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        exporting:{
            enabled:false
        },
        credits: {
            enabled: false
        },
        tooltip: {
            valueSuffix: '次'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '查询总量',
            data: pieData.allData
        }, {
            name: 'DW查询量',
            data: pieData.queryData
        },{
            name: '查询失败量',
            data: pieData.errorData
        }]
    });


}


function setQueryStateCount(container, path,request){

    var pieData=getData(request,path);
    Highcharts.chart(container, {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: pieData.day+'错误类型分析'
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        exporting:{
            enabled:false
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    enabled: true,
                    formatter:function(){
                        return '<b>'+this.point.name+'</b>:'+this.point.percentage.toFixed(2)+"%";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '错误类型占比',
            data: pieData.data

        }]
    });

}



function setPie( container, path) {
    var pieData=getData("",path);
    Highcharts.chart(container, {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Browser market shares January, 2015 to May, 2015'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color:  'black'
                    }
                }
            }
        },
        series:pieData
    });
}

function setLine(container,path){
    var dataStr=getData("",path);
     Highcharts.chart(container, {
        title: {
            text: '不同城市的月平均气温',
            x: -20
        },
        subtitle: {
            text: '数据来源: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
            title: {
                text: '温度 (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series:dataStr
    });
}

function setColumn(container,path) {
    Highcharts.chart(container, {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Monthly Average Rainfall'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: {
            categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Rainfall (mm)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Tokyo',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

        }, {
            name: 'New York',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

        }, {
            name: 'London',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

        }, {
            name: 'Berlin',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

        }]
    });
}


function scheduleLine() {
//  Get the CSV and create the chart
    $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=analytics.csv&callback=?', function (csv) {

        Highcharts.chart('container', {

            data: {
                csv: csv
            },

            title: {
                text: 'Daily visits at www.highcharts.com'
            },

            subtitle: {
                text: 'Source: Google Analytics'
            },

            xAxis: {
                tickInterval: 7 * 24 * 3600 * 1000, // one week
                tickWidth: 0,
                gridLineWidth: 1,
                labels: {
                    align: 'left',
                    x: 3,
                    y: -3
                }
            },

            yAxis: [{ // left y axis
                title: {
                    text: null
                },
                labels: {
                    align: 'left',
                    x: 3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }, { // right y axis
                linkedTo: 0,
                gridLineWidth: 0,
                opposite: true,
                title: {
                    text: null
                },
                labels: {
                    align: 'right',
                    x: -3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }],

            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },

            tooltip: {
                shared: true,
                crosshairs: true
            },

            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function (e) {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: e.pageX || e.clientX,
                                        y: e.pageY || e.clientY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: Highcharts.dateFormat('%A, %b %e, %Y', this.x) + ':<br/> ' +
                                    this.y + ' visits',
                                    width: 200
                                });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },

            series: [{
                name: 'All visits',
                lineWidth: 4,
                marker: {
                    radius: 4
                }
            }, {
                name: 'New visitors'
            }]
        });
    });

}

//    var dataStr= [{
//        name: '东京',
//        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
//    }, {
//        name: '纽约',
//        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
//    }, {
//        name: '柏林',
//        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
//    }, {
//        name: '伦敦',
//        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
//    }];
