import React from 'react'
import {
  CWidgetDropdown,
  CRow,
  CCol,
  CDropdown,

  CWidgetBrand
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import ChartLineSimple from '../charts/ChartLineSimple'
import ChartBarSimple from '../charts/ChartBarSimple';
import {TiFlowChildren} from 'react-icons/ti';
import {GiTeamUpgrade} from 'react-icons/gi';
import {FaChild} from 'react-icons/fa';
import {GiFamilyHouse} from 'react-icons/gi'

const WidgetsDropdown = () => {
  // render
  return (
    <CRow>
      <CCol sm="6" lg="3">
        <CWidgetDropdown

          color="xing"
          header="0"
          text="Total Household"
          footerSlot={
            <ChartLineSimple
              pointed
              className="c-chart-wrapper mt-3 mx-3"
              style={{height: '70px'}}
              dataPoints={[65, 59, 84, 84, 51, 55, 40]}
              pointHoverBackgroundColor="secondary"
              label="VC"
              labels="months"
            />
          }
        >
          <CDropdown>
          <GiFamilyHouse />
          </CDropdown>
        </CWidgetDropdown>
      </CCol>

      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="vimeo"
          header="0"
          text="Total VC"
          footerSlot={
            <ChartLineSimple
              pointed
              className="mt-3 mx-3"
              style={{height: '70px'}}
              dataPoints={[1, 18, 9, 17, 34, 22, 11]}
              pointHoverBackgroundColor="success"
              options={{ elements: { line: { tension: 0.00001 }}}}
              label="Members"
              labels="months"
            />
          }
        >
          <CDropdown>
          <FaChild />
          </CDropdown>
        </CWidgetDropdown>
      </CCol>

      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="gradient-warning"
          header="0"
          text="Total VC Graduated"
          footerSlot={
            <ChartLineSimple
              className="mt-3"
              style={{height: '70px'}}
              backgroundColor="rgba(255,255,255,.2)"
              dataPoints={[78, 81, 80, 45, 34, 12, 40]}
              options={{ elements: { line: { borderWidth: 2.5 }}}}
              pointHoverBackgroundColor="warning"
              label="Total"
              
            />
          }
        >
          <CDropdown>
           <GiTeamUpgrade />           
          </CDropdown>
        </CWidgetDropdown>
      </CCol>



      
    <CCol sm="6" lg="3">
      <CWidgetBrand
        rightHeader="0"
        rightFooter="Total VC Positive"
        leftHeader="0"
        leftFooter="Linked To Care "
        color="gradient-warning"
      >
        
        <TiFlowChildren
          height="50"
          width="50"
          size="20"
          className="my-3"
        /> 
      </CWidgetBrand>
    </CCol>



    </CRow>
  )
}

export default WidgetsDropdown
