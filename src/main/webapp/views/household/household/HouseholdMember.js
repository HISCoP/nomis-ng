import React, {useState, useEffect} from "react";
import { CCol, CRow, CButton, CCard,
    CCardBody,CCardFooter} from "@coreui/react";
import { Icon} from 'semantic-ui-react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { Link } from 'react-router-dom';
import NewOvc from './NewOvc';
import NewCareGiver from './NewCareGiver';
import ProvideService from './ProvideService';

const HouseholdMember = () => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [modal2, setModal2] = useState(false);
    const toggle2 = () => setModal2(!modal2);


    return (
        <>
            <CRow>
                <CCol xs="12">

                <Icon name='users' />  Household Members

                <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle2}> New Caregiver</CButton> {" "}
                <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle}> New OVC</CButton>{" "}
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
            <NewOvc  modal={modal} toggle={toggle}/>
            <NewCareGiver  modal={modal2} toggle={toggle2}/>
            
            </>
    );
}

const MemberCard = (props) => {
    const [modal3, setModal3] = useState(false);
    const toggle3 = () => setModal3(!modal3);
    
    return (
        <>
        <CCard>
            <CCardBody className={'text-center'}>
                <p className={'text-left'}><b>{props.member.type || ''}</b></p>
                <AccountCircleIcon fontSize="large" style={{padding:'5px'}}/><br/>
                <Link color="inherit" to ={{
                    pathname: "/household-member/home",
                }}  ><span>Ali Kindu</span></Link><br/>
                <span>Male | 23 years</span>

            </CCardBody>
            <CCardFooter>
                <CButton color="primary" block onClick={toggle3}>Provide Services</CButton>
            </CCardFooter>
        </CCard>
        <ProvideService  modal={modal3} toggle={toggle3}/>
    </>
    );
}
export default HouseholdMember;