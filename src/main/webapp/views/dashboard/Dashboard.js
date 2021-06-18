import React, { lazy } from 'react'
import {
  CBadge,
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CProgress,
  CRow,
  CCallout
} from '@coreui/react'
import CIcon from '@coreui/icons-react'

import MainChartExample from '../charts/MainChartExample.js'
import Chart from './Chart'

const WidgetsDropdown = lazy(() => import('../widgets/WidgetsDropdown.js'))
const WidgetsBrand = lazy(() => import('../widgets/WidgetsBrand.js'))


const Dashboard = () => {
  return (
    <>
      <WidgetsDropdown />
      <CCard>
        <CCardBody>
          <CRow>
            <CCol sm="12">
              <h4 id="traffic" className="card-title mb-0">TOtal OVC Enrollment For The Last 6months</h4>
             
            </CCol>
           
          </CRow>
          <MainChartExample style={{height: '300px', marginTop: '40px'}}/>
          <Chart />
        </CCardBody>
       
      </CCard>


     
    </>
  )
}

export default Dashboard
