import React from 'react'
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'

// Load Highcharts modules
require("highcharts/modules/exporting")(Highcharts);

//var Highcharts  = Highcharts

var options = {
    chart: {
        type: 'pie',
        options3d: {
            enabled: true,
            alpha: 45,
            beta: 0
        }
    },
    title: {
        text: 'OVC_SERV [Enrollment Stream]'
    },
    accessibility: {
        point: {
            valueSuffix: '%'
        }
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            depth: 35,
            dataLabels: {
                enabled: true,
                format: '{point.name}'
            }
        }
    },
    series: [{
        type: 'pie',
        name: '',
        data: [
            ['Child Living with HIV', 45.0],
            ['Adolescents at High Risk of HIV (including KP minors, OVC with previous STI, Teen Moms, etc.)', 26.8],
            {
                name: 'HIV Exposed Infant',
                y: 12.8,
                sliced: true,
                selected: true
            },
            ['Children of Key Populations', 8.5],
            ['Children Living with PLHIV ', 6.2],
        ]
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