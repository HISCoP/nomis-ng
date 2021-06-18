import React from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'

// Load Highcharts modules
require("highcharts/modules/exporting")(Highcharts);
// Age categories
var categories = [
    '0-4', '5-9', '10-14', '15-19',
    '20-24', '25-29', '30-34', '35-39', '40-44',
    '45-49', '50-54', '55-59', '60-64', '65-69',
    '70-74', '75-79', '80-84', '85-89', '90-94',
    '95-99', '100 + '
];

//var Highcharts  = Highcharts

var options = {
    chart: {
        zoomType: 'xy'
    },
    title: {
        text: 'Total OVC Enrollment For The Last 6 months'
    },
    
    xAxis: [{
        categories: [ 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        crosshair: true
    }],
    yAxis: [{ // Primary yAxis
        labels: {
            
        },
        title: {
            text: 'TOTAL OVC',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        }
    }, { // Secondary yAxis
        title: {
           
        },
        labels: {
            
        },
        
    }],
    tooltip: {
        shared: true
    },
    legend: {
        layout: 'vertical',
        align: 'left',
        x: 120,
        verticalAlign: 'top',
        y: 100,
        floating: true,
        backgroundColor:
            Highcharts.defaultOptions.legend.backgroundColor || // theme
            'rgba(255,255,255,0.25)'
    },
    series: [{
        name: 'OVC',
        type: 'column',
        yAxis: 1,
        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0,  54.4],
        tooltip: {
            valueSuffix: ' mm'
        }

    }, {
        name: 'Positive',
        type: 'spline',
        data: [ 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
        
    }]
}


 const Chart = () => {
return (

    <div>
  <HighchartsReact
    highcharts={Highcharts}
    options={options}
  />
</div>
)
}

export default Chart;