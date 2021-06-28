import React, { lazy } from 'react';
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
} from '@coreui/react';
import CIcon from '@coreui/icons-react';

import MainChartExample from '../charts/MainChartExample.js';
import Chart from './Chart'
import EnrollmentStream from './EnrollmentStream';
import ComprehensivePrevention from './ComprehensivePrevention';
import OVC_SERV_Female from './OVC_SERV_Female';
import OVC_SERV_Less_18 from './OVC_SERV_Less_18';
import OVC_SERV_Male from './OVC_SERV_Male';
import OVC_SERV_Than_18 from './OVC_SERV_Than_18';

const WidgetsDropdown = lazy(() => import('../widgets/WidgetsDropdown.js'))
const WidgetsBrand = lazy(() => import('../widgets/WidgetsBrand.js'))


const Dashboard = () => {
  return (
    <>
      {/* <WidgetsDropdown />
     */}
          <CRow>
            <CCol sm="6" className="p-2">
                <ComprehensivePrevention />
            </CCol>
            <CCol sm="6" className="p-2">
                <EnrollmentStream />
            </CCol>
            
          </CRow>

          <CRow>
            <CCol sm="6" className="p-2">
                <OVC_SERV_Than_18 />
            </CCol>
            <CCol sm="6" className="p-2">
                <OVC_SERV_Less_18 />
            </CCol>
            
          </CRow>
          <CRow>
            <CCol sm="6" className="p-2">
                <OVC_SERV_Female />
            </CCol>
            <CCol sm="6" className="p-2">
                <OVC_SERV_Male />
            </CCol>
            
          </CRow>
        
     
    </>
  )
}

export default Dashboard
