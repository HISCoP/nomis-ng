import React from "react";
import { CCol, CRow, CButton, CCard,
    CCardBody,
    CLink,
    CCardHeader, CCardFooter} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {Button, List, Container, Divider, Header, Icon} from 'semantic-ui-react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';

const HouseholdMember = () => {
    return (
        <>
            <CRow>
                <CCol xs="12">

                <Icon name='users' />  Household Members

                <CButton color={"primary"} className={"float-right mr-1 mb-1"}> New Caregiver</CButton> {" "}
                <CButton color={"primary"} className="float-right"> New OVC</CButton>
           <hr />
                </CCol>
            </CRow>
            <CRow>
                <CCol xs="12" sm="6" lg="4">
                   <MemberCard member={{type:"Caregiver"}}/>
                </CCol>
                <CCol xs="12" sm="6" lg="4">
                    <MemberCard  member={{type:"Child"}}/>
                </CCol>
                <CCol xs="12" sm="6" lg="4">
                    <MemberCard  member={{type:"Caregiver"}}/>
                </CCol>
                <CCol xs="12" sm="6" lg="4">
                    <MemberCard  member={{type:"Child"}}/>
                </CCol>
                <CCol xs="12" sm="6" lg="4">
                    <MemberCard  member={{type:"Child"}}/>
                </CCol>
            </CRow>
            </>
    );
}

const MemberCard = (props) => {
    return (
        <CCard>
            <CCardBody className={'text-center'}>
                <p className={'text-left'}><b>{props.member.type || ''}</b></p>
                <AccountCircleIcon fontSize="large" style={{padding:'5px'}}/><br/>
                <span>Ali Kindu</span><br/>
                <span>Male | 23 years</span>

            </CCardBody>
            <CCardFooter>
                <CButton color="primary" block >Provide Services</CButton>
            </CCardFooter>
        </CCard>
    );
}
export default HouseholdMember;