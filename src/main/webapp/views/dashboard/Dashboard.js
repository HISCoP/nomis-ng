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
           
           
          </CRow>
         
          <Chart />
        </CCardBody>
       
      </CCard>
     
       

     
    </>
  )
}

export default Dashboard
