import React, {useState} from "react";
import { CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Icon, Label} from 'semantic-ui-react'
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import ChildCareIcon from '@material-ui/icons/ChildCare';
import AccessibilityNewIcon from '@material-ui/icons/AccessibilityNew';
import ServiceHistoryPage from '../widgets/ServiceHistoryPage';
import LinearProgress from "@material-ui/core/LinearProgress";
import {fetchHouseHoldMemberById} from "../../../actions/houseHoldMember";
import {connect} from "react-redux";
import {calculateAge} from "./../../../utils/calculateAge";
import axios from "axios";
import {url} from "../../../api";
import {toast} from "react-toastify";
import * as CODES from "../../../api/codes";
import {last} from "rxjs/operators";


const Dashboard = (props) => {

    return (
        <>
            <TopDashboardStats/>
            < MidDashboardStats member={props.member} household={props.household} fetchingHousehold={props.fetchingHousehold} />
                <CRow>
                <CCol xs="12" >
                <RecentServiceOffered memberId={props.member.id}/>
                </CCol>
                <CCol xs="12" >
                <ServiceHistoryPage memberId={props.member.id}/>
                </CCol>
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

const MidDashboardStats = (props) => {
    const [fetchingMember, setFetchingMember] = useState(true);
    const [caregiver, setCaregiver] = useState(null);
    const caregiverId = props.member && props.member.details && props.member.details.caregiver ? props.member.details.caregiver : null;
    React.useEffect(() => {
        fetchCaregiver();
    }, [caregiverId]);

    const fetchCaregiver = () => {
        axios
            .get(`${url}household-members/${caregiverId}`)
            .then(response => {
                setFetchingMember(false);
                setCaregiver(response.data);
            })
            .catch(error => {
                    setFetchingMember(false);
                    setCaregiver(null);
                }

            );
    }
    return (
        <>
            {props.member.householdMemberType && props.member.householdMemberType === 1 &&
            <CRow>
                <CCol xs="12" sm="12" lg="12">
                    <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                        <CCardHeader> <Icon name='home'/> Household Information</CCardHeader>
                        <CCardBody>
                            {props.fetchingHousehold &&
                            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                            }
                            <span>Household ID: <small>{props.household && props.household.details ? props.household.details.uniqueId : 'Nil'} </small></span><br/>
                            <span>Address: <small> {props.household && props.household.details ? props.household.details.street : 'Nil'}</small></span><br/>
                            <span>Date Of Assessment: <small>{props.household && props.household.details ? props.household.details.assessmentDate : 'Nil'}</small> </span><br/>
                            <span>Primary Caregiver Name: <small>{props.household && props.household.details && props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName : 'Nil'}  </small></span><br/>
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>

            }
            {props.member.householdMemberType && props.member.householdMemberType !== 1 &&
            <CRow>
                <CCol xs="12" sm="6" lg="6">
                    <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                        <CCardHeader> <Icon name='home'/> Household Information</CCardHeader>
                        <CCardBody>
                            {props.fetchingHousehold &&
                            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                            }
                            <span>Household ID: <small>{props.household && props.household.details ? props.household.details.uniqueId : 'Nil'} </small></span><br/>
                            <span>Address: <small> {props.household && props.household.details ? props.household.details.street : 'Nil'}</small></span><br/>
                            <span>Date Of Assessment: <small>{props.household && props.household.details ? props.household.details.assessmentDate : 'Nil'}</small> </span><br/>
                            <span>Primary Caregiver Name: <small>{props.household && props.household.details && props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName : 'Nil'}  </small></span><br/>
                        </CCardBody>
                    </CCard>
                </CCol>
                <CCol xs="12" sm="6" lg="6">
                    <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                        <CCardHeader> <Icon name='user'/> Caregiver Information</CCardHeader>
                        <CCardBody>
                            {fetchingMember &&
                            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                            }
                            <span>Caregiver Name: <small>{caregiver && caregiver.details ? caregiver.details.firstName + ' ' + caregiver.details.lastName : 'Nil'}</small></span><br/>
                            <span>Age: <small>{caregiver && caregiver.details && caregiver.details.dob ? calculateAge(caregiver.details.dob) : 'Nil'}</small></span><br/>
                            <span>Sex: <small>{caregiver && caregiver.details && caregiver.details.sex ? caregiver.details.sex.display : 'Nil'}</small> </span><br/>
                            <span>Phone Number: <small>{caregiver && caregiver.details ? caregiver.details.mobilePhoneNumber : 'Nil'} </small></span><br/>
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
            }
        </>
    )
}

const RecentServiceOffered = (props) => {
    const [loading, setLoading] = useState(false);
    const [lastService, setLast] = useState([]);
    const [serviceDate, setServiceDate] = useState();

    React.useEffect(() => {
        fetchServices();
    }, [props.memberId]);

    const fetchServices = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
           // toast.error('Error: Could not fetch recent service!');
        }
        axios
            .get(`${url}household-members/${props.memberId}/${CODES.Caregiver_Household_Service}/encounters`)
            .then(response => {
                if(response.data.length > 0){
                    const formData = response.data[0].formData;
                    const services = formData[0].data;
                    console.log(services);
                    setServiceDate(services.serviceDate);
                    setLast(services.serviceOffered);
                } else {
                    setLast([]);
                }
                if(onSuccess){
                    onSuccess();
                }
            })
            .catch(error => {
                    if(onError){
                        onError();
                    }
                }

            );
    }
    return (
        <>
                    <CCard>
                        <CCardHeader>Recent Service Offered
                            <div className="card-header-actions">
                                {serviceDate || ''}
                            </div>
                        </CCardHeader>
                        <CCardBody>
                            {loading ?
                                <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                                :
                                <Label.Group color='blue'>
                                    {lastService && lastService.length > 0 ? lastService.map(x =>
                                            <Label key={x.id}>{x.name}</Label>
                                        ) :
                                        <Label>No Service has been offered</Label>
                                    }

                                </Label.Group>
                            }
                        </CCardBody>
                    </CCard>
            </>
    );
}
const mapStateToProps = (state) => {
    return {
        caregiver: state.houseHoldMember.member,
    };
};

const mapActionToProps = {
    fetchHouseHoldMemberById: fetchHouseHoldMemberById
};

export default connect(mapStateToProps, mapActionToProps)(Dashboard);