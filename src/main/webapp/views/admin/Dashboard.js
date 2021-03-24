import React from "react";
import { CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Icon, Label} from 'semantic-ui-react'
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import ChildCareIcon from '@material-ui/icons/ChildCare';
import AccessibilityNewIcon from '@material-ui/icons/AccessibilityNew';
//import ServiceHistoryPage from './../Widgets/ServiceHistoryPage';

const Dashboard = () => {

    return (
        <>
              <TopDashboardStats />
              <MidDashboardStats />
            <CRow>
                <CCol xs="12" >
              <RecentServiceOffered />
                </CCol>
            {/* <CCol xs="12" >
               <ServiceHistoryPage />
            </CCol> */}

    </CRow>
    </>

    )
}

const TopDashboardStats = (props) => {
    const isCareGiver = false;
    return (
        <CRow>
            <CCol xs="12" sm="6" lg="4">
                <CWidgetIcon text="HIV Status" header="Negative"  color="success" iconPadding={false}>
                    <FavoriteBorderIcon />
                </CWidgetIcon>
            </CCol>
            {isCareGiver ?
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Viral Load" header="1000 copies/ml" color="success" iconPadding={false}>
                        <CIcon width={24} name="cil-graph"/>
                    </CWidgetIcon>
                </CCol> :
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Weight" header="50 kg" color="success"  iconPadding={false}>
                        <CIcon width={24} name="cil-graph"/>
                    </CWidgetIcon>
                </CCol>
            }
            {isCareGiver ?
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Children" header="2" color="success" iconPadding={false}>
                        <ChildCareIcon/>
                    </CWidgetIcon>
                </CCol>
                :

                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="BMI" header="6 - Underweight" color="success" iconPadding={false}>
                        <AccessibilityNewIcon/>
                    </CWidgetIcon>
                </CCol>
            }
        </CRow>
    );
}

const MidDashboardStats = () => {
    return (
        <>
            <CRow>
                <CCol xs="12" sm="6" lg="6" >
                    <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                        <CCardHeader> <Icon name='home' /> Household Information</CCardHeader>
                        <CCardBody>

                                <span>Household ID: <small>FCT/FGH/12/2020 </small></span><br/>
                                <span>Address: <small>No 20, Apata Road, Ibadan </small></span><br/>
                                <span>Date Of Assessment: <small>12/11/2020</small> </span><br/>
                                <span>Primary Caregiver Name: <small>Moses Lambo </small></span><br/>
                        </CCardBody>
                    </CCard>
                </CCol>
                <CCol xs="12" sm="6" lg="6">
                    <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                        <CCardHeader> <Icon name='user' /> Caregiver Information</CCardHeader>
                        <CCardBody>

                            <span>Caregiver Name: <small>Amina Lambo </small></span><br/>
                            <span>Age: <small>30 years</small></span><br/>
                            <span>Sex: <small>Female</small> </span><br/>
                            <span>Phone Number: <small>07069969954 </small></span><br/>
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
        </>
    )
}

const RecentServiceOffered = () => {
    return (
        <>
                    <CCard>
                        <CCardHeader>Recent Service Offered
                            <div className="card-header-actions">
                                18-02-2020 12:00 PM
                            </div>
                        </CCardHeader>
                        <CCardBody>
                            <Label.Group color='blue' >
                                        <Label>Health Education</Label>
                                        <Label>HIV Care & Support </Label>
                                        <Label>Water Treatment </Label>
                            </Label.Group>
                        </CCardBody>
                    </CCard>
            </>
    );
}
export default Dashboard;