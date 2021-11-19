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
        type: 'column'
    },
    title: {
        text: 'OVC_SERV- 18+ Years by Status type'
    },
    xAxis: {
        categories: ['Oct to Dec 2020', 'Jan to Mar 2021']
    },
    yAxis: {
        min: 0,
       
        stackLabels: {
            enabled: true,
            style: {
                fontWeight: 'bold',
                color: ( // theme
                    Highcharts.defaultOptions.title.style &&
                    Highcharts.defaultOptions.title.style.color
                ) || 'gray'
            }
        }
    },
    // legend: {
    //     align: 'right',
    //     x: -30,
    //     verticalAlign: 'bottom',
    //     y: 25,
    //     floating: true,
    //     backgroundColor:
    //         Highcharts.defaultOptions.legend.backgroundColor || 'white',
    //     borderColor: '#CCC',
    //     borderWidth: 1,
    //     shadow: false
    // },
    tooltip: {
        headerFormat: '<b>{point.x}</b><br/>',
        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
    },
    plotOptions: {
        column: {
           
            stacking: 'normal',
            dataLabels: {
                enabled: true
            }
        }
    },
    series: [{
        name: 'OVC_Active-18+',
        data: [754, 989]
    }, {
        name: 'OVC_Graduated-18+',
        data: [432, 212]
    },]
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