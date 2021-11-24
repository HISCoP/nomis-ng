import React, {useState, useEffect} from "react";
import { CCol, CRow, CButton, CCard,
    CCardBody,CCardFooter} from "@coreui/react";
import { Icon} from 'semantic-ui-react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { Link } from 'react-router-dom';
import NewOvc from './NewOvc';
import NewCareGiver from './NewCareGiver';
import ProvideService from './ProvideService';
import { connect } from "react-redux";
import { fetchAllHouseHoldMembersByHouseholdId } from "./../../../actions/houseHold";
import { Alert } from 'reactstrap';
import {calculateAge} from "./../../../utils/calculateAge";

const HouseholdMember = (props) => {
    //Getting the household Id from the props 
    
    const [houseHoldId, sethouseHoldId] = useState(props.houseHoldId);
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [modal2, setModal2] = useState(false);
    const toggle2 = () => setModal2(!modal2);
    const [loading, setLoading] = useState('')

    useEffect(() => {
    fetchMembers();
    }, []); //componentDidMount

    const fetchMembers = () => {
        setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllMember(houseHoldId, onSuccess, onError);
    }

    return (
        <>
            <CRow>
                <CCol xs="12">

                <Icon name='users' />  Household Members

                <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle2}> New Caregiver</CButton> {" "}
                <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle}> New VC</CButton>{" "}
           <hr />
                </CCol>
            </CRow>
            <CRow>
            {!loading && props.houseMemberList !==null ? props.houseMemberList.map((member) => (
                (
                <CCol xs="12" sm="6" lg="4" key={member.id}>
                   <MemberCard  member={member} householdId={houseHoldId} />
                </CCol>
                )
            )
            )
            :
            "Loading please wait.."
            }
                        
            </CRow>
            {props.houseMemberList.length<=0 ?
                <Alert color="primary">
                    No Household member please click the button above to add a new member to this household
                </Alert>
            :
            ""
            }
            <NewOvc  modal={modal} toggle={toggle} householdId={props.houseHoldId} reload={fetchMembers}/>
            <NewCareGiver  modal={modal2} toggle={toggle2} householdId={props.houseHoldId} reload={fetchMembers}/>
            
            </>
    );
}

const MemberCard = (props) => {
    const [modal3, setModal3] = useState(false);
    const [memberId, setMemberId] = useState(null);
    const toggle3 = () => setModal3(!modal3);
    //Provide Service To Household Member Action Button to Load the Modal Form 
    const provideServiceModal = (memberId) =>{
        setMemberId(memberId)
        setModal3(!modal3)
    } 
    
    
    return (
        <>
        <CCard>
            <CCardBody className={'text-center'}>
                <p className={'text-left'}><b>{props.member.householdMemberType===1?"Caregiver": "VC" || ''}</b></p>
                <AccountCircleIcon fontSize="large" style={{padding:'5px'}}/><br/>
                <Link color="inherit" to ={{
                    pathname: "/household-member/home", state: props.member.id
                }}  ><span>{props.member.details.firstName + " " + props.member.details.lastName }</span></Link><br/>
                <span>{props.member.details.sex && props.member.details.sex.display ? props.member.details.sex.display  : '' } | {calculateAge(props.member.details.dob)} </span>

            </CCardBody>
            <CCardFooter>
                <CButton color="primary" block onClick={() =>provideServiceModal(props.member.id)}>Provide Services</CButton>
            </CCardFooter>
        </CCard>
        <ProvideService  modal={modal3} toggle={toggle3} memberId={memberId} householdId={props.householdId}/>
    </>
    );
}

const mapStateToProps = state => {
    return {
        houseMemberList: state.houseHold.householdMembers
    };
};
const mapActionToProps = {
    fetchAllMember: fetchAllHouseHoldMembersByHouseholdId
};
  
export default connect(mapStateToProps, mapActionToProps)(HouseholdMember);
