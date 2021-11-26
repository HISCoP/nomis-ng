import React, {useEffect,useState, lazy } from 'react'
import axios from "axios";
import { url as baseUrl } from "./../../api";
import {
  CBadge,
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,

  CRow,
  
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import Chart from './Chart'


const WidgetsDropdown = lazy(() => import('../widgets/WidgetsDropdown.js'))
const WidgetsBrand = lazy(() => import('../widgets/WidgetsBrand.js'))


const Dashboard = (props) => {
  
     

  return (
    <>

      <WidgetsDropdown />
      <CCard>
        <CCardBody>
          <CRow>
            {/* <CCol sm="12">
              <h4 id="traffic" className="card-title mb-0">TOtal OVC Enrollment For The Last 6months</h4>
             
            </CCol> */}
           
          </CRow>
          {/* <MainChartExample style={{height: '300px', marginTop: '40px'}}/> */}
          <Chart />
        </CCardBody>
       
      </CCard>
     
       

     
    </>
  )
}

export default Dashboard
