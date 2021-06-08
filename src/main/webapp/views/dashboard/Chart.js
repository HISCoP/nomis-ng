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
        text: 'TOtal OVC Enrollment For The Last 6months'
    },
    xAxis: {
        categories: ['Jan.', 'Feb.', 'March', 'April', 'May', 'June']
    },
    labels: {
        items: [{
            html: 'Total HIV Positive',
            style: {
                left: '50px',
                top: '18px',
                color: ( // theme
                    Highcharts.defaultOptions.title.style &&
                    Highcharts.defaultOptions.title.style.color
                ) || 'black'
            }
        }]
    },
    series: [{
        type: 'column',
        name: 'Total OVC',
        data: [3, 2, 1, 3, 4]
    }, {
        type: 'column',
        name: 'Total HIV Positive',
        data: [2, 3, 5, 7, 6]
    }, {
        type: 'spline',
        name: 'Average',
        data: [3, 2.67, 3, 6.33, 3.33],
        marker: {
            lineWidth: 2,
            lineColor: Highcharts.getOptions().colors[3],
            fillColor: 'white'
        }
    }, ]
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